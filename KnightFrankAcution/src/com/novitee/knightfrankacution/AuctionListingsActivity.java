package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.model.Property;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AuctionListingsActivity extends AdvertisementsActivity {
	
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	ListView list_auction_date;
	ImageView left;
	ImageView right;
	TextView auctionMonth;
	ProgressBar progress;
	
	FragmentTransaction fragmentTran;
	ArrayAdapter<String> adapter;
	
	//auction list
	ArrayList<Property> listPro;
	ArrayList<String> listImage;
	String date;
	
//	String[] stDate = {"13 Nov 2015, Friday", "21 Nov 2015, Saturday", "21 Nov 2015, Monday", "24 Nov 2015, Tuesday"};
	List<String> stDate;
	String[] stMonth = {"September 2015", "October 2015", "November 2015", "December 2015", "January 2016", "February 2016"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auction_listings);
		
		showAD();
		setTitleBarAndFooter();
		
		list_auction_date = (ListView) findViewById(R.id.auction_date);
		left = (ImageView) findViewById(R.id.auction_left);
		right = (ImageView) findViewById(R.id.auction_right);
		auctionMonth = (TextView) findViewById(R.id.auctiion_month);
		progress = (ProgressBar) findViewById(R.id.progress_date_list);
		
		stDate = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(context, R.layout.layout_auction_date, stDate);
		
		listPro = new ArrayList<Property>();
		listImage = new ArrayList<String>();
		
		list_auction_date.setAdapter(adapter);
		
		if(connectionManager.isConnected()) {
			new GetAuctionDate().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
		
		list_auction_date.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				date = stDate.get(position);
//				date = "August 2015";
				new GetAutionList().execute();
			}
		});
	}
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleText.setText("Auction Listing");
		titleImage.setBackgroundResource(R.drawable.notification_icon);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.auction_listing_footer, new FooterFragment());
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	public class GetAuctionDate extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getAuctionDate(pref.getSessionToken(context), "August 2015");
				
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
			progress.setVisibility(View.GONE);
			
			try {
				int json_responseCode = jObj.getInt("statusCode");
				int json_status = jObj.getInt("status");
				
				if(json_status == 1 && json_responseCode == 200){
					JSONArray data = jObj.getJSONArray("data");

					stDate.clear();
					for (int i = 0; i < data.length(); i++) {
						JSONObject json = new JSONObject();
						json = data.getJSONObject(i);
						stDate.add(json.getString("date"));
					}
					
					adapter = new ArrayAdapter<String>(context, R.layout.layout_auction_date, stDate);
					list_auction_date.setAdapter(adapter);
				}
				else if(json_status == 2 && json_responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}//GetAuctionDate
	
	public class GetAutionList extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AuctionListingsActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getAuctionList(pref.getSessionToken(context), date);
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
					Property property;
					JSONArray jArray = jObj.getJSONArray("property");
					
					listPro.clear();
					Photo photo;
					List<Photo> list = new ArrayList<Photo>();
					
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json = jArray.getJSONObject(i);
						property = new Property(json);
						listPro.add(property);

						list = property.getPhoto();
						if(list.size() > 0) {
							photo = list.get(0);
						}
						else {
							photo = new Photo("");
						}
						
						listImage.add(photo.getName());
					}

					Intent intent = new Intent(context, PropertyListActivity.class);
//					intent.putExtra("auctionDate", date);
					intent.putExtra("pList", listPro);
					intent.putExtra("imageList", listImage);
					startActivity(intent);
					
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
	}
}	
