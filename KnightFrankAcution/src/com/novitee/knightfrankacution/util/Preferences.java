package com.novitee.knightfrankacution.util;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

  private static Preferences pref;
  private static SharedPreferences mPreference;
  private static SharedPreferences.Editor mEditor;
  private Context mContext;
  
  public static String REGID="regid";
  public static String SESSION_TOKEN = "session_token";
  public static String GENERATE_KEY = "generate_key";
  public static String USER_TYPE = "user_type";
  public static String USER_NAME = "user_name";
  public static String PASSWORD = "";
  public static String AD_LIST = "ad_list";
  
  public Preferences(Context context) {
    this.mContext = context;
    mPreference = context.getSharedPreferences("KnightFrank", 0);
    mEditor = mPreference.edit();
  }

  public static synchronized Preferences getInstance(Context context) {
    if (pref == null) {
      pref = new Preferences(context);
    }

    return pref;
  }

  public void clearAll() {
    mEditor.clear();
    mEditor.commit();
  }

  public static String getRegid(Context context) {
  	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return prefs.getString(REGID, null);
  }

  public static void setRegid(Context context,String regid) {
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    prefs.edit().putString(REGID, regid).commit();
  }
  
  public String getSessionToken(Context context) {
  	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return prefs.getString(SESSION_TOKEN, null);
  }

  public static void setSessionToken(Context context,String session_token) {
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    prefs.edit().putString(SESSION_TOKEN, session_token).commit();
  }
  
  public static int getUserType(Context context) {
  	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return prefs.getInt(USER_TYPE, 0);
  }

  public static void setUserType(Context context,int user_type) {
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    prefs.edit().putInt(USER_TYPE, user_type).commit();
  }
  
  public static String getGenerateKey(Context context) {
  	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return prefs.getString(GENERATE_KEY, null);
  }

  public static void setGenerateKey(Context context,String generate_key) {
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    prefs.edit().putString(GENERATE_KEY, generate_key).commit();
  }
  
  public static String getUserName(Context context) {
  	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return prefs.getString(USER_NAME,"");
  }

  public static void setUserName(Context context,String username) {
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    prefs.edit().putString(USER_NAME, username).commit();
  }
  
  public static String getPassword(Context context) {
  	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return prefs.getString(PASSWORD, "");
  }

  public static void setPassword(Context context,String password) {
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    prefs.edit().putString(PASSWORD, password).commit();
  }
  
  
  public static Set<String> getAdList(Context context) {
  	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return prefs.getStringSet(AD_LIST, null);
  }

  public static void setAdList(Context context,Set<String> mList) {
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    prefs.edit().putStringSet(AD_LIST, mList).commit();
  }

}
