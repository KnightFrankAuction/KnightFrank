package com.novitee.knightfrankacution;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class ChangePasswordActivity extends BaseActivity {
	
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	EditText oldPassword, newPassword, retypePassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		setTitleBarAndFooter();
		
		oldPassword = (EditText) findViewById(R.id.edit_old_password);
		newPassword = (EditText) findViewById(R.id.edit_new_password);
		retypePassword = (EditText) findViewById(R.id.edit_retype_password);
	}//onCreate
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.pwd_title_text);
		titleText2 = (TextView) findViewById(R.id.pwd_title_text2);

		ImageView titleBack = (ImageView) findViewById(R.id.pwd_title_back);
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		titleText2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changePassword();
			}
		});
	}//setTitleBarAndFooter

	protected void changePassword() {
		// TODO Auto-generated method stub
		if(oldPassword.getText().equals("") || oldPassword.getText().equals(null) ||
			newPassword.getText().equals("") || newPassword.getText().equals(null) ||
			retypePassword.getText().equals("") || retypePassword.getText().equals(null)) {
			
			Toast.makeText(context, "You have to fill all fields", Toast.LENGTH_LONG).show();
		}
		else if( !newPassword.getText().toString().equals(retypePassword.getText().toString()) ) {
			Toast.makeText(context, "New password and Re-type password are do not match", Toast.LENGTH_LONG).show();
		}
		else {
			if(connectionManager.isConnected()) {
				new ChangePassword().execute();
			}
			else {
				Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
			}
		}
	}//changePassword
	
	private class ChangePassword extends AsyncTask<Void, Void, Void> {
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
				String old = oldPassword.getText().toString();
				String newp = newPassword.getText().toString();
				jObj = api.changePassword(pref.getSessionToken(), old, newp);
				
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
					pref.setPassword(newPassword.getText().toString());
					Intent intent = new Intent(context, AboutActivity.class);
					startActivity(intent);

				}
				else if(status == 2 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else if(status == 3 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}//ChangePassword

}
