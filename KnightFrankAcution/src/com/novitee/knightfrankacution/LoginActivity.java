package com.novitee.knightfrankacution;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.util.Preferences;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class LoginActivity extends BaseFragmentActivity {
	
	Context context = this;
	
	EditText username;
	EditText password;
	Button btnLogin;
	ImageView back;
	TextView forgetPwd;
	
	String email, passWord;

	ViewFlipper mViewFlipper;
	EditText editForgetEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_fragment);
		
		//Start ViewFlipper
		mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		mViewFlipper.setAutoStart(true);
		mViewFlipper.setFlipInterval(4000);
		mViewFlipper.startFlipping();
		
		username = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);
		btnLogin = (Button) findViewById(R.id.btnLogIn);
		back = (ImageView) findViewById(R.id.login_back);
		forgetPwd = (TextView) findViewById(R.id.txt_forget_pwd);
		
		String pref_username = pref.getEmail();
		String pref_password = pref.getPassword();
		if(!pref_username.equals("")) {
			username.setText(pref_username);
			password.setText(pref_password);
		}
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(connectionManager.isConnected()) {
					email = username.getText().toString();
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
		
		forgetPwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				forgetPassword();
			}

		});
		
	}//onCreate

	private void forgetPassword() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(LoginActivity.this);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setTitle("Forget Password");
		dialog.setContentView(R.layout.forget_password);
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		
		editForgetEmail = (EditText) dialog.findViewById(R.id.forget_email);
		Button btnForgetEmail = (Button) dialog.findViewById(R.id.btn_forget_email);
		
		btnForgetEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Pattern pattern = Patterns.EMAIL_ADDRESS;
			    boolean valid = pattern.matcher(editForgetEmail.getText().toString()).matches();
			    if(!valid) {
			    	Toast.makeText(context, "Enter invalid email", Toast.LENGTH_LONG).show();
			    }
			    else {
			    	dialog.dismiss();
					new ForgetPassword().execute();
			    }
				
			}
		});
		
		dialog.show();
	}
	
	private class Login extends AsyncTask<Void, Void, Void> {
		
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
				jObj = api.login(email, passWord, pref.getGenerateKey());
				
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
					
					Preferences.getInstance(context).setEmail(email);
					Preferences.getInstance(context).setPassword(passWord);
					Preferences.getInstance(context).setUserType(user_type);
					Preferences.getInstance(context).setSessionToken(session_token);
					
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
					
					Preferences.getInstance(context).setEmail(email);
					
					Intent intent = new Intent(context, SignUpActivity.class);
					startActivity(intent);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
	}//Login
	
	private class ForgetPassword extends AsyncTask<Void, Void, Void> {
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
				jObj = api.forgetPassword(editForgetEmail.getText().toString());
				
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
				String message = jObj.getString("message");
				
				if(status == 1 && responseCode == 200){
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else if(status == 2 && responseCode == 401) {
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else if(status == 3 && responseCode == 401) {
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
					
					Preferences.getInstance(context).setEmail(email);
					
					Intent intent = new Intent(context, SignUpActivity.class);
					startActivity(intent);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}//ForgetPassword
}
