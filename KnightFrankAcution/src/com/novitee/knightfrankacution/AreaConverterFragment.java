package com.novitee.knightfrankacution;

import com.novitee.knightfrankacution.base.BaseFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AreaConverterFragment extends BaseFragment {
	
	RelativeLayout layoutArea;
	LinearLayout layoutSelect;
	TextView txtArea;
	TextView valueArea;
	TextView txtAns;
	TextView valueAns;
	Button calculate;
	TextView sqf;
	TextView sqm;
	String check = "sqf";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.area_converter_fragment, container, false);
		
		layoutArea = (RelativeLayout) view.findViewById(R.id.layout_area_converter);
		layoutSelect = (LinearLayout) view.findViewById(R.id.layout_select);
		txtArea = (TextView) view.findViewById(R.id.txt_area);
		valueArea = (TextView) view.findViewById(R.id.area);
		txtAns = (TextView) view.findViewById(R.id.txt_ans_area);
		valueAns = (TextView) view.findViewById(R.id.ans_area);
		calculate = (Button) view.findViewById(R.id.area_calculate);
		sqf = (TextView) view.findViewById(R.id.sqf);
		sqm = (TextView) view.findViewById(R.id.sqm);
		
		txtArea.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(layoutSelect.getVisibility() == View.GONE) {
					layoutSelect.setVisibility(View.VISIBLE);
				}
				else {
					layoutSelect.setVisibility(View.GONE);
				}
			}
		});//txtArea
		
		valueArea.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDialog(txtArea.getText().toString(), valueArea.getText().toString(), valueArea);
			}
		});//valueArea
		
		calculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(valueArea.equals("") || valueArea.equals(null) ) {
					
					Toast.makeText(context, "You must fill all fields.", Toast.LENGTH_LONG).show();
				}
				else {
					CalculateArea();
				}
			}
		});
		
		sqf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				check = "sqf";
				txtArea.setText(sqf.getText().toString());
				layoutSelect.setVisibility(View.GONE);
			}
		});
		
		sqm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				check = "sqm";
				txtArea.setText(sqm.getText().toString());
				layoutSelect.setVisibility(View.GONE);
			}
		});
		
		return view;
	}//onCreateView
	
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
//				String st = String.format("%.2f", args)
				text.setText(toolsValue.getText().toString());
			}
		});
		
		dialog.show();
		
	}//showDialog
	
	private void CalculateArea() {
		
		float ans = (float) 0.0;
		String st = valueArea.getText().toString();
		st = st.replace(",", "");
		Float f = Float.valueOf(st);
		
		if(check.equals("sqf")) {
			ans = (float) (f*0.093);
		}
		else if(check.equals("sqm")) {
			ans = (float) (f/0.093);
		}
		
		String st1 = String.format("%.2f", ans);
		valueAns.setText(st1);
		
	}//CalculateLoan

}
