package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.model.Property;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class MenuActivity extends AdvertisementsActivity implements OnClickListener {
	
	Context context = this;
	
	LinearLayout auction_listings;
	LinearLayout private_listings;
	LinearLayout project_listings;
	LinearLayout starbuys;
	LinearLayout info_news;
	LinearLayout tools;
	ImageView document;
	
	ArrayList<Property> listPro;
	ArrayList<String> listImage;
	
	String type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		//download advertisements image
		new downloadAd().execute();
		
		auction_listings = (LinearLayout) findViewById(R.id.auction_listings);
		private_listings = (LinearLayout) findViewById(R.id.private_listings);
		project_listings = (LinearLayout) findViewById(R.id.project_listings);
		starbuys = (LinearLayout) findViewById(R.id.starbuys);
		info_news = (LinearLayout) findViewById(R.id.info_and_news);
		tools = (LinearLayout) findViewById(R.id.tools);
		document = (ImageView) findViewById(R.id.document);
		
		listPro = new ArrayList<Property>();
		listImage = new ArrayList<String>();
		
		auction_listings.setOnClickListener(this);
		private_listings.setOnClickListener(this);
		project_listings.setOnClickListener(this);
		starbuys.setOnClickListener(this);
		info_news.setOnClickListener(this);
		tools.setOnClickListener(this);
		document.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId() == auction_listings.getId()) {
			Intent intent = new Intent();
			intent = new Intent(context, AuctionListingsActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == private_listings.getId()) {
			Intent intent = new Intent();
			intent = new Intent(context, PrivateListingsActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == project_listings.getId()) {
			type = "Project Listings";
			new GetAllList().execute();
		}
		else if(v.getId() == starbuys.getId()) {
			type = "Starbuys";
			new GetAllList().execute();
		}
		else if(v.getId() == info_news.getId()) {
			Intent intent = new Intent();
			intent = new Intent(context, InfoNewsActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == tools.getId()) {
			Intent intent = new Intent();
			intent = new Intent(context, ToolsActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == document.getId()) {
			Intent intent = new Intent();
			intent = new Intent(context, DocumentsActivity.class);
			startActivity(intent);
		}
		
	}//onClick
	
	public class GetAllList extends AsyncTask<Void, Void, Void> {

		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MenuActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getAllList(pref.getSessionToken(context), type);
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
					intent.putExtra("title", type);
					intent.putExtra("pList", listPro);
					intent.putExtra("imageList", listImage);
					startActivity(intent);
					
				}
				else if(json_status == 2 && json_responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//onPostExecute
		
	}//GetAllStarbuy
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		moveTaskToBack(true);
	}

}
