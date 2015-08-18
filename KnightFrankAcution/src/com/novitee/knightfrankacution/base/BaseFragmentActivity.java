package com.novitee.knightfrankacution.base;

import com.novitee.knightfrankacution.R;
import com.novitee.knightfrankacution.api.KnightFrankAPI;
import com.novitee.knightfrankacution.util.ConnectionManager;
import com.novitee.knightfrankacution.util.Preferences;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseFragmentActivity extends FragmentActivity {
	
	protected Preferences pref;
	protected KnightFrankAPI api;
	protected Context context;
	protected ConnectionManager connectionManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.title_layout);

		context = getApplicationContext();
		
		pref = Preferences.getInstance(context);
		
		api = new KnightFrankAPI();
		
		connectionManager = new ConnectionManager(context);
	}
}
