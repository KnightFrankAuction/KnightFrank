package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.novitee.knightfrankacution.MenuActivity;
import com.novitee.knightfrankacution.R;
import com.novitee.knightfrankacution.SignUpActivity;
import com.novitee.knightfrankacution.base.BaseFragment;
import com.novitee.knightfrankacution.util.Preferences;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FacebookLoginFragment extends BaseFragment {
	
	LoginButton facebookLoginButton;
	CallbackManager callbackManager;
	ArrayList<String> userInfo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getActivity());
		callbackManager = CallbackManager.Factory.create();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.facebook_login_fragment, container, false);
		
		facebookLoginButton = (LoginButton) view.findViewById(R.id.login_btnFacebook);
		facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", "user_location","email"));
		facebookLoginButton.setFragment(this);
		
//		LoginManager.getInstance().logInWithPublishPermissions(getActivity(), Arrays.asList("publish_actions"));
		
		if(connectionManager.isConnected()) {
			facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
				
				@Override
				public void onSuccess(LoginResult result) {
					AccessToken fb_token = AccessToken.getCurrentAccessToken();
					if(fb_token !=null){
	
						GraphRequest request = GraphRequest.newMeRequest(fb_token, new GraphJSONObjectCallback() {
							
							@Override
							public void onCompleted(JSONObject data, GraphResponse response) {
								try {
									userInfo = new ArrayList<String>();
									userInfo.add( (data.optJSONObject("picture") != null ) ? data.getJSONObject("picture")
											.getJSONObject("data")
											.getString("url") : "");
									userInfo.add( (data.optString("name") != null ) ? data.getString("name") : "");
									userInfo.add( (data.optString("email") != null ) ? data.getString("email") : "");
									userInfo.add( (data.optString("gender") != null ) ? data.getString("gender") : "");
									userInfo.add( (data.optString("id") != null ) ? data.getString("id") : "");
									userInfo.add( (data.optString("token_for_business") != null ) ? data.getString("token_for_business") : "");
									userInfo.add( (data.optJSONObject("age_range") != null ) ? data.getJSONObject("age_range").getString("min") : "");
									Log.d("userinfo", "");
									
									new FacebookLogin().execute();
	
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
	
						});
			        	Bundle parameters = new Bundle();
			        	parameters.putString("fields", "id,name,token_for_business,email,gender,picture,age_range");
			        	request.setParameters(parameters);
			        	request.executeAsync();
					}
				}
				
				@Override
				public void onError(FacebookException error) {
					Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
				}
				
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
				}
	
			});
		}
		else {
			Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
	private class FacebookLogin extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Signing in......");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				JSONObject json = new JSONObject();
				json = api.generateKey("A", Preferences.getRegid(getActivity()));
				int json_responseCode = json.getInt("statusCode");
				int json_status = json.getInt("status");
				
				if(json_status == 1 && json_responseCode == 200){
					String client_key = json.getString("key");
					Preferences.setGenerateKey(getActivity(), client_key);
					jObj = new JSONObject();
//					jObj = api.loginWithFacebook("2345i123456783455678978765", client_key);
					jObj = api.loginWithFacebook(userInfo.get(4),userInfo.get(2), Preferences.getGenerateKey(getActivity()));
					
				}
				else if(json_status == 2 && json_responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
			if(pDialog.isShowing()){
				pDialog.dismiss();
			}
			
			try {
				int responseCode = jObj.getInt("statusCode");
				int status = jObj.getInt("status");				
				
				if(status == 1 && responseCode == 200){
					int user_type = jObj.getInt("type");
					String session_token = jObj.getString("session_token");
					
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
					
					Preferences.setUserName(context, userInfo.get(2));
					
					Intent intent = new Intent(getActivity(), SignUpActivity.class);
					startActivity(intent);
				}
				else {
					Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
	}

}
