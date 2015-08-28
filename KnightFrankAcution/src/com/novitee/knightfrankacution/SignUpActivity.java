package com.novitee.knightfrankacution;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.api.KnightFrankAPI;
import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.util.Preferences;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends BaseFragmentActivity {
	
	Context context = this;
	FragmentTransaction fragmentTran;
	
	EditText edit_email;
	EditText edit_username;
	EditText edit_password;
	EditText edit_phone;
	EditText edit_building_no;
	EditText edit_building_name;
	EditText edit_street;
	EditText edit_unit_no;
	EditText edit_postal_code;
	EditText edit_cea_no;
	EditText edit_company;
	Spinner user_type;
	Button signup;
	ImageView back;
	CheckBox chkTerms;
	CheckBox chkSubscribe;
	TextView txtTerms;
	
	LinearLayout user_layout;
	LinearLayout agent_layout;
	
	String email = "";
	String username = "";
	String password = "";
	String phone = "";
	String building_no = "";
	String building_name = "";
	String street = "";
	String unit_no = "";
	String postal_code = "";
	String cea_no = "";
	String company = "";
	String type = "";
	String userType = "3";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.login_frame, new FacebookLoginFragment());
		fragmentTran.commit();
		
		edit_email = (EditText) findViewById(R.id.edit_email);
		edit_username = (EditText) findViewById(R.id.edit_username);
		edit_password = (EditText) findViewById(R.id.edit_password);
		edit_phone = (EditText) findViewById(R.id.edit_phone);
		edit_building_no = (EditText) findViewById(R.id.edit_building_no);
		edit_building_name = (EditText) findViewById(R.id.edit_building_name);
		edit_street = (EditText) findViewById(R.id.edit_street);
		edit_unit_no = (EditText) findViewById(R.id.edit_unit_no);
		edit_postal_code = (EditText) findViewById(R.id.edit_postal_code);
		edit_cea_no = (EditText) findViewById(R.id.edit_cea_no);
		edit_company = (EditText) findViewById(R.id.edit_company);
		user_type = (Spinner) findViewById(R.id.spinner_user_type);
		signup = (Button) findViewById(R.id.btnSignUp);
		user_layout = (LinearLayout) findViewById(R.id.user_box);
		agent_layout = (LinearLayout) findViewById(R.id.agent_box);
		back = (ImageView) findViewById(R.id.signup_back);
		chkTerms = (CheckBox) findViewById(R.id.chk_terms);
		chkSubscribe = (CheckBox) findViewById(R.id.chk_subscribe);
		txtTerms = (TextView) findViewById(R.id.txtTerms);
		
		if(connectionManager.isConnected()) {
			new GetTerms().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
		}
		
		//testing
		edit_email.setText("user@gmail.com");
		edit_username.setText("user");
		edit_password.setText("user");
		edit_phone.setText("093333333333");
		edit_building_no.setText("33");
		edit_building_name.setText("Building");
		edit_street.setText("street");
		edit_unit_no.setText("666");
		edit_postal_code.setText("2345");
		edit_cea_no.setText("");
		edit_company.setText("");
		
		String pref_email = Preferences.getUserName(context);
		if(!pref_email.equals("")) {
			edit_email.setText(pref_email);
		}
		
		user_type.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				type = user_type.getSelectedItem().toString();
				if(type.equals("Public User")) {
					userType = "3";
					user_layout.setVisibility(View.VISIBLE);
					agent_layout.setVisibility(View.GONE);
				}
				else if(type.equals("Public Agent")) {
					userType = "4";
					user_layout.setVisibility(View.GONE);
					agent_layout.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//change to string
				email = edit_email.getText().toString();
				username = edit_username.getText().toString();
				password = edit_password.getText().toString();
				phone = edit_phone.getText().toString();
				building_no = edit_building_no.getText().toString();
				building_name = edit_building_name.getText().toString();
				street = edit_street.getText().toString();
				unit_no = edit_unit_no.getText().toString();
				postal_code = edit_postal_code.getText().toString();
				cea_no = edit_cea_no.getText().toString();
				company = edit_company.getText().toString();
				
				boolean validate_flag = true;
				//Validation
				if(type.equals("Public User")) { 
					if(email.equals("") || email.equals(null) ||
						username.equals("") || username.equals(null) ||
						password.equals("") || password.equals(null) ||
						phone.equals("") || phone.equals(null) ||
						building_no.equals("") || building_no.equals(null) ||
						building_name.equals("") || building_name.equals(null) ||
						street.equals("") || street.equals(null) ||
						unit_no.equals("") || unit_no.equals(null) ||
						postal_code.equals("") || postal_code.equals(null) ) {
						
							Toast.makeText(context, "You must fill all fields.", Toast.LENGTH_LONG).show();
							validate_flag = false;
					}
				}
				else {
					if(email.equals("") || email.equals(null) ||
						username.equals("") || username.equals(null) ||
						password.equals("") || password.equals(null) ||
						phone.equals("") || phone.equals(null) ||
						cea_no.equals("") || cea_no.equals(null) ||
						company.equals("") || company.equals(null) ) {
						
							Toast.makeText(context, "You must fill all fields.", Toast.LENGTH_LONG).show();
							validate_flag = false;
					}
				}
				
				//validate terms and conditions
				if(validate_flag == true) {
					if(chkTerms.isChecked() == false) {
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setTitle("Sign Up")
							   .setMessage("Please agree to our terms and conditions.")
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
					else {
						if(connectionManager.isConnected()) {
							new SignUp().execute();
						}
						else {
							Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
						}
					}
				}//validate_flag

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
	
	public class SignUp extends AsyncTask<Void, Void, Void> {
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
			KnightFrankAPI api = new KnightFrankAPI();
			try {
				String subscribe = "0";
				if(chkSubscribe.isChecked() == true) {
					subscribe = "1";
				}
				
				jObj = api.signUp(email, username, password, phone, building_no, building_name, street, unit_no, postal_code, cea_no, company, userType, subscribe, pref.getGenerateKey(context));
				
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
					Preferences.setUserName(context, email);
					Preferences.setPassword(context, password);
					
					Intent intent = new Intent(context, MainActivity.class);
					startActivity(intent);
				}
				else if(status == 2 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(context, "Unfortunately stopped", Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
	}//SignUp

	private class GetTerms extends AsyncTask<Void, Void, Void> {
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
				jObj = api.getTerms(pref.getGenerateKey(context));
				
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
			super.onPostExecute(result);
			if(pDialog.isShowing()){
				pDialog.dismiss();
			}
			
			try {
				int responseCode = jObj.getInt("statusCode");
				int status = jObj.getInt("status");	
				String message = jObj.getString("message");
				
				if(status == 1 && responseCode == 200){
					txtTerms.setText(message);
				}
				else if(status == 2 && responseCode == 401) {
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
	}//GetTerms

}
