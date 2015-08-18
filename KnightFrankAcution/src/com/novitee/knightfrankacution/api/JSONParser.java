package com.novitee.knightfrankacution.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.novitee.knightfrankacution.util.CommonConstants;

public class JSONParser {
	
	InputStream is = null;
	JSONObject jObj = null;
	String json = "";
	int  StatusCode ;
	public JSONObject getJSONFromUrl(String url,List<NameValuePair> params,String method) throws JSONException{
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		//Post method
		if(CommonConstants.POST.equals(method)){
			//create http post 
			HttpPost httpPost = new HttpPost(url);
			try {
				//set the parameters for post data 
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				//call the the url and receive response data
				HttpResponse res = httpClient.execute(httpPost);
				StatusCode = res.getStatusLine().getStatusCode();
				//get the data from the response
				HttpEntity httpEntity = res.getEntity();
				//set content to input stream
				is = httpEntity.getContent();
			} catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } 
			catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(CommonConstants.GET.equals(method)){
			//create http Get 
			HttpGet httpGet = new HttpGet(url);
			try {
				//call the the url and receive response data
				HttpResponse res = httpClient.execute(httpGet);
				StatusCode = res.getStatusLine().getStatusCode();
				//get the data from the response
				HttpEntity httpEntity = res.getEntity();
				//set content to input stream
				is = httpEntity.getContent();
			} catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } 
			catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(CommonConstants.PUT.equals(method)){
			//create http Get 
			HttpPut httpPut = new HttpPut(url);
			try {
				//set the parameters for post data 
				httpPut.setEntity(new UrlEncodedFormEntity(params));
				//call the the url and receive response data
				HttpResponse res = httpClient.execute(httpPut);
				StatusCode = res.getStatusLine().getStatusCode();
				//get the data from the response
				HttpEntity httpEntity = res.getEntity();
				//set content to input stream
				is = httpEntity.getContent();
			} catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } 
			catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			// read data from input stream of url response
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine())!= null){
				sb.append(line+"\n");
			}
			is.close();
			json = sb.toString();
			jObj = new JSONObject(json);
			jObj.put("statusCode", StatusCode);
			System.out.println(json);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jObj;
	}
	
	public JSONObject uploadFile(String url, List<NameValuePair> params, String path){
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		try {
			MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			multipartEntity.addPart("file", new FileBody(new File(path)));
			
			for (int i = 0; i < params.size(); i++) {
				multipartEntity.addPart(params.get(i).getName(), new StringBody(params.get(i).getValue()));
			}
			
			httpost.setEntity(multipartEntity);
			HttpResponse response = httpClient.execute(httpost);
			is = response.getEntity().getContent();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			jObj = new JSONObject(json);
			jObj.put("statusCode", StatusCode);
			System.out.println(json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
			jObj = null;
		}
		return jObj;
	}
}
