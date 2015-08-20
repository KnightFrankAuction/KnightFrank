package com.novitee.knightfrankacution;

import com.novitee.knightfrankacution.base.BaseFragment;

import android.os.Bundle;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GrossYieldFragment extends BaseFragment implements OnClickListener {
	
	RelativeLayout layoutPropertyPrice;
	RelativeLayout layoutAnnualPrice;
	TextView txtPropertyPrice;
	TextView txtAnnualPrice;
	TextView valuePropertyPrice;
	TextView valueAnnualPrice;
	Button calculate;
	TextView grossYield;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.gross_yield_fragment, container, false);
		
		layoutPropertyPrice = (RelativeLayout) view.findViewById(R.id.layout_gross_property_price);
		layoutAnnualPrice = (RelativeLayout) view.findViewById(R.id.layout_gross_annual_price);
		txtPropertyPrice = (TextView) view.findViewById(R.id.txt_gross_property_price);
		txtAnnualPrice = (TextView) view.findViewById(R.id.txt_anual_price);
		valuePropertyPrice = (TextView) view.findViewById(R.id.gross_property_price);
		valueAnnualPrice = (TextView) view.findViewById(R.id.anual_price);
		calculate = (Button) view.findViewById(R.id.gross_yield_calculate);
		grossYield = (TextView) view.findViewById(R.id.gross_yield);
		
		layoutPropertyPrice.setOnClickListener(this);
		layoutAnnualPrice.setOnClickListener(this);
		
		calculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CalculateLoan();
			}
		});
		
		return view;
	}//onCreateView
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == layoutPropertyPrice.getId()) {
			ShowDialog(txtPropertyPrice.getText().toString(), valuePropertyPrice.getText().toString(), valuePropertyPrice);
		}
		else if(v.getId() == layoutAnnualPrice.getId()) {
			ShowDialog(txtAnnualPrice.getText().toString(), valueAnnualPrice.getText().toString(), valueAnnualPrice);
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
		
		//Property Price
		String st = valuePropertyPrice.getText().toString();
		st = st.replace(",", "");
		Float P = Float.valueOf(st);
		
		//Annual Price
		Float R = Float.valueOf(valueAnnualPrice.getText().toString());
		
		Float Y = (R/P)*100;
		
		String st1 = String.format("%.2f", Y);
		grossYield.setText(st1);
		
	}//CalculateLoan

}
