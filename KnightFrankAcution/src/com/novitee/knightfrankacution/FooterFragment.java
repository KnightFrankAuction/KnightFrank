package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.novitee.knightfrankacution.base.BaseFragment;
import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.model.Property;

public class FooterFragment extends BaseFragment implements OnClickListener {
	
	ImageView home;
	ImageView shortlist;
	ImageView about;
	ImageView enquiry;
	ImageView menu;
	
	ArrayList<Property> listPro;
	ArrayList<String> listImage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.footer, container, false);
		
		home = (ImageView) view.findViewById(R.id.footer_home);
		shortlist = (ImageView) view.findViewById(R.id.footer_shortlist);
		about = (ImageView) view.findViewById(R.id.footer_about);
		enquiry = (ImageView) view.findViewById(R.id.footer_enquiry);
		menu = (ImageView) view.findViewById(R.id.footer_menu);
		
		listPro = new ArrayList<Property>();
		listImage = new ArrayList<String>();
		
		home.setOnClickListener(this);
		shortlist.setOnClickListener(this);
		about.setOnClickListener(this);
		enquiry.setOnClickListener(this);
		menu.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		
		if(v.getId() == home.getId()) {
			intent = new Intent(getActivity(), MenuActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == shortlist.getId()) {
			new GetAllShortList().execute();
//			intent = new Intent(getActivity(), ShortListActivity.class);
		}
		else if(v.getId() == about.getId()) {
//			intent = new Intent(getActivity(), AboutActivity.class);
		}
		else if(v.getId() == enquiry.getId()) {
			intent = new Intent(getActivity(), EnquiryActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == menu.getId()) {
//			intent = new Intent(context, MenuListActivity.class);
		}
		
	}//onClick
	
	public class GetAllShortList extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getAllShortlist(pref.getSessionToken(getActivity()));
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
						if(list.size() > 0) {
							photo = list.get(0);
						}
						else {
							photo = new Photo("");
						}
						
						listImage.add(photo.getName());
					}

					Intent intent = new Intent(getActivity(), PropertyListActivity.class);
					intent.putExtra("title", "Shortlist");
					intent.putExtra("pList", listPro);
					intent.putExtra("imageList", listImage);
					startActivity(intent);
					
				}
				else if(json_status == 2 && json_responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(getActivity(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}//GetAllShortList

}
