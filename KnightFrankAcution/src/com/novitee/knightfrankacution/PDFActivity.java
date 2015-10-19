package com.novitee.knightfrankacution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseActivity;
import com.novitee.knightfrankacution.model.Pdf;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class PDFActivity extends BaseActivity {
	
	Context context = this; 

	WebView pdfView;
	Button btnComplete;
	
	Pdf pdf;
	String pdf_title;
	String api_link;
	String send_status;
	
	//mail send status code
	int REQUEST_CODE_MAIL = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdf);
		
		pdfView = (WebView) findViewById(R.id.pdf_webview);
		btnComplete = (Button) findViewById(R.id.pdf_complete);
		
		pdf = (Pdf) getIntent().getSerializableExtra("PDF");
		pdf_title = getIntent().getStringExtra("Email Title");
		api_link = getIntent().getStringExtra("Api Link");
		
		//show pdf view with google plugin
		pdfView.getSettings().setJavaScriptEnabled(true);
		pdfView.getSettings().setAllowFileAccess(true);	
		String url = "https://docs.google.com/gview?embedded=true&url=" + pdf.getPdf_link();
		pdfView.loadUrl(url);
		
		btnComplete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new SendPDF().execute();
			}
		});
		
	}//onCreate

	private class SendPDF extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				URL u = new URL(pdf.getPdf_link());
			
			    HttpURLConnection c = (HttpURLConnection) u.openConnection();
			    c.setRequestMethod("GET");
			    c.setDoOutput(true);
			    c.connect();
			    
			    FileOutputStream f = new FileOutputStream(new File(Environment.getExternalStorageDirectory().toString() +"/", pdf.getPdf_name()));

			    InputStream in = c.getInputStream();

			    byte[] buffer = new byte[1024];
			    int len1 = 0;
			    while ( (len1 = in.read(buffer)) > 0 ) {
			         f.write(buffer);
			    }
			    f.close();
			    
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			String subject = "Knight Frank Auction "+ pdf_title;
			String body = "This email is generated via KnightFrank Auction App \n\n";
			
			Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), pdf.getPdf_name()));
			
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setData(Uri.parse("mailto:"));
		    i.setType("text/html");
			i.putExtra(Intent.EXTRA_SUBJECT, subject);
			i.putExtra(Intent.EXTRA_TEXT, body);
			i.putExtra(Intent.EXTRA_STREAM, uri);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			try {
//			    context.startActivity(Intent.createChooser(i, "Send mail..."));
				startActivityForResult(Intent.createChooser(i, "Send mail..."), REQUEST_CODE_MAIL);
			} catch (android.content.ActivityNotFoundException ex) {
			    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			}
		}
		
	}//SendPDF
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(requestCode == REQUEST_CODE_MAIL){
	    	
	        if(resultCode == 0){//email sent
	           send_status = "1";

	        } else {//email cancelled
	        	send_status = "0";
	        }
	        
	        if(api_link != null) {
	        	new sendStatus().execute();
	        }
	        
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}//onActivityResult
	
	private class sendStatus extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PDFActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.sendPdfStatus(pref.getSessionToken(), send_status, pdf.getPdf_id(), api_link);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(pDialog.isShowing()){
				pDialog.dismiss();
			}
			
			try {
				int json_responseCode = jObj.getInt("statusCode");
				int json_status = jObj.getInt("status");
				
				if(json_status == 1 && json_responseCode == 200){
					
				}
				else if(json_status == 2 && json_responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}//sendStatus

}
