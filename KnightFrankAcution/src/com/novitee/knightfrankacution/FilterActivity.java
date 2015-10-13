package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.model.Property;
import com.novitee.knightfrankacution.util.Preferences;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FilterActivity extends BaseFragmentActivity {
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	FragmentTransaction fragmentTran;
	
	RadioButton rdAny;
	RadioButton rdBuilding;
	RadioButton rdCondo;
	RadioButton rdHDB;
	RadioButton rdLanded;
	RadioButton rdOffices;
	EditText maxSize;
	EditText minSize;
	EditText maxValue;
	EditText minValue;
	Spinner district;
	Spinner sqft;
	Spinner psf;

	String rdString;
	String min_size, max_size, min_value, max_value, district_value, sqft_sort, psf_sort;
	String[] stDistrict;
	String[] stSpinner = {"Low to High", "High to Low"};
	
	ArrayList<Property> listPro;
	ArrayList<String> listImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		
		rdAny = (RadioButton) findViewById(R.id.any);
		rdBuilding = (RadioButton) findViewById(R.id.Building);
		rdCondo = (RadioButton) findViewById(R.id.Condo);
		rdHDB = (RadioButton) findViewById(R.id.hdb);
		rdLanded = (RadioButton) findViewById(R.id.Landed);
		rdOffices = (RadioButton) findViewById(R.id.Offices);
		maxSize = (EditText) findViewById(R.id.edit_max_size);
		minSize = (EditText) findViewById(R.id.edit_min_size);
		maxValue = (EditText) findViewById(R.id.edit_max_value);
		minValue = (EditText) findViewById(R.id.edit_min_value);
		district = (Spinner) findViewById(R.id.spinner_district);
		sqft = (Spinner) findViewById(R.id.spinner_sqft);
		psf = (Spinner) findViewById(R.id.spinner_psf);
		
		listPro = new ArrayList<Property>();
		listImage = new ArrayList<String>();
		
		setTitleBarAndFooter();
		
		if(connectionManager.isConnected()) {
			new GetDistrict().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
		
		setSpinner();
		
		titleText2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				min_size = minSize.getText().toString();
				max_size = maxSize.getText().toString();
				min_value = minValue.getText().toString();
				max_value = maxValue.getText().toString();
				district_value = district.getSelectedItem().toString();
				sqft_sort = sqft.getSelectedItem().toString();
				psf_sort = psf.getSelectedItem().toString();
				
				List<String> list = new ArrayList<String>();
				
				if(rdAny.isChecked() == true) {
					list.add("Building,Condo,HDB,Landed,Offices");
				}
				else {
					if(rdBuilding.isChecked() == true) {
						list.add("Building");
					}
					if(rdCondo.isChecked() == true) {
						list.add("Condo");
					}
					if(rdHDB.isChecked() == true) {
						list.add("HDB");
					}
					if(rdLanded.isChecked() == true) {
						list.add("Landed");
					}
					if(rdOffices.isChecked() == true) {
						list.add("Offices");
					}
				}
				
				rdString = "";
				for (int i = 0; i < list.size(); i++) {
					if(i == list.size() - 1) {
						rdString = rdString + list.get(i);
					}
					else {
						rdString = rdString + list.get(i) + ",";
					}
				}
				
				if(connectionManager.isConnected()) {
					new Filter().execute();
				}
				else {
					Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
				}
			}
		});//titleText2.setOnClickListener
		
	}//onCreate
	
	public class GetDistrict extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FilterActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getDistrict(pref.getGenerateKey());
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
					stDistrict = new String[jArray.length()];
					
					for (int i = 0; i < jArray.length(); i++) {
						stDistrict[i] = (String) jArray.get(i);
					}
					
					setDistrictSpinner();
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
		}//onPostExecute
		
	}//GetDistrict
	
	public class Filter extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FilterActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.filter(rdString, district_value, min_size, max_size, min_value, max_value, sqft_sort, psf_sort, Preferences.getInstance(context).getSessionToken());
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
					JSONArray jArray = jObj.getJSONArray("data");
					
					listPro.clear();
					Photo photo;
					List<Photo> list = new ArrayList<Photo>();
					
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json = jArray.getJSONObject(i);
						property = new Property(json);
						listPro.add(property);

//						list = property.getPhoto();
//						photo = list.get(0);
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
					intent.putExtra("title", rdString);
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
		
	}//Filter

	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		titleText.setText("Filter");
		titleText2.setText("Apply");
		titleImage.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.filter_footer, new FooterFragment());
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
	
	private void setDistrictSpinner() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.kf_single_textview_layout, stDistrict);
		district.setAdapter(adapter);
	}
	
	private void setSpinner() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.kf_single_textview_layout, stSpinner);
		sqft.setAdapter(adapter);
		psf.setAdapter(adapter);
	}

}
