package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.util.CommonConstants;
import com.novitee.knightfrankacution.util.Preferences;
import com.squareup.picasso.Picasso;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class AdvertisementsActivity extends BaseFragmentActivity {
	
	List<String> ad_list;
	
	ImageView load;
	ImageView close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advertisements);
		
		load = (ImageView) findViewById(R.id.load_image);
		
		ad_list = new ArrayList<String>();
	}

	public class downloadAd extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;
		

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				jObj = api.getAD();
				int json_responseCode = jObj.getInt("statusCode");
				int json_status = jObj.getInt("status");
				
				if(json_status == 1 && json_responseCode == 200){
					JSONArray data = new JSONArray();
					data = jObj.getJSONArray("data");
					
					for (int i = 0; i < data.length(); i++) {
						JSONObject json = data.getJSONObject(i);
						String url = CommonConstants.HOST + json.getString("url");
						ad_list.add(url);
					}
					
					Set<String> set = new HashSet<String>();
					set.addAll(ad_list);
					
					Preferences.setAdList(AdvertisementsActivity.this, set);
				}
				else {
					String message = jObj.getString("message");
					Toast.makeText(AdvertisementsActivity.this, message, Toast.LENGTH_SHORT).show();
				}
				
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
			
			for (int j = 0; j < ad_list.size(); j++) {		
				Picasso.with(AdvertisementsActivity.this).load(ad_list.get(j)).into(load);
			}
//			showAD();
		}
		
	}//downloadAd
	
	public void showAD() {
		Set<String> set = Preferences.getAdList(AdvertisementsActivity.this);
		Object[] list = set.toArray();
		
		int index = randomInt(list.length);
		String ad_url = (String) list[index];
		
		final Dialog dialog = new Dialog(AdvertisementsActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_advertisements);
		
		Picasso.with(AdvertisementsActivity.this)
		.load(ad_url)
		.error(R.drawable.ic_launcher)
		.into((ImageView) dialog.findViewById(R.id.ad_image));
		
		close = (ImageView) dialog.findViewById(R.id.close_ad);
		close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		dialog.show();
		
	}
	
	public int randomInt(int range) {
		Random rand = new Random();
		int num = rand.nextInt(range);
		return num;
	}
}
