package com.novitee.knightfrankacution.adapter;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.R;
import com.novitee.knightfrankacution.api.KnightFrankAPI;
import com.novitee.knightfrankacution.util.ConnectionManager;
import com.novitee.knightfrankacution.util.Preferences;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	
//	Pref pref = Pref.getInstance(getActivity());
	
	private int pagerCount = 3;
	Fragment next_auction_fragment = new NextAuction();
	
	static String key = "zfDmWXAra1XqP1mElIIL3RyjJsMw81pxWsatGaag";
	
	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		return pagerCount;
	}

	@Override
	public Fragment getItem(int position) {
		
		Fragment frg = null;
		
		switch (position) {
		case 0:
				frg = new AboutKnightFrank();
				break;
			
		case 1:
				frg = next_auction_fragment;
//				frg = new NextAuction();
				break;
			
		case 2:
				frg = new Introduction();
				break;

		default:
			break;
		}
		
		return frg;
	}
	
	
	public static class AboutKnightFrank extends Fragment {
		@Override
		@Nullable
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.about_knight_frank_fragment, container, false);
			return view;
		}
	}
	
	public static class Introduction extends Fragment {
		@Override
		@Nullable
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.introduction_fragment, container, false);
			return view;
		}
	}
	
	public static class NextAuction extends Fragment {
		
		TextView auction_date;
		TextView auction_time;
		TextView auction_venue;
		
		@Override
		@Nullable
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.next_auction_fragment, container, false);
			
			auction_date =(TextView) view.findViewById(R.id.next_auction_date);
			auction_time =(TextView) view.findViewById(R.id.next_auction_time);
			auction_venue =(TextView) view.findViewById(R.id.next_auction_venue);
			
			ConnectionManager connectionManager = new ConnectionManager(getActivity());
			if(connectionManager.isConnected()) {
				new GetNextAuction().execute();
			}
			else {
				Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
			}
			
			return view;
		}
		
		public class GetNextAuction extends AsyncTask<Void, Void, Void> {
			
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
				KnightFrankAPI api = new KnightFrankAPI();
				try {
//					String session = Preferences.getSessionToken(getActivity());
//					jObj = api.getNextAuction(Preferences.getSessionToken(getActivity()));
					jObj = api.getNextAuction();
					
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
				pDialog.dismiss();
				
				try {
					int responseCode = jObj.getInt("statusCode");
					int status = jObj.getInt("status");				
					
					if(status == 1 && responseCode == 200){
						String date = jObj.getString("date");
						String time = jObj.getString("time");
						String venue = jObj.getString("Venue");
						
						auction_date.setText(date);
						auction_time.setText(time);
						auction_venue.setText(venue);
					}
					else if(status == 2 && responseCode == 401) {
						String message = jObj.getString("message");
						Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
			
		}
	}
	
	

}
