package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.adapter.ExpandableListAdapter;
import com.novitee.knightfrankacution.model.Property;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class PrivateListingsActivity extends AdvertisementsActivity {
	
	Context context = this;
	
	FragmentTransaction fragmentTran;
	
	ExpandableListView expandableList;
	
	ArrayList<String> categoryList, childList;
	HashMap<String, ArrayList<String>> subCategoryList;
	ExpandableListAdapter adapter;
	ArrayList<ArrayList<String>> childHolderList;
	String stChild; //to get clicked child value
	
	//for search
	ArrayList<Property> listPro;
	ArrayList<String> listImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_private_listings);
		
		setTitleBarAndFooter();
		showAD();
		
		expandableList = (ExpandableListView) findViewById(R.id.private_expandable_list);
		
		categoryList = new ArrayList<String>();
		subCategoryList = new HashMap<String, ArrayList<String>>();
		childList = new ArrayList<String>();
		childHolderList = new ArrayList<ArrayList<String>>();
		
		listPro = new ArrayList<Property>();
		listImage = new ArrayList<String>();
		
		if (connectionManager.isConnected()) {
			new GetPrivateListing().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
		}
		
		expandableList.setOnChildClickListener(new OnChildClickListener() {
			 
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				stChild = childHolderList.get(groupPosition).get(childPosition);
				if(stChild.equals("Condo/Appartment")) {
					stChild = "Condo";
				}
				
				ArrayList<String> searchList = new ArrayList<String>();
				for (int i = 0; i < 9; i++) {
					if(i == 0) {
						searchList.add("");
					}
					else if(i == 2) {
						searchList.add(stChild);
					}
					else {
						searchList.add("Any");
					}
				}
				
				Intent i = new Intent(context, PropertyListActivity.class);
				i.putExtra("Title", stChild);
				i.putExtra("SearchList", searchList);
				startActivity(i);

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
		TextView titleText = (TextView) findViewById(R.id.title_text);
		TextView titleText2 = (TextView) findViewById(R.id.title_text2);
		ImageView titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleText.setText("Private Treaty Listing");
		titleImage.setVisibility(View.GONE);

		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.private_listing_footer, new FooterFragment());
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
	
	private class GetPrivateListing extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PrivateListingsActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getPrivateListing(pref.getSessionToken());
				
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
					JSONObject json;
					JSONArray jsonArray;
					
					for (int i = 0; i < jArray.length(); i++) {
						json = jArray.getJSONObject(i);
						
						//add value to header
						categoryList.add(json.getString("category"));
						
						//add value to child list
						jsonArray = json.getJSONArray("sub_category");
						childList = new ArrayList<String>();
						for (int j = 0; j < jsonArray.length(); j++) {
							childList.add((String) jsonArray.get(j));
						}
						
						//add value to holder list
						childHolderList.add(childList);
						
						//add list to expandable adapter
						subCategoryList.put(categoryList.get(i), childHolderList.get(i));
					}
					
					adapter = new ExpandableListAdapter(context, categoryList, subCategoryList);
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
	}//GetPrivateListing

}
