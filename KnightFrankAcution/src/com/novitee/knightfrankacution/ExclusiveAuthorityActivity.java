package com.novitee.knightfrankacution;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.javacodegeeks.androidcanvasexample.CanvasView;
import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.Pdf;
import com.novitee.knightfrankacution.util.CommonConstants;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ExclusiveAuthorityActivity extends BaseFragmentActivity implements OnClickListener {
	
	Context context = this;
	
	FragmentTransaction fragmentTran;
	
	EditText editPropertyPostal, editPropertyAddress1, editPropertyAddress2, editPropertyBuildingName, editPropertyType, editPropertyFloor, editPropertyLand, editPropertyRooms, editPropetyYear, editPropertyExpiry, editPropertyRent;
	EditText editTermsCommencement, editTermsExpiry, editTermsExpectedSale, editTermsFeeSale, editTermsFeeExpectedSale, editSoLanguage;
	EditText editOwner1Name, editOwner1Ic, editOwner1Postal, editOwner1Address1, editOwner1Address2, editOwner1BuildingName; 
	EditText editOwner2Name, editOwner2Ic, editOwner2Postal, editOwner2Address1, editOwner2Address2, editOwner2BuildingName;
	TextView txtAgentName, txtAgentLicense, txtAgentCompany, txtSaleName, txtSaleLicense;
	RadioGroup rdHolderGp, rdPossessionGp;
	RadioButton rdHolderFree, rdHolderLead, rdPossessionVacant, rdPossessionTenacy;
	Button btnSubmit;
	String stHolder, stPossession;
	
	//Signature
	Button btnOwner1Sign, btnOwner2Sign, btnSalePersonSign;
	ImageView imgOwner1Sign, imgOwner2Sign, imgSalePersonSign;
	String stOwner1Sign, stOwner2Sign, stSalePersonSign;
	List<NameValuePair> signList;
	String sign_flag;
	
	//datePicker
	Calendar myCalendar = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener datePicker;
	String calDate;
	EditText text;
	
	//for signature
	CanvasView canvasView;
	ImageView imgSign;
	String signName = null, todayDate = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exclusive_authority);
		
		LoadData();
		setTitleBarAndFooter();
		
		editPropertyExpiry.setOnClickListener(this);
		editTermsCommencement.setOnClickListener(this);
		editTermsExpiry.setOnClickListener(this);
		btnOwner1Sign.setOnClickListener(this);
		btnOwner2Sign.setOnClickListener(this);
		btnSalePersonSign.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		
		//Date Listener
		datePicker = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				
				Date date = myCalendar.getTime();
				calDate = java.text.DateFormat.getDateInstance().format(date);
				
				text.setText(calDate);
			}
			
		};
	}//onCreate
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == editPropertyExpiry.getId()) {
			setDate(editPropertyExpiry);
		}
		else if(v.getId() == editTermsCommencement.getId()) {
			setDate(editTermsCommencement);
		}
		else if(v.getId() == editTermsExpiry.getId()) {
			setDate(editTermsExpiry);
		}
		else if(v.getId() == btnOwner1Sign.getId()) {
			sign_flag = "Owner1Sign";
			MakeSignature(imgOwner1Sign);
		}
		else if(v.getId() == btnOwner2Sign.getId()) {
			sign_flag = "Owner2Sign";
			MakeSignature(imgOwner2Sign);
		}
		else if(v.getId() == btnSalePersonSign.getId()) {
			sign_flag = "SalePersonSign";
			MakeSignature(imgSalePersonSign);
		}
		else if(v.getId() == btnSubmit.getId()) {
			onSubmit();
		}
	}//onClick

	private void LoadData() {
		// TODO Auto-generated method stub
		editPropertyPostal = (EditText) findViewById(R.id.ex_edit_property_postal);
		editPropertyAddress1 = (EditText) findViewById(R.id.ex_edit_property_address1);
		editPropertyAddress2 = (EditText) findViewById(R.id.ex_edit_property_address2);
		editPropertyBuildingName = (EditText) findViewById(R.id.ex_edit_property_building_name);
		editPropertyType = (EditText) findViewById(R.id.ex_edit_property_type);
		editPropertyFloor = (EditText) findViewById(R.id.ex_edit_property_floor_area);
		editPropertyLand = (EditText) findViewById(R.id.ex_edit_property_land_area);
		editPropertyRooms = (EditText) findViewById(R.id.ex_edit_property_rooms);
		editPropetyYear = (EditText) findViewById(R.id.ex_edit_property_years);
		editPropertyExpiry = (EditText) findViewById(R.id.ex_edit_property_expiry);
		editPropertyRent = (EditText) findViewById(R.id.ex_edit_property_rent);
		rdHolderGp = (RadioGroup) findViewById(R.id.ex_rd_holder_gp);
		rdPossessionGp = (RadioGroup) findViewById(R.id.ex_rd_possession_gp);
		rdHolderFree = (RadioButton) findViewById(R.id.ex_rd_holder_free);
		rdHolderLead = (RadioButton) findViewById(R.id.ex_rd_holder_lead);
		rdPossessionVacant = (RadioButton) findViewById(R.id.ex_rd_possession_vacant);
		rdPossessionTenacy = (RadioButton) findViewById(R.id.ex_rd_possession_tenacy);
		txtAgentName = (TextView) findViewById(R.id.ex_agent_name);
		txtAgentLicense = (TextView) findViewById(R.id.ex_agent_license);
		txtAgentCompany = (TextView) findViewById(R.id.ex_agent_company);
		editTermsCommencement = (EditText) findViewById(R.id.ex_edit_terms_commencement);
		editTermsExpiry = (EditText) findViewById(R.id.ex_edit_terms_expiry);
		editTermsExpectedSale = (EditText) findViewById(R.id.ex_edit_terms_expected_sale);
		editTermsFeeSale = (EditText) findViewById(R.id.ex_edit_terms_fee_sale);
		editTermsFeeExpectedSale = (EditText) findViewById(R.id.ex_edit_terms_fee_expected_sale);
		editSoLanguage = (EditText) findViewById(R.id.ex_edit_so_language);
		editOwner1Name = (EditText) findViewById(R.id.ex_owner1_name);
		editOwner1Ic = (EditText) findViewById(R.id.ex_owner1_nric);
		editOwner1Postal = (EditText) findViewById(R.id.ex_owner1_postal);
		editOwner1Address1 = (EditText) findViewById(R.id.ex_owner1_address1);
		editOwner1Address2 = (EditText) findViewById(R.id.ex_owner1_address2);
		editOwner1BuildingName = (EditText) findViewById(R.id.ex_owner1_building_name);
		editOwner2Name = (EditText) findViewById(R.id.ex_owner2_name);
		editOwner2Ic = (EditText) findViewById(R.id.ex_owner2_nric);
		editOwner2Postal = (EditText) findViewById(R.id.ex_owner2_postal);
		editOwner2Address1 = (EditText) findViewById(R.id.ex_owner2_address1);
		editOwner2Address2 = (EditText) findViewById(R.id.ex_owner2_address2);
		editOwner2BuildingName = (EditText) findViewById(R.id.ex_owner2_building_name);
		txtSaleName = (TextView) findViewById(R.id.ex_sale_name);
		txtSaleLicense = (TextView) findViewById(R.id.ex_sale_license);
		btnSubmit = (Button) findViewById(R.id.ex_submit);
		
		//Signature
		btnOwner1Sign = (Button) findViewById(R.id.ex_owner1_sign);
		btnOwner2Sign = (Button) findViewById(R.id.ex_owner2_sign);
		btnSalePersonSign = (Button) findViewById(R.id.ex_sale_sign);
		imgOwner1Sign = (ImageView) findViewById(R.id.ex_owner1_img);
		imgOwner2Sign = (ImageView) findViewById(R.id.ex_owner2_img);
		imgSalePersonSign = (ImageView) findViewById(R.id.ex_sale_img);
		
		signList = new ArrayList<NameValuePair>();
	}//LoadData

	public void setTitleBarAndFooter() {
		//title bar
		TextView titleText = (TextView) findViewById(R.id.title_text);
		TextView titleText2 = (TextView) findViewById(R.id.title_text2);
		ImageView titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleText.setText("Exclusive Authority to Sell");
		titleImage.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.exclusive_authority_footer, new FooterFragment());
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

	private void setDate(EditText editText) {
		text = editText;
		
		DatePickerDialog dateDialog = new DatePickerDialog(ExclusiveAuthorityActivity.this,
				datePicker, 
				myCalendar.get(Calendar.YEAR), 
				myCalendar.get(Calendar.MONTH), 
				myCalendar.get(Calendar.DAY_OF_MONTH));
		
		dateDialog.show();
	}//setDate
	
	private void MakeSignature(ImageView imgView) {
		// TODO Auto-generated method stub
		imgSign = imgView;
		
		final Dialog dialog = new Dialog(ExclusiveAuthorityActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.signature_dialog_layout);
		
		canvasView = (CanvasView) dialog.findViewById(R.id.signature_canvas);
		Button btnCancel = (Button) dialog.findViewById(R.id.btnSignCancle);
		Button btnClear = (Button) dialog.findViewById(R.id.btnSignClear);
		Button btnSave = (Button) dialog.findViewById(R.id.btnSignSave);
		final EditText txtUserName = (EditText) dialog.findViewById(R.id.edit_sign_name);
		
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		btnClear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				canvasView.clearCanvas();
			}
		});
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(todayDate.equals("")) {
					todayDate = getCurrentDate();
				}
				signName = txtUserName.getText().toString();
				signName = "exclusive_authority_" + signName +"_"+ todayDate +".png";
				
				Bitmap b = canvasView.saveCanvas(context, canvasView, signName);
				imgSign.setImageBitmap(b);
				
				signName = Environment.getExternalStorageDirectory().toString() + "/"+ signName;
				
				if(sign_flag.equals("Owner1Sign")) {
					stOwner1Sign = signName;
				}
				else if(sign_flag.equals("Owner2Sign")) {
					stOwner2Sign = signName;
				}
				else if(sign_flag.equals("SalePersonSign")) {
					stSalePersonSign = signName;
				}
				
				dialog.dismiss();
			}

			@SuppressLint("SimpleDateFormat")
			private String getCurrentDate() {
				// TODO Auto-generated method stub
				myCalendar.get(Calendar.YEAR); 
				myCalendar.get(Calendar.MONTH); 
				myCalendar.get(Calendar.DAY_OF_MONTH);
		
				Date date = myCalendar.getTime();
				SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy");
				calDate = format.format(date);
				
				return calDate;
			}
		});
		dialog.show();

	}//MakeSignature
	
	private void onSubmit() {
		// TODO Auto-generated method stub
		if(rdHolderFree.isChecked()) {
			stHolder = rdHolderFree.getText().toString();
		}
		else if(rdHolderLead.isChecked()) {
			stHolder = rdHolderLead.getText().toString();
		}
		
		if(rdPossessionVacant.isChecked()) {
			stPossession = rdPossessionVacant.getText().toString();
		}
		else if(rdPossessionTenacy.isChecked()) {
			stPossession = rdPossessionTenacy.getText().toString();
		}
		
		signList.add(new BasicNameValuePair("owner1_signature", stOwner1Sign));
		signList.add(new BasicNameValuePair("owner2_signature", stOwner2Sign));
		signList.add(new BasicNameValuePair("sale_person_signature", stSalePersonSign));
		
		if(connectionManager.isConnected()) {
			new GetExclusiveAuthority().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
		}
	}//onSubmit
	
	private class GetExclusiveAuthority extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				jObj = api.ExclusiveAuthority(pref.getSessionToken(), editPropertyPostal.getText().toString(), editPropertyAddress1.getText().toString(), editPropertyAddress2.getText().toString(), editPropertyBuildingName.getText().toString(), 
						editPropertyType.getText().toString(), editPropertyFloor.getText().toString(), editPropertyLand.getText().toString(), editPropertyRooms.getText().toString(), stHolder, 
						editPropetyYear.getText().toString(), stPossession, editPropertyExpiry.getText().toString(), editPropertyRent.getText().toString(), txtAgentName.getText().toString(), 
						txtAgentLicense.getText().toString(), txtAgentCompany.getText().toString(), editTermsCommencement.getText().toString(), editTermsExpiry.getText().toString(), editTermsExpectedSale.getText().toString(), 
						editTermsFeeSale.getText().toString(), editTermsFeeExpectedSale.getText().toString(), editSoLanguage.getText().toString(), editOwner1Name.getText().toString(), 
						editOwner1Ic.getText().toString(), editOwner1Postal.getText().toString(), editOwner1Address1.getText().toString(), editOwner1Address2.getText().toString(), editOwner1BuildingName.getText().toString(), 
						editOwner2Name.getText().toString(), editOwner2Ic.getText().toString(), editOwner2Postal.getText().toString(), editOwner2Address1.getText().toString(), 
						editOwner2Address2.getText().toString(), editOwner2BuildingName.getText().toString(), txtSaleName.getText().toString(), txtSaleLicense.getText().toString(), 
						signList);
				
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
				int responseCode = jObj.getInt("statusCode");
				int status = jObj.getInt("status");				
				
				if(status == 1 && responseCode == 200){
					Pdf pdf = new Pdf();
					pdf.setPdf_id(jObj.getInt("pdf_id"));
					pdf.setPdf_link(CommonConstants.HOST + jObj.getString("pdf"));
					pdf.setPdf_name(jObj.getString("pdf_name"));
					
					Intent i = new Intent(context, PDFActivity.class);
					i.putExtra("PDF", pdf);
					i.putExtra("Email Title", "Exclusive Authority to Sell");
					i.putExtra("Api Link", CommonConstants.HOST + CommonConstants.STATUS_EXCLUSIVE_AUTHORITY_URL);
					startActivity(i);
				}
				else if(status == 2 && responseCode == 200) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(context, "Unfortunately stopped", Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}//GetAGTpdf
	
}
