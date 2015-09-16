package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.model.Property;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends BaseFragmentActivity {
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	FragmentTransaction fragmentTran;
	
	EditText buildingName;
	TextView listed_date;
	Spinner spListing, spBuilding, spTypeOfSale, spBedroom, spBathroom, spMinSize, spMaxPrice;
	
	String[] stBuildingType, stTypeOfSale;
	String[] stListingType = {"Any", "Auction Listings", "Private Listings", "Star Buys", "Project Listing"};
	String[] stBedBath = {"Any", "1", "2", "3", "4", "5"};
	String[] stMinSize = {"Any", "500 sqft", "750 sqft", "1000 sqft", "1200 sqft", "1500 sqft", "2000 sqft", "2500 sqft", "3000 sqft", "4000 sqft", "5000 sqft", "7500 sqft", "10000 sqft"};
	String[] stMaxPrice = {"Any","200k","300k","400k","500k","600k","700k","800k","900k","1000k","1250k","1500k","2000k","2500k","3000k","4000k","5000k","6000k","8000k","10000k","150000k","20000k","30000k","50000k"};
	
	ArrayList<Property> listPro;
	ArrayList<String> listImage;
	String building_name, listing_type, building_type, type_of_sale, bedroom, bathroom, min_size, max_price, listed_on = ""; 

	//datePicker
	Calendar myCalendar = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener datePicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		setTitleBarAndFooter();
		
		buildingName = (EditText) findViewById(R.id.search_building_name);
		spListing = (Spinner) findViewById(R.id.listing_type);
		spBuilding = (Spinner) findViewById(R.id.building_type);
		spTypeOfSale = (Spinner) findViewById(R.id.type_of_sales);
		spBedroom = (Spinner) findViewById(R.id.bedroom);
		spBathroom = (Spinner) findViewById(R.id.bathroom);
		spMinSize = (Spinner) findViewById(R.id.min_size);
		spMaxPrice = (Spinner) findViewById(R.id.max_price);
		listed_date = (TextView) findViewById(R.id.listed_on);
		
		listPro = new ArrayList<Property>();
		listImage = new ArrayList<String>();
		
		if(connectionManager.isConnected()) {
			new GetSpinnerData().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
		
		titleText2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				building_name = buildingName.getText().toString();
				listing_type = spListing.getSelectedItem().toString();
				building_type = spBuilding.getSelectedItem().toString();
				type_of_sale = spTypeOfSale.getSelectedItem().toString();
				bedroom = spBedroom.getSelectedItem().toString();
				bathroom = spBathroom.getSelectedItem().toString();
				min_size = spMinSize.getSelectedItem().toString();
				max_price = spMaxPrice.getSelectedItem().toString();
				listed_on = listed_date.getText().toString();
				
				if(connectionManager.isConnected()) {
					new Search().execute();
				}
				else {
					Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
				}
			}
		});//titleText2.setOnClickListener
		
		listed_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DatePickerDialog dateDialog = new DatePickerDialog(SearchActivity.this,
						datePicker, 
						myCalendar.get(Calendar.YEAR), 
						myCalendar.get(Calendar.MONTH), 
						myCalendar.get(Calendar.DAY_OF_MONTH));
				
				dateDialog.show();
			}
		});
		
		//Date Listener
		datePicker = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				/*String YEAR = String.valueOf(year);
				String MONTH = String.valueOf(monthOfYear + 1);
				String DAY = String.valueOf(dayOfMonth + 1);*/
				
				Date date = myCalendar.getTime();
				String calDate = java.text.DateFormat.getDateInstance().format(date);

				listed_date.setText(calDate);
			}
			
		};

	}

	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		titleText.setText("Property Search");
		titleText2.setText("Apply");
		titleImage.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.search_footer, new FooterFragment());
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
	
	private class Search extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.Search(pref.getSessionToken(), building_name, listing_type, building_type, type_of_sale, bedroom, bathroom, min_size, max_price, listed_on);
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

						/*list = property.getPhoto();
						photo = list.get(0);
						listImage.add(photo.getName());*/
						if(property.getPhoto().size() > 0) {
							list = property.getPhoto();
							photo = list.get(0);
							listImage.add(photo.getName());
						}
						else {
//							photoList.add(new Photo(""));
							listImage.add("");
						}
					}

					Intent intent = new Intent(context, PropertyListActivity.class);
					intent.putExtra("pList", listPro);
					intent.putExtra("imageList", listImage);
//					intent.putExtra("title", rdString);
					startActivity(intent);
					
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
		}//onPostExecute
	}//GetSpinnerData
	
	private class GetSpinnerData extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getSearchData(pref.getSessionToken());
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
					JSONArray arrBuliding = jObj.getJSONArray("building_type");
					JSONArray arrAuction = jObj.getJSONArray("type_of_sale");
					
					stBuildingType = new String[arrBuliding.length()];
					for (int i = 0; i < arrBuliding.length(); i++) {
						stBuildingType[i] = (String) arrBuliding.get(i);
					}
					
					stTypeOfSale = new String[arrAuction.length()];
					for (int j = 0; j < arrAuction.length(); j++) {
						stTypeOfSale[j] = (String) arrAuction.get(j);
					}
					
					setSpinner();
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
		}//onPostExecute
	}//GetSpinnerData
	
	private void setSpinner() {
		ArrayAdapter<String> adapter;
		
		adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, stListingType);
		spListing.setAdapter(adapter);
		
		adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, stBuildingType);
		spBuilding.setAdapter(adapter);
		
		adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, stTypeOfSale);
		spTypeOfSale.setAdapter(adapter);
		
		adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, stBedBath);
		spBedroom.setAdapter(adapter);
		
		adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, stBedBath);
		spBathroom.setAdapter(adapter);
		
		adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, stMinSize);
		spMinSize.setAdapter(adapter);
		
		adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, stMaxPrice);
		spMaxPrice.setAdapter(adapter);
		
	}//setSpinner
}
