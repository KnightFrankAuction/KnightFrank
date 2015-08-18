package com.novitee.knightfrankacution.base;

import com.novitee.knightfrankacution.R;
import com.novitee.knightfrankacution.api.KnightFrankAPI;
import com.novitee.knightfrankacution.util.ConnectionManager;
import com.novitee.knightfrankacution.util.Preferences;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

public class BaseActivity extends Activity {
	
	protected Preferences pref;
	protected KnightFrankAPI api;
	protected Context context;
	protected ConnectionManager connectionManager;
	
	public ImageView back;
	public ImageView titleImage;
	public TextView titleText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.title_layout);
		
		back = (ImageView) findViewById(R.id.title_back);
		titleImage = (ImageView) findViewById(R.id.title_image);
		titleText = (TextView) findViewById(R.id.title_text);
		
		context = getApplicationContext();
		
		pref = Preferences.getInstance(context);
		
		api = new KnightFrankAPI();
		
		connectionManager = new ConnectionManager(context);
	}

	public void setTitleValue(String title, int d) {
		//titleText.setText(title);
//		titleText.show
		
		titleImage.setBackgroundResource(d);
	}
}
