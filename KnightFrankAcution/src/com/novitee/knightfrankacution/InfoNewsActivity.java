package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.adapter.ExpandableListAdapter;
import com.novitee.knightfrankacution.util.CommonConstants;
import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class InfoNewsActivity extends AdvertisementsActivity implements OnClickListener {
	
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	FragmentTransaction fragmentTran;
	
	TextView news;
	ExpandableListView expandableList;
	ImageView infoImage, backImage;
	RelativeLayout layoutImage;
	
	ArrayList<String> categoryList, childList, imageList;
	ArrayList<ArrayList<String>> childHolderList;
	HashMap<String, ArrayList<String>> titleList;
	ExpandableListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_news);

		setTitleBarAndFooter();
		showAD();
		
		news = (TextView) findViewById(R.id.news);
		expandableList = (ExpandableListView) findViewById(R.id.info_expandable_list);
		infoImage = (ImageView) findViewById(R.id.info_image);
		backImage = (ImageView) findViewById(R.id.info_image_back);
		layoutImage = (RelativeLayout) findViewById(R.id.info_image_layout);
		
		categoryList = new ArrayList<String>();
		childList = new ArrayList<String>();
		imageList = new ArrayList<String>();
		childHolderList = new ArrayList<ArrayList<String>>();
		titleList = new HashMap<String, ArrayList<String>>();
		
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
		
		backImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layoutImage.setVisibility(View.GONE);
			}
		});
		
		expandableList.setOnChildClickListener(new OnChildClickListener() {
			 
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			
				layoutImage.setVisibility(View.VISIBLE);
				Picasso.with(context).load(imageList.get(groupPosition)).into(infoImage);
				
				return false;
			}
		});
		
		expandableList.setOnGroupClickListener(new OnGroupClickListener() {
			 
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
			// TODO: Do your stuff
//				Toast.makeText(getApplicationContext(), "Group is Clicked", Toast.LENGTH_LONG).show();
				return false;
			}
		});
			
		expandableList.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			 
			@Override
			public void onGroupCollapse(int groupPosition) {
			// TODO: Do your stuff
//				Toast.makeText(getApplicationContext(), "Child is Collapsed", Toast.LENGTH_LONG).show();
			}
		});
			 
		expandableList.setOnGroupExpandListener(new OnGroupExpandListener() {
			 
			@Override
			public void onGroupExpand(int groupPosition) {
			// TODO: Do your stuff
//				Toast.makeText(getApplicationContext(), "Child is Expanded", Toast.LENGTH_LONG).show();
			}
		});

		
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
		/*Uri uri = null;
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
		startActivity(launchBrowser);*/
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
				jObj = api.getInfo(pref.getSessionToken());
				
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
					JSONArray jArray = jObj.getJSONArray("data");
					
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json = jArray.getJSONObject(i);
						categoryList.add(json.getString("info_layer1"));
						childList = new ArrayList<String>();
						if(json.getString("info_layer2").length() > 0) {
							childList.add(json.getString("info_layer2"));
						}
						imageList.add(CommonConstants.HOST + json.getString("info_layer3"));
						
						childHolderList.add(childList);
						titleList.put(categoryList.get(i), childHolderList.get(i));
					}
					
					adapter = new ExpandableListAdapter(context, categoryList, titleList);
					expandableList.setAdapter(adapter);

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
