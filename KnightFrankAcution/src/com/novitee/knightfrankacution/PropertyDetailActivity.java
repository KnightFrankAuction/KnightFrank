package com.novitee.knightfrankacution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.model.Property;
import com.novitee.knightfrankacution.util.CommonConstants;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class PropertyDetailActivity extends BaseFragmentActivity {
//	BaseSliderView
	Context context = this;

//	String pID = "1";
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	GoogleMap googleMap;
	FragmentTransaction fragmentTran;
	
	ImageView starbuy;
	ImageView shortlist;
//	TextView buildingName;
	TextView titlePrice;
	TextView titleBedroom;
	TextView titleBathroom;
	TextView titleSqft;
	TextView district;
	TextView auctionType;
	TextView buildingType;
	TextView bedroom;
	TextView bathroom;
	TextView floorArea;
	TextView sqft;
	TextView price;
	TextView tenure;
	TextView highlight;
	TextView contact;
	TextView ceaNo;
	TextView companyName;
	ImageView makeCall;
	ImageView sendEmail;
	SliderLayout detailSlider;
	GridLayout facilitiesLayout;
	
	String phoneNo;
	String email;
	String postal_code;
	String facilities;
	List<Photo> photoList;
	List<String> facList;
	
	Property property;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_detail);
		
		pDialog = new ProgressDialog(PropertyDetailActivity.this);
        pDialog.setMessage("Please wait....");
        pDialog.setCancelable(false);
        pDialog.show();
		
		setTitleBarAndFooter();
		
		starbuy = (ImageView) findViewById(R.id.property_starbuy);
		shortlist = (ImageView) findViewById(R.id.property_shortlist);
//		buildingName = (TextView) findViewById(R.id.building_name);
		titlePrice = (TextView) findViewById(R.id.txtPrice);
		titleBedroom = (TextView) findViewById(R.id.txtBedroom);
		titleBathroom = (TextView) findViewById(R.id.txtBathroom);
		titleSqft = (TextView) findViewById(R.id.txtSqft);
		district = (TextView) findViewById(R.id.district);
		auctionType = (TextView) findViewById(R.id.auction_type);
		buildingType = (TextView) findViewById(R.id.building_type);
		bedroom = (TextView) findViewById(R.id.bedroom);
		bathroom = (TextView) findViewById(R.id.bathroom);
		floorArea = (TextView) findViewById(R.id.floor_area);
		sqft = (TextView) findViewById(R.id.sqft);
		price = (TextView) findViewById(R.id.price);
		tenure = (TextView) findViewById(R.id.tenure);
		highlight = (TextView) findViewById(R.id.highlight);
		contact = (TextView) findViewById(R.id.contact);
		ceaNo = (TextView) findViewById(R.id.cea_no);
		companyName = (TextView) findViewById(R.id.company_name);
		makeCall = (ImageView) findViewById(R.id.detail_phone);
		sendEmail = (ImageView) findViewById(R.id.detail_email);
		facilitiesLayout = (GridLayout) findViewById(R.id.facilities);
		detailSlider = (SliderLayout) findViewById(R.id.detail_slider);
		
		titleImage.setBackgroundResource(R.drawable.share_circle);
		
		photoList = new ArrayList<Photo>();
		facList = new ArrayList<String>();
		
		property = (Property) getIntent().getSerializableExtra("Property");
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				new AsyncShowProperty().execute();
			}
		}, 1000);
		
		makeCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phoneNo.length() > 0) {
					try {
						Intent phoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo));
						startActivity(phoneIntent);
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
				}
				else {
					Toast.makeText(context, "Sorry. You can't call now.",  Toast.LENGTH_LONG).show();
				}
			}
		});//makeCall

		shortlist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new saveShortlist().execute();
			}
		});//shortlist
		
	}//onCreate
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		titleText2.setVisibility(View.GONE);
		
		titleImage.setBackgroundResource(R.drawable.share_circle);
		
		titleImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FacebookShareContentFragment shareContent = new FacebookShareContentFragment();
				shareContent.share(PropertyDetailActivity.this);
			}
		});

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
	
	public void showProperty() {
		titleText.setText(property.getBuilding_name());
		titlePrice.setText(property.getPrice());
		titleBedroom.setText(property.getBedroom());
		titleBathroom.setText(property.getBath());
		titleSqft.setText(property.getPsf());
		district.setText(property.getDistrict());
		auctionType.setText(property.getAuction_type());
		buildingType.setText(property.getBuilding_type());
		bedroom.setText(property.getBedroom());
		bathroom.setText(property.getBath());
		floorArea.setText(property.getFloor_area());
		sqft.setText(property.getPsf());
		price.setText(property.getPrice());
		tenure.setText(property.getTenure());
		highlight.setText(property.getHighlights());
		contact.setText(property.getSeller_name());
		ceaNo.setText(property.getCea_no());
		companyName.setText(property.getCompany_name());
		phoneNo = property.getPhone();
		email = property.getEmail();
		postal_code = property.getPostal_code();
		
		photoList.clear();
		photoList = property.getPhoto();
		
		String starbuy_flag = property.getStarbuy_flag();
		String shortlist_flag = property.getShortlist_flag();
		
		if(starbuy_flag.equals("1")) {
			starbuy.setImageResource(R.drawable.starbuy_check);
		}
		
		if(shortlist_flag.equals("1")) {
			shortlist.setImageResource(R.drawable.shortlist_check);
		}
		
		//Facilities
		String gym = property.getGym();
		String playground = property.getPlayground();
		String swimming_pool = property.getSwimming_pool();
		String tennis_court = property.getTennis_court();
		String function_room = property.getFunction_room();
		
		if(gym.equals("1")) {
			addCheckbox("Gym");
		}
		if(playground.equals("1")) {
			addCheckbox("Play Ground");
		}
		if(swimming_pool.equals("1")) {
			addCheckbox("Swimming Pool");
		}
		if(tennis_court.equals("1")) {
			addCheckbox("Tennis Court");
		}
		if(function_room.equals("1")) {
			addCheckbox("Function Room");
		}
		
		
		//add Image to slider
		if(photoList.size() > 0 ) {
			addImagetoSlider();
		}
		
		//google Map
		try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}//showProperty

	public void addCheckbox(String text) {
		CheckBox cb = new CheckBox(context);
		cb.setText(text);
		cb.setTextColor(getResources().getColor(R.color.black));
		cb.setChecked(true);
		cb.setTextSize((float) 20.0);
		facilitiesLayout.addView(cb);
	}
	
	public void addImagetoSlider() {
		HashMap<String,String> url_maps = new HashMap<String, String>();
		
		for (int j = 0; j < photoList.size(); j++) {
			url_maps.put(String.valueOf(j), CommonConstants.HOST + photoList.get(j).getName());
		}
		
		for(String name : url_maps.keySet()) {
			TextSliderView textSliderView = new TextSliderView(context);
			textSliderView
						.image(url_maps.get(name))
						.setScaleType(BaseSliderView.ScaleType.Fit);
			
			detailSlider.addSlider(textSliderView);
		}
		
		detailSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
		detailSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        detailSlider.setDuration(4000);
		
	}
	
	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else {
        	//set location in google map
//        	locationFromPostCode(postal_code);
        	locationFromPostCode("308215");
        }
    }//initilizeMap
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    

	public void locationFromPostCode(String postCode){ 
		Geocoder geocoder1 = new Geocoder(this); 
		try { 
			List<Address> addresses1 = geocoder1.getFromLocationName(postCode, 1); 
			if (addresses1 != null && !addresses1.isEmpty()) { 
				Address address1 = addresses1.get(0); 
				
				// Use the address as needed 
				double latitude_start = address1.getLatitude(); 
				double longitude_start = address1.getLongitude(); 
				
				setLocationMarker(latitude_start, longitude_start);
			} 
			else { 
				// Display appropriate message when Geocoder services are not available 
				Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show(); 
			} 
		} catch (IOException e) { 
			// handle exception } 
		}
	}//locationFromPostCode
	
	public void setLocationMarker(double latitude, double longitude) {
		// create marker
    	MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");
    	 
    	// Changing marker icon
    	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon));
    	 
    	// adding marker
    	googleMap.addMarker(marker);
    	
    	/*fromAsset(String assetName) – Loading from assets folder
    	fromBitmap (Bitmap image) – Loading bitmap image
    	fromFile (String path) – Loading from file
    	fromResource (int resourceId) – Loading from drawable resource*/
    	
    	/*googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    	googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    	googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    	googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    	googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);*/
    	
	}//setLocationMarker
	
	public class saveShortlist extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PropertyDetailActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.saveShortlist(pref.getSessionToken(context), property.getProperty_id());
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
					property.setShortlist_flag("1");
					
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
	
	public class AsyncShowProperty extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			showProperty();
			
			if(pDialog.isShowing()){
				pDialog.dismiss();
			}
		}
		
	}//saveShortlist

}

 