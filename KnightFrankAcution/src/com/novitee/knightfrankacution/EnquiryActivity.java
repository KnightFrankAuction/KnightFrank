package com.novitee.knightfrankacution;

import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.util.Preferences;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EnquiryActivity extends BaseFragmentActivity {
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	FragmentTransaction fragmentTran;
	
	EditText editEmail;
	EditText editContact;
	EditText editName;
	EditText editRemark;
//	EditText editAttachment;
	TextView editAttachment;
	Button btnSubmit;
	
	String email, contact, name, remark;
	String attachment = null;
	String imageFilePath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enquiry);
		
		setTitleBarAndFooter();
		
		editEmail = (EditText) findViewById(R.id.enquiry_email);
		editContact = (EditText) findViewById(R.id.enquiry_contact);
		editName = (EditText) findViewById(R.id.enquiry_name);
		editRemark = (EditText) findViewById(R.id.enquiry_remark);
		editAttachment = (TextView) findViewById(R.id.enquiry_attachment);
		btnSubmit = (Button) findViewById(R.id.enquiry_submit);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				email = editEmail.getText().toString();
				contact = editContact.getText().toString();
				name = editName.getText().toString();
				remark = editRemark.getText().toString();

				if(connectionManager.isConnected()) {			
					new sendEnquiry().execute();
				}
				else {
					Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
				}

			}
		});
		
		editAttachment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select photo"), 1);
			}
		});
		
		
	}//onCreate
	
	public class sendEnquiry extends AsyncTask<Void, Void, Void> {
		
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EnquiryActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				if(imageFilePath != null) {
					jObj = api.sendEnquiryWithAttachment(email, contact, name, remark, attachment, Preferences.getInstance(context).getSessionToken());
				}
				else {
					jObj = api.sendEnquiry(email, contact, name, remark, Preferences.getInstance(context).getSessionToken());
				}
				
				
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
					Toast.makeText(context, "Enquiry is sent", Toast.LENGTH_SHORT).show();
					
					editEmail.setText("");
					editContact.setText("");
					editName.setText("");
					editRemark.setText("");
					editAttachment.setText("");
				}
				else if(json_status == 2 && json_responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}//sendEnquiry
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		titleText2.setVisibility(View.GONE);
		titleImage.setVisibility(View.GONE);
		titleText.setText("Enquiry");
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.enquiry_footer, new FooterFragment());
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
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && data != null && data.getData() != null) {
               Uri _uri = data.getData();

               //User had pick an image.
               Cursor cursor = getContentResolver().query(_uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
               cursor.moveToFirst();
  
               //Link to the image
               imageFilePath = cursor.getString(0);               
//               editImage.setImageBitmap(BitmapFactory.decodeFile(imageFilePath));
//               txtImageText.setText("");
               editAttachment.setText(imageFilePath);
               attachment = imageFilePath;
               cursor.close();
           }
           super.onActivityResult(requestCode, resultCode, data);
    }//onActivityResult

}
