package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.util.Preferences;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoNewsActivity extends BaseFragmentActivity implements OnClickListener {
	
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	FragmentTransaction fragmentTran;
	
	TextView news;
	TextView what_is_auction;
	TextView why_auction;
	TextView buyer_guide;
	TextView seller_guide;
	
	List<String> infoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_news);
		
		setTitleBarAndFooter();
		
		news = (TextView) findViewById(R.id.news);
		what_is_auction = (TextView) findViewById(R.id.what_is_auction);
		why_auction = (TextView) findViewById(R.id.why_auction);
		buyer_guide = (TextView) findViewById(R.id.buyer_guide);
		seller_guide = (TextView) findViewById(R.id.seller_guide);		
		
		infoList = new ArrayList<String>();
		
		if (connectionManager.isConnected()) {
			new GetInfo().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
		}
		
		news.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, NewsActivity.class);
				startActivity(intent);
			}
		});
		
		what_is_auction.setOnClickListener(this);
		why_auction.setOnClickListener(this);
		buyer_guide.setOnClickListener(this);
		seller_guide.setOnClickListener(this);
		
	}//onCreate
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleImage.setVisibility(View.GONE);
		titleText.setText("KnightFrank News / Info");
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.news_footer, new FooterFragment());
		fragmentTran.commit();
		
		ImageView titleBack = (ImageView) findViewById(R.id.title_back);
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}//setTitleBarAndFooter

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Uri uri = null;
		if(v.getId() == what_is_auction.getId()) {
			uri = Uri.parse(infoList.get(0));
		}
		else if(v.getId() == why_auction.getId()) {
			uri = Uri.parse(infoList.get(1));
		}
		else if(v.getId() == buyer_guide.getId()) {
			uri = Uri.parse(infoList.get(2));	
		}
		else {
			uri = Uri.parse(infoList.get(3));
		}
		
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(launchBrowser);
	}
	
	private class GetInfo extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(InfoNewsActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getInfo(Preferences.getInstance(context).getSessionToken());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(pDialog.isShowing()){
				pDialog.dismiss();
			}
			
			try {
				int json_responseCode = jObj.getInt("statusCode");
				int json_status = jObj.getInt("status");
				
				if(json_status == 1 && json_responseCode == 200){
					infoList.clear();
					
					infoList.add(jObj.getString("WhatIsAuction"));
					infoList.add(jObj.getString("WhyAuction"));
					infoList.add(jObj.getString("BuyerGuide"));
					infoList.add(jObj.getString("SellerGuide"));
					
				}
				else if(json_status == 2 && json_responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}//GetInfo
	
}
