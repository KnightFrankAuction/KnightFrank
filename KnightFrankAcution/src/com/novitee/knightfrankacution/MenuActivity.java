package com.novitee.knightfrankacution;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Context;
import android.content.Intent;

public class MenuActivity extends AdvertisementsActivity implements OnClickListener {
	
	Context context = this;
	
	LinearLayout auction_listings;
	LinearLayout private_listings;
	LinearLayout project_listings;
	LinearLayout starbuys;
	LinearLayout info_news;
	LinearLayout tools;
	ImageView document;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		//download advertisements image
		new downloadAd().execute();
		
		auction_listings = (LinearLayout) findViewById(R.id.auction_listings);
		private_listings = (LinearLayout) findViewById(R.id.private_listings);
		project_listings = (LinearLayout) findViewById(R.id.project_listings);
		starbuys = (LinearLayout) findViewById(R.id.starbuys);
		info_news = (LinearLayout) findViewById(R.id.info_and_news);
		tools = (LinearLayout) findViewById(R.id.tools);
		document = (ImageView) findViewById(R.id.document);
		
		auction_listings.setOnClickListener(this);
		private_listings.setOnClickListener(this);
		project_listings.setOnClickListener(this);
		starbuys.setOnClickListener(this);
		info_news.setOnClickListener(this);
		tools.setOnClickListener(this);
		document.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		
		if(v.getId() == auction_listings.getId()) {
			intent = new Intent(context, AuctionListingsActivity.class);
		}
		else if(v.getId() == private_listings.getId()) {
			intent = new Intent(context, PrivateListingsActivity.class);
		}
		else if(v.getId() == project_listings.getId()) {
			intent = new Intent(context, ProjectListingsActivity.class);
		}
		else if(v.getId() == starbuys.getId()) {
			intent = new Intent(context, StarbuysActivity.class);
		}
		else if(v.getId() == info_news.getId()) {
			intent = new Intent(context, InfoNewsActivity.class);
		}
		else if(v.getId() == tools.getId()) {
			intent = new Intent(context, ToolsActivity.class);
		}
		else if(v.getId() == document.getId()) {
			intent = new Intent(context, DocumentsActivity.class);
		}
		
		startActivity(intent);
	}

}
