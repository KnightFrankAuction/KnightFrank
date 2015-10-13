package com.novitee.knightfrankacution;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.model.Property;
import com.novitee.knightfrankacution.util.Preferences;

import android.annotation.SuppressLint;
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

@SuppressLint("SimpleDateFormat")
public class AuctionListingsActivity extends AdvertisementsActivity {
	
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	ListView list_auction_date;
	ImageView left;
	ImageView right;
	TextView venue;
	TextView auctionMonth;
	ProgressBar progress;
	
	FragmentTransaction fragmentTran;
	ArrayAdapter<String> adapter;
	
	//auction list
	ArrayList<Property> listPro;
	ArrayList<String> listImage;
	String date;
	
	//for current date
	Calendar myCalendar = Calendar.getInstance();
	SimpleDateFormat month_date = new SimpleDateFormat("MMMM");//("MMM") for short month name
	SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
	String currentMonth;
	int month_position = 3;
	
	List<String> stDate;
	List<String> stMonth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auction_listings);
		
		showAD();
		setTitleBarAndFooter();
		
		list_auction_date = (ListView) findViewById(R.id.auction_date);
		left = (ImageView) findViewById(R.id.auction_left);
		right = (ImageView) findViewById(R.id.auction_right);
		auctionMonth = (TextView) findViewById(R.id.auction_month);
		venue = (TextView) findViewById(R.id.venue);
		progress = (ProgressBar) findViewById(R.id.progress_date_list);
		
		stDate = new ArrayList<String>();
		stMonth = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(context, R.layout.layout_auction_date, stDate);
		
		listPro = new ArrayList<Property>();
		listImage = new ArrayList<String>();
		
		list_auction_date.setAdapter(adapter);
		
		//set Current month
		getCurrentMonth();
		
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
				Intent intent = new Intent(context, AuctionPropertyListActivity.class);
				intent.putExtra("AuctionDate", date);
				startActivity(intent);
			}
		});
		
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(month_position > 0) {
					month_position = month_position -1;
					currentMonth = stMonth.get(month_position);
					auctionMonth.setText(currentMonth);
					new GetAuctionDate().execute();
				}
			}
		});
		
		right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(month_position < 6) {
					month_position = month_position + 1;
					currentMonth = stMonth.get(month_position);
					auctionMonth.setText(currentMonth);
					new GetAuctionDate().execute();
				}
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
		
		int user_type = Preferences.getInstance(context).getUserType();
		if(user_type == 2) {
			titleImage.setBackgroundResource(R.drawable.document);
		}
		else {
			titleImage.setVisibility(View.GONE);
		}
		
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
	
	private void getCurrentMonth() {
		// TODO Auto-generated method stub
		currentMonth = month_date.format(myCalendar.getTime()) +" "+ year_date.format(myCalendar.getTime());
		auctionMonth.setText(currentMonth);
		
		for(int i = -3; i < 4; i++) {
			myCalendar = Calendar.getInstance();
			myCalendar.add(Calendar.MONTH, i);
			stMonth.add(month_date.format(myCalendar.getTime()) +" "+ year_date.format(myCalendar.getTime()));
		}

	}//getCurrentMonth
	
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
				jObj = api.getAuctionDate(Preferences.getInstance(context).getSessionToken(), currentMonth);
				
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
					String stVenue = jObj.getString("venue");
					venue.setText(stVenue);
					
					JSONArray data = jObj.getJSONArray("data");

					stDate.clear();
					for (int i = 0; i < data.length(); i++) {
						JSONObject json = new JSONObject();
						json = data.getJSONObject(i);
						stDate.add(json.getString("date"));
					}
					
					adapter.notifyDataSetChanged();
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
	
}	
