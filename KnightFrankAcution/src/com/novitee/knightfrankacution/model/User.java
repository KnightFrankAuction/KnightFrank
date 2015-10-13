package com.novitee.knightfrankacution.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {

	String userName;
	String email;
	String phoneNo;
	int type;
	String ceaNo;
	String company;
	String buildingNo;
	String buildingName;
	String street;

	public User(JSONObject j) {
		try {
			userName = j.getString("user_name");
			email = j.getString("email");
			phoneNo = j.getString("phone_no");
			type = Integer.parseInt(j.getString("user_type"));
			ceaNo = j.getString("cea_no");
			company = j.getString("company");
			buildingNo = j.getString("building_no");
			buildingName = j.getString("building_name");
			street = j.getString("street");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCeaNo() {
		return ceaNo;
	}
	public void setCeaNo(String ceaNo) {
		this.ceaNo = ceaNo;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getBuildingNo() {
		return buildingNo;
	}
	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	
}
