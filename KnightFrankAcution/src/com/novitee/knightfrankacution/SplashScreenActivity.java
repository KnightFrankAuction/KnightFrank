package com.novitee.knightfrankacution;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import com.novitee.knightfrankacution.base.BaseActivity;
import com.novitee.knightfrankacution.model.Photo;
import com.novitee.knightfrankacution.util.CommonConstants;
import com.squareup.picasso.Picasso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashScreenActivity extends BaseActivity {
    
    Context context = this;
    
    ImageView imgLogo;
    
    //GCM
    String regId;
    GoogleCloudMessaging gcm;
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_ON_SERVER_EXPIRATION_TIME = "onServerExpirationTimeMs";
    public static final long REGISTRATION_EXPIRY_TIME_MS = 1000 * 3600 * 24 * 7;
    boolean call_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.novitee.knightfrankacution", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        
        imgLogo = (ImageView) findViewById(R.id.logo);
        if(connectionManager.isConnected()) {
        	new GetLogo().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
			finish();
		}

        if (pref.getRegid() == null) {
                regId = getRegistrationId(getApplicationContext());
//                regId = "1856c20b587c402c00e0b1ff901ebcd5d3d";
                
                gcm = GoogleCloudMessaging.getInstance(this);
                
                if (regId.length() == 0) {
                    new registerBackground().execute();
//                  registerBackground();
                } else {
                    Log.i("registration id", "registration id =====  " + regId);
                    
                    new GenerateKey().execute();
                    
                    pref.setRegid(regId);
                    /*Intent i = new Intent();
                    i.setClass(context, MainActivity.class);
                    finish();
                    startActivity(i);*/
                    new GetData().execute();
                }
         } else {
            Thread logoTimer = new Thread() {
                public void run() {
                    try {
                        int logoTimer = 0;
                        while (logoTimer < 3000) {
                            sleep(100);
                            logoTimer = logoTimer + 100;

                        }
//                        call_flag = true;
                        new GenerateKey().execute();
                        
//                        Intent i = new Intent();
//                        i.setClass(context, MainActivity.class);
//                        finish();
//                        startActivity(i);
                        new GetData().execute();
                        
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        finish();
                    }
                }
            };
            logoTimer.start();
        }
    }
    
    private class registerBackground extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }

                regId = gcm.register(com.novitee.knightfrankacution.util.CommonUtilities.SENDER_ID);
                setRegistrationId(context, regId);
                
            } catch (IOException ex) {
                ex.getMessage();
            }

            return null;
        }
        
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            
            /*Intent i = new Intent();
            i.setClass(context, MainActivity.class)
            finish();
            startActivity(i);*/
            new GetData().execute();
        }
        
    }
    
    /**
     * Gets the current registration id for application on GCM service.
     * <p>
     * If result is empty, the registration has failed.
     *
     * @return registration id, or empty string if the registration is not
     *         complete.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.length() == 0) {
            Log.v("registration id", "Registration not found.");
            return "";
        }
        
        // check if app was updated; if so, it must clear registration id to
        // avoid a race condition if GCM sends a message
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion || isRegistrationExpired()) {
            Log.v("registration id", "App version changed or registration expired.");
            return "";
        }
        return registrationId;
    }
    
    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    }
    
    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        return 6;
    }
    
    /**
     * Checks if the registration has expired.
     *
     * <p>To avoid the scenario where the device sends the registration to the
     * server but the server loses it, the app developer may choose to re-register
     * after REGISTRATION_EXPIRY_TIME_MS.
     *
     * @return true if the registration has expired.
     */
    private boolean isRegistrationExpired() {
        final SharedPreferences prefs = getGCMPreferences(context);
        // checks if the information is not stale
        long expirationTime = prefs.getLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, -1);
        return System.currentTimeMillis() > expirationTime;
    }
    
    /**
     * Stores the registration id, app versionCode, and expiration time in the
     * application's {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration id
     */
    private void setRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.v("registration id", "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        long expirationTime = System.currentTimeMillis() + REGISTRATION_EXPIRY_TIME_MS;

        Log.v("registration id", "Setting registration expiry time to " +
                new Timestamp(expirationTime));
        editor.putLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, expirationTime);
        editor.commit();
        
        new GenerateKey().execute();

        pref.setRegid(regId);
    }
    
    private class GenerateKey extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {
                JSONObject json = new JSONObject();

                json = api.generateKey("A", pref.getRegid());
                int json_responseCode = json.getInt("statusCode");
                int json_status = json.getInt("status");
                
                if(json_status == 1 && json_responseCode == 200){
                    String client_key = json.getString("key");
                    pref.setGenerateKey(client_key);
                    
                    if(call_flag == true) {
                    	new GetData().execute();
                    }

                }
                else if(json_status == 2 && json_responseCode == 401) {
                    String message = json.getString("message");
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        
    }//GenerateKey
    
    private class GetData extends AsyncTask<Void, Void, Void> {
    	
    	@Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {
                JSONObject json = new JSONObject();

                json = api.getData(pref.getGenerateKey());
                int json_responseCode = json.getInt("statusCode");
                int json_status = json.getInt("status");
                
                if(json_status == 1 && json_responseCode == 200){
                	//get Logo
                	String pinkLogo = json.getString("pink_logo");
                	pref.setLogo(pinkLogo);
                	
                	//get Background photo
                	JSONArray jArray = json.getJSONArray("bg_photo");
                	ArrayList<Photo> bgList = new ArrayList<Photo>();
                	Photo photo;
                	for (int i = 0; i < jArray.length(); i++) {
						photo = new Photo(jArray.getJSONObject(i));
						bgList.add(photo);
					}
                	
                	Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                	intent.putExtra("BgPhoto", bgList);
                	startActivity(intent);
                	
                }
                else if(json_status == 2 && json_responseCode == 401) {
                    String message = json.getString("message");
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    	
    }
    
    private class GetLogo extends AsyncTask<Void, Void, Void> {
    	JSONObject json;
    	
    	@Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {
               json = new JSONObject();
               json = api.getLogo();
                
                
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
	    		int json_responseCode = json.getInt("statusCode");
	            int json_status = json.getInt("status");
			
	            if(json_status == 1 && json_responseCode == 200){
	            	//get Logo
	            	String logo_url = CommonConstants.HOST + json.getString("white_logo");
	    			Picasso.with(context).load(logo_url).into(imgLogo);
	            }
	            else if(json_status == 2 && json_responseCode == 401) {
	                String message = json.getString("message");
	                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	            }
	            else {
	                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
	            }
            
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
}
