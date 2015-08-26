package com.novitee.knightfrankacution;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.util.Preferences;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class LoginActivity extends BaseFragmentActivity {
	
	Context context = this;
	
	EditText username;
	EditText password;
	Button btnLogin;
	ImageView back;
	
	String userName, passWord;
	
	FragmentTransaction fragmentTran;
	ViewFlipper mViewFlipper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_fragment);
		
		//Start ViewFlipper
		mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		mViewFlipper.setAutoStart(true);
		mViewFlipper.setFlipInterval(4000);
		mViewFlipper.startFlipping();
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.login_frame, new FacebookLoginFragment());
		fragmentTran.commit();
		
		username = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);
		btnLogin = (Button) findViewById(R.id.btnLogIn);
		back = (ImageView) findViewById(R.id.login_back);

		String pref_username = Preferences.getUserName(context);
		String pref_password = Preferences.getPassword(context);
		if(!pref_username.equals("")) {
			username.setText(pref_username);
			password.setText(pref_password);
		}
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(connectionManager.isConnected()) {
					userName = username.getText().toString();
					passWord = password.getText().toString();
					new Login().execute();
				}
				else {
					Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}//onCreate
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	public class Login extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.login(userName, passWord, pref.getGenerateKey(context));
				
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
				int responseCode = jObj.getInt("statusCode");
				int status = jObj.getInt("status");				
				
				if(status == 1 && responseCode == 200){
					int user_type = jObj.getInt("type");
					String session_token = jObj.getString("session_token");
					
					Preferences.setUserName(context, userName);
					Preferences.setPassword(context, passWord);
					Preferences.setUserType(context, user_type);
					Preferences.setSessionToken(context, session_token);
					
					Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
					
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
					
					Preferences.setUserName(context, userName);
					
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
