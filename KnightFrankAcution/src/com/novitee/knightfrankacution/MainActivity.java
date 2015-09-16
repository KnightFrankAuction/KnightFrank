package com.novitee.knightfrankacution;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.util.Preferences;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends BaseFragmentActivity {

	ViewFlipper mViewFlipper;
	
	FragmentTransaction fragmentTran;
	String pref_username;
	String pref_password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pref_username = Preferences.getInstance(context).getUserName();
		pref_password = Preferences.getInstance(context).getPassword();
		if(!pref_username.equals("") && !pref_password.equals("")) {
			if(connectionManager.isConnected()) {
				new Login().execute();
			}
			else {
				Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
		
		//Start ViewFlipper
		mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		mViewFlipper.setAutoStart(true);
		mViewFlipper.setFlipInterval(4000);
		mViewFlipper.startFlipping();
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.place_holder, new ViewPagerFragment());
		fragmentTran.commit();

	}

	private class Login extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.login(pref_username, pref_password, Preferences.getInstance(context).getGenerateKey());
				
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
					
					Preferences.getInstance(context).setUserName(pref_username);
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
					
					Preferences.getInstance(context).setUserName(pref_username);
					
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
