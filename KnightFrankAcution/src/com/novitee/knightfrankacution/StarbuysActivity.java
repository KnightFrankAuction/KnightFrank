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

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SimpleDateFormat")
public class StarbuysActivity extends AdvertisementsActivity {
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	ListView list_starbuys_date;
	ImageView left;
	ImageView right;
	TextView venue;
	TextView starbuys_Month;
	ProgressBar progress;
	
	FragmentTransaction fragmentTran;
	ArrayAdapter<String> adapter;
	
	//starbuys list
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
		setContentView(R.layout.activity_starbuys);
		
		showAD();
		setTitleBarAndFooter();
		
		list_starbuys_date = (ListView) findViewById(R.id.starbuys_date);
		left = (ImageView) findViewById(R.id.starbuys_left);
		right = (ImageView) findViewById(R.id.starbuys_right);
		starbuys_Month = (TextView) findViewById(R.id.starbuys_month);
		venue = (TextView) findViewById(R.id.venue);
		progress = (ProgressBar) findViewById(R.id.progress_date_list);
		
		stDate = new ArrayList<String>();
		stMonth = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(context, R.layout.layout_auction_date, stDate);
		
		listPro = new ArrayList<Property>();
		listImage = new ArrayList<String>();
		
		list_starbuys_date.setAdapter(adapter);
		
		//set Current month
		getCurrentMonth();
		
		if(connectionManager.isConnected()) {
			new GetStarbuysDate().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
		
		list_starbuys_date.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				date = stDate.get(position);
				Intent intent = new Intent(context, StarbuysListActivity.class);
				intent.putExtra("StarbuysDate", date);
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
					starbuys_Month.setText(currentMonth);
					new GetStarbuysDate().execute();
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
					starbuys_Month.setText(currentMonth);
					new GetStarbuysDate().execute();
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
		titleText.setText("Starbuys");
		
		int user_type = Preferences.getInstance(context).getUserType();
		if(user_type == 2) {
			titleImage.setBackgroundResource(R.drawable.document);
		}
		else {
			titleImage.setVisibility(View.GONE);
		}
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.starbuys_footer, new FooterFragment());
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
		starbuys_Month.setText(currentMonth);
		
		for(int i = -3; i < 4; i++) {
			myCalendar = Calendar.getInstance();
			myCalendar.add(Calendar.MONTH, i);
			stMonth.add(month_date.format(myCalendar.getTime()) +" "+ year_date.format(myCalendar.getTime()));
		}

	}//getCurrentMonth
	
	public class GetStarbuysDate extends AsyncTask<Void, Void, Void> {
		
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
				jObj = api.getStarbuysDate(Preferences.getInstance(context).getSessionToken(), currentMonth);
				
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
		
	}//GetStarbuysDate
	
}	
