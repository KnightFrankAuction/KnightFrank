package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.model.Property;
import com.novitee.knightfrankacution.util.CommonConstants;
import com.squareup.picasso.Picasso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PropertyListActivity extends BaseFragmentActivity {
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	FragmentTransaction fragmentTran;
	ArrayList<Property> propertyList;
	List<Photo> imageList;
	List<String> imageNameList;
	String title = null;
	
	ListView listProperty;
	Property shortlist_property;
	ImageView shortlist;
	int shortlist_position;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_list);
		
		listProperty = (ListView) findViewById(R.id.filter_list);
		
		imageNameList = (ArrayList<String>) getIntent().getStringArrayListExtra("imageList");
		propertyList = (ArrayList<Property>) getIntent().getSerializableExtra("pList");
		title = getIntent().getStringExtra("title");
		
		setTitleBarAndFooter();
		
		if(propertyList.size() > 0) {
			CustomList customList = new CustomList(context, R.layout.property_listview_layout, propertyList);
			listProperty.setAdapter(customList);
		}
		else  {
			Toast.makeText(context, "There is no property", Toast.LENGTH_LONG).show();
		}

		listProperty.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				Property p = propertyList.get(position);
				Intent intent = new Intent(context, PropertyDetailActivity.class);
				intent.putExtra("Property", p);
				startActivity(intent);
			}
		});
	}
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);

		if(title != null) {
			titleText.setText(title);
		}
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.filter_list_search, new SearchFragment());
		fragmentTran.commit();
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.filter_list_footer, new FooterFragment());
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

	private class CustomList extends ArrayAdapter<Property> {
		ArrayList<Property> propertyList;
		Context context;

		public CustomList(Context c, int resource, ArrayList<Property> propertyList) {
			super(c, R.layout.property_listview_layout, propertyList);
			this.propertyList = propertyList;
			this.context = c;
		}
		
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflator.inflate(R.layout.property_listview_layout, parent, false);
            
            ImageView propertyImage = (ImageView) row.findViewById(R.id.listview_image);
            RelativeLayout shortlistLayout = (RelativeLayout) row.findViewById(R.id.shortlist_layout);
            shortlist = (ImageView) row.findViewById(R.id.listview_shortlist);
            TextView buildingName = (TextView) row.findViewById(R.id.listview_building_name);
            TextView price = (TextView) row.findViewById(R.id.listview_price);
            TextView district = (TextView) row.findViewById(R.id.listview_district);
            TextView auctionType = (TextView) row.findViewById(R.id.listview_auction_type);
            TextView buildingType = (TextView) row.findViewById(R.id.listview_building_type);
            TextView floor_area = (TextView) row.findViewById(R.id.listview_floor_area);
            TextView bed_bath = (TextView) row.findViewById(R.id.listview_bed_bath);
            TextView tenure = (TextView) row.findViewById(R.id.listview_tenure);
            TextView psf = (TextView) row.findViewById(R.id.listview_psf);
            
            Property property = propertyList.get(position);
            buildingName.setText(property.getBuilding_name());
            price.setText(property.getPrice());
            district.setText(property.getDistrict());
            auctionType.setText(property.getAuction_type());
            buildingType.setText(property.getBuilding_type());
            floor_area.setText(property.getFloor_area() + " sqft");
            bed_bath.setText(property.getBedroom() + " bedroom, " + property.getBath() + " bathroom");
            tenure.setText(property.getTenure());
            psf.setText("$"+ property.getPsf() + " psft");

            String image_name = imageNameList.get(position);
            if(image_name.length() > 0) {
            	String url = CommonConstants.HOST + imageNameList.get(position);
                Picasso.with(context).load(url).into(propertyImage);
            }
            
            String shortlist_flag = property.getShortlist_flag();
            
            if(shortlist_flag.equals("1")) {
            	shortlist.setImageResource(R.drawable.shortlist_check);
    		}
            
            shortlistLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View parentRow = (View) v.getParent();
					LinearLayout linearRow = (LinearLayout) parentRow.getParent();
					ListView listView = (ListView) linearRow.getParent();
					shortlist_position = listView.getPositionForView(parentRow);
					shortlist_property = propertyList.get(shortlist_position);
					shortlist = (ImageView) linearRow.findViewById(R.id.listview_shortlist);
					
					if(shortlist_property.getShortlist_flag().equals("1")) {
						new deleteShortlist().execute();
					}
					else {
						new saveShortlist().execute();
					}
				}
			});

            return(row);
        }
	}//CustomList
	
	public class saveShortlist extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PropertyListActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.saveShortlist(pref.getSessionToken(), shortlist_property.getProperty_id());
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
					shortlist.setBackgroundResource(R.drawable.shortlist_check);
					shortlist_property.setShortlist_flag("1");
					
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
		}
		
	}//saveShortlist
	
public class deleteShortlist extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PropertyListActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.deleteShortlist(pref.getSessionToken(), propertyList.get(shortlist_position).getProperty_id());
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
					shortlist.setImageResource(R.drawable.shortlist_uncheck);
					shortlist_property.setShortlist_flag("0");
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
		}
		
	}//deleteShortlist

}
