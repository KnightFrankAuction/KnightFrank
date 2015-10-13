package com.novitee.knightfrankacution;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.User;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends BaseFragmentActivity {
	
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	FragmentTransaction fragmentTran;
	FooterFragment footer_frg;
	
	TextView txtEmail, txtUserName, txtPhone, txtUserType, txtBuildingNo, txtBuildingName, txtStreet, txtCeaNo, txtCompany;
	RelativeLayout relativeChangePwd;
	LinearLayout layoutUser, layoutAgent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		setTitleBarAndFooter();
		
		txtEmail = (TextView) findViewById(R.id.about_email);
		txtUserName = (TextView) findViewById(R.id.about_username);
		txtPhone = (TextView) findViewById(R.id.about_phone);
		txtUserType = (TextView) findViewById(R.id.about_user_type);
		txtBuildingNo = (TextView) findViewById(R.id.about_building_no);
		txtBuildingName = (TextView) findViewById(R.id.about_building_name);
		txtStreet = (TextView) findViewById(R.id.about_street);
		txtCeaNo = (TextView) findViewById(R.id.about_cea_no);
		txtCompany = (TextView) findViewById(R.id.about_company);
		relativeChangePwd = (RelativeLayout) findViewById(R.id.layout_password);
		layoutUser = (LinearLayout) findViewById(R.id.linear_public_user);
		layoutAgent = (LinearLayout) findViewById(R.id.linear_public_agent);
		
		if(connectionManager.isConnected()) {
			new About().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
		
		relativeChangePwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, ChangePasswordActivity.class);
				startActivity(intent);
			}
		});
		
	}//onCreate
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleImage.setVisibility(View.GONE);
		titleText.setText("About");
		
		footer_frg = new FooterFragment();
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.about_footer, footer_frg);
		fragmentTran.commit();
		
		ImageView titleBack = (ImageView) findViewById(R.id.title_back);
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}//setTitleBarAndFooter
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		footer_frg.about.setImageResource(R.drawable.about_icon_pink);
	}
	
	private class About extends AsyncTask<Void, Void, Void> {
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
				jObj = api.about(pref.getSessionToken());
				
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
					JSONObject json = jObj.getJSONObject("data");
					User user = new User(json);
//					JSONArray jarray = jObj.getJSONArray("data");
//					JSONObject json = jarray.getJSONObject(0);
//					User user = new User(json);
					
					showUser(user);
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
	}//About
	
	private void showUser(User user) {
		// TODO Auto-generated method stub
		txtEmail.setText(user.getEmail());
		txtUserName.setText(user.getUserName());
		txtPhone.setText(user.getPhoneNo());
		
		if(user.getType() == 2) {
			txtUserType.setText("Knight Frank Agent");
			layoutUser.setVisibility(View.GONE);
			txtCeaNo.setText(user.getCeaNo());
			txtCompany.setText(user.getCompany());
		}
		else if(user.getType() == 3) {
			txtUserType.setText("Public User");
			layoutAgent.setVisibility(View.GONE);
			txtBuildingNo.setText(user.getBuildingNo());
			txtBuildingName.setText(user.getBuildingName());
			txtStreet.setText(user.getStreet());
		}
		else {
			txtUserType.setText("Public Agent");
			layoutUser.setVisibility(View.GONE);
			txtCeaNo.setText(user.getCeaNo());
			txtCompany.setText(user.getCompany());
		}
	}
}
