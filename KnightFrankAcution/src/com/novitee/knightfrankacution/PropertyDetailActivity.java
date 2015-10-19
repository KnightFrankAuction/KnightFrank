package com.novitee.knightfrankacution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.model.Property;
import com.novitee.knightfrankacution.util.CommonConstants;
import com.squareup.picasso.Picasso;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class PropertyDetailActivity extends BaseFragmentActivity {
	Context context = this;

	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	GoogleMap googleMap;
	FragmentTransaction fragmentTran;
	
	ImageView default_image;
	ImageView starbuy;
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
//	TextView companyName;
	ImageView makeCall;
	ImageView sendEmail;
	SliderLayout detailSlider;
	GridLayout facilitiesLayout;
	GridLayout featuresLayout;
	LinearLayout facilitiesLinear;
	LinearLayout featuresLinear;
	
	String phoneNo;
	String email;
	String postal_code;
	String facilities;
	List<Photo> photoList;
	List<String> facList;
	
	Property property;
	int video_flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_detail);

		setTitleBarAndFooter();
		
		default_image = (ImageView) findViewById(R.id.default_image);
		starbuy = (ImageView) findViewById(R.id.property_starbuy);
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
//		companyName = (TextView) findViewById(R.id.company_name);
		makeCall = (ImageView) findViewById(R.id.detail_phone);
		sendEmail = (ImageView) findViewById(R.id.detail_email);
		facilitiesLayout = (GridLayout) findViewById(R.id.facilities);
		featuresLayout = (GridLayout) findViewById(R.id.features);
		detailSlider = (SliderLayout) findViewById(R.id.detail_slider);
		titleImage.setBackgroundResource(R.drawable.share_circle);
		facilitiesLinear = (LinearLayout) findViewById(R.id.facilitesLayout);
		featuresLinear = (LinearLayout) findViewById(R.id.featuresLayout);
		
		photoList = new ArrayList<Photo>();
		facList = new ArrayList<String>();
		
		property = (Property) getIntent().getSerializableExtra("Property");
		
		showProperty();

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
		
		sendEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setData(Uri.parse("mailto:"));
			    i.setType("message/rfc822");
			    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{property.getEmail()});
			    try {
				    context.startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}//onCreate
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		titleText2.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.detail_footer, new FooterFragment());
		fragmentTran.commit();
		
		titleImage.setBackgroundResource(R.drawable.share_circle);
		
		titleImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareClick(v);
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
		phoneNo = property.getPhone();
		email = property.getEmail();
		postal_code = property.getPostal_code();
		
		if(property.getSeller_name().length() > 0) {
			contact.setText(property.getSeller_name());
		} else {
			contact.setVisibility(View.GONE);
		}

		if(property.getCea_no().length() > 0) {
			ceaNo.setText(property.getCea_no());
		} else {
			ceaNo.setVisibility(View.GONE);
		}

		/*if(property.getCompany_name().length() > 0) {
			companyName.setText(property.getCompany_name());
		} else {
			companyName.setVisibility(View.GONE);
		}*/
		
		String starbuy_flag = property.getStarbuy_flag();
		
		if(starbuy_flag.equals("1")) {
			starbuy.setImageResource(R.drawable.starbuy_check);
		}

		//Facilities
		String gym = property.getGym();
		String playground = property.getPlayground();
		String swimming_pool = property.getSwimming_pool();
		String tennis_court = property.getTennis_court();
		String function_room = property.getFunction_room();

		if(gym.equals("0") && playground.equals("0") && swimming_pool.equals("0") && tennis_court.equals("0") && function_room.equals("0")) {
			facilitiesLinear.setVisibility(View.GONE);
		}
		else {
			if(gym.equals("1")) {
				addTextView("Gym");
			}
			if(playground.equals("1")) {
				addTextView("Play Ground");
			}
			if(swimming_pool.equals("1")) {
				addTextView("Swimming Pool");
			}
			if(tennis_court.equals("1")) {
				addTextView("Tennis Court");
			}
			if(function_room.equals("1")) {
				addTextView("Function Room");
			}
		}

		String pentHouse = property.getPenthouse();
		String cityView = property.getCity_view();
		String highFloor = property.getHigh_floor();
		String balcony = property.getBalcony();
		
		if(pentHouse.equals("0") && cityView.equals("0") && highFloor.equals("0") && balcony.equals("0")) {
			featuresLinear.setVisibility(View.GONE);
		}
		else {
			if(pentHouse.equals("1")) {
				addTextViewFeature("Pent House");
			}
			if(cityView.equals("1")) {
				addTextViewFeature("City View");
			}
			if(highFloor.equals("1")) {
				addTextViewFeature("High Floor");
			}
			if(balcony.equals("1")) {
				addTextViewFeature("Balcony");
			}
		}
		
		photoList.clear();
		photoList = property.getPhoto();
		
		if(!property.getVideo().equals("")) {
			Photo photo = new Photo(CommonConstants.VIDEO_IMAGE);
			photoList.add(photo);
			video_flag = 1;
		}

		//add Image to slider
		if(photoList.size() == 1 && !property.getVideo().equals("")) {
			default_image.setScaleType(ScaleType.FIT_XY);
			String url = CommonConstants.HOST + photoList.get(0).getName();
            Picasso.with(context).load(url).into(default_image);

            default_image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Uri uri = Uri.parse(property.getVideo());
					Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(launchBrowser);
				}
			});
		}
		else if(photoList.size() == 1 && property.getVideo().equals("")) {
			default_image.setScaleType(ScaleType.FIT_XY);
			String url = CommonConstants.HOST + photoList.get(0).getName();
            Picasso.with(context).load(url).into(default_image);
		}
		else if(photoList.size() > 1) {
			default_image.setVisibility(View.GONE);
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

	public void addImagetoSlider() {
		final String count = String.valueOf(photoList.size() - 1);
		
		for (int i = 0; i < photoList.size(); i++) {
			String name = CommonConstants.HOST + photoList.get(i).getName();;
			
			/*if(video_flag == 1 && Integer.parseInt(count) == i) {
				name = photoList.get(i).getName();
			}
			else {
				name = CommonConstants.HOST + photoList.get(i).getName();
			}*/
			
			TextSliderView textSliderView = new TextSliderView(PropertyDetailActivity.this);
			textSliderView.bundle(new Bundle());
			
			textSliderView.image(name)
				   		  .setScaleType(BaseSliderView.ScaleType.Fit)
				   		  .setOnSliderClickListener(new OnSliderClickListener() {
								
								@Override
								public void onSliderClick(BaseSliderView slider) {
//									Toast.makeText(context,slider.getBundle().get("Extra") + "",Toast.LENGTH_LONG).show();
									
									if(count.equals(slider.getBundle().getString("Extra")) && video_flag == 1) {
//										Toast.makeText(context, "Show Video", Toast.LENGTH_LONG).show();
										Uri uri = Uri.parse(property.getVideo());
										Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
										startActivity(launchBrowser);
									}
								}
								
							});
			textSliderView.getBundle().putString("Extra", Integer.toString(i));
			
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
        	locationFromPostCode(postal_code);
//        	locationFromPostCode("308215");
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
	
	public void setLocationMarker(final double latitude, final double longitude) {
		// create marker
    	MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");
    	 
    	// Changing marker icon
    	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon));
    	 
    	// adding marker
    	googleMap.addMarker(marker);
    	googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 12.0f));
    	googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

    		@Override
    		public boolean onMarkerClick(Marker arg0) {
	    		String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
	    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
	    		context.startActivity(intent);
	    		return true;
    		}
    	});
    	
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

	public void addTextViewFeature(String text) {
		TextView txt = new TextView(context);
		txt.setText(text);
		txt.setTextColor(getResources().getColor(R.color.black));
		txt.setTextSize((float) 20.0);
		txt.setPadding(10, 10, 10, 10);
		txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check, 0, 0, 0);
		featuresLayout.addView(txt);
	}
	
	private void addTextView(String text) {
		TextView txt = new TextView(context);
		txt.setText(text);
		txt.setTextColor(getResources().getColor(R.color.black));
		txt.setTextSize((float) 20.0);
		txt.setPadding(10, 10, 10, 10);
		txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check, 0, 0, 0);
		facilitiesLayout.addView(txt);
	}
	
	private void shareClick(View v) {
		// TODO Auto-generated method stub
		
		String body = "Hi,\n\nI would like to share the following property from KnightFrank action app with you:\n\n";

		body = body + "1.";
		body = body + property.getBuilding_name() +"\n";
		body = body + property.getDistrict() +" / "+ property.getAuction_type() +" / "+ property.getBuilding_type() +"\n";
		body = body + property.getFloor_area() +" Sqft / "+ property.getBedroom() +" Bedroom, "+ property.getBath() +" Bath\n";
		body = body + property.getPrice() +" / $"+ property.getPsf() +" psf" +"\n";
		body = body + property.getTenure() +"\n";
		body = body + "Postal Code "+ property.getTenure() +"\n\n\n";
		
		body = body + "This email is generated via KnightFrank Auction App \n\n";
			
		Intent emailIntent = new Intent();
	    emailIntent.setAction(Intent.ACTION_SEND);
	    // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
	    emailIntent.putExtra(Intent.EXTRA_TEXT, body);
	    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Title");
	    emailIntent.setType("message/rfc822");
	    
	    PackageManager pm = getPackageManager();
	    Intent sendIntent = new Intent(Intent.ACTION_SEND);     
	    sendIntent.setType("text/plain");
	    
//	    FacebookShareContentFragment shareContent = new FacebookShareContentFragment();
//		shareContent.share(PropertyDetailActivity.this);
	    
	    Intent openInChooser = Intent.createChooser(emailIntent, "Share");
	    List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
	    List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();   
	    
	    for (int i = 0; i < resInfo.size(); i++) {
	        // Extract the label, append it, and repackage it in a LabeledIntent
	        ResolveInfo ri = resInfo.get(i);
	        String packageName = ri.activityInfo.packageName;
	        if(packageName.contains("android.email")) {
	            emailIntent.setPackage(packageName);
	        } else if(packageName.contains("mms") || packageName.contains("android.gm")||packageName.contains("whatsapp")||packageName.contains("com.tencent.mm") || packageName.contains("facebook")) {
	            Intent intent = new Intent();
	            intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
	            intent.setAction(Intent.ACTION_SEND);
	            intent.setType("text/plain");
	            
	            if(packageName.contains("mms")) {
	                intent.putExtra(Intent.EXTRA_TEXT, body);
	            } else if(packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
	                intent.putExtra(Intent.EXTRA_TEXT, body);
	                intent.putExtra(Intent.EXTRA_SUBJECT, "Gmail");               
	                intent.setType("message/rfc822");
	            } else if (packageName.contains("whatsapp")) {
	                intent.putExtra(Intent.EXTRA_TEXT, body);		                   
	            } else if (packageName.contains("com.tencent.mm")) {
	                intent.putExtra(Intent.EXTRA_TEXT, body);		                   
	            }
	            else if (packageName.contains("facebook")) {
	            	intent.setPackage(packageName);
	                intent.putExtra(Intent.EXTRA_TEXT, "http://www.knightfrank.com.sg");	
	            }

	            intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
	        }
	    }

	    // convert intentList to array
	    LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);

	    openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
	    startActivity(openInChooser); 
	}
	
}

 