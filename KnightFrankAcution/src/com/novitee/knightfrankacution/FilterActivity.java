package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.model.Property;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class FilterActivity extends BaseFragmentActivity {
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	RadioButton rdAny;
	RadioButton rdBuilding;
	RadioButton rdCondo;
	RadioButton rdHDB;
	RadioButton rdLanded;
	RadioButton rdOffices;
	EditText maxSize;
	EditText minSize;
	
	FragmentTransaction fragmentTran;
	String[] rdString;
	String min, max;
	
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
		
		listPro = new ArrayList<Property>();
		listImage = new ArrayList<String>();
		
		setTitleBarAndFooter();
		
		titleText2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				min = minSize.getText().toString();
				max = maxSize.getText().toString();
				
				List<String> list = new ArrayList<String>();
				
				if(rdAny.isChecked() == true) {
					list.add("Any");
				}
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
				
				rdString = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					rdString[i] = list.get(i);
				}
				
				if(connectionManager.isConnected()) {
					new Filter().execute();
				}
				else {
					Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	
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
				jObj = api.filter(rdString, min, max, pref.getSessionToken(context));
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

						list = property.getPhoto();
						photo = list.get(0);
						listImage.add(photo.getName());
					}

					Intent intent = new Intent(context, PropertyListActivity.class);
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
		}//onPostExecute
	}

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
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
