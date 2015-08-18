package com.novitee.knightfrankacution;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ProjectListingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_listings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project_listings, menu);
		return true;
	}

}
