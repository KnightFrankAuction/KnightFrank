package com.novitee.knightfrankacution;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class DocumentsActivity extends BaseFragmentActivity implements OnClickListener {
	
	Context context = this;
	
	FragmentTransaction fragmentTran;
	
	TextView samplePDF, AGTAuction, exclusiveEstate, exclusiveAuthority, nonExclusiveCEA, nonExclusiveAuthority, offerToPurchase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_documents);
		
		setTitleBarAndFooter();
		
		samplePDF = (TextView) findViewById(R.id.sample_pdf);
		AGTAuction = (TextView) findViewById(R.id.agt_auction);
		exclusiveEstate = (TextView) findViewById(R.id.exclusive_estate);
		exclusiveAuthority = (TextView) findViewById(R.id.exclusive_authority);
		nonExclusiveCEA = (TextView) findViewById(R.id.non_exclusive_cea);
		nonExclusiveAuthority = (TextView) findViewById(R.id.non_exclusive_authority);
		offerToPurchase = (TextView) findViewById(R.id.offer_purchase);
		
		samplePDF.setOnClickListener(this);
		AGTAuction.setOnClickListener(this);
		exclusiveEstate.setOnClickListener(this);
		exclusiveAuthority.setOnClickListener(this);
		nonExclusiveCEA.setOnClickListener(this);
		nonExclusiveAuthority.setOnClickListener(this);
		offerToPurchase.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == samplePDF.getId()) {
			if(connectionManager.isConnected()) {
				new GetSamplePDF().execute();
			}
			else {
				Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
			}
		}
		else if(v.getId() == AGTAuction.getId()) {
			Intent intent = new Intent(context, AGTAuctionActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == exclusiveEstate.getId()) {
			Intent intent = new Intent(context, CeaExclusiveEstateActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == exclusiveAuthority.getId()) {
			Intent intent = new Intent(context, ExclusiveAuthorityActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == nonExclusiveCEA.getId()) {
			Intent intent = new Intent(context, NonExclusiveCeaActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == nonExclusiveAuthority.getId()) {
			Intent intent = new Intent(context, NonExclusiveAuthorityActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == offerToPurchase.getId()) {
			Intent intent = new Intent(context, OfferToPurchaseActivity.class);
			startActivity(intent);
		}
	}

	public void setTitleBarAndFooter() {
		//title bar
		TextView titleText = (TextView) findViewById(R.id.title_text);
		TextView titleText2 = (TextView) findViewById(R.id.title_text2);
		ImageView titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleText.setText("Documents");
		titleImage.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.documents_footer, new FooterFragment());
		fragmentTran.commit();
		
		ImageView titleBack = (ImageView) findViewById(R.id.title_back);
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
	}//setTitleBarAndFooter
	
	private class GetSamplePDF extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DocumentsActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getSamplePDF(pref.getSessionToken());	
				
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
//					String pdf_link = CommonConstants.HOST + jObj.getString("simple_pdf");
					String pdf_link = "http://128.199.142.149:8282/document/SpecimentExample.pdf";
					
					Intent i = new Intent(context, PDFActivity.class);
					i.putExtra("PDFName", pdf_link);
					startActivity(i);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

