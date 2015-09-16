package com.novitee.knightfrankacution.util;

public class CommonConstants {
	
	public static String POST="post";
	public static String GET="get";
	public static String PUT="put";
	
	/***********URL*********/
	public static String HOST = "http://128.199.142.149:8282/";
	
	/** Generate key URL**/
	public static final String GENERATE_KEY_URL = "api/v1/generatekey";
	
	/** Login URL**/
	public static final String LOGIN_URL = "api/v1/login";
	
	/** Next Auction URL**/
	public static final String NEXT_AUCTION_URL = "api/v1/nextevent";
	
	/** Sign Up URL**/
	public static final String SIGN_UP_URL = "api/v1/sign_up_public";
	
	/** Advertisements URL**/
	public static final String AD_URL = "api/v1/advertisements";
	
	/** Auction Listings URL**/
	public static final String AUCTION_DATE_URL = "api/v1/auction_month";
	public static final String AUCTION_LIST_URL = "api/v1/auction_date";
	
	/** Filter URL**/
	public static final String FILTER_URL = "api/v1/propertyfilter";
	public static final String DISTRICT_URL = "api/v1/district";
	
	/** Enquiry URL**/
	public static final String ENQUIRY_URL = "api/v1/sendenquiry";
	
	/** Shortlist URL**/
	public static final String SAVE_SHORTLIST_URL = "api/v1/save_shortlist";
	public static final String DELETE_SHORTLIST_URL = "api/v1/delete_shortlist";
	public static final String GET_ALL_SHORTLIST_URL = "api/v1/getshortlist";
	
	/** Starbuy / Project Listings URL**/
	public static final String LISTINGS_URL = "api/v1/listings";
	
	/** Info/News URL**/
	public static final String INFO_URL = "api/v1/news/";
	public static final String NEWS_URL = "api/v1/news/{session_token}";
	
	/** Terms URL**/
	public static final String TERMS_URL = "api/v1/terms";
	
	/** Search URL**/
	public static final String SEARCH_URL = "api/v1/search/{session_token}";
	
}

