package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.util.CommonConstants;
import com.novitee.knightfrankacution.util.Preferences;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends BaseFragmentActivity {

	ViewFlipper mViewFlipper;
	
	FragmentTransaction fragmentTran;
	String pref_email;
	String pref_password;
	
	List<Photo> bgList;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pref_email = pref.getEmail();
		pref_password = pref.getPassword();
		if(!pref_email.equals("") && !pref_password.equals("")) {
			if(connectionManager.isConnected()) {
				new Login().execute();
			}
			else {
				Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
		
		bgList = (ArrayList<Photo>) getIntent().getSerializableExtra("BgPhoto");
		
		if(bgList != null) {
			makeViewFlipper();
		}
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.place_holder, new ViewPagerFragment());
		fragmentTran.commit();

	}//onCreate
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		moveTaskToBack(true);
	}

	private void makeViewFlipper() {
		// TODO Auto-generated method stub
		
		//Start ViewFlipper
		mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		String url;
		ImageView imgFlipper;
		for (int i = 0; i < bgList.size(); i++) {
			url = CommonConstants.HOST + bgList.get(i).getName();
			imgFlipper = new ImageView(context);
			Picasso.with(context).load(url).into(imgFlipper);
			mViewFlipper.addView(imgFlipper);
		}
		
		mViewFlipper.setAutoStart(true);
		mViewFlipper.setFlipInterval(4000);
		mViewFlipper.startFlipping();
	}

	private class Login extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.login(pref_email, pref_password, Preferences.getInstance(context).getGenerateKey());
				
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
			
			try {
				int responseCode = jObj.getInt("statusCode");
				int status = jObj.getInt("status");				
				
				if(status == 1 && responseCode == 200){
					int user_type = jObj.getInt("type");
					String session_token = jObj.getString("session_token");
					
					Preferences.getInstance(context).setEmail(pref_email);
					Preferences.getInstance(context).setPassword(pref_password);
					Preferences.getInstance(context).setUserType(user_type);
					Preferences.getInstance(context).setSessionToken(session_token);
					
					Intent intent = new Intent(context, MenuActivity.class);
					startActivity(intent);
				}
				else if(status == 2 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else if(status == 3 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
					
					Preferences.getInstance(context).setEmail(pref_email);
					
					Intent intent = new Intent(context, SignUpActivity.class);
					startActivity(intent);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
	}//Login

}
