package com.novitee.knightfrankacution.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Property implements Serializable {
	
//	public int id;
	public String property_id;
	public String building_name;
	public String district;
	public String auction_type;
	public String building_type;
	public String floor_area;
	public String price;
	public String bedroom;
	public String bath;
	public String tenure;
	public String psf;
	public String starbuy_flag;
	public String shortlist_flag;
	public String gym;
	public String playground;
	public String swimming_pool;
	public String tennis_court;
	public String function_room;
	public String penthouse;
	public String city_view;
	public String high_floor;
	public String balcony;
	public String highlights;
	public String postal_code;
	public String seller_name;
	public String cea_no;
	public String company_name;
	public String phone;
	public String email;
	public String date;
	
	List<Photo> photo ;

	public Property(JSONObject j) {
		
		try {
			property_id = j.getString("property_id");
			phone = j.getString("phone");
			building_name = j.getString("building_name");
			district = j.getString("district");
			auction_type = j.getString("auction_type");
			building_type = j.getString("building_type");
			floor_area = j.getString("floor_area");
			price = j.getString("price");
			bedroom = j.getString("bedroom");
			bath = j.getString("bath");
			tenure = j.getString("tenure");
			psf = j.getString("psf");
			starbuy_flag = j.getString("starbuy_flag");
			shortlist_flag = j.getString("shortlist_flag");
			gym = j.getString("Gym");
			playground = j.getString("Play Ground");
			swimming_pool = j.getString("Swimming Pool");
			tennis_court = j.getString("Tennis Court");
			function_room = j.getString("Function Room");
			penthouse = j.getString("Pent House");
			city_view = j.getString("City View");
			high_floor = j.getString("High Floor");
			balcony = j.getString("Balcony");
			highlights = j.getString("Highlights");
			postal_code = j.getString("postal_code");
			seller_name = j.getString("seller_name");
			cea_no = j.getString("cea_no");
			company_name = j.getString("company_name");
			phone = j.getString("phone");
			email = j.getString("email");
			date = j.getString("start_date");
			
			JSONArray jArray = j.getJSONArray("photo");
			photo = new ArrayList<Photo>();
			for (int i = 0; i < jArray.length(); i++) {
				photo.add(new Photo(jArray.getJSONObject(i)));
			}
			Log.d("dsfsd", "sd");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public String getFunction_room() {
		return function_room;
	}
	public void setFunction_room(String function_room) {
		this.function_room = function_room;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProperty_id() {
		return property_id;
	}
	public void setProperty_id(String property_id) {
		this.property_id = property_id;
	}
	public String getBuilding_name() {
		return building_name;
	}
	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getAuction_type() {
		return auction_type;
	}
	public void setAuction_type(String auction_type) {
		this.auction_type = auction_type;
	}
	public String getBuilding_type() {
		return building_type;
	}
	public void setBuilding_type(String building_type) {
		this.building_type = building_type;
	}
	public String getFloor_area() {
		return floor_area;
	}
	public void setFloor_area(String floor_area) {
		this.floor_area = floor_area;
	}
	public String getBedroom() {
		return bedroom;
	}
	public void setBedroom(String bedroom) {
		this.bedroom = bedroom;
	}
	public String getBath() {
		return bath;
	}
	public void setBath(String bath) {
		this.bath = bath;
	}
	public String getTenure() {
		return tenure;
	}
	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
	public String getPsf() {
		return psf;
	}
	public void setPsf(String psf) {
		this.psf = psf;
	}
	public String getStarbuy_flag() {
		return starbuy_flag;
	}
	public void setStarbuy_flag(String starbuy_flag) {
		this.starbuy_flag = starbuy_flag;
	}
	public String getShortlist_flag() {
		return shortlist_flag;
	}
	public void setShortlist_flag(String shortlist_flag) {
		this.shortlist_flag = shortlist_flag;
	}
	public String getGym() {
		return gym;
	}
	public void setGym(String gym) {
		this.gym = gym;
	}
	public String getPlayground() {
		return playground;
	}
	public void setPlayground(String playground) {
		this.playground = playground;
	}
	public String getSwimming_pool() {
		return swimming_pool;
	}
	public void setSwimming_pool(String swimming_pool) {
		this.swimming_pool = swimming_pool;
	}
	public String getTennis_court() {
		return tennis_court;
	}
	public void setTennis_court(String tennis_court) {
		this.tennis_court = tennis_court;
	}
	public String getPenthouse() {
		return penthouse;
	}
	public void setPenthouse(String penthouse) {
		this.penthouse = penthouse;
	}
	public String getCity_view() {
		return city_view;
	}
	public void setCity_view(String city_view) {
		this.city_view = city_view;
	}
	public String getHigh_floor() {
		return high_floor;
	}
	public void setHigh_floor(String high_floor) {
		this.high_floor = high_floor;
	}
	public String getBalcony() {
		return balcony;
	}
	public void setBalcony(String balcony) {
		this.balcony = balcony;
	}
	public String getHighlights() {
		return highlights;
	}
	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}
	public String getSeller_name() {
		return seller_name;
	}
	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}
	public String getCea_no() {
		return cea_no;
	}
	public void setCea_no(String cea_no) {
		this.cea_no = cea_no;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<Photo> getPhoto() {
		return photo;
	}
	public void setPhoto(List<Photo> photo) {
		this.photo = photo;
	}	
	
}
