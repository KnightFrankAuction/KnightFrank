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
	public static String SIGN_UP = CommonConstants.HOST + CommonConstants.SIGN_UP_URL;
	public static String AD = CommonConstants.HOST + CommonConstants.AD_URL;
	public static String AUCTION_DATE = CommonConstants.HOST + CommonConstants.AUCTION_DATE_URL;
	public static String AUCTION_LIST = CommonConstants.HOST + CommonConstants.AUCTION_LIST_URL;
	public static String PROJECT_LISTINGS_DATE = CommonConstants.HOST + CommonConstants.PROJECT_LISTINGS_DATE_URL;
	public static String PROJECT_LISTINGS_LIST = CommonConstants.HOST + CommonConstants.PROJECT_LISTINGS_LIST_URL;
	public static String STARBUYS_DATE = CommonConstants.HOST + CommonConstants.STARBUYS_DATE_URL;
	public static String STARBUYS_LIST = CommonConstants.HOST + CommonConstants.STARBUYS_LIST_URL;
	public static String DISTRICT = CommonConstants.HOST + CommonConstants.DISTRICT_URL;
	public static String FILTER = CommonConstants.HOST + CommonConstants.FILTER_URL;
	public static String ENQUIRY = CommonConstants.HOST + CommonConstants.ENQUIRY_URL;
	public static String SAVE_SHORTLIST = CommonConstants.HOST + CommonConstants.SAVE_SHORTLIST_URL;
	public static String DELETE_SHORTLIST = CommonConstants.HOST + CommonConstants.DELETE_SHORTLIST_URL;
	public static String GET_ALL_SHORTLIST = CommonConstants.HOST + CommonConstants.GET_ALL_SHORTLIST_URL;
	public static String LISTINGS = CommonConstants.HOST + CommonConstants.LISTINGS_URL;
	public static String NEWS = CommonConstants.HOST + CommonConstants.NEWS_URL;
	public static String INFO = CommonConstants.HOST + CommonConstants.INFO_URL;
	public static String TERMS = CommonConstants.HOST + CommonConstants.TERMS_URL;
	public static String SEARCH = CommonConstants.HOST + CommonConstants.SEARCH_URL;
	public static String DOCUMENT = CommonConstants.HOST + CommonConstants.DOCUMENT_URL;
	public static String AGT = CommonConstants.HOST + CommonConstants.AGT_URL;
	public static String CEA_EXCLUSIVE_ESTATE = CommonConstants.HOST + CommonConstants.CEA_EXCLUSIVE_ESTATE_URL;
	public static String EXCLUSIVE_AUTHORITY = CommonConstants.HOST + CommonConstants.EXCLUSIVE_AUTHORITY_URL;
	public static String NON_EXCLUSIVE_CEA = CommonConstants.HOST + CommonConstants.NON_EXCLUSIVE_CEA_URL;
	public static String NON_EXCLUSIVE_AUTHORITY = CommonConstants.HOST + CommonConstants.NON_EXCLUSIVE_AUTHORITY_URL;
	public static String OFFER_TO_PURCHASE = CommonConstants.HOST + CommonConstants.OFFER_TO_PURCHASE_URL;
	public static String NOTIFICATION = CommonConstants.HOST + CommonConstants.NOTIFICATION_URL;
	public static String ABOUT = CommonConstants.HOST + CommonConstants.ABOUT_URL;
	public static String CHANGE_PASSWORD = CommonConstants.HOST + CommonConstants.CHANGE_PASSWORD_URL;
	public static String PRIVATE_LISTINGS = CommonConstants.HOST + CommonConstants.PRIVATE_LISTINGS_URL;
	public static String SPLASH = CommonConstants.HOST + CommonConstants.SPLASH_URL;
	public static String FORGET_PASSWORD = CommonConstants.HOST + CommonConstants.FORGET_PASSWORD_URL;
	public static String LOGO = CommonConstants.HOST + CommonConstants.LOGO_URL;
	
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
	}
	
	public JSONObject getAuctionList(String session_token, String date, int count) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("auction_date", date));
		params.add(new BasicNameValuePair("page_count", String.valueOf(count)));
		return new JSONParser().getJSONFromUrl(AUCTION_LIST, params, CommonConstants.POST);
//		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/14dga", null, CommonConstants.GET);
	}
	
	public JSONObject getProjectListingsDate(String session_token, String month) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("project_listings_month", month));
		return new JSONParser().getJSONFromUrl(PROJECT_LISTINGS_DATE, params, CommonConstants.POST);
	}
	
	public JSONObject getProjectListingsList(String session_token, String date, int count) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("project_date", date));
		params.add(new BasicNameValuePair("page_count", String.valueOf(count)));
		return new JSONParser().getJSONFromUrl(PROJECT_LISTINGS_LIST, params, CommonConstants.POST);
	}
	
	public JSONObject getStarbuysDate(String session_token, String month) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("starbuys_month", month));
		return new JSONParser().getJSONFromUrl(STARBUYS_DATE, params, CommonConstants.POST);
	}
	
	public JSONObject getStarbuysList(String session_token, String date, int count) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("starbuy_date", date));
		params.add(new BasicNameValuePair("page_count", String.valueOf(count)));
		return new JSONParser().getJSONFromUrl(STARBUYS_LIST, params, CommonConstants.POST);
	}
	
	public JSONObject getDistrict(String key) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("key", key));
		return new JSONParser().getJSONFromUrl(DISTRICT, params, CommonConstants.POST);
	}
	
	public JSONObject filter(String session_token, String filter, String district, String min_size, String max_size, String min_value, String max_value, String sqft, String psf, int page_count) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("building_type", filter));
		params.add(new BasicNameValuePair("district", district));
		params.add(new BasicNameValuePair("min_size", min_size));
		params.add(new BasicNameValuePair("max_size", max_size));
		params.add(new BasicNameValuePair("min_value", min_value));
		params.add(new BasicNameValuePair("max_value", max_value));
		params.add(new BasicNameValuePair("sqft_sort", sqft));
		params.add(new BasicNameValuePair("psf_sort", psf));
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("page_count", String.valueOf(page_count)));
		return new JSONParser().getJSONFromUrl(FILTER, params, CommonConstants.POST);
	}
	
	public JSONObject sendEnquiryWithAttachment(String email, String contact, String name, String remark, String attachment, String session) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("number", contact));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("remarks", remark));
		params.add(new BasicNameValuePair("session_token", session));
		return new JSONParser().uploadFile(ENQUIRY, params, attachment);
	}
	
	public JSONObject sendEnquiry(String email, String contact, String name, String remark, String session) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("number", contact));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("remarks", remark));
		params.add(new BasicNameValuePair("file", ""));
		params.add(new BasicNameValuePair("session_token", session));
		return new JSONParser().getJSONFromUrl(ENQUIRY, params, CommonConstants.POST);
	}
	
	public JSONObject saveShortlist(String session_key, String pID) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		params.add(new BasicNameValuePair("property_id", pID));
		return new JSONParser().getJSONFromUrl(SAVE_SHORTLIST, params, CommonConstants.POST);
	}
	
	public JSONObject deleteShortlist(String session_key, String pID) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		params.add(new BasicNameValuePair("property_id", pID));
		return new JSONParser().getJSONFromUrl(DELETE_SHORTLIST, params, CommonConstants.POST);
//		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/3dyxg", null, CommonConstants.GET);
	}
	
	public JSONObject getAllShortlist(String session_key, int count) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		params.add(new BasicNameValuePair("page_count", String.valueOf(count)));
		return new JSONParser().getJSONFromUrl(GET_ALL_SHORTLIST, params, CommonConstants.POST);
//		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/1395g", null, CommonConstants.GET);
	}
	
	public JSONObject getAllList(String session_key, String type, int count) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		params.add(new BasicNameValuePair("type", type));
		params.add(new BasicNameValuePair("page_count", String.valueOf(count)));
		return new JSONParser().getJSONFromUrl(LISTINGS, params, CommonConstants.POST);
//		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/1395g", null, CommonConstants.GET);
	}
	
	public JSONObject getNews(String session_key) throws JSONException {
		NEWS = NEWS.replace("{session_token}", session_key);
//		https://api.myjson.com/bins/1hdc6
		return new JSONParser().getJSONFromUrl(NEWS, null, CommonConstants.GET);
	}
	
	public JSONObject getInfo(String session_token) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		return new JSONParser().getJSONFromUrl(INFO, params, CommonConstants.POST);
//		return new JSONParser().getJSONFromUrl("https://api.myjson.com/bins/22ea6", null, CommonConstants.GET);
	}
	
	public JSONObject getTerms(String generate_key) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("key", generate_key));
		return new JSONParser().getJSONFromUrl(TERMS, params, CommonConstants.POST);
	}
	
	public JSONObject getSearchData(String session_token) throws JSONException {
		SEARCH = SEARCH.replace("{session_token}", session_token);
		return new JSONParser().getJSONFromUrl(SEARCH, null, CommonConstants.GET);
	}
	
	public JSONObject Search(String session_token, String building_name, String listing_type, String building_type, String type_of_sale, String bedroom, String bathroom, String min_size, String max_price, String listed_on, int page_count) throws JSONException {
		SEARCH = SEARCH.replace("{session_token}", session_token);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("building_name", building_name));
		params.add(new BasicNameValuePair("listing_type", listing_type));
		params.add(new BasicNameValuePair("building_type", building_type));
		params.add(new BasicNameValuePair("type_of_sale", type_of_sale));
		params.add(new BasicNameValuePair("bedroom", bedroom));
		params.add(new BasicNameValuePair("bathroom", bathroom));
		params.add(new BasicNameValuePair("min_size", min_size));
		params.add(new BasicNameValuePair("max_price", max_price));
		params.add(new BasicNameValuePair("created_date", listed_on));
		params.add(new BasicNameValuePair("page_count", String.valueOf(page_count)));
		return new JSONParser().getJSONFromUrl(SEARCH, params, CommonConstants.POST);
	}
	
	public JSONObject getSamplePDF(String session_key) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		return new JSONParser().getJSONFromUrl(DOCUMENT, params, CommonConstants.POST);
	}
	
	public JSONObject agtAuction(
			String session_token, String correspondant_date, String postal_code, String address_1, String address_2,
			String building_name, String display_partner_agent, String agent_name, String agent_ic, String agent_agency,
			String date_of_auction, String auction_venue, String time, String display_comission_fee, String fees_information,
			String display_service_fee, String service_fees_information, String display_forfeiture, String forfeiture_information, String display_sole_rights,
			String sole_rights_information, String kfa_agent_name, String kfa_agent_title, String kfa_agent_cea,
			String kfa_agent_email, String kfa_agent_did, String kfa_agent_hp, String appointment_name, String display_reserve_price,
			String reserve_price, String display_solicitors, String solicitors_attn, String solicitors_postal_code, String solicitors_address_1,
			String solicitors_address_2, String solicitors_building_name, String solicitors_contact_no, String owner1_name, String owner1_ic,
			String mailing_postal_code, String mailing_address_1, String mailing_address_2, String mailing_building_name,
			String contact_no, String email, String current_date, String indiminty_name,
			String on_behalf_of, List<NameValuePair> signList
				) throws JSONException {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("correspondant_date", correspondant_date));
		params.add(new BasicNameValuePair("postal_code", postal_code));
		params.add(new BasicNameValuePair("address_1", address_1));
		params.add(new BasicNameValuePair("address_2", address_2));
		params.add(new BasicNameValuePair("building_name", building_name));
		params.add(new BasicNameValuePair("display_partner_agent", display_partner_agent));
		params.add(new BasicNameValuePair("agent_name", agent_name));
		params.add(new BasicNameValuePair("agent_ic", agent_ic));
		params.add(new BasicNameValuePair("agent_agency", agent_agency));
		params.add(new BasicNameValuePair("date_of_auction", date_of_auction));
		params.add(new BasicNameValuePair("auction_venue", auction_venue));
		params.add(new BasicNameValuePair("time", time));
		params.add(new BasicNameValuePair("display_comission_fee", display_comission_fee));
		params.add(new BasicNameValuePair("fees_information", fees_information));
		params.add(new BasicNameValuePair("display_service_fee", display_service_fee));
		params.add(new BasicNameValuePair("service_fees_information", service_fees_information));
		params.add(new BasicNameValuePair("display_forfeiture", display_forfeiture));
		params.add(new BasicNameValuePair("forfeiture_information", forfeiture_information));
		params.add(new BasicNameValuePair("display_sole_rights", display_sole_rights));
		params.add(new BasicNameValuePair("sole_rights_information", sole_rights_information));
		params.add(new BasicNameValuePair("kfa_agent_name", kfa_agent_name));
		params.add(new BasicNameValuePair("kfa_agent_title", kfa_agent_title));
		params.add(new BasicNameValuePair("kfa_agent_cea", kfa_agent_cea));
		params.add(new BasicNameValuePair("kfa_agent_email", kfa_agent_email));
		params.add(new BasicNameValuePair("kfa_agent_did", kfa_agent_did));
		params.add(new BasicNameValuePair("kfa_agent_hp", kfa_agent_hp));
		params.add(new BasicNameValuePair("appointment_name", appointment_name));
		params.add(new BasicNameValuePair("display_reserve_price", display_reserve_price));
		params.add(new BasicNameValuePair("reserve_price", reserve_price));
		params.add(new BasicNameValuePair("display_solicitors", display_solicitors));
		params.add(new BasicNameValuePair("solicitors_attn", solicitors_attn));
		params.add(new BasicNameValuePair("solicitors_postal_code", solicitors_postal_code));
		params.add(new BasicNameValuePair("solicitors_address_1", solicitors_address_1));
		params.add(new BasicNameValuePair("solicitors_address_2", solicitors_address_2));
		params.add(new BasicNameValuePair("solicitors_building_name", solicitors_building_name));
		params.add(new BasicNameValuePair("solicitors_contact_no", solicitors_contact_no));
		params.add(new BasicNameValuePair("owner1_name", owner1_name));
		params.add(new BasicNameValuePair("owner1_ic", owner1_ic));
		params.add(new BasicNameValuePair("mailing_postal_code", mailing_postal_code));
		params.add(new BasicNameValuePair("mailing_address_1", mailing_address_1));
		params.add(new BasicNameValuePair("mailing_address_2", mailing_address_2));
		params.add(new BasicNameValuePair("mailing_building_name", mailing_building_name));
		params.add(new BasicNameValuePair("contact_no", contact_no));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("current_date", current_date));
		params.add(new BasicNameValuePair("indiminty_name", indiminty_name));
		params.add(new BasicNameValuePair("on_behalf_of", on_behalf_of));
		
		return new JSONParser().uploadPdfFile(AGT, params, signList);
	}	
	
	public JSONObject CeaExclusiveEstate(
            String session_token, String type_of_agreement, String agreement_date, String seller1_name, String seller1_ic,
            String seller1_postal_code, String seller1_address1, String seller1_address2, String seller1_building_name, String seller2_name,
            String seller2_ic, String seller2_postal_code, String seller2_address1, String seller2_address2, String seller2_building_name,
            String seller3_name, String seller3_ic, String seller3_postal_code, String seller3_address1, String seller3_address2,
            String seller3_building_name, String seller4_name, String seller4_ic, String seller4_postal_code, String seller4_address1, 
            String seller4_address2, String seller4_building_name, String agent_name, String agent_licence, String agent_postal_code,
            String agent_address1, String agent_address2, String agent_building_name, String property_postal_code, String property_address1,
            String property_address2, String property_building_name, String commencement_date, String expiry_date, String price_in_words, 
            String price_in_number, String comission_dollar, String comission_percentage, String gst_payable, String gst_type, 
            String conflict_of_interest, String conflict_of_interest_detail, String co_broke, String seller_warrant, String another_sale_person, 
            String another_sale_person_detail, String bank_referral_fees, String seller1_interpreter_name, 
            String seller1_interpreter_ic, String seller2_interpreter_name, String seller2_interpreter_ic, 
            String seller3_interpreter_name, String seller3_interpreter_ic, 
            String seller4_interpreter_name, String seller4_interpreter_ic, String sale_person_name, 
            String sale_person_ic, String sale_person_postal_code, String sale_person_address1, String sale_person_address2, String sale_person_building_name, 
            String sale_person_cea, String sale_person_tel, List<NameValuePair> signList
                ) throws JSONException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("session_token", session_token));
        params.add(new BasicNameValuePair("type_of_agreement", type_of_agreement));
        params.add(new BasicNameValuePair("agreement_date", agreement_date));
        params.add(new BasicNameValuePair("seller1_name", seller1_name));
        params.add(new BasicNameValuePair("seller1_ic", seller1_ic));
        params.add(new BasicNameValuePair("seller1_postal_code", seller1_postal_code));
        params.add(new BasicNameValuePair("seller1_address1", seller1_address1));
        params.add(new BasicNameValuePair("seller1_address2", seller1_address2));
        params.add(new BasicNameValuePair("seller1_building_name", seller1_building_name));
        params.add(new BasicNameValuePair("seller2_name", seller2_name));
        params.add(new BasicNameValuePair("seller2_ic", seller2_ic));
        params.add(new BasicNameValuePair("seller2_postal_code", seller2_postal_code));
        params.add(new BasicNameValuePair("seller2_address1", seller2_address1));
        params.add(new BasicNameValuePair("seller2_address2", seller2_address2));
        params.add(new BasicNameValuePair("seller2_building_name", seller2_building_name));
        params.add(new BasicNameValuePair("seller3_name", seller3_name));
        params.add(new BasicNameValuePair("seller3_ic", seller3_ic));
        params.add(new BasicNameValuePair("seller3_postal_code", seller3_postal_code));
        params.add(new BasicNameValuePair("seller3_address1", seller3_address1));
        params.add(new BasicNameValuePair("seller3_address2", seller3_address2));
        params.add(new BasicNameValuePair("seller3_building_name", seller3_building_name));
        params.add(new BasicNameValuePair("seller4_name", seller4_name));
        params.add(new BasicNameValuePair("seller4_ic", seller4_ic));
        params.add(new BasicNameValuePair("seller4_postal_code", seller4_postal_code));
        params.add(new BasicNameValuePair("seller4_address1", seller4_address1));
        params.add(new BasicNameValuePair("seller4_address2", seller4_address2));
        params.add(new BasicNameValuePair("seller4_building_name", seller4_building_name));
        params.add(new BasicNameValuePair("agent_name", agent_name));
        params.add(new BasicNameValuePair("agent_licence", agent_licence));
        params.add(new BasicNameValuePair("agent_postal_code", agent_postal_code));
        params.add(new BasicNameValuePair("agent_address1", agent_address1));
        params.add(new BasicNameValuePair("agent_address2", agent_address2));
        params.add(new BasicNameValuePair("agent_building_name", agent_building_name));
        params.add(new BasicNameValuePair("property_postal_code", property_postal_code));
        params.add(new BasicNameValuePair("property_address1", property_address1));
        params.add(new BasicNameValuePair("property_address2", property_address2));
        params.add(new BasicNameValuePair("property_building_name", property_building_name));
        params.add(new BasicNameValuePair("commencement_date", commencement_date));
        params.add(new BasicNameValuePair("expiry_date", expiry_date));
        params.add(new BasicNameValuePair("price_in_words", price_in_words));
        params.add(new BasicNameValuePair("price_in_number", price_in_number));
        params.add(new BasicNameValuePair("comission_dollar", comission_dollar));
        params.add(new BasicNameValuePair("comission_percentage", comission_percentage));
        params.add(new BasicNameValuePair("gst_payable", gst_payable));
        params.add(new BasicNameValuePair("gst_type", gst_type));
        params.add(new BasicNameValuePair("conflict_of_interest", conflict_of_interest));
        params.add(new BasicNameValuePair("conflict_of_interest_detail", conflict_of_interest_detail));
        params.add(new BasicNameValuePair("co_broke", co_broke));
        params.add(new BasicNameValuePair("seller_warrant", seller_warrant));
        params.add(new BasicNameValuePair("another_sale_person", another_sale_person));
        params.add(new BasicNameValuePair("another_sale_person_detail", another_sale_person_detail));
        params.add(new BasicNameValuePair("bank_referral_fees", bank_referral_fees));
        params.add(new BasicNameValuePair("seller1_interpreter_name", seller1_interpreter_name));
        params.add(new BasicNameValuePair("seller1_interpreter_ic", seller1_interpreter_ic));
        params.add(new BasicNameValuePair("seller2_interpreter_name", seller2_interpreter_name));
        params.add(new BasicNameValuePair("seller2_interpreter_ic", seller2_interpreter_ic));
        params.add(new BasicNameValuePair("seller3_interpreter_name", seller3_interpreter_name));
        params.add(new BasicNameValuePair("seller3_interpreter_ic", seller3_interpreter_ic));
        params.add(new BasicNameValuePair("seller4_interpreter_name", seller4_interpreter_name));
        params.add(new BasicNameValuePair("seller4_interpreter_ic", seller4_interpreter_ic));
        params.add(new BasicNameValuePair("sale_person_name", sale_person_name));
        params.add(new BasicNameValuePair("sale_person_ic", sale_person_ic));
        params.add(new BasicNameValuePair("sale_person_postal_code", sale_person_postal_code));
        params.add(new BasicNameValuePair("sale_person_address1", sale_person_address1));
        params.add(new BasicNameValuePair("sale_person_address2", sale_person_address2));
        params.add(new BasicNameValuePair("sale_person_building_name", sale_person_building_name));
        params.add(new BasicNameValuePair("sale_person_cea", sale_person_cea));
        params.add(new BasicNameValuePair("sale_person_tel", sale_person_tel));

      return new JSONParser().uploadPdfFile(CEA_EXCLUSIVE_ESTATE, params, signList);
    }

	public JSONObject ExclusiveAuthority(
			String session_token, String postal_code, String address_1, String address_2, String building_name,
			String property_type, String floor_area, String land_area, String no_of_room, String type_of_holder,
			String no_of_year, String type_of_building, String expiry_date, String monthly_rent, String agent_name,
			String license_no, String agent_company, String commencement_date, String terms_expiry_date, String expected_sale_price,
			String percent_of_sale, String percent_of_expected_sale, String interpreted_language, String owner1_name,
			String owner1_ic, String owner1_postal_code, String owner1_address_1, String owner1_address_2, String owner1_building_name,
			String owner2_name, String owner2_ic, String owner2_postal_code, String owner2_address_1,
			String owner2_address_2, String owner2_building_name, String sale_person_name, String sale_person_cea,
			List<NameValuePair> signList	) throws JSONException {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("postal_code", postal_code));
		params.add(new BasicNameValuePair("address_1", address_1));
		params.add(new BasicNameValuePair("address_2", address_2));
		params.add(new BasicNameValuePair("building_name", building_name));
		params.add(new BasicNameValuePair("property_type", property_type));
		params.add(new BasicNameValuePair("floor_area", floor_area));
		params.add(new BasicNameValuePair("land_area", land_area));
		params.add(new BasicNameValuePair("no_of_room", no_of_room));
		params.add(new BasicNameValuePair("type_of_holder", type_of_holder));
		params.add(new BasicNameValuePair("no_of_year", no_of_year));
		params.add(new BasicNameValuePair("type_of_building", type_of_building));
		params.add(new BasicNameValuePair("expiry_date", expiry_date));
		params.add(new BasicNameValuePair("monthly_rent", monthly_rent));
		params.add(new BasicNameValuePair("agent_name", agent_name));
		params.add(new BasicNameValuePair("license_no", license_no));
		params.add(new BasicNameValuePair("agent_company", agent_company));
		params.add(new BasicNameValuePair("commencement_date", commencement_date));
		params.add(new BasicNameValuePair("terms_expiry_date", terms_expiry_date));
		params.add(new BasicNameValuePair("expected_sale_price", expected_sale_price));
		params.add(new BasicNameValuePair("percent_of_sale", percent_of_sale));
		params.add(new BasicNameValuePair("percent_of_expected_sale", percent_of_expected_sale));
		params.add(new BasicNameValuePair("interpreted_language", interpreted_language));
		params.add(new BasicNameValuePair("owner1_name", owner1_name));
		params.add(new BasicNameValuePair("owner1_ic", owner1_ic));
		params.add(new BasicNameValuePair("owner1_postal_code", owner1_postal_code));
		params.add(new BasicNameValuePair("owner1_address_1", owner1_address_1));
		params.add(new BasicNameValuePair("owner1_address_2", owner1_address_2));
		params.add(new BasicNameValuePair("owner1_building_name", owner1_building_name));
		params.add(new BasicNameValuePair("owner2_name", owner2_name));
		params.add(new BasicNameValuePair("owner2_ic", owner2_ic));
		params.add(new BasicNameValuePair("owner2_postal_code", owner2_postal_code));
		params.add(new BasicNameValuePair("owner2_address_1", owner2_address_1));
		params.add(new BasicNameValuePair("owner2_address_2", owner2_address_2));
		params.add(new BasicNameValuePair("owner2_building_name", owner2_building_name));
		params.add(new BasicNameValuePair("sale_person_name", sale_person_name));
		params.add(new BasicNameValuePair("sale_person_cea", sale_person_cea));
		
		return new JSONParser().uploadPdfFile(EXCLUSIVE_AUTHORITY, params, signList);
	}
	
	public JSONObject NonExclusiveCea(
			String session_token, String agreement_date, String seller1_name, String seller1_ic, String seller1_postal_code, 
            String seller1_address1, String seller1_address2, String seller1_building_name, String seller2_name, String seller2_ic, 
            String seller2_postal_code, String seller2_address1, String seller2_address2, String seller2_building_name, String seller3_name, 
            String seller3_ic, String seller3_postal_code, String seller3_address1, String seller3_address2, String seller3_building_name, 
            String seller4_name, String seller4_ic, String seller4_postal_code, String seller4_address1, String seller4_address2, 
            String seller4_building_name, String agent_name, String agent_licence, String agent_postal_code, String agent_address1, 
            String agent_address2, String agent_building_name, String property_postal_code, String property_address1, String property_address2, 
            String property_building_name, String comission_dollar, String comission_percentage, String gst_payable, String gst_type, 
            String gst_note, String conflict_of_interest, String conflict_of_interest_detail, String co_broke, String seller_warrant, 
            String additional_term, String seller1_interpreter_name, String seller1_interpreter_ic, 
            String seller2_interpreter_name, String seller2_interpreter_ic,
            String seller3_interpreter_name, String seller3_interpreter_ic, String seller4_interpreter_name, 
            String seller4_interpreter_ic, String sale_person_name, String sale_person_ic, 
            String sale_person_postal_code, String sale_person_address1, String sale_person_address2, String sale_person_building_name, String sale_person_cea, 
            String sale_person_tel, List<NameValuePair> signList
                ) throws JSONException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("session_token", session_token));
        params.add(new BasicNameValuePair("agreement_date", agreement_date));
        params.add(new BasicNameValuePair("seller1_name", seller1_name));
        params.add(new BasicNameValuePair("seller1_ic", seller1_ic));
        params.add(new BasicNameValuePair("seller1_postal_code", seller1_postal_code));
        params.add(new BasicNameValuePair("seller1_address1", seller1_address1));
        params.add(new BasicNameValuePair("seller1_address2", seller1_address2));
        params.add(new BasicNameValuePair("seller1_building_name", seller1_building_name));
        params.add(new BasicNameValuePair("seller2_name", seller2_name));
        params.add(new BasicNameValuePair("seller2_ic", seller2_ic));
        params.add(new BasicNameValuePair("seller2_postal_code", seller2_postal_code));
        params.add(new BasicNameValuePair("seller2_address1", seller2_address1));
        params.add(new BasicNameValuePair("seller2_address2", seller2_address2));
        params.add(new BasicNameValuePair("seller2_building_name", seller2_building_name));
        params.add(new BasicNameValuePair("seller3_name", seller3_name));
        params.add(new BasicNameValuePair("seller3_ic", seller3_ic));
        params.add(new BasicNameValuePair("seller3_postal_code", seller3_postal_code));
        params.add(new BasicNameValuePair("seller3_address1", seller3_address1));
        params.add(new BasicNameValuePair("seller3_address2", seller3_address2));
        params.add(new BasicNameValuePair("seller3_building_name", seller3_building_name));
        params.add(new BasicNameValuePair("seller4_name", seller4_name));
        params.add(new BasicNameValuePair("seller4_ic", seller4_ic));
        params.add(new BasicNameValuePair("seller4_postal_code", seller4_postal_code));
        params.add(new BasicNameValuePair("seller4_address1", seller4_address1));
        params.add(new BasicNameValuePair("seller4_address2", seller4_address2));
        params.add(new BasicNameValuePair("seller4_building_name", seller4_building_name));
        params.add(new BasicNameValuePair("agent_name", agent_name));
        params.add(new BasicNameValuePair("agent_licence", agent_licence));
        params.add(new BasicNameValuePair("agent_postal_code", agent_postal_code));
        params.add(new BasicNameValuePair("agent_address1", agent_address1));
        params.add(new BasicNameValuePair("agent_address2", agent_address2));
        params.add(new BasicNameValuePair("agent_building_name", agent_building_name));
        params.add(new BasicNameValuePair("property_postal_code", property_postal_code));
        params.add(new BasicNameValuePair("property_address1", property_address1));
        params.add(new BasicNameValuePair("property_address2", property_address2));
        params.add(new BasicNameValuePair("property_building_name", property_building_name));
        params.add(new BasicNameValuePair("comission_dollar", comission_dollar));
        params.add(new BasicNameValuePair("comission_percentage", comission_percentage));
        params.add(new BasicNameValuePair("gst_payable", gst_payable));
        params.add(new BasicNameValuePair("gst_type", gst_type));
        params.add(new BasicNameValuePair("gst_note", gst_note));
        params.add(new BasicNameValuePair("conflict_of_interest", conflict_of_interest));
        params.add(new BasicNameValuePair("conflict_of_interest_detail", conflict_of_interest_detail));
        params.add(new BasicNameValuePair("co_broke", co_broke));
        params.add(new BasicNameValuePair("seller_warrant", seller_warrant));
        params.add(new BasicNameValuePair("additional_term", additional_term));
        params.add(new BasicNameValuePair("seller1_interpreter_name", seller1_interpreter_name));
        params.add(new BasicNameValuePair("seller1_interpreter_ic", seller1_interpreter_ic));
        params.add(new BasicNameValuePair("seller2_interpreter_name", seller2_interpreter_name));
        params.add(new BasicNameValuePair("seller2_interpreter_ic", seller2_interpreter_ic));
        params.add(new BasicNameValuePair("seller3_interpreter_name", seller3_interpreter_name));
        params.add(new BasicNameValuePair("seller3_interpreter_ic", seller3_interpreter_ic));
        params.add(new BasicNameValuePair("seller4_interpreter_name", seller4_interpreter_name));
        params.add(new BasicNameValuePair("seller4_interpreter_ic", seller4_interpreter_ic));
        params.add(new BasicNameValuePair("sale_person_name", sale_person_name));
        params.add(new BasicNameValuePair("sale_person_ic", sale_person_ic));
        params.add(new BasicNameValuePair("sale_person_postal_code", sale_person_postal_code));
        params.add(new BasicNameValuePair("sale_person_address1", sale_person_address1));
        params.add(new BasicNameValuePair("sale_person_address2", sale_person_address2));
        params.add(new BasicNameValuePair("sale_person_building_name", sale_person_building_name));
        params.add(new BasicNameValuePair("sale_person_cea", sale_person_cea));
        params.add(new BasicNameValuePair("sale_person_tel", sale_person_tel));
        
      return new JSONParser().uploadPdfFile(NON_EXCLUSIVE_CEA, params, signList);
    }
	
	public JSONObject NonExclusiveAuthority(
			String session_token, String postal_code, String address_1, String address_2, String building_name,
			String property_type, String floor_area, String land_area, String no_of_room, String type_of_holder,
			String no_of_year, String type_of_building, String expiry_date, String monthly_rent, String agent_name,
			String license_no, String agent_company, String commencement_date, String terms_expiry_date, String expected_sale_price,
			String percent_of_sale, String percent_of_expected_sale, String interpreted_language, String owner1_name,
			String owner1_ic, String owner1_postal_code, String owner1_address_1, String owner1_address_2, String owner1_building_name,
			String owner2_name, String owner2_ic, String owner2_postal_code, String owner2_address_1,
			String owner2_address_2, String owner2_building_name, String sale_person_name, String sale_person_cea,
			List<NameValuePair> signList	) throws JSONException {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("postal_code", postal_code));
		params.add(new BasicNameValuePair("address_1", address_1));
		params.add(new BasicNameValuePair("address_2", address_2));
		params.add(new BasicNameValuePair("building_name", building_name));
		params.add(new BasicNameValuePair("property_type", property_type));
		params.add(new BasicNameValuePair("floor_area", floor_area));
		params.add(new BasicNameValuePair("land_area", land_area));
		params.add(new BasicNameValuePair("no_of_room", no_of_room));
		params.add(new BasicNameValuePair("type_of_holder", type_of_holder));
		params.add(new BasicNameValuePair("no_of_year", no_of_year));
		params.add(new BasicNameValuePair("type_of_building", type_of_building));
		params.add(new BasicNameValuePair("expiry_date", expiry_date));
		params.add(new BasicNameValuePair("monthly_rent", monthly_rent));
		params.add(new BasicNameValuePair("agent_name", agent_name));
		params.add(new BasicNameValuePair("license_no", license_no));
		params.add(new BasicNameValuePair("agent_company", agent_company));
		params.add(new BasicNameValuePair("commencement_date", commencement_date));
		params.add(new BasicNameValuePair("terms_expiry_date", terms_expiry_date));
		params.add(new BasicNameValuePair("expected_sale_price", expected_sale_price));
		params.add(new BasicNameValuePair("percent_of_sale", percent_of_sale));
		params.add(new BasicNameValuePair("percent_of_expected_sale", percent_of_expected_sale));
		params.add(new BasicNameValuePair("interpreted_language", interpreted_language));
		params.add(new BasicNameValuePair("owner1_name", owner1_name));
		params.add(new BasicNameValuePair("owner1_ic", owner1_ic));
		params.add(new BasicNameValuePair("owner1_postal_code", owner1_postal_code));
		params.add(new BasicNameValuePair("owner1_address_1", owner1_address_1));
		params.add(new BasicNameValuePair("owner1_address_2", owner1_address_2));
		params.add(new BasicNameValuePair("owner1_building_name", owner1_building_name));
		params.add(new BasicNameValuePair("owner2_name", owner2_name));
		params.add(new BasicNameValuePair("owner2_ic", owner2_ic));
		params.add(new BasicNameValuePair("owner2_postal_code", owner2_postal_code));
		params.add(new BasicNameValuePair("owner2_address_1", owner2_address_1));
		params.add(new BasicNameValuePair("owner2_address_2", owner2_address_2));
		params.add(new BasicNameValuePair("owner2_building_name", owner2_building_name));
		params.add(new BasicNameValuePair("sale_person_name", sale_person_name));
		params.add(new BasicNameValuePair("sale_person_cea", sale_person_cea));
		
		return new JSONParser().uploadPdfFile(NON_EXCLUSIVE_AUTHORITY, params, signList);
	}
	
	public JSONObject OfferToPurchase(
			String session_token, String postal_code, String address_1, String address_2, String building_name,
			String price_in_words, String price_in_number, String option_period, String completion_date, String area,
			String type_of_holder, String type_of_building, String working_day, String deadline_date, String bank, 
			String cheque_no, String cheque_amount, String payable_to_vendor, String purchaser1_signature, String purchaser1_name, 
			String purchaser1_ic, String purchaser2_signature, String purchaser2_name, String purchaser2_ic, String purchaser_postal_code, 
			String purchaser_address_1, String purchaser_address_2, String purchaser_building_name, String sale_person_name, String sale_person_ic, 
			String sale_person_signature, String property_owner_name, String property_proposal, String property_owner1_signature, String property_owner1_name, 
			String property_owner1_ic, String property_purchaser1_signature, String property_purchaser1_name, String property_purchaser1_ic
				) throws JSONException {
						
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("postal_code", postal_code));
		params.add(new BasicNameValuePair("address_1", address_1));
		params.add(new BasicNameValuePair("address_2", address_2));
		params.add(new BasicNameValuePair("building_name", building_name));
		params.add(new BasicNameValuePair("price_in_words", price_in_words));
		params.add(new BasicNameValuePair("price_in_number", price_in_number));
		params.add(new BasicNameValuePair("option_period", option_period));
		params.add(new BasicNameValuePair("completion_date", completion_date));
		params.add(new BasicNameValuePair("area", area));
		params.add(new BasicNameValuePair("type_of_holder", type_of_holder));
		params.add(new BasicNameValuePair("type_of_building", type_of_building));
		params.add(new BasicNameValuePair("working_day", working_day));
		params.add(new BasicNameValuePair("deadline_date", deadline_date));
		params.add(new BasicNameValuePair("bank", bank));
		params.add(new BasicNameValuePair("cheque_no", cheque_no));
		params.add(new BasicNameValuePair("cheque_amount", cheque_amount));
		params.add(new BasicNameValuePair("payable_to_vendor", payable_to_vendor));
		params.add(new BasicNameValuePair("purchaser1_signature", purchaser1_signature));
		params.add(new BasicNameValuePair("purchaser1_name", purchaser1_name));
		params.add(new BasicNameValuePair("purchaser1_ic", purchaser1_ic));
		params.add(new BasicNameValuePair("purchaser2_signature", purchaser2_signature));
		params.add(new BasicNameValuePair("purchaser2_name", purchaser2_name));
		params.add(new BasicNameValuePair("purchaser2_ic", purchaser2_ic));
		params.add(new BasicNameValuePair("purchaser_postal_code", purchaser_postal_code));
		params.add(new BasicNameValuePair("purchaser_address_1", purchaser_address_1));
		params.add(new BasicNameValuePair("purchaser_address_2", purchaser_address_2));
		params.add(new BasicNameValuePair("purchaser_building_name", purchaser_building_name));
		params.add(new BasicNameValuePair("sale_person_name", sale_person_name));
		params.add(new BasicNameValuePair("sale_person_ic", sale_person_ic));
		params.add(new BasicNameValuePair("sale_person_signature", sale_person_signature));
		params.add(new BasicNameValuePair("property_owner_name", property_owner_name));
		params.add(new BasicNameValuePair("property_proposal", property_proposal));
		params.add(new BasicNameValuePair("property_owner1_signature", property_owner1_signature));
		params.add(new BasicNameValuePair("property_owner1_name", property_owner1_name));
		params.add(new BasicNameValuePair("property_owner1_ic", property_owner1_ic));
		params.add(new BasicNameValuePair("property_purchaser1_signature", property_purchaser1_signature));
		params.add(new BasicNameValuePair("property_purchaser1_name", property_purchaser1_name));
		params.add(new BasicNameValuePair("property_purchaser1_ic", property_purchaser1_ic));

		return new JSONParser().getJSONFromUrl(OFFER_TO_PURCHASE, params, CommonConstants.POST);
	}
	
	public JSONObject OfferToPurchaseFile(
			String session_token, String postal_code, String address_1, String address_2, String building_name,
			String price_in_words, String price_in_number, String option_period, String completion_date, String area,
			String type_of_holder, String type_of_building, String working_day, String deadline_date, String bank, 
			String cheque_no, String cheque_amount, String payable_to_vendor, String purchaser1_name, 
			String purchaser1_ic, String purchaser2_name, String purchaser2_ic, String purchaser_postal_code, 
			String purchaser_address_1, String purchaser_address_2, String purchaser_building_name, String sale_person_name, String sale_person_ic, 
			String property_owner_name, String property_proposal, String property_owner1_name, 
			String property_owner1_ic, String property_purchaser1_name, String property_purchaser1_ic,
			List<NameValuePair> signList) throws JSONException {
						
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("postal_code", postal_code));
		params.add(new BasicNameValuePair("address_1", address_1));
		params.add(new BasicNameValuePair("address_2", address_2));
		params.add(new BasicNameValuePair("building_name", building_name));
		params.add(new BasicNameValuePair("price_in_words", price_in_words));
		params.add(new BasicNameValuePair("price_in_number", price_in_number));
		params.add(new BasicNameValuePair("option_period", option_period));
		params.add(new BasicNameValuePair("completion_date", completion_date));
		params.add(new BasicNameValuePair("area", area));
		params.add(new BasicNameValuePair("type_of_holder", type_of_holder));
		params.add(new BasicNameValuePair("type_of_building", type_of_building));
		params.add(new BasicNameValuePair("working_day", working_day));
		params.add(new BasicNameValuePair("deadline_date", deadline_date));
		params.add(new BasicNameValuePair("bank", bank));
		params.add(new BasicNameValuePair("cheque_no", cheque_no));
		params.add(new BasicNameValuePair("cheque_amount", cheque_amount));
		params.add(new BasicNameValuePair("payable_to_vendor", payable_to_vendor));
		params.add(new BasicNameValuePair("purchaser1_name", purchaser1_name));
		params.add(new BasicNameValuePair("purchaser1_ic", purchaser1_ic));
		params.add(new BasicNameValuePair("purchaser2_name", purchaser2_name));
		params.add(new BasicNameValuePair("purchaser2_ic", purchaser2_ic));
		params.add(new BasicNameValuePair("purchaser_postal_code", purchaser_postal_code));
		params.add(new BasicNameValuePair("purchaser_address_1", purchaser_address_1));
		params.add(new BasicNameValuePair("purchaser_address_2", purchaser_address_2));
		params.add(new BasicNameValuePair("purchaser_building_name", purchaser_building_name));
		params.add(new BasicNameValuePair("sale_person_name", sale_person_name));
		params.add(new BasicNameValuePair("sale_person_ic", sale_person_ic));
		params.add(new BasicNameValuePair("property_owner_name", property_owner_name));
		params.add(new BasicNameValuePair("property_proposal", property_proposal));
		params.add(new BasicNameValuePair("property_owner1_name", property_owner1_name));
		params.add(new BasicNameValuePair("property_owner1_ic", property_owner1_ic));
		params.add(new BasicNameValuePair("property_purchaser1_name", property_purchaser1_name));
		params.add(new BasicNameValuePair("property_purchaser1_ic", property_purchaser1_ic));

		return new JSONParser().uploadPdfFile(OFFER_TO_PURCHASE, params, signList);
	}
	
	public JSONObject sendPdfStatus(String session_key, String sent_status, int pdf_id, String LINK) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		params.add(new BasicNameValuePair("sent_status", sent_status));
		params.add(new BasicNameValuePair("pdf_id", String.valueOf(pdf_id)));
		return new JSONParser().getJSONFromUrl(LINK, params, CommonConstants.POST);
	}
	
	public JSONObject getNotification(String session_key) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		return new JSONParser().getJSONFromUrl(NOTIFICATION, params, CommonConstants.POST);
	}
	
	public JSONObject updateNotification(String session_key, String muteNoti, String newListing, String updateShortlist, String newStarbuys, String newNews, String auctionRemainder, String newAuctions) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_key));
		params.add(new BasicNameValuePair("mute_notification", muteNoti));
		params.add(new BasicNameValuePair("new_listing", newListing));
		params.add(new BasicNameValuePair("updates_on_shortlist", updateShortlist));
		params.add(new BasicNameValuePair("new_star_buy", newStarbuys));
		params.add(new BasicNameValuePair("new_news", newNews));
		params.add(new BasicNameValuePair("auction_remainder", auctionRemainder));
		params.add(new BasicNameValuePair("new_auctions", newAuctions));
		return new JSONParser().getJSONFromUrl(NOTIFICATION, params, CommonConstants.PUT);
	}
	
	public JSONObject about(String session_token) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		return new JSONParser().getJSONFromUrl(ABOUT, params, CommonConstants.POST);
	}
	
	/*public JSONObject changePassword(String session_token, String old_password, String new_password) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("old_password", old_password));
		params.add(new BasicNameValuePair("new_password", new_password));
		return new JSONParser().getJSONFromUrl(CHANGE_PASSWORD, params, CommonConstants.POST);
	}*/
	
	public JSONObject changePassword(String session_token, String old_pwd, String new_pwd) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		params.add(new BasicNameValuePair("old_password", old_pwd));
		params.add(new BasicNameValuePair("new_password", new_pwd));
		return new JSONParser().getJSONFromUrl(CHANGE_PASSWORD, params, CommonConstants.POST);
	}
	
	public JSONObject getPrivateListing(String session_token) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session_token", session_token));
		return new JSONParser().getJSONFromUrl(PRIVATE_LISTINGS, params, CommonConstants.POST);
	}
	
	public JSONObject getData(String key) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("key", key));
		return new JSONParser().getJSONFromUrl(SPLASH, params, CommonConstants.POST);
	}
	
	public JSONObject forgetPassword(String email) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		return new JSONParser().getJSONFromUrl(FORGET_PASSWORD, params, CommonConstants.POST);
	}
	
	public JSONObject getLogo() throws JSONException {
		return new JSONParser().getJSONFromUrl(LOGO, null, CommonConstants.GET);
	}
}
