package com.novitee.knightfrankacution;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ToolsActivity extends BaseFragmentActivity implements OnClickListener {
	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	FragmentTransaction fragmentTran;
	
	TextView loan;
	TextView grossYield;
	TextView areaConverter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);
		
		setTitleBarAndFooter();
		
		loan = (TextView) findViewById(R.id.Loan);
		grossYield = (TextView) findViewById(R.id.GrossYield);
		areaConverter = (TextView) findViewById(R.id.Area);
		
		loan.setOnClickListener(this);
		grossYield.setOnClickListener(this);
		areaConverter.setOnClickListener(this); 
		
	}//onCreate
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleImage.setVisibility(View.GONE);
		titleText.setText("Tools");
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.tools_footer, new FooterFragment());
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent = new Intent(context, ToolsFragmentActivity.class);
		
		if(v.getId() == loan.getId()) {
			intent.putExtra("tools_type", "Loan Calculator");
		}
		else if(v.getId() == grossYield.getId()) {
			intent.putExtra("tools_type", "Gross Yield Calculator");
		}
		else if(v.getId() == areaConverter.getId()) {
			intent.putExtra("tools_type", "Area Converter");
		}
		
		startActivity(intent);
	}

}
