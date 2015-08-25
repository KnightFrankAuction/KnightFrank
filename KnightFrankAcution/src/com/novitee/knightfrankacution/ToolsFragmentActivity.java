package com.novitee.knightfrankacution;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ToolsFragmentActivity extends BaseFragmentActivity {
	
//	View view;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	FragmentTransaction fragmentTran;
	
	String title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tools_fragment);
		
		title = getIntent().getStringExtra("tools_type");
		setTitleBarAndFooter();
	}
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleImage.setVisibility(View.GONE);
		titleText.setText(title);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		
		if(title.equals("Loan Calculator")) {
			fragmentTran.replace(R.id.tools_layout, new LoanFragment());
		}
		else if(title.equals("Gross Yield Calculator")) {
			fragmentTran.replace(R.id.tools_layout, new GrossYieldFragment() );
		}
		else if(title.equals("Area Converter")) {
			fragmentTran.replace(R.id.tools_layout, new AreaConverterFragment() );
		}
		
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

}
