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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PropertyListActivity extends BaseFragmentActivity {
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	FragmentTransaction fragmentTran;
	ArrayList<String> searchList;
	ArrayList<Property> propertyList;
	List<String> imageNameList;
	String title = null;

	ListView listProperty;
	ProgressBar refresh;
	ProgressBar loading;
	TextView shareView;
	
	Property shortlist_property;
	ImageView shortlist;
	int shortlist_position;
	CustomList customList;
	Property property;
	int pageCount = 1;
	int end_flag; //check more properties exist or not
	
	//Share
	boolean share_flag = false;
	int layout_color;
	int share_count = 0;
	View row;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_list);
		
		listProperty = (ListView) findViewById(R.id.filter_list);
		refresh = (ProgressBar) findViewById(R.id.filter_list_refresh);
		loading = (ProgressBar) findViewById(R.id.filter_list_loading);
		shareView = (TextView) findViewById(R.id.filter_share_view);

		imageNameList = new ArrayList<String>();
		propertyList = new ArrayList<Property>();
		searchList = (ArrayList<String>) getIntent().getStringArrayListExtra("SearchList");
		title = getIntent().getStringExtra("Title");
		
		setTitleBarAndFooter();
		
		customList = new CustomList(context, R.layout.property_listview_layout, propertyList);
		
		if(connectionManager.isConnected()) {
			new Search().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}

		listProperty.setOnItemClickListener(new OnItemClickListener() {

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
		
		titleText.setText(title);
		titleText2.setVisibility(View.GONE);
		titleImage.setBackgroundResource(R.drawable.share_circle);

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

            String image_name = imageNameList.get(position);
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
			pDialog = new ProgressDialog(PropertyListActivity.this);
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
			pDialog = new ProgressDialog(PropertyListActivity.this);
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
		titleText.setText(title);
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
	
	private class Search extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.Search(pref.getSessionToken(), searchList.get(0), searchList.get(1), searchList.get(2), searchList.get(3), searchList.get(4), searchList.get(5), searchList.get(6), searchList.get(7), searchList.get(8), pageCount); //"1" for page_count
				
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
				listProperty.setVisibility(View.VISIBLE);
			}

			try {
				int json_responseCode = jObj.getInt("statusCode");
				int json_status = jObj.getInt("status");
				
				if(json_status == 1 && json_responseCode == 200){
					Property property;
					JSONArray jArray = jObj.getJSONArray("property");
					
					propertyList.clear();
					Photo photo;
					List<Photo> list = new ArrayList<Photo>();
					
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json = jArray.getJSONObject(i);
						property = new Property(json);
						propertyList.add(property);

						if(property.getPhoto().size() > 0) {
							list = property.getPhoto();
							photo = list.get(0);
							imageNameList.add(photo.getName());
						}
						else {
							imageNameList.add("");
						}
					}
					
					if(propertyList.size() <= 0 && pageCount == 0) {
						Toast.makeText(context, "There is no property", Toast.LENGTH_LONG).show();
					}
					else  {
						refresh.setVisibility(View.GONE);
						if(listProperty.getAdapter() == null) {
							listProperty.setAdapter(customList);
						}
						else { //after load more properties
							customList.notifyDataSetChanged();
						}
					}
					
					listProperty.setOnScrollListener(new OnScrollListener() {
						
						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {
							// TODO Auto-generated method stub
						}
						
						@Override
						public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
							// TODO Auto-generated method stub  
							
							if(end_flag == 1) {
								if (listProperty.getLastVisiblePosition() == listProperty.getAdapter().getCount() -1 &&
									listProperty.getChildAt(listProperty.getChildCount() - 1).getBottom() <= listProperty.getHeight())
								{
									refresh.setVisibility(View.VISIBLE);
					            	pageCount++;
					            	new Search().execute();
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
					Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//onPostExecute
	}//Search

}
