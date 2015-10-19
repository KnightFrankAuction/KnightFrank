package com.novitee.knightfrankacution;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.util.Preferences;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreActivity extends BaseFragmentActivity implements OnClickListener {
	
	Context context = this;
	
	FragmentTransaction fragmentTran;
	
	TextView myAcc;
	TextView notiSetting;
	TextView documents;
	TextView search;
	TextView logout;
	View document_view;
	
	FooterFragment footer_frg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		
		myAcc = (TextView) findViewById(R.id.MyAccount);
		notiSetting = (TextView) findViewById(R.id.NotificationSettings);
		documents = (TextView) findViewById(R.id.Documents);
		search = (TextView) findViewById(R.id.SearchMore);
		logout = (TextView) findViewById(R.id.Logout);
		document_view = (View) findViewById(R.id.Documents_view);
		
		if(pref.getUserType() == 2) {
			documents.setVisibility(View.VISIBLE);
			document_view.setVisibility(View.VISIBLE);
		}
		
		myAcc.setOnClickListener(this);
		notiSetting.setOnClickListener(this);
		documents.setOnClickListener(this);
		search.setOnClickListener(this);
		logout.setOnClickListener(this);
		
		setTitleBarAndFooter();
	}//onCreate
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		footer_frg.more.setImageResource(R.drawable.more_icon_pink);
	}
	
	public void setTitleBarAndFooter() {
		//title bar
		TextView titleText = (TextView) findViewById(R.id.title_text);
		TextView titleText2 = (TextView) findViewById(R.id.title_text2);
		ImageView titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleText.setText("More");
		titleImage.setVisibility(View.GONE);
		
		footer_frg = new FooterFragment();
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.more_footer, footer_frg);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == logout.getId()) {
			Logout();
		}
		else if (v.getId() == notiSetting.getId()) {
			Intent intent = new Intent(context, NotificationSettingsActivity.class);
			startActivity(intent);
		}
		else if (v.getId() == documents.getId()) {
			Intent intent = new Intent(context, DocumentsActivity.class);
			startActivity(intent);
		}
		else if (v.getId() == search.getId()) {
			Intent intent = new Intent(context, SearchActivity.class);
			startActivity(intent);
		}
		else if (v.getId() == myAcc.getId()) {
			Intent intent = new Intent(context, AboutActivity.class);
			startActivity(intent);
		}
	}//onClick

	private void Logout() {
		Preferences.getInstance(context).clearAll();
		Intent intent = new Intent(context, MainActivity.class);
		startActivity(intent);
	}

}
