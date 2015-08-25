package com.novitee.knightfrankacution.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class News implements Serializable {
	
	String title;
	String description;
	String news_link;
	String date;
	
	public News(JSONObject j) {
		try {
			title = j.getString("title");
			description = j.getString("description");
			news_link = j.getString("link");
			date = "Added on " + j.getString("date");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNews_link() {
		return news_link;
	}
	public void setNews_link(String news_link) {
		this.news_link = news_link;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	

}
