package com.novitee.knightfrankacution;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class NotificationSettingsActivity extends BaseFragmentActivity {
	
	Context context = this;
	
	FragmentTransaction fragmentTran;
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	
	ToggleButton mute_noti;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_settings);
		
		setTitleBarAndFooter();
		
		mute_noti = (ToggleButton) findViewById(R.id.toggle_mute_noti);
		
		mute_noti.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked) {
					//On
				}
				else {
					//Off
				}
			}
		});
		
	}//onCreate

	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setText("Apply");
		titleText.setText("Notification Settings");
		titleImage.setVisibility(View.GONE);
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.noti_setting_footer, new FooterFragment());
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
