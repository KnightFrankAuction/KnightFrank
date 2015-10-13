package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.model.Property;
import com.novitee.knightfrankacution.util.CommonConstants;
import com.novitee.knightfrankacution.util.Preferences;
import com.squareup.picasso.Picasso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class ProjectListingsListActivity extends BaseFragmentActivity {
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;

	ListView listShortlist;
	Property property;
	Property shortlist_property;
	ImageView shortlist;
	ProgressBar refresh;
	ProgressBar loading;
	TextView shareView;
	
	CustomList customList;
	FragmentTransaction fragmentTran;
	int pageCount = 1;
	int shortlist_position;
	ArrayList<Property> propertyList;
	List<Photo> photoList;
	int end_flag; //check more properties exist or not
	String project_listings_date; //date for auction listing
	
	//Share
	boolean share_flag = false;
	int layout_color;
	int share_count = 0;
	View row;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auction_property_list);
		
		listShortlist = (ListView) findViewById(R.id.property_list);
		refresh = (ProgressBar) findViewById(R.id.list_refresh);
		loading = (ProgressBar) findViewById(R.id.property_list_loading);
		shareView = (TextView) findViewById(R.id.share_view);
		
		propertyList = new ArrayList<Property>();
		photoList = new ArrayList<Photo>();
		project_listings_date = getIntent().getStringExtra("ProjectListingsDate");
		
		setTitleBarAndFooter();
		
		if(connectionManager.isConnected()) {
			new GetProjectListingsList().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}

		customList = new CustomList(context, R.layout.property_listview_layout, propertyList);
		
		listShortlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub

				if(share_flag == false) {
					Property p = propertyList.get(position);
					Intent intent = new Intent(context, PropertyDetailActivity.class);
					intent.putExtra("Property", p);
					startActivity(intent);
				}
				else { // in share mode, share_flag == true
					checkBgColor(v, position);
				}
			}
		});
	}
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText.setText(project_listings_date);
		titleText2.setVisibility(View.GONE);
		titleImage.setBackgroundResource(R.drawable.share_circle);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.property_list_search, new SearchFragment());
		fragmentTran.commit();
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.auction_property_list_footer, new FooterFragment());
		fragmentTran.commit();
	
		ImageView titleBack = (ImageView) findViewById(R.id.title_back);
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(share_flag == false) {
					onBackPressed();
				}
				else {
					removeShare();
				}
			}
		});
		
		titleImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareView.setVisibility(View.VISIBLE);
				share_flag = true;
				
				titleImage.setVisibility(View.GONE);
				titleText.setText("");
				titleText2.setVisibility(View.VISIBLE);
				titleText2.setText("Share");
			}
		});
		
		titleText2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShareWithEmail activity = new ShareWithEmail();
				activity.shareProperty(propertyList, context);		
				removeShare();
			}
		});
	}//setTitleBarAndFooter
	
	public class GetProjectListingsList extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getProjectListingsList(Preferences.getInstance(context).getSessionToken(), project_listings_date, pageCount);
				
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

			if(loading.isShown()) {
				loading.setVisibility(View.GONE);
				listShortlist.setVisibility(View.VISIBLE);
			}
			
			try {
				int json_responseCode = jObj.getInt("statusCode");
				int json_status = jObj.getInt("status");
				end_flag = jObj.getInt("end");
				
				if(json_status == 1 && json_responseCode == 200){
					Property property;
					JSONArray jArray = jObj.getJSONArray("property");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json = jArray.getJSONObject(i);
						property = new Property(json);
						propertyList.add(property);
						
						if(property.getPhoto().size() > 0) {
							photoList.add(property.getPhoto().get(0));
						}
						else {
							photoList.add(new Photo(""));
						}
					}
					
					if(propertyList.size() <= 0 && pageCount == 0) {
						Toast.makeText(context, "There is no property", Toast.LENGTH_LONG).show();
					}
					else  {
						refresh.setVisibility(View.GONE);
						if(listShortlist.getAdapter() == null) {
							listShortlist.setAdapter(customList);
						}
						else { //after load more properties
							customList.notifyDataSetChanged();
						}
					}
					
					listShortlist.setOnScrollListener(new OnScrollListener() {
						
						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {
							// TODO Auto-generated method stub
						}
						
						@Override
						public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
							// TODO Auto-generated method stub  
							
							if(end_flag == 1) {
								if (listShortlist.getLastVisiblePosition() == listShortlist.getAdapter().getCount() -1 &&
									listShortlist.getChildAt(listShortlist.getChildCount() - 1).getBottom() <= listShortlist.getHeight())
								{
									refresh.setVisibility(View.VISIBLE);
					            	pageCount++;
					            	new GetProjectListingsList().execute();
								}
				            }
						}
					});
					
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
		
	}//GetAuctionDate

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
            TextView txtStatus = (TextView) row.findViewById(R.id.txt_unavailable);
            
            property = propertyList.get(position);
            if(!property.getStatus().equals("Active")) {
            	txtStatus.setVisibility(View.VISIBLE);
            }
            
            if(property.getBuilding_name().length() > 0) {
            	buildingName.setText(property.getBuilding_name());
            } else {
            	buildingName.setVisibility(View.GONE);
            }

            if(property.getBuilding_name().length() > 0) {
            	price.setText(property.getPrice());
            } else {
            	price.setVisibility(View.GONE);
            }

            if(property.getBuilding_name().length() > 0) {
            	district.setText(property.getDistrict());
            } else {
            	district.setVisibility(View.GONE);
            }

            if(property.getBuilding_name().length() > 0) {
            	auctionType.setText(property.getAuction_type());
            } else {
            	auctionType.setVisibility(View.GONE);
            }

            if(property.getBuilding_name().length() > 0) {
            	buildingType.setText(property.getBuilding_type());
            } else {
            	buildingType.setVisibility(View.GONE);
            }

            if(property.getBuilding_name().length() > 0) {
            	floor_area.setText(property.getFloor_area() + " sqft");
            } else {
            	floor_area.setVisibility(View.GONE);
            }

            if(property.getBuilding_name().length() > 0) {
            	bed_bath.setText(property.getBedroom() + " bedroom, " + property.getBath() + " bathroom");
            } else {
            	bed_bath.setVisibility(View.GONE);
            }

            if(property.getBuilding_name().length() > 0) {
            	tenure.setText(property.getTenure());
            } else {
            	tenure.setVisibility(View.GONE);
            }

            if(property.getBuilding_name().length() > 0) {
            	psf.setText("$"+ property.getPsf() + " psft");
            } else {
            	psf.setVisibility(View.GONE);
            }

            String image_name = photoList.get(position).getName();
            if(image_name.length() > 0) {
            	String url = CommonConstants.HOST + image_name;
                Picasso.with(context).load(url).into(propertyImage);
            }
            
            String shortlist_flag = property.getShortlist_flag();
            
            if(shortlist_flag.equals("1")) {
            	shortlist.setImageResource(R.drawable.shortlist_check);
    		}
            
            //select share color
            if(!property.getBgcolor().equals("#FFFFFF")) {
            	row.setBackgroundColor(getResources().getColor(R.color.blue));
            }
            
            shortlistLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View parentRow = (View) v.getParent();
					LinearLayout linearRow = (LinearLayout) parentRow.getParent();
					LinearLayout linearRow1 = (LinearLayout) linearRow.getParent();
					ListView listView = (ListView) linearRow1.getParent();
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
			pDialog = new ProgressDialog(ProjectListingsListActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.saveShortlist(Preferences.getInstance(context).getSessionToken(), shortlist_property.getProperty_id());
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
			pDialog = new ProgressDialog(ProjectListingsListActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.deleteShortlist(Preferences.getInstance(context).getSessionToken(), propertyList.get(shortlist_position).getProperty_id());
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

	/************** SHARE *****************/
	private void checkBgColor(View v, int pos) {
		layout_color = ((ColorDrawable) v.getBackground()).getColor();
		Property p = propertyList.get(pos);
		
		if(layout_color == Color.parseColor("#FFFFFF")) {
			if(share_count < 10) {
				share_count++;
				p.setBgcolor("#000000");
				v.setBackgroundColor(getResources().getColor(R.color.blue));
			}
			else {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Share")
					   .setMessage("Allow only 10 property to share.")
				       .setCancelable(false)
				       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                //do things
				        	   dialog.dismiss();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		else {
			share_count--;
			
			p.setBgcolor("#FFFFFF");
			v.setBackgroundColor(getResources().getColor(R.color.white));
		}
		
		shareView.setText(share_count + " select to share");
	}
	
	private void removeShare() {
		titleText.setText("Project Listings");
		titleText2.setVisibility(View.GONE);
		titleImage.setVisibility(View.VISIBLE);
		titleImage.setBackgroundResource(R.drawable.share_circle);
		share_flag = false;
		share_count = 0;
		shareView.setText("0 select to share");
		shareView.setVisibility(View.GONE);
		
		for(Property p : propertyList) {
			if(p.getBgcolor().equals("#000000")) {
				p.setBgcolor("#FFFFFF");
			}
		}
		
		customList.notifyDataSetChanged();
	}

}
