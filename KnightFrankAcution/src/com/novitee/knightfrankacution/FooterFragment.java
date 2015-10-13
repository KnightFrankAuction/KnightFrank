package com.novitee.knightfrankacution;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.novitee.knightfrankacution.base.BaseFragment;
import com.novitee.knightfrankacution.model.Property;

public class FooterFragment extends BaseFragment implements OnClickListener {
	
	public ImageView home;
	public ImageView shortlist;
	public ImageView about;
	public ImageView enquiry;
	public ImageView more;
	
	ArrayList<Property> listPro;
	ArrayList<String> listImage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.footer, container, false);
		
		home = (ImageView) view.findViewById(R.id.footer_home);
		shortlist = (ImageView) view.findViewById(R.id.footer_shortlist);
		about = (ImageView) view.findViewById(R.id.footer_about);
		enquiry = (ImageView) view.findViewById(R.id.footer_enquiry);
		more = (ImageView) view.findViewById(R.id.footer_more);
		
		listPro = new ArrayList<Property>();
		listImage = new ArrayList<String>();
		
		home.setOnClickListener(this);
		shortlist.setOnClickListener(this);
		about.setOnClickListener(this);
		enquiry.setOnClickListener(this);
		more.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		
		if(v.getId() == home.getId()) {
			intent = new Intent(getActivity(), MenuActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == shortlist.getId()) {
			intent = new Intent(getActivity(), ShortListActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == about.getId()) {
			intent = new Intent(getActivity(), AboutActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == enquiry.getId()) {
			intent = new Intent(getActivity(), EnquiryActivity.class);
			startActivity(intent);
		}
		else if(v.getId() == more.getId()) {
			intent = new Intent(getActivity(), MoreActivity.class);
			startActivity(intent);
		}
		
	}//onClick
	
}
