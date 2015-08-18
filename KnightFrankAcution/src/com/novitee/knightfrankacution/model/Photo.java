package com.novitee.knightfrankacution.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo implements Serializable {
	
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Photo(JSONObject j) {
		try {
			name = j.getString("name");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Photo(String name) {
		super();
		this.name = name;
	}

}
