package com.novitee.knightfrankacution;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragment;
import com.novitee.knightfrankacution.util.Preferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends BaseFragment {
	
	EditText username;
	EditText password;
	Button btnLogin;
	
	String userName, passWord;
	
	FragmentTransaction fragmentTran;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.login_fragment, container, false);
		
		fragmentTran = getActivity().getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.login_frame, new FacebookLoginFragment());
		fragmentTran.commit();
		
		username = (EditText) view.findViewById(R.id.user_name);
		password = (EditText) view.findViewById(R.id.password);
		btnLogin = (Button) view.findViewById(R.id.btnLogIn);

		String pref_username = Preferences.getUserName(getActivity());
		if(!pref_username.equals("")) {
			username.setText(pref_username);
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
					Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		return view;
	}
	
	public class Login extends AsyncTask<Void, Void, Void> {
		
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
					
					Preferences.setUserName(getActivity(), userName);
					Preferences.setPassword(getActivity(), passWord);
					Preferences.setUserType(getActivity(), user_type);
					Preferences.setSessionToken(getActivity(), session_token);
					
					Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
					
					Intent intent = new Intent(getActivity(), MenuActivity.class);
					startActivity(intent);
				}
				else if(status == 2 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				}
				else if(status == 3 && responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
					
					Preferences.setUserName(context, userName);
					
					Intent intent = new Intent(getActivity(), SignUpActivity.class);
					startActivity(intent);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
	}

}
