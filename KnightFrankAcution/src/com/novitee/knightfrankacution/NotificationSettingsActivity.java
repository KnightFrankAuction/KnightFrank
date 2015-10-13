package com.novitee.knightfrankacution;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class NotificationSettingsActivity extends BaseFragmentActivity {
	
	Context context = this;
	
	FragmentTransaction fragmentTran;
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	ToggleButton mute_noti, new_listing, update_shortlist, new_starbuys, new_news, auction_remainder, new_auctions;
	
	String muteNoti, newListing, updateShortlist, newStarbuys, newNews, auctionRemainder, newAuctions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_settings);
		
		setTitleBarAndFooter();
		
		mute_noti = (ToggleButton) findViewById(R.id.toggle_mute_noti);
		new_listing = (ToggleButton) findViewById(R.id.toggle_new_listing);
		update_shortlist = (ToggleButton) findViewById(R.id.toggle_update_shortlist);
		new_starbuys = (ToggleButton) findViewById(R.id.toggle_new_starbuy);
		new_news = (ToggleButton) findViewById(R.id.toggle_new_news);
		auction_remainder = (ToggleButton) findViewById(R.id.toggle_auction_ramainder);
		new_auctions = (ToggleButton) findViewById(R.id.toggle_new_auctions);
		
		if(connectionManager.isConnected()) {
			new GetNotification().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
		
		titleText2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mute_noti.isChecked()) {
					muteNoti = "1";
				}
				else {
					muteNoti = "0";
				}
				
				if(new_listing.isChecked()) {
					newListing = "1";
				}
				else {
					newListing = "0";
				}
				
				if(update_shortlist.isChecked()) {
					updateShortlist = "1";
				}
				else {
					updateShortlist = "0";
				}
				
				if(new_starbuys.isChecked()) {
					newStarbuys = "1";
				}
				else {
					newStarbuys = "0";
				}
				
				if(new_news.isChecked()) {
					newNews = "1";
				}
				else {
					newNews = "0";
				}
				
				if(auction_remainder.isChecked()) {
					auctionRemainder = "1";
				}
				else {
					auctionRemainder = "0";
				}
				
				if(new_auctions.isChecked()) {
					newAuctions = "1";
				}
				else {
					newAuctions = "0";
				}
				
				if(connectionManager.isConnected()) {
					new UpdateNotification().execute();
				}
				else {
					Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		mute_noti.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked) {
					//On
				}
				else {
					//Off
				}
			}
		});
		
	}//onCreate

	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setText("Apply");
		titleText.setText("Notification Settings");
		titleImage.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.noti_setting_footer, new FooterFragment());
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
	
	private class GetNotification extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getNotification(pref.getSessionToken());
				
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
				int responseCode = jObj.getInt("statusCode");
				int status = jObj.getInt("status");				
				
				if(status == 1 && responseCode == 200){
					muteNoti = jObj.getString("mute_notification");
					newListing = jObj.getString("new_listing");
					updateShortlist = jObj.getString("updates_on_shortlist");
					newStarbuys = jObj.getString("new_star_buy");
					newNews = jObj.getString("new_news");
					auctionRemainder = jObj.getString("auction_remainder");
					newAuctions = jObj.getString("new_auctions");
					
					if(muteNoti.equals("1")) {
						mute_noti.setChecked(true);
					}
					if(newListing.equals("1")) {
						new_listing.setChecked(true);
					}
					if(updateShortlist.equals("1")) {
						update_shortlist.setChecked(true);
					}
					if(newStarbuys.equals("1")) {
						new_starbuys.setChecked(true);
					}
					if(newNews.equals("1")) {
						new_news.setChecked(true);
					}
					if(auctionRemainder.equals("1")) {
						auction_remainder.setChecked(true);
					}
					if(newAuctions.equals("1")) {
						new_auctions.setChecked(true);
					}
					
				}
				else if(status == 2 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else if(status == 3 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
	}//GetNotification
	
	private class UpdateNotification extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.updateNotification(pref.getSessionToken(), muteNoti, newListing, updateShortlist, newStarbuys, newNews, auctionRemainder, newAuctions);
				
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
				int responseCode = jObj.getInt("statusCode");
				int status = jObj.getInt("status");				
				
				if(status == 1 && responseCode == 200){
					Toast.makeText(context, "Saved Notification Setting", Toast.LENGTH_SHORT).show();
					
				}
				else if(status == 2 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else if(status == 3 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}//UpdateNotification
	
}
