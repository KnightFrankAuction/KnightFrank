package com.novitee.knightfrankacution.api;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.util.CommonConstants;

public class KnightFrankAPI {
	
	public static String GENERATE_KEY = CommonConstants.HOST + CommonConstants.GENERATE_KEY_URL;
	public static String LOGIN = CommonConstants.HOST + CommonConstants.LOGIN_URL;
	public static String NEXT_AUCTION = CommonConstants.HOST + CommonConstants.NEXT_AUCTION_URL;
	public static String LOGIN_FACEBOOK = CommonConstants.HOST + CommonConstants.LOGIN_FACEBOOK_URL;
	public static String SIGN_UP = CommonConstants.HOST + CommonConstants.SIGN_UP_URL;
	public static String AD = CommonConstants.HOST + CommonConstants.AD_URL;
	public static String AUCTION_DATE = CommonConstants.HOST + CommonConstants.AUCTION_DATE_URL;
	public static String AUCTION_LIST = CommonConstants.HOST + CommonConstants.AUCTION_LIST_URL;
	public static String PROPERTY_DETAIL = CommonConstants.HOST + CommonConstants.PROPERTY_DETAIL_URL;
	public static String FILTER = CommonConstants.HOST + CommonConstants.FILTER_URL;
	public static String ENQUIRY = CommonConstants.HOST + CommonConstants.ENQUIRY_URL;
	public static String SAVE_SHORTLIST = CommonConstants.HOST + CommonConstants.SAVE_SHORTLIST_URL;
	public static String DELETE_SHORTLIST = CommonConstants.HOST + CommonConstants.DELETE_SHORTLIST_URL;
	public static String GET_ALL_SHORTLIST = CommonConstants.HOST + CommonConstants.GET_ALL_SHORTLIST_URL;
	public static String STARBUY = CommonConstants.HOST + CommonConstants.STARBUY_URL;
	public static String NEWS = CommonConstants.HOST + CommonConstants.NEWS_URL;
	public static String INFO = CommonConstants.HOST + CommonConstants.INFO_URL;
	public static String TERMS = CommonConstants.HOST + CommonConstants.TERMS_URL;
	
	public JSONObject generateKey(String clientPlatform, String clientToken) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("clientPlatform", clientPlatform));
		params.add(new BasicNameValuePair("clientToken", clientToken));
		return new JSONParser().getJSONFromUrl(GENERATE_KEY, params, CommonConstants.POST);
	}
	
	public JSONObject login(String username, String password, String key) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("key", key));
		return new JSONParser().getJSONFromUrl(LOGIN, params, CommonConstants.POST);
	}
	
	public JSONObject getNextAuction() throws JSONException {
		return new JSONParser().getJSONFromUrl(NEXT_AUCTION, null, CommonConstants.GET);
	}
	
	public JSONObject loginWithFacebook(String facebook_id, String email, String key) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("facebook_id", facebook_id));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("key", key));
		return new JSONParser().getJSONFromUrl(LOGIN_FACEBOOK, params, CommonConstants.POST);
	}
	
	public JSONObject signUp(String email, String username, String password, String phone, String building_no, String building_name, String street, String unit_no, String postal_code, String cea_no, String company, String userType, String subscribe, String key) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("phone_no", phone));
		params.add(new BasicNameValuePair("user_type", userType));
		params.add(new BasicNameValuePair("cea_no", cea_no));
		params.add(new BasicNameValuePair("company", company));
		params.add(new BasicNameValuePair("building_no", building_no));
		params.add(new BasicNameValuePair("building_name", building_name));
		params.add(new BasicNameValuePair("street", street));
		params.add(new BasicNameValuePair("unit_no", unit_no));
		params.add(new BasicNameValuePair("postal_code", postal_code));
		params.add(new BasicNameValuePair("subscribe", subscribe));
		params.add(new BasicNameValuePair("key", key));
		return new JSONParser().getJSONFromUrl(SIGN_UP, params, CommonConstants.POST);
	}
	
	public JSONObject getAD() throws JSONException {
		return new JSONParser().getJSONFromUrl(AD, null, CommonConstants.GET);
	}
	
	public JSONObject getAuctionDate(String session_token, String auctionMonth) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("auction_month", auctionMonth));
		return new JSONParser().getJSONFromUrl(AUCTION_DATE, params, CommonConstants.POST);
//		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/112ws", null, CommonConstants.GET);
	}
	
	public JSONObject getAuctionList(String session_token, String date) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("auction_date", date));
		return new JSONParser().getJSONFromUrl(AUCTION_LIST, params, CommonConstants.POST);
//		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/1395g", null, CommonConstants.GET);
	}
	
	public JSONObject getPropertyDetail(String auctionDate) throws JSONException {
		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/104f4", null, CommonConstants.GET);
	}
	
	public JSONObject filter(String[] filterList, String min, String max, String session_token) throws JSONException {
		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/1395g", null, CommonConstants.GET);
	}
	
	public JSONObject sendEnquiryWithAttachment(String email, String contact, String name, String remark, String attachment) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("number", contact));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("remarks", remark));
		return new JSONParser().uploadFile(ENQUIRY, params, attachment);
	}
	
	public JSONObject sendEnquiry(String email, String contact, String name, String remark) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("number", contact));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("remarks", remark));
		params.add(new BasicNameValuePair("file", ""));
		return new JSONParser().getJSONFromUrl(ENQUIRY, params, CommonConstants.POST);
	}
	
	public JSONObject saveShortlist(String session_key, String pID) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		params.add(new BasicNameValuePair("property_id", pID));
		return new JSONParser().getJSONFromUrl(SAVE_SHORTLIST, params, CommonConstants.POST);
//		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/3dyxg", null, CommonConstants.GET);
	}
	
	public JSONObject deleteShortlist(String session_key, String pID) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		params.add(new BasicNameValuePair("property_id", pID));
		return new JSONParser().getJSONFromUrl(DELETE_SHORTLIST, params, CommonConstants.POST);
//		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/3dyxg", null, CommonConstants.GET);
	}
	
	public JSONObject getAllShortlist(String session_key) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		return new JSONParser().getJSONFromUrl(GET_ALL_SHORTLIST, params, CommonConstants.POST);
//		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/1395g", null, CommonConstants.GET);
	}
	
	public JSONObject getAllList(String session_key, String type) throws JSONException {
		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/1395g", null, CommonConstants.GET);
	}
	
	public JSONObject getNews(String session_key) throws JSONException {
		NEWS = NEWS.replace("{session_token}", session_key);
//		https://api.myjson.com/bins/1hdc6
		return new JSONParser().getJSONFromUrl(NEWS, null, CommonConstants.GET);
	}
	
	public JSONObject getInfo(String session_key) throws JSONException {
		NEWS = NEWS.replace("{session_token}", session_key);
//		https://api.myjson.com/bins/1hdc6
		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/22ea6", null, CommonConstants.GET);
	}
	
	public JSONObject getTerms(String generate_key) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("key", generate_key));
		return new JSONParser().getJSONFromUrl(TERMS, params, CommonConstants.POST);
	}
	
}
