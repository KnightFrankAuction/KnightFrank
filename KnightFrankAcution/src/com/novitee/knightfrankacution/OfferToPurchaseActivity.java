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

public class OfferToPurchaseActivity extends BaseFragmentActivity implements OnClickListener {

	Context context = this;
	
	FragmentTransaction fragmentTran;
	
	EditText editPropertyPostal, editPropertyAddress1, editPropertyAddress2, editPropertyBuilding, editPropertyPriceWord, editPropertyPriceNumber;
	EditText editPropertyOptionPeriod, editPropertyCompletionDate, editPropertyArea, editPropertyWorkingDays, editPropertyDaedline;
	RadioButton rdFreehold, rdLeadhold, rdVacant, rdSubject, rdAccept, rdReject;
	String stHolder, stPossession, stSignOff;
	EditText editBank, editChequeNo, editChequeAmount, editVendor, editSalePersonName, editSalePersonIc, editPurchaserName;
	EditText editPurchaser1Name, editPurchaser1Ic, editPurchaser2Name, editPurchaser2Ic, editPurchaserPostal, editPurchaserAddress1, editPurchaserAddress2, editPurchaserbuilding;
	EditText editPropertySoOwner1Name, editPropertySoOwner1ic, editPropertySoPurchaser1Name, editPropertySoPurchaser1Ic;
	Button btnSubmit;
	
	//Signature
	Button btnPurchaser1Sign, btnPurchaser2Sign, btnSalePersonSign, btnSoOwner1Sign, btnSoPurchaser1Sign;
	ImageView imgPurchaser1Sign, imgPurchaser2Sign, imgSalePersonSign, imgSoOwner1Sign, imgSoPurchaser1Sign;
	String stPurchaser1Sign = "", stPurchaser2Sign = "", stSalePersonSign = "", stSoOwner1Sign = "", stSoPurchaser1Sign = "";
	
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
		setContentView(R.layout.activity_offer_to_purchase);
		
		LoadData();
		setTitleBarAndFooter();
		
		editPropertyOptionPeriod.setOnClickListener(this);
		editPropertyCompletionDate.setOnClickListener(this);
		editPropertyDaedline.setOnClickListener(this);
		btnPurchaser1Sign.setOnClickListener(this);
		btnPurchaser2Sign.setOnClickListener(this);
		btnSalePersonSign.setOnClickListener(this);
		btnSoOwner1Sign.setOnClickListener(this);
		btnSoPurchaser1Sign.setOnClickListener(this);
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
		if(v.getId() == editPropertyOptionPeriod.getId()) {
			setDate(editPropertyOptionPeriod);
		}
		else if(v.getId() == editPropertyCompletionDate.getId()) {
			setDate(editPropertyCompletionDate);
		}
		else if(v.getId() == editPropertyDaedline.getId()) {
			setDate(editPropertyDaedline);
		}
		else if(v.getId() == btnPurchaser1Sign.getId()) {
			sign_flag = "Purchaser1Sign";
			MakeSignature(imgPurchaser1Sign);
		}
		else if(v.getId() == btnPurchaser2Sign.getId()) {
			sign_flag = "Purchaser2Sign";
			MakeSignature(imgPurchaser2Sign);
		}
		else if(v.getId() == btnSalePersonSign.getId()) {
			sign_flag = "SalePersonSign";
			MakeSignature(imgSalePersonSign);
		}
		else if(v.getId() == btnSoOwner1Sign.getId()) {
			sign_flag = "SoOwner1Sign";
			MakeSignature(imgSoOwner1Sign);
		}
		else if(v.getId() == btnSoPurchaser1Sign.getId()) {
			sign_flag = "SoPurchaser1Sign";
			MakeSignature(imgSoPurchaser1Sign);
		}
		else if(v.getId() == btnSubmit.getId()) {
			onSubmit();
		}
		
	}//onClick
	
	private void LoadData() {
		// TODO Auto-generated method stub
		editPropertyPostal = (EditText) findViewById(R.id.offer_edit_property_postal);
		editPropertyAddress1 = (EditText) findViewById(R.id.offer_edit_property_address1);
		editPropertyAddress2 = (EditText) findViewById(R.id.offer_edit_property_address2);
		editPropertyBuilding = (EditText) findViewById(R.id.offer_edit_property_building);
		editPropertyPriceWord = (EditText) findViewById(R.id.offer_edit_property_price_words);
		editPropertyPriceNumber = (EditText) findViewById(R.id.offer_edit_property_price_number);
		editPropertyOptionPeriod = (EditText) findViewById(R.id.offer_edit_property_option_period);
		editPropertyCompletionDate = (EditText) findViewById(R.id.offer_edit_property_completion_date);
		editPropertyArea = (EditText) findViewById(R.id.offer_edit_property_area);
		editPropertyWorkingDays = (EditText) findViewById(R.id.offer_edit_property_working_days);
		editPropertyDaedline = (EditText) findViewById(R.id.offer_edit_property_deadline);
		rdFreehold = (RadioButton) findViewById(R.id.offer_rd_freehold);
		rdLeadhold = (RadioButton) findViewById(R.id.offer_rd_leadhold);
		rdVacant = (RadioButton) findViewById(R.id.offer_rd_vacant);
		rdSubject = (RadioButton) findViewById(R.id.offer_rd_subject);
		editBank = (EditText) findViewById(R.id.offer_edit_bank);
		editChequeNo = (EditText) findViewById(R.id.offer_edit_cheque_no);
		editChequeAmount = (EditText) findViewById(R.id.offer_edit_cheque_amount);
		editVendor = (EditText) findViewById(R.id.offer_edit_vendor);
		editPurchaser1Name = (EditText) findViewById(R.id.offer_edit_purchaser1_name);
		editPurchaser1Ic = (EditText) findViewById(R.id.offer_edit_purchaser1_ic);
		editPurchaser2Name = (EditText) findViewById(R.id.offer_edit_purchaser2_name);
		editPurchaser2Ic = (EditText) findViewById(R.id.offer_edit_purchaser2_ic);
		editPurchaserPostal = (EditText) findViewById(R.id.offer_edit_purchaser_postal);
		editPurchaserAddress1 = (EditText) findViewById(R.id.offer_edit_purchaser_address1);
		editPurchaserAddress2 = (EditText) findViewById(R.id.offer_edit_purchaser_address2);
		editPurchaserbuilding = (EditText) findViewById(R.id.offer_edit_purchaser_building);
		editSalePersonName = (EditText) findViewById(R.id.offer_edit_sale_person_name);
		editSalePersonIc = (EditText) findViewById(R.id.offer_edit_sale_person_ic);
		editPurchaserName = (EditText) findViewById(R.id.offer_edit_purchaser_name);
		rdAccept = (RadioButton) findViewById(R.id.offer_rd_accept);
		rdReject = (RadioButton) findViewById(R.id.offer_rd_reject);
		editPropertySoOwner1Name = (EditText) findViewById(R.id.offer_edit_property_owner1_name);
		editPropertySoOwner1ic = (EditText) findViewById(R.id.offer_edit_property_owner1_ic);
		editPropertySoPurchaser1Name = (EditText) findViewById(R.id.offer_edit_property_purchaser1_name);
		editPropertySoPurchaser1Ic = (EditText) findViewById(R.id.offer_edit_property_purchaser1_ic);
		btnSubmit = (Button) findViewById(R.id.offer_submit);
		
		//Signature
		btnPurchaser1Sign = (Button) findViewById(R.id.offer_btn_purchaser1_sign);
		btnPurchaser2Sign = (Button) findViewById(R.id.offer_btn_purchaser2_sign);
		btnSalePersonSign = (Button) findViewById(R.id.offer_btn_sale_person_sign);
		btnSoOwner1Sign = (Button) findViewById(R.id.offer_btn_property_owner1_sign);
		btnSoPurchaser1Sign = (Button) findViewById(R.id.offer_btn_property_purchaser1_sign);
		imgPurchaser1Sign = (ImageView) findViewById(R.id.offer_img_purchaser1_sign);
		imgPurchaser2Sign = (ImageView) findViewById(R.id.offer_img_purchaser2_sign);
		imgSalePersonSign = (ImageView) findViewById(R.id.offer_img_sale_person_sign);
		imgSoOwner1Sign = (ImageView) findViewById(R.id.offer_img_property_owner1_sign);
		imgSoPurchaser1Sign = (ImageView) findViewById(R.id.offer_img_property_purchaser1_sign);
		
		signList = new ArrayList<NameValuePair>();
	}//LoadData

	public void setTitleBarAndFooter() {
		//title bar
		TextView titleText = (TextView) findViewById(R.id.title_text);
		TextView titleText2 = (TextView) findViewById(R.id.title_text2);
		ImageView titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleText.setText("Offer To Purchase");
		titleImage.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.offer_to_purchase_footer, new FooterFragment());
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
		
		DatePickerDialog dateDialog = new DatePickerDialog(OfferToPurchaseActivity.this,
				datePicker, 
				myCalendar.get(Calendar.YEAR), 
				myCalendar.get(Calendar.MONTH), 
				myCalendar.get(Calendar.DAY_OF_MONTH));
		
		dateDialog.show();
	}//setDate

	private String MakeSignature(ImageView imgView) {
		// TODO Auto-generated method stub
		imgSign = imgView;
		
		final Dialog dialog = new Dialog(OfferToPurchaseActivity.this);
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
				
				if(sign_flag.equals("Purchaser1Sign")) {
					stPurchaser1Sign = signName;
					
					stPurchaser2Sign = signName;
					stSalePersonSign = signName;
					stSoOwner1Sign = signName;
					stSoPurchaser1Sign = signName;
				}
				else if(sign_flag.equals("Purchaser2Sign")) {
					stPurchaser2Sign = signName;
				}
				else if(sign_flag.equals("SalePersonSign")) {
					stSalePersonSign = signName;
				}
				else if(sign_flag.equals("SoOwner1Sign")) {
					stSoOwner1Sign = signName;
				}
				else if(sign_flag.equals("SoPurchaser1Sign")) {
					stSoPurchaser1Sign = signName;
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
//		rdFreehold, rdLeadhold, rdVacant, rdSubject, rdAccept, rdReject;
		if(rdFreehold.isChecked()) {
			stHolder = rdFreehold.getText().toString();
		}
		else if(rdLeadhold.isChecked()) {
			stHolder = rdLeadhold.getText().toString();
		}
		
		if(rdVacant.isChecked()) {
			stPossession = rdVacant.getText().toString();
		}
		else if(rdSubject.isChecked()) {
			stPossession = rdSubject.getText().toString();
		}
		
		if(rdAccept.isChecked()) {
			stSignOff = rdAccept.getText().toString();
		}
		else if(rdReject.isChecked()) {
			stSignOff = rdReject.getText().toString();
		}
		
		signList.add(new BasicNameValuePair("purchaser1_signature", stPurchaser1Sign));
		signList.add(new BasicNameValuePair("purchaser2_signature", stPurchaser2Sign));
		signList.add(new BasicNameValuePair("sale_person_signature", stSalePersonSign));
		signList.add(new BasicNameValuePair("property_owner1_signature", stSoOwner1Sign));
		signList.add(new BasicNameValuePair("property_purchaser1_signature", stSoPurchaser1Sign));
		
		if(connectionManager.isConnected()) {
			new GetOfferToPurchase().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
		}
	}//onSubmit
	
	private class GetOfferToPurchase extends AsyncTask<Void, Void, Void> {
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
				//Exclusive Authority and Non Exclusive Authority of fields are same
				/*jObj = api.OfferToPurchase(
											pref.getSessionToken(), editPropertyPostal.getText().toString(), editPropertyAddress1.getText().toString(), editPropertyAddress2.getText().toString(), editPropertyBuilding.getText().toString(), 
											editPropertyPriceWord.getText().toString(), editPropertyPriceNumber.getText().toString(), editPropertyOptionPeriod.getText().toString(), editPropertyCompletionDate.getText().toString(), editPropertyArea.getText().toString(), 
											stHolder, stPossession, editPropertyWorkingDays.getText().toString(), editPropertyDaedline.getText().toString(), editBank.getText().toString(), 
											editChequeNo.getText().toString(), editChequeAmount.getText().toString(), editVendor.getText().toString(), stPurchaser1Sign, editPurchaser1Name.getText().toString(), editPurchaser1Ic.getText().toString(), 
											stPurchaser2Sign, editPurchaser2Name.getText().toString(), editPurchaser2Ic.getText().toString(), editPurchaserPostal.getText().toString(), editPurchaserPostal.getText().toString(), 
											editPurchaserAddress1.getText().toString(), editPurchaserAddress2.getText().toString(), editSalePersonName.getText().toString(), editSalePersonIc.getText().toString(), stSalePersonSign, 
											editPurchaserName.getText().toString(), stSignOff, stSoOwner1Sign, editPropertySoOwner1Name.getText().toString(), editPropertySoOwner1ic.getText().toString(), 
											stSoPurchaser1Sign, editPropertySoPurchaser1Name.getText().toString(), editPropertySoPurchaser1Ic.getText().toString());
				*/
				
				jObj = api.OfferToPurchaseFile(
						pref.getSessionToken(), editPropertyPostal.getText().toString(), editPropertyAddress1.getText().toString(), editPropertyAddress2.getText().toString(), editPropertyBuilding.getText().toString(), 
						editPropertyPriceWord.getText().toString(), editPropertyPriceNumber.getText().toString(), editPropertyOptionPeriod.getText().toString(), editPropertyCompletionDate.getText().toString(), editPropertyArea.getText().toString(), 
						stHolder, stPossession, editPropertyWorkingDays.getText().toString(), editPropertyDaedline.getText().toString(), editBank.getText().toString(), 
						editChequeNo.getText().toString(), editChequeAmount.getText().toString(), editVendor.getText().toString(), editPurchaser1Name.getText().toString(), editPurchaser1Ic.getText().toString(), 
						editPurchaser2Name.getText().toString(), editPurchaser2Ic.getText().toString(), editPurchaserPostal.getText().toString(), editPurchaserPostal.getText().toString(), 
						editPurchaserAddress1.getText().toString(), editPurchaserAddress2.getText().toString(), editSalePersonName.getText().toString(), editSalePersonIc.getText().toString(), 
						editPurchaserName.getText().toString(), stSignOff, editPropertySoOwner1Name.getText().toString(), editPropertySoOwner1ic.getText().toString(), 
						editPropertySoPurchaser1Name.getText().toString(), editPropertySoPurchaser1Ic.getText().toString(), signList);

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
					i.putExtra("Email Title", "Offer To Purchase");
					i.putExtra("Api Link", CommonConstants.HOST + CommonConstants.STATUS_OFFER_TO_PURCHASE_URL);
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
	}//GetOfferToPurchase

}
