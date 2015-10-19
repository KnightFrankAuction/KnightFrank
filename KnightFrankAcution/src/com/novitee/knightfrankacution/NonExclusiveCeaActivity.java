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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class NonExclusiveCeaActivity extends BaseFragmentActivity implements OnClickListener {

	Context context = this;
	
	FragmentTransaction fragmentTran;
	
	EditText editAgreementDate, editComissionDollar, editComissionPercent, editGstNote, editConflictReason, editWarrantRemark;
	EditText editSeller1Name, editSeller1Ic, editSeller1Postal, editSeller1Address1, editSeller1Address2, editSeller1Building;
	EditText editSeller2Name, editSeller2Ic, editSeller2Postal, editSeller2Address1, editSeller2Address2, editSeller2Building;
	EditText editSeller3Name, editSeller3Ic, editSeller3Postal, editSeller3Address1, editSeller3Address2, editSeller3Building;
	EditText editSeller4Name, editSeller4Ic, editSeller4Postal, editSeller4Address1, editSeller4Address2, editSeller4Building;
	EditText editSAgentName, editSAgentLicence, editSAgentPostal, editSAgentAddress1, editSAgentAddress2, editSAgentBuilding;
	EditText editPropertyPostal, editPropertyAddress1, editPropertyAddress2, editPropertyBuilding;  
	RadioButton rdGstPayableYes, rdGstPayableNo, rdGstTypeInclusive, rdGstTypeExclusive, rdConflictHave, rdConflictNot;
	RadioButton rdCoBrokeMay, rdCoBrokeNot, rdWarrantOwner, rdWarrantDuty;
	EditText editInterpreter1Name, editInterpreter1Ic, editInterpreter2Name, editInterpreter2Ic, editInterpreter3Name, editInterpreter3Ic,editInterpreter4Name, editInterpreter4Ic;
	TextView txtAgentName, txtAgentIc, txtAgentCea, txtAgentTel;
	EditText editAgentPostal, editAgentAddress1, editAgentAddress2, editAgentBuilding;
	String stGstPayable, stGstType, stConflict, stCoBroke, stSellerWarrant;
	Button btnSubmit;
	
	//Signature
	Button btnSeller1Sign, btnSeller2Sign, btnSeller3Sign, btnSeller4Sign, btnInterpreter1Sign, btnInterpreter2Sign, btnInterpreter3Sign, btnInterpreter4Sign, btnSAgentSign;
	ImageView imgSeller1Sign, imgSeller2Sign, imgSeller3Sign, imgSeller4Sign, imgInterpreter1Sign, imgInterpreter2Sign, imgInterpreter3Sign, imgInterpreter4Sign, imgSAgentSign;
	String stSeller1Sign = "", stSeller2Sign = "", stSeller3Sign = "", stSeller4Sign = "", stInterpreter1Sign = "", stInterpreter2Sign = "", stInterpreter3Sign = "", stInterpreter4Sign = "", stAgentSign = "";
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_non_exclusive_cea);
		
		LoadData();
		setTitleBarAndFooter();
		
		editAgreementDate.setOnClickListener(this);
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
		
	}//onCreate
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == editAgreementDate.getId()) {
			setDate(editAgreementDate);
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
        	sign_flag = "AgentSign";
            MakeSignature(imgSAgentSign);
        }
        else if(v.getId() == btnSubmit.getId()) {
            onSubmit();
        }
		
	}//onClick

	private void LoadData() {
		// TODO Auto-generated method stub
		editAgreementDate = (EditText) findViewById(R.id.non_cea_edit_agreement_date);
		editSeller1Name = (EditText) findViewById(R.id.non_cea_edit_seller1_name);
		editSeller1Ic = (EditText) findViewById(R.id.non_cea_edit_seller1_ic);
		editSeller1Postal = (EditText) findViewById(R.id.non_cea_edit_seller1_postal);
		editSeller1Address1 = (EditText) findViewById(R.id.non_cea_edit_seller1_address1);
		editSeller1Address2 = (EditText) findViewById(R.id.non_cea_edit_seller1_address2);
		editSeller1Building = (EditText) findViewById(R.id.non_cea_edit_seller1_building);
		editSeller2Name = (EditText) findViewById(R.id.non_cea_edit_seller2_name);
        editSeller2Ic = (EditText) findViewById(R.id.non_cea_edit_seller2_ic);
        editSeller2Postal = (EditText) findViewById(R.id.non_cea_edit_seller2_postal);
        editSeller2Address1 = (EditText) findViewById(R.id.non_cea_edit_seller2_address1);
        editSeller2Address2 = (EditText) findViewById(R.id.non_cea_edit_seller2_address2);
        editSeller2Building = (EditText) findViewById(R.id.non_cea_edit_seller2_building);
        editSeller3Name = (EditText) findViewById(R.id.non_cea_edit_seller3_name);
        editSeller3Ic = (EditText) findViewById(R.id.non_cea_edit_seller3_ic);
        editSeller3Postal = (EditText) findViewById(R.id.non_cea_edit_seller3_postal);
        editSeller3Address1 = (EditText) findViewById(R.id.non_cea_edit_seller3_address1);
        editSeller3Address2 = (EditText) findViewById(R.id.non_cea_edit_seller3_address2);
        editSeller3Building = (EditText) findViewById(R.id.non_cea_edit_seller3_building);
        editSeller4Name = (EditText) findViewById(R.id.non_cea_edit_seller4_name);
        editSeller4Ic = (EditText) findViewById(R.id.non_cea_edit_seller4_ic);
        editSeller4Postal = (EditText) findViewById(R.id.non_cea_edit_seller4_postal);
        editSeller4Address1 = (EditText) findViewById(R.id.non_cea_edit_seller4_address1);
        editSeller4Address2 = (EditText) findViewById(R.id.non_cea_edit_seller4_address2);
        editSeller4Building = (EditText) findViewById(R.id.non_cea_edit_seller4_building);
        editSAgentName = (EditText) findViewById(R.id.non_cea_edit_easeller_name);
        editSAgentLicence = (EditText) findViewById(R.id.non_cea_edit_easeller_licence);
        editSAgentPostal = (EditText) findViewById(R.id.non_cea_edit_easeller_postal);
        editSAgentAddress1 = (EditText) findViewById(R.id.non_cea_edit_easeller_address1);
        editSAgentAddress2 = (EditText) findViewById(R.id.non_cea_edit_easeller_address2);
        editSAgentBuilding = (EditText) findViewById(R.id.non_cea_edit_easeller_building);
        editPropertyPostal = (EditText) findViewById(R.id.non_cea_edit_property_postal);
        editPropertyAddress1 = (EditText) findViewById(R.id.non_cea_edit_property_address1);
        editPropertyAddress2 = (EditText) findViewById(R.id.non_cea_edit_property_address2);
        editPropertyBuilding = (EditText) findViewById(R.id.non_cea_edit_property_building);
        editComissionDollar = (EditText) findViewById(R.id.non_cea_edit_comission_dollar);
        editComissionPercent = (EditText) findViewById(R.id.non_cea_edit_comission_percentage);
        rdGstPayableYes = (RadioButton) findViewById(R.id.non_cea_rd_gst_payable_yes);
        rdGstPayableNo = (RadioButton) findViewById(R.id.non_cea_rd_gst_payable_no);
        rdGstTypeInclusive = (RadioButton) findViewById(R.id.non_cea_rd_gst_type_inclusive);
        rdGstTypeExclusive = (RadioButton) findViewById(R.id.non_cea_rd_gst_type_exclusive);
        editGstNote = (EditText) findViewById(R.id.non_cea_edit_gst_note);
        rdConflictHave = (RadioButton) findViewById(R.id.non_cea_rd_conflict_interest_have);
        rdConflictNot = (RadioButton) findViewById(R.id.non_cea_rd_conflict_interest_not);
        editConflictReason = (EditText) findViewById(R.id.non_cea_edit_conflict_reason);
        rdCoBrokeMay = (RadioButton) findViewById(R.id.non_cea_rd_co_broke_may);
        rdCoBrokeNot = (RadioButton) findViewById(R.id.non_cea_rd_co_broke_not);
        rdWarrantOwner = (RadioButton) findViewById(R.id.non_cea_rd_seller_warrant_owner);
        rdWarrantDuty = (RadioButton) findViewById(R.id.non_cea_rd_seller_warrant_duty);
        editWarrantRemark = (EditText) findViewById(R.id.non_cea_edit_remark);
        editInterpreter1Name = (EditText) findViewById(R.id.non_cea_edit_interpreter1_name);
        editInterpreter1Ic = (EditText) findViewById(R.id.non_cea_edit_interpreter1_ic);
        editInterpreter2Name = (EditText) findViewById(R.id.non_cea_edit_interpreter2_name);
        editInterpreter2Ic = (EditText) findViewById(R.id.non_cea_edit_interpreter2_ic);
        editInterpreter3Name = (EditText) findViewById(R.id.non_cea_edit_interpreter3_name);
        editInterpreter3Ic = (EditText) findViewById(R.id.non_cea_edit_interpreter3_ic);
        editInterpreter4Name = (EditText) findViewById(R.id.non_cea_edit_interpreter4_name);
        editInterpreter4Ic = (EditText) findViewById(R.id.non_cea_edit_interpreter4_ic);
        txtAgentName = (TextView) findViewById(R.id.non_cea_txt_agent_name);
        txtAgentIc = (TextView) findViewById(R.id.non_cea_txt_agent_ic);
    	editAgentPostal = (EditText) findViewById(R.id.non_cea_edit_agent_postal);
    	editAgentAddress1 = (EditText) findViewById(R.id.non_cea_edit_agent_address1);
    	editAgentAddress2 = (EditText) findViewById(R.id.non_cea_edit_agent_address2);
    	editAgentBuilding = (EditText) findViewById(R.id.non_cea_edit_agent_building);
        txtAgentCea = (TextView) findViewById(R.id.non_cea_txt_agent_licence);
        txtAgentTel = (TextView) findViewById(R.id.non_cea_txt_agent_tel);
        btnSubmit = (Button) findViewById(R.id.non_cea_submit);
        
        //Signature
        btnSeller1Sign = (Button) findViewById(R.id.non_cea_btn_seller1_sign);
        btnSeller2Sign = (Button) findViewById(R.id.non_cea_btn_seller2_sign);
        btnSeller3Sign = (Button) findViewById(R.id.non_cea_btn_seller3_sign);
        btnSeller4Sign = (Button) findViewById(R.id.non_cea_btn_seller4_sign);
        btnSAgentSign = (Button) findViewById(R.id.non_cea_btn_agent_sign);
		imgSeller1Sign = (ImageView) findViewById(R.id.non_cea_img_seller1_sign);
		imgSeller2Sign = (ImageView) findViewById(R.id.non_cea_img_seller2_sign);
		imgSeller3Sign = (ImageView) findViewById(R.id.non_cea_img_seller3_sign);
		imgSeller4Sign = (ImageView) findViewById(R.id.non_cea_img_seller4_sign);
		imgSAgentSign = (ImageView) findViewById(R.id.non_cea_img_agent_sign);
		btnInterpreter1Sign = (Button) findViewById(R.id.non_cea_btn_interpreter1_sign);
        btnInterpreter2Sign = (Button) findViewById(R.id.non_cea_btn_interpreter2_sign);
        btnInterpreter3Sign = (Button) findViewById(R.id.non_cea_btn_interpreter3_sign);
        btnInterpreter4Sign = (Button) findViewById(R.id.non_cea_btn_interpreter4_sign);
        imgInterpreter1Sign = (ImageView) findViewById(R.id.non_cea_img_interpreter1_sign);
        imgInterpreter2Sign = (ImageView) findViewById(R.id.non_cea_img_interpreter2_sign);
        imgInterpreter3Sign = (ImageView) findViewById(R.id.non_cea_img_interpreter3_sign);
        imgInterpreter4Sign = (ImageView) findViewById(R.id.non_cea_img_interpreter4_sign);
        
        signList = new ArrayList<NameValuePair>();
	}//LoadData

	public void setTitleBarAndFooter() {
		//title bar
		TextView titleText = (TextView) findViewById(R.id.title_text);
		TextView titleText2 = (TextView) findViewById(R.id.title_text2);
		ImageView titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleText.setText("Non-Exclusive CEA Estate Agency agreement for residential");
		titleImage.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.non_exclusive_cea_footer, new FooterFragment());
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
		
		DatePickerDialog dateDialog = new DatePickerDialog(NonExclusiveCeaActivity.this,
				datePicker, 
				myCalendar.get(Calendar.YEAR), 
				myCalendar.get(Calendar.MONTH), 
				myCalendar.get(Calendar.DAY_OF_MONTH));
		
				Date date = myCalendar.getTime();
				calDate = java.text.DateFormat.getDateInstance().format(date);
		
		dateDialog.show();
	}

	private String MakeSignature(ImageView imgView) {
		// TODO Auto-generated method stub
		imgSign = imgView;
		
		final Dialog dialog = new Dialog(NonExclusiveCeaActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.signature_dialog_layout);
		
		canvasView = (CanvasView) dialog.findViewById(R.id.signature_canvas);
		Button btnCancel = (Button) dialog.findViewById(R.id.btnSignCancle);
		Button btnClear = (Button) dialog.findViewById(R.id.btnSignClear);
		Button btnSave = (Button) dialog.findViewById(R.id.btnSignSave);
		final EditText txtUserName = (EditText) dialog.findViewById(R.id.edit_sign_name);
		signName = txtUserName.getText().toString();
		
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
				signName = "non_exclusive_" + signName +"_"+ todayDate +".png";
				
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
				else if(sign_flag.equals("AgentSign")) {
					stAgentSign = signName;
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
		
		return signName;
	}//MakeSignature
	
	private void onSubmit() {
		// TODO Auto-generated method stub
		//GST Payable
		if(rdGstPayableYes.isChecked()) {
			stGstPayable = rdGstPayableYes.getText().toString();
		}
		else if(rdGstPayableNo.isChecked()) {
			stGstPayable = rdGstPayableNo.getText().toString();
		}
		
		//GST type
		if(rdGstTypeInclusive.isChecked()) {
			stGstType = rdGstTypeInclusive.getText().toString();
		}
		else if(rdGstTypeExclusive.isChecked()) {
			stGstType = rdGstTypeExclusive.getText().toString();
		}
		
		//Conflict of Interest
		if(rdConflictNot.isChecked()) {
			stConflict = rdConflictNot.getText().toString();
		}
		else if(rdConflictHave.isChecked()) {
			stConflict = rdConflictHave.getText().toString();
		}
		
		//Co broke
		if(rdCoBrokeMay.isChecked()) {
			stCoBroke = rdCoBrokeMay.getText().toString();
		}
		else if(rdCoBrokeNot.isChecked()) {
			stCoBroke = rdCoBrokeNot.getText().toString();
		}
		
		//Seller Warrant
		if(rdWarrantOwner.isChecked()) {
			stSellerWarrant = rdWarrantOwner.getText().toString();
		}
		else if(rdWarrantDuty.isChecked()) {
			stSellerWarrant = rdWarrantDuty.getText().toString();
		}
		
		signList.add(new BasicNameValuePair("seller1_signature", stSeller1Sign));
		signList.add(new BasicNameValuePair("seller2_signature", stSeller2Sign));
		signList.add(new BasicNameValuePair("seller3_signature", stSeller3Sign));
		signList.add(new BasicNameValuePair("seller4_signature", stSeller4Sign));
		signList.add(new BasicNameValuePair("seller1_interpreter_signature", stInterpreter1Sign));
		signList.add(new BasicNameValuePair("seller2_interpreter_signature", stInterpreter2Sign));
		signList.add(new BasicNameValuePair("seller3_interpreter_signature", stInterpreter3Sign));
		signList.add(new BasicNameValuePair("seller4_interpreter_signature", stInterpreter4Sign));
		signList.add(new BasicNameValuePair("sale_person_signature", stAgentSign));
		
		if(connectionManager.isConnected()) {
			new GetNonExclusiveCEA().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
		}
	}//onSubmit
	
	private class GetNonExclusiveCEA extends AsyncTask<Void, Void, Void> {
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
				jObj = api.NonExclusiveCea(
						pref.getSessionToken(), editAgreementDate.getText().toString(), editSeller1Name.getText().toString(), editSeller1Ic.getText().toString(), editSeller1Postal.getText().toString(), 
						editSeller1Address1.getText().toString(), editSeller1Address2.getText().toString(), editSeller1Building.getText().toString(), editSeller2Name.getText().toString(), editSeller2Ic.getText().toString(), 
						editSeller2Postal.getText().toString(), editSeller2Address1.getText().toString(), editSeller2Address2.getText().toString(), editSeller2Building.getText().toString(), editSeller3Name.getText().toString(), 
						editSeller3Ic.getText().toString(), editSeller3Postal.getText().toString(), editSeller3Address1.getText().toString(), editSeller3Address2.getText().toString(), editSeller3Building.getText().toString(), 
						editSeller4Name.getText().toString(), editSeller4Ic.getText().toString(), editSeller4Postal.getText().toString(), editSeller4Address1.getText().toString(), editSeller4Address2.getText().toString(), 
						editSeller4Building.getText().toString(), editSAgentName.getText().toString(), editSAgentLicence.getText().toString(), editSAgentPostal.getText().toString(), editSAgentAddress1.getText().toString(), 
						editSAgentAddress2.getText().toString(), editSAgentBuilding.getText().toString(), editPropertyPostal.getText().toString(), editPropertyAddress1.getText().toString(), editPropertyAddress2.getText().toString(), 
						editPropertyBuilding.getText().toString(), editComissionDollar.getText().toString(), editComissionPercent.getText().toString(), stGstPayable, stGstType, 
						editGstNote.getText().toString(), stConflict, editConflictReason.getText().toString(), stCoBroke, stSellerWarrant, 
						editWarrantRemark.getText().toString(), editInterpreter1Name.getText().toString(), editInterpreter1Ic.getText().toString(), 
						editInterpreter2Name.getText().toString(), editInterpreter2Ic.getText().toString(), 
						editInterpreter3Name.getText().toString(), editInterpreter3Ic.getText().toString(), editInterpreter4Name.getText().toString(), 
						editInterpreter4Ic.getText().toString(), txtAgentName.getText().toString(), txtAgentIc.getText().toString(), 
						editAgentPostal.getText().toString(), editAgentAddress1.getText().toString(), editAgentAddress2.getText().toString(), editAgentBuilding.getText().toString(), txtAgentCea.getText().toString(), 
						txtAgentTel.getText().toString(), signList);
				
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
					i.putExtra("Email Title", "Non-Exclusive CEA Estate Agency agreement for residential");
					i.putExtra("Api Link", CommonConstants.HOST + CommonConstants.STATUS_NON_EXCLUSIVE_CEA_URL);
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
	}//GetNonExclusiveCEA

}
