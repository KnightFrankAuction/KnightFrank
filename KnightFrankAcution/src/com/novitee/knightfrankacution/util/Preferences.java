package com.novitee.knightfrankacution.util;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

  private Preferences pref;
  private SharedPreferences mPreference;
  private SharedPreferences.Editor mEditor;
  private Context mContext;
  
  public static String REGID="regid";
  public String SESSION_TOKEN = "session_token";
  public static String GENERATE_KEY = "generate_key";
  public String USER_TYPE = "user_type";
  public String USER_NAME = "user_name";
  public String PASSWORD = "";
  public String AD_LIST = "ad_list";
  
  public Preferences(Context context) {
    this.mContext = context;
    mPreference = context.getSharedPreferences("KnightFrank", 0);
    mEditor = mPreference.edit();
  }
  
  public static Preferences getInstance(Context context){
      Preferences instance = new Preferences(context);
      instance.mContext = context;
      return  instance;
  }

  public void clearAll() {
//      PreferenceManager.getDefaultSharedPreferences(mContext).edit().clear().commit();
	  setUserName("");
	  setPassword("");
	  setSessionToken("");
	  setUserType(0);
  }

  public String getRegid() {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    return prefs.getString(REGID, null);
  }

  public void setRegid(String regid) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    prefs.edit().putString(REGID, regid).commit();
  }
  
  public String getSessionToken() {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    return prefs.getString(SESSION_TOKEN, null);
  }

  public void setSessionToken(String session_token) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    prefs.edit().putString(SESSION_TOKEN, session_token).commit();
  }
  
  public int getUserType() {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    return prefs.getInt(USER_TYPE, 0);
  }

  public void setUserType(int user_type) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    prefs.edit().putInt(USER_TYPE, user_type).commit();
  }
  
  public String getGenerateKey() {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    return prefs.getString(GENERATE_KEY, null);
  }

  public void setGenerateKey(String generate_key) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    prefs.edit().putString(GENERATE_KEY, generate_key).commit();
  }
  
  public String getUserName() {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    return prefs.getString(USER_NAME,"");
  }

  public void setUserName(String username) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    prefs.edit().putString(USER_NAME, username).commit();
  }
  
  public String getPassword() {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    return prefs.getString(PASSWORD, "");
  }

  public void setPassword(String password) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    prefs.edit().putString(PASSWORD, password).commit();
  }
  
  
  public Set<String> getAdList() {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    return prefs.getStringSet(AD_LIST, null);
  }

  public void setAdList(Set<String> mList) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    prefs.edit().putStringSet(AD_LIST, mList).commit();
  }

}
