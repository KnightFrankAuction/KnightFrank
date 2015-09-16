package com.novitee.knightfrankacution;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.novitee.knightfrankacution.base.BaseFragment;

public class LoanFragment extends BaseFragment implements OnClickListener {
	
	RelativeLayout layoutPrice;
	RelativeLayout layoutRate;
	RelativeLayout layoutYear;
	TextView txtPrice;
	TextView txtRate;
	TextView txtYear;
	TextView valuePrice;
	TextView valueRate;
	TextView valueYear;
	Button calculate;
	TextView payment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.loan_fragment, container, false);
		
		layoutPrice = (RelativeLayout) view.findViewById(R.id.layout_loan_property_price);
		layoutRate = (RelativeLayout) view.findViewById(R.id.layout_interest_rate);
		layoutYear = (RelativeLayout) view.findViewById(R.id.layout_loan_year);
		valuePrice = (TextView) view.findViewById(R.id.loan_property_price);
		valueRate = (TextView) view.findViewById(R.id.interest_rate);
		valueYear = (TextView) view.findViewById(R.id.loan_year);
		txtPrice = (TextView) view.findViewById(R.id.txt_loan_property_price);
		txtRate = (TextView) view.findViewById(R.id.txt_interest_rate);
		txtYear = (TextView) view.findViewById(R.id.txt_loan_year);
		calculate = (Button) view.findViewById(R.id.loan_calculate);
		payment = (TextView) view.findViewById(R.id.monthly_payment);
		
		layoutPrice.setOnClickListener(this);
		layoutRate.setOnClickListener(this);
		layoutYear.setOnClickListener(this);
		
		calculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(valuePrice.equals("") || valuePrice.equals(null) ||
						valueRate.equals("") || valueRate.equals(null) ||
						valueYear.equals("") || valueYear.equals(null) ) {
					
					Toast.makeText(context, "You must fill all fields.", Toast.LENGTH_LONG).show();
				}
				else {
					CalculateLoan();
				}
				
			}
		});
		
		return view;
		
	}//onCreateView
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == layoutPrice.getId()) {
			ShowDialog(txtPrice.getText().toString(), valuePrice.getText().toString(), valuePrice);
		}
		else if(v.getId() == layoutRate.getId()) {
			ShowDialog(txtRate.getText().toString(), valueRate.getText().toString(), valueRate);
		}
		else if(v.getId() == layoutYear.getId()) {
			ShowDialog(txtYear.getText().toString(), valueYear.getText().toString(), valueYear);
		}
		
	}//onClick
	
	private void ShowDialog(String title, String value, final TextView text) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.tools_dialog);
		
		TextView toolsTitle = (TextView) dialog.findViewById(R.id.tools_dialog_title);
		final EditText toolsValue = (EditText) dialog.findViewById(R.id.tools_dialog_value);
		Button toolsOK = (Button) dialog.findViewById(R.id.tools_dialog_button);
		
		toolsTitle.setText(title);
		toolsValue.setText(value);
		
		toolsOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				text.setText(toolsValue.getText().toString());
			}
		});
		
		dialog.show();
		
	}//showDialog

	private void CalculateLoan() {
		
		//Price
		String st = valuePrice.getText().toString();
		st = st.replace(",", "");
		Float P = Float.valueOf(st);

		//Rate
		String st1 = valueRate.getText().toString();
		st1 = st1.replace("%", "");
		Float i = Float.valueOf(st1);
		
		//Year
		int n = Integer.parseInt(valueYear.getText().toString());
		
		//Payment (P* [ (i(1+i)^n) / ((1+i)^n - 1) ])
		Float i1 = 1+i;//2.5
		Float in = (float) Math.pow(i1, n);//97.65625
		Float a = in*i;//146.484375
		Float b = in-1;//96.65625
		
		Float M = P*(a/b);
		String st2 = String.format("%.2f", M);
		payment.setText(st2);
		
	}//CalculateLoan
}
