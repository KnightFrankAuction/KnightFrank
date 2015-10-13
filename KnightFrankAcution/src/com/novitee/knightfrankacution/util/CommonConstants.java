package com.novitee.knightfrankacution.util;

public class CommonConstants {
	
	public static String POST="post";
	public static String GET="get";
	public static String PUT="put";
	
	/***********URL*********/
	public static String HOST = "http://128.199.142.149:8282/";
	
	/** default video image URL **/
//	public static String VIDEO_IMAGE = "http://img.youtube.com/vi/VIDEO_ID/default.jpg";
	public static String VIDEO_IMAGE = "property/youtube_generic.jpg";
	
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
	
	/** Project Listings URL**/
	public static final String PROJECT_LISTINGS_DATE_URL = "api/v1/project_listings_month";
	public static final String PROJECT_LISTINGS_LIST_URL = "api/v1/project_listings_date";
	
	/** Starbuys URL**/
	public static final String STARBUYS_DATE_URL = "api/v1/starbuys_month";
	public static final String STARBUYS_LIST_URL = "api/v1/starbuys_date";
	
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
	public static final String INFO_URL = "api/v1/info";
	public static final String NEWS_URL = "api/v1/news/{session_token}";
	
	/** Terms URL**/
	public static final String TERMS_URL = "api/v1/terms";
	
	/** Search URL**/
	public static final String SEARCH_URL = "api/v1/search/{session_token}";
	
	/** Documents URL**/
	public static final String DOCUMENT_URL = "api/v1/sample_pdf";
	public static final String AGT_URL = "api/v1/agt_auction";
	public static final String CEA_EXCLUSIVE_ESTATE_URL = "api/v1/exclusive_estate";
	public static final String EXCLUSIVE_AUTHORITY_URL = "api/v1/excluseive_authority";
	public static final String NON_EXCLUSIVE_CEA_URL = "api/v1/non_exclusive_cea";
	public static final String NON_EXCLUSIVE_AUTHORITY_URL = "api/v1/non_exclusive_authority";
	public static final String OFFER_TO_PURCHASE_URL = "api/v1/offer_to_purchase";
	
	/** Send Email Status URL**/
	public static final String STATUS_AGT_URL = "api/v1/status_agt_auction";
	public static final String STATUS_CEA_EXCLUSIVE_ESTATE_URL = "api/v1/status_exclusive_estate";
	public static final String STATUS_EXCLUSIVE_AUTHORITY_URL = "api/v1/status_excluseive_authority";
	public static final String STATUS_NON_EXCLUSIVE_CEA_URL = "api/v1/status_non_exclusive_cea";
	public static final String STATUS_NON_EXCLUSIVE_AUTHORITY_URL = "api/v1/status_non_exclusive_authority";
	public static final String STATUS_OFFER_TO_PURCHASE_URL = "api/v1/status_offer_to_purchase";
	
	/** Notification URL**/
	public static final String NOTIFICATION_URL = "api/v1/notification_setting";
	
	/** About URL**/
	public static final String ABOUT_URL = "api/v1/about";
	
	/** Password URL**/
	public static final String CHANGE_PASSWORD_URL = "api/v1/change_password";
	public static final String FORGET_PASSWORD_URL = "api/v1/forget_password";
	
	/** Private Listings URL**/
	public static final String PRIVATE_LISTINGS_URL = "api/v1/private_listing";
	
	/** Splash Screen URL**/
	public static final String SPLASH_URL = "api/v1/splash_screen";
	public static final String LOGO_URL = "api/v1/kf_logo";
	
}

