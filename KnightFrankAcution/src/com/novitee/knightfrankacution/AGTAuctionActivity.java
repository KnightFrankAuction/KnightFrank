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

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AGTAuctionActivity extends BaseFragmentActivity implements OnClickListener {
	
	Context context = this;
	
	FragmentTransaction fragmentTran;
	
	EditText editCorrespondanceDate, editOwnerName, editPostalCode, editAddress1, editAddress2, editBuildingName;
	EditText editDpaName, editDpaIc, editDpaAgency, editIndemintyOnBehalf;
	EditText editAuctionDate, editAuctionVanue, editAuctionTime, editReservePrice;
	EditText editDsAttn, editDsPostal, editDsAddress1,editDsAddress2, editDsBuildingName, editDsContact;
	EditText editClientIc, editMailingPostal,  editMailingAddress1,  editMailingAddress2,  editMailingBuildingName,  editMailingContact,  editMailingEmail,  editMailingDate;
	RadioGroup rdGroupDpa, rdGroupDcf, rdGroupDsf, rdGroupDf, rdGroupDsr, rdGroupDrp, rdGroupDs;
	RadioButton rdDpaYes, rdDpaNo, rdDcfYes, rdDcfNo, rdDsfYes, rdDsfNo, rdDfYes, rdDfNo, rdDsrYes, rdDsrNo, rdDrpYes, rdDrpNo, rdDsYes, rdDsNo;
	EditText editAgentName, editAgentPosition, editAgentCEA, editAgentEmail, editAgentDID, editAgentHP; 
	TextView txtInfoName, txtIndemintyName, txtClientName, txtDcfInfo, txtDsfInfo, txtDfInfo, txtDsrInfo;
	Button btnAddMoreName, btnSubmit;	
	//Signature
	Button btnSoSign, btnClientSign, btnIndemintySign;
	ImageView imgSoSign, imgClientSign, imgIndemintySign;
	
	//datePicker
	Calendar myCalendar = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener datePicker;
	String calDate;
	EditText text;
	
	String correspondanceDate, ownerName, address1, address2, buildingName;
	String displayPartnerAgent, displayComissionFee, displayServiceFee, displayForfeiture, displaySoleRights, displayReservePrice, displaySolicitors;

	//for signature
	CanvasView canvasView;
	String stSoName = "", stClientName = "", stIndemintyName = "";
	ImageView imgSign;
	String signName = null, todayDate = "";
	List<NameValuePair> signList;
	String sign_flag;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agtauction);
		
		LoadData();
		setTitleBarAndFooter();
		
		editAgentName.setText(pref.getUserName());
		editAgentPosition.setText(pref.getPosition());
		editAgentCEA.setText(pref.getCEA());
		editAgentEmail.setText(pref.getEmail());
		editAgentDID.setText(pref.getDID());
		editAgentHP.setText(pref.getHP());
		
		editCorrespondanceDate.setOnClickListener(this);
		rdDpaYes.setOnClickListener(this);
		rdDpaNo.setOnClickListener(this);
		editAuctionDate.setOnClickListener(this);
		rdDcfYes.setOnClickListener(this);
		rdDcfNo.setOnClickListener(this);
		rdDsfYes.setOnClickListener(this);
		rdDsfNo.setOnClickListener(this);
		rdDfYes.setOnClickListener(this);
		rdDfNo.setOnClickListener(this);
		rdDsrYes.setOnClickListener(this);
		rdDsrNo.setOnClickListener(this);
		btnAddMoreName.setOnClickListener(this);
		rdDrpYes.setOnClickListener(this);
		rdDrpNo.setOnClickListener(this);
		rdDsYes.setOnClickListener(this);
		rdDsNo.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		btnSoSign.setOnClickListener(this);
		btnClientSign.setOnClickListener(this);
		btnIndemintySign.setOnClickListener(this);
		
		//add owner name to appointment info name
		editOwnerName.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				txtInfoName.setText(editOwnerName.getText().toString());
			}
		});
		
		//Date Listener
		datePicker = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				
				text.setText(calDate);
			}
		};
		
	}
	
	private void LoadData() {
		// TODO Auto-generated method stub
		editCorrespondanceDate = (EditText) findViewById(R.id.agt_correspondance_date);
		editOwnerName = (EditText) findViewById(R.id.agt_owner_name);
		editPostalCode = (EditText) findViewById(R.id.agt_postal_code);
		editAddress1 = (EditText) findViewById(R.id.agt_address1);
		editAddress2 = (EditText) findViewById(R.id.agt_address2);
		editBuildingName = (EditText) findViewById(R.id.agt_building_name);
		rdGroupDpa = (RadioGroup) findViewById(R.id.agt_rd_dpa_gp);
		rdDpaYes = (RadioButton) findViewById(R.id.agt_rd_dpa_yes);
		rdDpaNo = (RadioButton) findViewById(R.id.agt_rd_dpa_no);
		editDpaName = (EditText) findViewById(R.id.agt_pa_name);
		editDpaIc = (EditText) findViewById(R.id.agt_pa_ic);
		editDpaAgency = (EditText) findViewById(R.id.agt_pa_agency);
		editAuctionDate = (EditText) findViewById(R.id.agt_auction_date);
		editAuctionVanue = (EditText) findViewById(R.id.agt_auction_vanue);
		editAuctionTime = (EditText) findViewById(R.id.agt_auction_time);
		rdGroupDcf = (RadioGroup) findViewById(R.id.agt_rd_dcf_gp);
		rdDcfYes = (RadioButton) findViewById(R.id.agt_rd_dcf_yes);
		rdDcfNo = (RadioButton) findViewById(R.id.agt_rd_dcf_no);
		txtDcfInfo = (TextView) findViewById(R.id.agt_dcf_info);
		rdGroupDsf = (RadioGroup) findViewById(R.id.agt_rd_dsf_gp);
		rdDsfYes = (RadioButton) findViewById(R.id.agt_rd_dsf_yes);
		rdDsfNo = (RadioButton) findViewById(R.id.agt_rd_dsf_no);
		txtDsfInfo = (TextView) findViewById(R.id.agt_dsf_info);
		rdGroupDf = (RadioGroup) findViewById(R.id.agt_rd_df_gp);
		rdDfYes = (RadioButton) findViewById(R.id.agt_rd_df_yes);
		rdDfNo = (RadioButton) findViewById(R.id.agt_rd_df_no);
		txtDfInfo = (TextView) findViewById(R.id.agt_df_info);
		rdGroupDsr = (RadioGroup) findViewById(R.id.agt_rd_dsr_gp);
		rdDsrYes = (RadioButton) findViewById(R.id.agt_rd_dsr_yes);
		rdDsrNo = (RadioButton) findViewById(R.id.agt_rd_dsr_no);
		txtDsrInfo = (TextView) findViewById(R.id.agt_dsr_info);
		editAgentName = (EditText) findViewById(R.id.agt_edit_agent_so_name);
		editAgentPosition = (EditText) findViewById(R.id.agt_edit_agent_so_position);
		editAgentCEA = (EditText) findViewById(R.id.agt_edit_agent_so_cea);
		editAgentEmail = (EditText) findViewById(R.id.agt_edit_agent_so_email);
		editAgentDID = (EditText) findViewById(R.id.agt_edit_agent_so_did);
		editAgentHP = (EditText) findViewById(R.id.agt_edit_agent_so_hp);
		txtInfoName = (TextView) findViewById(R.id.agt_app_info_name);
		btnAddMoreName = (Button) findViewById(R.id.agt_btn_app_info_add);
		rdGroupDrp = (RadioGroup) findViewById(R.id.agt_rd_drp_gp);
        rdDrpYes = (RadioButton) findViewById(R.id.agt_rd_drp_yes);
        rdDrpNo = (RadioButton) findViewById(R.id.agt_rd_drp_no);
        editReservePrice = (EditText) findViewById(R.id.agt_edit_reserve_price);
        rdGroupDs = (RadioGroup) findViewById(R.id.agt_rd_ds_gp);
		rdDsYes = (RadioButton) findViewById(R.id.agt_rd_ds_yes);
		rdDsNo = (RadioButton) findViewById(R.id.agt_rd_ds_no);
		editDsAttn = (EditText) findViewById(R.id.agt_edit_attn);
		editDsPostal = (EditText) findViewById(R.id.agt_edit_ds_postal);
		editDsAddress1 = (EditText) findViewById(R.id.agt_edit_ds_address1);
		editDsAddress2 = (EditText) findViewById(R.id.agt_edit_ds_address2);
		editDsBuildingName = (EditText) findViewById(R.id.agt_edit_ds_building_name);
		editDsContact = (EditText) findViewById(R.id.agt_edit_ds_contact);
		txtClientName = (TextView) findViewById(R.id.agt_client_name);
		editClientIc = (EditText) findViewById(R.id.agt_edit_client_ic);
		editMailingPostal = (EditText) findViewById(R.id.agt_edit_mailing_postal); 
		editMailingAddress1 = (EditText) findViewById(R.id.agt_edit_mailing_address1); 
		editMailingAddress2 = (EditText) findViewById(R.id.agt_edit_mailing_address2); 
		editMailingBuildingName = (EditText) findViewById(R.id.agt_edit_mailing_building_name); 
		editMailingContact = (EditText) findViewById(R.id.agt_edit_mailing_contact); 
		editMailingEmail = (EditText) findViewById(R.id.agt_edit_mailing_email); 
		editMailingDate = (EditText) findViewById(R.id.agt_edit_mailing_date);
		txtIndemintyName = (TextView) findViewById(R.id.agt_indeminty_name);
		editIndemintyOnBehalf = (EditText) findViewById(R.id.agt_edit_on_behalf);
		btnSubmit = (Button) findViewById(R.id.agt_submit);
		
		//Signature
		btnSoSign = (Button) findViewById(R.id.btn_agt_agent_so_sign);
		btnClientSign = (Button) findViewById(R.id.btn_agt_client_sign);
		btnIndemintySign = (Button) findViewById(R.id.btn_agt_indeminty_sign);
		imgSoSign = (ImageView) findViewById(R.id.img_agent_so_sign);
		imgClientSign = (ImageView) findViewById(R.id.img_agt_client_sign);
		imgIndemintySign = (ImageView) findViewById(R.id.img_agt_indeminty_sign);
		
		signList = new ArrayList<NameValuePair>();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		//Correspondance Date
		if(v.getId() == editCorrespondanceDate.getId()) {
			setDate(editCorrespondanceDate);
		}
		//Auction Date
		else if(v.getId() == editAuctionDate.getId()) {
			setDate(editAuctionDate);
		}
		//add more name to appoinment info
		else if(v.getId() == btnAddMoreName.getId()) {
			
		}
		//Agent sign off signature
		else if(v.getId() == btnSoSign.getId()) {
			sign_flag = "SoSign";
			MakeSignature(imgSoSign);
		}
		//Client signature
		else if(v.getId() == btnClientSign.getId()) {
			sign_flag = "ClientSign";
			MakeSignature(imgClientSign);
		}
		//Client signature
		else if(v.getId() == btnIndemintySign.getId()) {
			sign_flag = "IndemintySign";
			MakeSignature(imgIndemintySign);
		}
		//AGT Submit
		else if(v.getId() == btnSubmit.getId()) {
			onSubmit();
		}

	}//onClick

	public void setTitleBarAndFooter() {
		//title bar
		TextView titleText = (TextView) findViewById(R.id.title_text);
		TextView titleText2 = (TextView) findViewById(R.id.title_text2);
		ImageView titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleText.setText("AGT Auction Proposal");
		titleImage.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.agt_footer, new FooterFragment());
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
		
		DatePickerDialog dateDialog = new DatePickerDialog(AGTAuctionActivity.this,
				datePicker, 
				myCalendar.get(Calendar.YEAR), 
				myCalendar.get(Calendar.MONTH), 
				myCalendar.get(Calendar.DAY_OF_MONTH));
		
				Date date = myCalendar.getTime();
				calDate = java.text.DateFormat.getDateInstance().format(date);
		
		dateDialog.show();
	}
	
	private void MakeSignature(ImageView imgView) {
		// TODO Auto-generated method stub
		imgSign = imgView;
		
		final Dialog dialog = new Dialog(AGTAuctionActivity.this);
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
				signName = "agt_auction_" + signName +"_"+ todayDate +".png";
				
				Bitmap b = canvasView.saveCanvas(context, canvasView, signName);
				imgSign.setImageBitmap(b);
				
				signName = Environment.getExternalStorageDirectory().toString() + "/"+ signName;
				
				if(sign_flag.equals("SoSign")) {
					stSoName = signName;
				}
				else if(sign_flag.equals("ClientSign")) {
					stClientName = signName;
				}
				else if(sign_flag.equals("IndemintySign")) {
					stIndemintyName = signName;
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
		
	}
	
	private void onSubmit() {
		//Display Partner Group
		if(rdDpaYes.isChecked()) {
			displayPartnerAgent = rdDpaYes.getText().toString();
		}
		else if(rdDpaNo.isChecked()) {
			displayPartnerAgent = rdDpaNo.getText().toString();
		}
		
		//Display Comission Fee Group
		if(rdDcfYes.isChecked()) {
			displayComissionFee = rdDcfYes.getText().toString();
		}
		else if(rdDcfNo.isChecked()) {
			displayComissionFee = rdDcfNo.getText().toString();
		}
		
		//Display service fee Group
		if(rdDsfYes.isChecked()) {
			displayServiceFee = rdDsfYes.getText().toString();
		}
		else if(rdDsfNo.isChecked()) {
			displayServiceFee = rdDsfNo.getText().toString();
		}
		
		//Display Forfeiture Group
		if(rdDfYes.isChecked()) {
			displayForfeiture = rdDfYes.getText().toString();
		}
		if(rdDfNo.isChecked()) {
			displayForfeiture = rdDfNo.getText().toString();
		}
		
		//Display sole rights Group
		if(rdDsrYes.isChecked()) {
			displaySoleRights = rdDsrYes.getText().toString();
		}
		else if(rdDsrNo.isChecked()) {
			displaySoleRights = rdDsrNo.getText().toString();
		}
		
		//Display reserve price Group
		if(rdDrpYes.isChecked()) {
			displayReservePrice = rdDrpYes.getText().toString();
		}
		else if(rdDrpNo.isChecked()) {
			displayReservePrice = rdDrpNo.getText().toString();
		}
		
		//Display reserve price Group
		if(rdDsYes.isChecked()) {
			displaySolicitors = rdDsYes.getText().toString();
		}
		else if(rdDsNo.isChecked()) {
			displaySolicitors = rdDsNo.getText().toString();
		}
		
		signList.add(new BasicNameValuePair("owner1_signature", stClientName));
		signList.add(new BasicNameValuePair("sign_by_owner", stIndemintyName));
		signList.add(new BasicNameValuePair("kfa_agent_signature", stSoName));
		
		if(connectionManager.isConnected()) {
			new GetAGTpdf().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
		}
	}

	private class GetAGTpdf extends AsyncTask<Void, Void, Void> {
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
				
				jObj = api.agtAuction(
						pref.getSessionToken(), editCorrespondanceDate.getText().toString(), editPostalCode.getText().toString(), editAddress1.getText().toString(), editAddress2.getText().toString(), 
						editBuildingName.getText().toString(), displayPartnerAgent, editDpaName.getText().toString(), editDpaIc.getText().toString(), editDpaAgency.getText().toString(), 
						editAuctionDate.getText().toString(), editAuctionVanue.getText().toString(), editAuctionTime.getText().toString(), displayComissionFee, txtDcfInfo.getText().toString(), 
						displayServiceFee, txtDsfInfo.getText().toString(), displayForfeiture, txtDfInfo.getText().toString(), displaySoleRights, 
						txtDsrInfo.getText().toString(), editAgentName.getText().toString(), editAgentPosition.getText().toString(), editAgentCEA.getText().toString(), 
						editAgentEmail.getText().toString(), editAgentDID.getText().toString(), editAgentHP.getText().toString(), txtInfoName.getText().toString(), displayReservePrice, 
						editReservePrice.getText().toString(), displaySolicitors, editDsAttn.getText().toString(), editDsPostal.getText().toString(), editDsAddress1.getText().toString(), 
						editDsAddress2.getText().toString(), editDsBuildingName.getText().toString(), editDsContact.getText().toString(), txtClientName.getText().toString(), editClientIc.getText().toString(), 
						editMailingPostal.getText().toString(), editMailingAddress1.getText().toString(), editMailingAddress2.getText().toString(), editMailingBuildingName.getText().toString(), 
						editMailingContact.getText().toString(), editMailingEmail.getText().toString(), editMailingDate.getText().toString(), txtIndemintyName.getText().toString(),
						editIndemintyOnBehalf.getText().toString(), signList);
				
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
					i.putExtra("Email Title", "AGT Auction");
					i.putExtra("Api Link", CommonConstants.HOST + CommonConstants.STATUS_AGT_URL);
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

