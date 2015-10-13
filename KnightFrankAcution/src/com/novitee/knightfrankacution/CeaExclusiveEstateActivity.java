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
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

public class CeaExclusiveEstateActivity extends BaseFragmentActivity implements OnClickListener {
	
	Context context = this;
	
	FragmentTransaction fragmentTran;
	
	RadioGroup rdGroupAgree;
	RadioButton rdAgreeOriginal, rdAgreeRenewal, rdPayableYes, rdPayableNo, rdTypeInclusive, rdTypeExclusive;
	RadioButton rdConflictHave, rdConflictNot, rdBrokeMay, rdBrokeNot, rdWarrantOwner, rdWarrantDuty, rdRespresentBuyer, rdRespresentSeller;
	EditText editAgreeDate;
	EditText editSeller1Name, editSeller1Ic, editSeller1Postal, editSeller1Address1, editSeller1Address2, editSeller1Building; 
	EditText editSeller2Name, editSeller2Ic, editSeller2Postal, editSeller2Address1, editSeller2Address2, editSeller2Building;
	EditText editSeller3Name, editSeller3Ic, editSeller3Postal, editSeller3Address1, editSeller3Address2, editSeller3Building;
	EditText editSeller4Name, editSeller4Ic, editSeller4Postal, editSeller4Address1, editSeller4Address2, editSeller4Building;
	EditText editEAgentName, editEAgentLicence, editEAgentPostal, editEAgentAddress1, editEAgentAddress2, editEAgentBuilding;
	EditText editPropertyPostal, editPropertyAddress1, editPropertyAddress2, editPropertyBuilding, editPersonDetail, editBankFees;
	EditText editCommenceDate, editExpiryDate, editPriceWords, editPriceNumbers, editComissionDollar, editComissionPercent, editConflict;
	Button btnSeller1Sign, btnSeller2Sign, btnSeller3Sign, btnSeller4Sign, btnInterpreter1Sign, btnInterpreter2Sign, btnInterpreter3Sign, btnInterpreter4Sign, btnSAgentSign;
	ImageView imgSeller1Sign, imgSeller2Sign, imgSeller3Sign, imgSeller4Sign, imgInterpreter1Sign, imgInterpreter2Sign, imgInterpreter3Sign, imgInterpreter4Sign, imgSAgentSign;
	EditText editInterpreter1Name, editInterpreter2Name, editInterpreter3Name, editInterpreter4Name, editInterpreter1Ic, editInterpreter2Ic, editInterpreter3Ic, editInterpreter4Ic;
	String stSeller1Sign, stSeller2Sign, stSeller3Sign, stSeller4Sign, stInterpreter1Sign, stInterpreter2Sign, stInterpreter3Sign, stInterpreter4Sign, stSAgentSign; 
	EditText editSAgentPostal, editSAgentAddress1, editSAgentAddress2, editSAgentBuilding; 
	TextView txtSAgentName, txtSAgentIc, txtSAgentCea, txtSAgentTel;
	Button btnSubmit;
	
	//datePicker
	Calendar myCalendar = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener datePicker;
	String calDate;
	EditText text;
	
	//for signature
	CanvasView canvasView;
	ImageView imgSign;
	String signName = null, todayDate = "";
	List<NameValuePair> signList;
	String sign_flag;
	
	String stTypeAgree, stGSTPayable, stGSTType, stConflictInterest, stCoBroke, stSellerWarrant, stSaleRepresent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cea_exclusive_estate);
		
		LoadData();
		setTitleBarAndFooter();
		
        editAgreeDate.setOnClickListener(this);
        editCommenceDate.setOnClickListener(this);
        editExpiryDate.setOnClickListener(this);
        btnSeller1Sign.setOnClickListener(this);
        btnSeller2Sign.setOnClickListener(this);
        btnSeller3Sign.setOnClickListener(this);
        btnSeller4Sign.setOnClickListener(this);
        btnInterpreter1Sign.setOnClickListener(this);
        btnInterpreter2Sign.setOnClickListener(this);
        btnInterpreter3Sign.setOnClickListener(this);
        btnInterpreter4Sign.setOnClickListener(this);
        btnSAgentSign.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == editAgreeDate.getId()) {
			setDate(editAgreeDate);
		}
		else if(v.getId() == editCommenceDate.getId()) {
			setDate(editCommenceDate);
		}
		else if(v.getId() == editExpiryDate.getId()) {
			setDate(editExpiryDate);
		}
		else if(v.getId() == btnSeller1Sign.getId()) {
			sign_flag = "Seller1Sign";
			MakeSignature(imgSeller1Sign);
		}
		else if(v.getId() == btnSeller2Sign.getId()) {
			sign_flag = "Seller2Sign";
            MakeSignature(imgSeller2Sign);
        }
		else if(v.getId() == btnSeller3Sign.getId()) {
			sign_flag = "Seller3Sign";
            MakeSignature(imgSeller3Sign);
        }
		else if(v.getId() == btnSeller4Sign.getId()) {
			sign_flag = "Seller4Sign";
            MakeSignature(imgSeller4Sign);
        }
		else if(v.getId() == btnInterpreter1Sign.getId()) {
			sign_flag = "Interpreter1Sign";
            MakeSignature(imgInterpreter1Sign);
        }
        else if(v.getId() == btnInterpreter2Sign.getId()) {
        	sign_flag = "Interpreter2Sign";
            MakeSignature(imgInterpreter2Sign);
        }
        else if(v.getId() == btnInterpreter3Sign.getId()) {
        	sign_flag = "Interpreter3Sign";
            MakeSignature(imgInterpreter3Sign);
        }
        else if(v.getId() == btnInterpreter4Sign.getId()) {
        	sign_flag = "Interpreter4Sign";
            MakeSignature(imgInterpreter4Sign);
        }
        else if(v.getId() == btnSAgentSign.getId()) {
        	sign_flag = "SAgentSign";
            MakeSignature(imgSAgentSign);
        }
        else if(v.getId() == btnSubmit.getId()) {
            onSubmit();
        }
	}
	
	private void LoadData() {
		rdGroupAgree = (RadioGroup) findViewById(R.id.rd_cee_agree_gp);
		rdAgreeOriginal = (RadioButton) findViewById(R.id.rd_cee_agree_original);
		rdAgreeRenewal = (RadioButton) findViewById(R.id.rd_cee_agree_renewal);
		editAgreeDate = (EditText) findViewById(R.id.cee_edit_aggrement_date);
		editSeller1Name = (EditText) findViewById(R.id.cee_edit_seller1_name);
		editSeller1Ic = (EditText) findViewById(R.id.cee_edit_seller1_ic);
		editSeller1Postal = (EditText) findViewById(R.id.cee_edit_seller1_postal);
		editSeller1Address1 = (EditText) findViewById(R.id.cee_edit_seller1_address1);
		editSeller1Address2 = (EditText) findViewById(R.id.cee_edit_seller1_address2);
		editSeller1Building = (EditText) findViewById(R.id.cee_edit_seller1_building_name);
		editSeller2Name = (EditText) findViewById(R.id.cee_edit_seller2_name);
        editSeller2Ic = (EditText) findViewById(R.id.cee_edit_seller2_ic);
        editSeller2Postal = (EditText) findViewById(R.id.cee_edit_seller2_postal);
        editSeller2Address1 = (EditText) findViewById(R.id.cee_edit_seller2_address1);
        editSeller2Address2 = (EditText) findViewById(R.id.cee_edit_seller2_address2);
        editSeller2Building = (EditText) findViewById(R.id.cee_edit_seller2_building_name);
        editSeller3Name = (EditText) findViewById(R.id.cee_edit_seller3_name);
        editSeller3Ic = (EditText) findViewById(R.id.cee_edit_seller3_ic);
        editSeller3Postal = (EditText) findViewById(R.id.cee_edit_seller3_postal);
        editSeller3Address1 = (EditText) findViewById(R.id.cee_edit_seller3_address1);
        editSeller3Address2 = (EditText) findViewById(R.id.cee_edit_seller3_address2);
        editSeller3Building = (EditText) findViewById(R.id.cee_edit_seller3_building_name);
        editSeller4Name = (EditText) findViewById(R.id.cee_edit_seller4_name);
        editSeller4Ic = (EditText) findViewById(R.id.cee_edit_seller4_ic);
        editSeller4Postal = (EditText) findViewById(R.id.cee_edit_seller4_postal);
        editSeller4Address1 = (EditText) findViewById(R.id.cee_edit_seller4_address1);
        editSeller4Address2 = (EditText) findViewById(R.id.cee_edit_seller4_address2);
        editSeller4Building = (EditText) findViewById(R.id.cee_edit_seller4_building_name);
        editEAgentName = (EditText) findViewById(R.id.cee_edit_agent_name);
        editEAgentLicence = (EditText) findViewById(R.id.cee_edit_agent_licence);
        editEAgentPostal = (EditText) findViewById(R.id.cee_edit_agent_postal);
        editEAgentAddress1 = (EditText) findViewById(R.id.cee_edit_agent_address1);
        editEAgentAddress2 = (EditText) findViewById(R.id.cee_edit_agent_address2);
        editEAgentBuilding = (EditText) findViewById(R.id.cee_edit_agent_building_name);
        editPropertyPostal = (EditText) findViewById(R.id.cee_edit_property_postal);
        editPropertyAddress1 = (EditText) findViewById(R.id.cee_edit_property_address1);
        editPropertyAddress2 = (EditText) findViewById(R.id.cee_edit_property_address2);
        editPropertyBuilding = (EditText) findViewById(R.id.cee_edit_property_building_name);
        editCommenceDate = (EditText) findViewById(R.id.cee_edit_validity_commencement);
        editExpiryDate = (EditText) findViewById(R.id.cee_edit_validity_expiry);
        editPriceWords = (EditText) findViewById(R.id.cee_edit_price_spelling);
        editPriceNumbers = (EditText) findViewById(R.id.cee_edit_price_numbers);
        editComissionDollar = (EditText) findViewById(R.id.cee_edit_comission_dollar);
        editComissionPercent = (EditText) findViewById(R.id.cee_edit_comission_percent);
        rdPayableYes = (RadioButton) findViewById(R.id.rd_cee_gst_yes);
        rdPayableNo = (RadioButton) findViewById(R.id.rd_cee_gst_no);
        rdTypeInclusive = (RadioButton) findViewById(R.id.rd_cee_gst_type_inclusive);
        rdTypeExclusive = (RadioButton) findViewById(R.id.rd_cee_gst_type_exclusive);
        rdConflictHave = (RadioButton) findViewById(R.id.rd_cee_conflict_have);
        rdConflictNot = (RadioButton) findViewById(R.id.rd_cee_conflict_not);
        editConflict = (EditText) findViewById(R.id.cee_edit_conflict_reason);
        rdBrokeMay = (RadioButton) findViewById(R.id.rd_cee_broke_may);
        rdBrokeNot = (RadioButton) findViewById(R.id.rd_cee_broke_not);
        rdWarrantOwner = (RadioButton) findViewById(R.id.rd_cee_warrant_owner);
        rdWarrantDuty = (RadioButton) findViewById(R.id.rd_cee_warrant_duty);
        rdRespresentBuyer = (RadioButton) findViewById(R.id.rd_cee_warrant_buyer);
        rdRespresentSeller = (RadioButton) findViewById(R.id.rd_cee_warrant_seller);
        editPersonDetail = (EditText) findViewById(R.id.cee_edit_person_detail);
		editBankFees = (EditText) findViewById(R.id.cee_edit_bank_fees);
		btnSeller1Sign = (Button) findViewById(R.id.btn_cee_seller1_sign);
		imgSeller1Sign = (ImageView) findViewById(R.id.img_cee_seller1_sign);
		editInterpreter1Name = (EditText) findViewById(R.id.cee_edit_interpreter1_name);
		btnInterpreter1Sign = (Button) findViewById(R.id.btn_cee_interpreter1_sign);
		imgInterpreter1Sign = (ImageView) findViewById(R.id.img_cee_interpreter1_sign);
		editInterpreter1Ic = (EditText) findViewById(R.id.cee_edit_interpreter1_ic);
		btnSeller2Sign = (Button) findViewById(R.id.btn_cee_seller2_sign);
        imgSeller2Sign = (ImageView) findViewById(R.id.img_cee_seller2_sign);
        editInterpreter2Name = (EditText) findViewById(R.id.cee_edit_interpreter2_name);
        btnInterpreter2Sign = (Button) findViewById(R.id.btn_cee_interpreter2_sign);
        imgInterpreter2Sign = (ImageView) findViewById(R.id.img_cee_interpreter2_sign);
        editInterpreter2Ic = (EditText) findViewById(R.id.cee_edit_interpreter2_ic);
        btnSeller3Sign = (Button) findViewById(R.id.btn_cee_seller3_sign);
        imgSeller3Sign = (ImageView) findViewById(R.id.img_cee_seller3_sign);
        editInterpreter3Name = (EditText) findViewById(R.id.cee_edit_interpreter3_name);
        btnInterpreter3Sign = (Button) findViewById(R.id.btn_cee_interpreter3_sign);
        imgInterpreter3Sign = (ImageView) findViewById(R.id.img_cee_interpreter3_sign);
        editInterpreter3Ic = (EditText) findViewById(R.id.cee_edit_interpreter3_ic);
        btnSeller4Sign = (Button) findViewById(R.id.btn_cee_seller4_sign);
        imgSeller4Sign = (ImageView) findViewById(R.id.img_cee_seller4_sign);
        editInterpreter4Name = (EditText) findViewById(R.id.cee_edit_interpreter4_name);
        btnInterpreter4Sign = (Button) findViewById(R.id.btn_cee_interpreter4_sign);
        imgInterpreter4Sign = (ImageView) findViewById(R.id.img_cee_interpreter4_sign);
        editInterpreter4Ic = (EditText) findViewById(R.id.cee_edit_interpreter4_ic);
        btnSAgentSign = (Button) findViewById(R.id.btn_cee_agent_sign);
        imgSAgentSign = (ImageView) findViewById(R.id.img_cee_agent_sign);
        txtSAgentName = (TextView) findViewById(R.id.cee_txt_sagent_name);
        txtSAgentIc = (TextView) findViewById(R.id.cee_txt_sagent_ic);
        editSAgentPostal = (EditText) findViewById(R.id.cee_edit_sagent_postal);
        editSAgentAddress1 = (EditText) findViewById(R.id.cee_edit_sagent_address1);
        editSAgentAddress2 = (EditText) findViewById(R.id.cee_edit_sagent_address2);
        editSAgentBuilding = (EditText) findViewById(R.id.cee_edit_sagent_building_name);
        txtSAgentCea = (TextView) findViewById(R.id.cee_txt_sagent_cea);
        txtSAgentTel = (TextView) findViewById(R.id.cee_txt_sagent_tel);
        btnSubmit = (Button) findViewById(R.id.cee_submit);
        
        signList = new ArrayList<NameValuePair>();
	}
	
	public void setTitleBarAndFooter() {
		//title bar
		TextView titleText = (TextView) findViewById(R.id.title_text);
		TextView titleText2 = (TextView) findViewById(R.id.title_text2);
		ImageView titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleText.setTextSize((float) 18);
		titleText.setPadding(10, 0, 0, 0);
		titleText.setText("CEA Exclusive Estate Agency Agreement");
		titleImage.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.cea_exclusive_footer, new FooterFragment());
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
		
		DatePickerDialog dateDialog = new DatePickerDialog(CeaExclusiveEstateActivity.this,
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
		
		final Dialog dialog = new Dialog(CeaExclusiveEstateActivity.this);
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
				signName = "cea_exclusive_" + signName +"_"+ todayDate +".png";
				
				Bitmap b = canvasView.saveCanvas(context, canvasView, signName);
				imgSign.setImageBitmap(b);
				
				signName = Environment.getExternalStorageDirectory().toString() + "/"+ signName;
				
				if(sign_flag.equals("Seller1Sign")) {
					stSeller1Sign = signName;
				}
				else if(sign_flag.equals("Seller2Sign")) {
					stSeller2Sign = signName;
				}
				else if(sign_flag.equals("Seller3Sign")) {
					stSeller3Sign = signName;
				}
				else if(sign_flag.equals("Seller4Sign")) {
					stSeller4Sign = signName;
				}
				else if(sign_flag.equals("Interpreter1Sign")) {
					stInterpreter1Sign = signName;
				}
				else if(sign_flag.equals("Interpreter2Sign")) {
					stInterpreter2Sign = signName;
				}
				else if(sign_flag.equals("Interpreter3Sign")) {
					stInterpreter3Sign = signName;
				}
				else if(sign_flag.equals("Interpreter4Sign")) {
					stInterpreter4Sign = signName;
				}
				else if(sign_flag.equals("SAgentSign")) {
					stSAgentSign = signName;
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
		//type of agreement
		if(rdAgreeOriginal.isChecked()) {
			stTypeAgree = rdAgreeOriginal.getText().toString();
		}
		else if(rdAgreeRenewal.isChecked()) {
			stTypeAgree = rdAgreeRenewal.getText().toString();
		}
		
		//GST Payable
		if(rdPayableYes.isChecked()) {
			stGSTPayable = rdPayableYes.getText().toString();
		}
		else if(rdPayableNo.isChecked()) {
			stGSTPayable = rdPayableNo.getText().toString();
		}
		
		//GST type
		if(rdTypeInclusive.isChecked()) {
			stGSTType = rdTypeInclusive.getText().toString();
		}
		else if(rdTypeExclusive.isChecked()) {
			stGSTType = rdTypeExclusive.getText().toString();
		}
		
		//Conflict of Interest
		if(rdConflictNot.isChecked()) {
			stConflictInterest = rdConflictNot.getText().toString();
		}
		else if(rdConflictHave.isChecked()) {
			stConflictInterest = rdConflictHave.getText().toString();
		}
		
		//Co broke
		if(rdBrokeMay.isChecked()) {
			stCoBroke = rdBrokeMay.getText().toString();
		}
		else if(rdBrokeNot.isChecked()) {
			stCoBroke = rdBrokeNot.getText().toString();
		}
		
		//Seller Warrant
		if(rdWarrantOwner.isChecked()) {
			stSellerWarrant = rdWarrantOwner.getText().toString();
		}
		else if(rdWarrantDuty.isChecked()) {
			stSellerWarrant = rdWarrantDuty.getText().toString();
		}
		
		//Sales person represent
		if(rdRespresentBuyer.isChecked()) {
			stSaleRepresent = rdRespresentBuyer.getText().toString();
		}
		else if(rdRespresentSeller.isChecked()) {
			stSaleRepresent = rdRespresentSeller.getText().toString();
		}
		
		signList.add(new BasicNameValuePair("seller1_signature", stSeller1Sign));
		signList.add(new BasicNameValuePair("seller2_signature", stSeller2Sign));
		signList.add(new BasicNameValuePair("seller3_signature", stSeller3Sign));
		signList.add(new BasicNameValuePair("seller4_signature", stSeller4Sign));
		signList.add(new BasicNameValuePair("seller1_interpreter_signature", stInterpreter1Sign));
		signList.add(new BasicNameValuePair("seller2_interpreter_signature", stInterpreter2Sign));
		signList.add(new BasicNameValuePair("seller3_interpreter_signature", stInterpreter3Sign));
		signList.add(new BasicNameValuePair("seller4_interpreter_signature", stInterpreter4Sign));
		signList.add(new BasicNameValuePair("sale_person_signature", stSAgentSign));
		
		if(connectionManager.isConnected()) {
			new GetCeaExclusiveEstate().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
		}
		
	}//onSubmit
	
	private class GetCeaExclusiveEstate extends AsyncTask<Void, Void, Void> {
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
				/*jObj = api.CeaExclusiveEstate(
											pref.getSessionToken(), stTypeAgree, editAgreeDate.getText().toString(), editSeller1Name.getText().toString(), editSeller1Ic.getText().toString(), 
											editSeller1Postal.getText().toString(), editSeller1Address1.getText().toString(), editSeller1Address2.getText().toString(), editSeller1Building.getText().toString(), editSeller2Name.getText().toString(), 
											editSeller2Ic.getText().toString(), editSeller2Postal.getText().toString(), editSeller2Address1.getText().toString(), editSeller2Address2.getText().toString(), editSeller2Building.getText().toString(), 
											editSeller3Name.getText().toString(), editSeller3Ic.getText().toString(), editSeller3Postal.getText().toString(), editSeller3Address1.getText().toString(), editSeller3Address2.getText().toString(), 
											editSeller3Building.getText().toString(), editSeller4Name.getText().toString(), editSeller4Ic.getText().toString(), editSeller4Postal.getText().toString(), editSeller4Address1.getText().toString(), 
											editSeller4Address2.getText().toString(), editSeller4Building.getText().toString(), editEAgentName.getText().toString(), editEAgentLicence.getText().toString(), editEAgentPostal.getText().toString(), 
											editEAgentAddress1.getText().toString(), editEAgentAddress2.getText().toString(), editEAgentBuilding.getText().toString(), editPropertyPostal.getText().toString(), editPropertyAddress1.getText().toString(), 
											editPropertyAddress2.getText().toString(), editPropertyBuilding.getText().toString(), editCommenceDate.getText().toString(), editExpiryDate.getText().toString(), editPriceWords.getText().toString(), 
											editPriceNumbers.getText().toString(), editComissionDollar.getText().toString(), editComissionPercent.getText().toString(), stGSTPayable, stGSTType, 
											stConflictInterest, editConflict.getText().toString(), stCoBroke, stSellerWarrant, stSaleRepresent, 
											editPersonDetail.getText().toString(), editBankFees.getText().toString(), stSeller1Sign, editInterpreter1Name.getText().toString(), stInterpreter1Sign, 
											editInterpreter1Ic.getText().toString(), stSeller2Sign, editInterpreter2Name.getText().toString(), stInterpreter2Sign, editInterpreter2Ic.getText().toString(), 
											stSeller3Sign, editInterpreter2Name.getText().toString(), stInterpreter3Sign, editInterpreter3Ic.getText().toString(), stSeller4Sign, 
											editInterpreter4Name.getText().toString(), stInterpreter4Sign, editInterpreter4Ic.getText().toString(), stSAgentSign, txtSAgentName.getText().toString(), 
											txtSAgentIc.getText().toString(), editSAgentPostal.getText().toString(), editSAgentAddress1.getText().toString(), editSAgentAddress2.getText().toString(), editSAgentBuilding.getText().toString(), 
											txtSAgentCea.getText().toString(), txtSAgentTel.getText().toString());*/
				
				jObj = api.CeaExclusiveEstate(
						pref.getSessionToken(), stTypeAgree, editAgreeDate.getText().toString(), editSeller1Name.getText().toString(), editSeller1Ic.getText().toString(), 
						editSeller1Postal.getText().toString(), editSeller1Address1.getText().toString(), editSeller1Address2.getText().toString(), editSeller1Building.getText().toString(), editSeller2Name.getText().toString(), 
						editSeller2Ic.getText().toString(), editSeller2Postal.getText().toString(), editSeller2Address1.getText().toString(), editSeller2Address2.getText().toString(), editSeller2Building.getText().toString(), 
						editSeller3Name.getText().toString(), editSeller3Ic.getText().toString(), editSeller3Postal.getText().toString(), editSeller3Address1.getText().toString(), editSeller3Address2.getText().toString(), 
						editSeller3Building.getText().toString(), editSeller4Name.getText().toString(), editSeller4Ic.getText().toString(), editSeller4Postal.getText().toString(), editSeller4Address1.getText().toString(), 
						editSeller4Address2.getText().toString(), editSeller4Building.getText().toString(), editEAgentName.getText().toString(), editEAgentLicence.getText().toString(), editEAgentPostal.getText().toString(), 
						editEAgentAddress1.getText().toString(), editEAgentAddress2.getText().toString(), editEAgentBuilding.getText().toString(), editPropertyPostal.getText().toString(), editPropertyAddress1.getText().toString(), 
						editPropertyAddress2.getText().toString(), editPropertyBuilding.getText().toString(), editCommenceDate.getText().toString(), editExpiryDate.getText().toString(), editPriceWords.getText().toString(), 
						editPriceNumbers.getText().toString(), editComissionDollar.getText().toString(), editComissionPercent.getText().toString(), stGSTPayable, stGSTType, 
						stConflictInterest, editConflict.getText().toString(), stCoBroke, stSellerWarrant, stSaleRepresent, 
						editPersonDetail.getText().toString(), editBankFees.getText().toString(), editInterpreter1Name.getText().toString(), 
						editInterpreter1Ic.getText().toString(), editInterpreter2Name.getText().toString(), editInterpreter2Ic.getText().toString(), 
						editInterpreter2Name.getText().toString(), editInterpreter3Ic.getText().toString(),  
						editInterpreter4Name.getText().toString(), editInterpreter4Ic.getText().toString(), txtSAgentName.getText().toString(), 
						txtSAgentIc.getText().toString(), editSAgentPostal.getText().toString(), editSAgentAddress1.getText().toString(), editSAgentAddress2.getText().toString(), editSAgentBuilding.getText().toString(), 
						txtSAgentCea.getText().toString(), txtSAgentTel.getText().toString(), signList);
				
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
					i.putExtra("Email Title", "CEA Exclusive Estate Agency Agreement");
					i.putExtra("Api Link", CommonConstants.HOST + CommonConstants.STATUS_CEA_EXCLUSIVE_ESTATE_URL);
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
	}//GetCeaExclusiveEstate
}
