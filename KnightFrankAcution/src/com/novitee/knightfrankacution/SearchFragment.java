package com.novitee.knightfrankacution;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.novitee.knightfrankacution.base.BaseFragment;

public class SearchFragment extends BaseFragment {
	
	ImageView filter;
	EditText search;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.search_layout, container, false);
		
		filter = (ImageView) view.findViewById(R.id.filter);
		search = (EditText) view.findViewById(R.id.edit_search);
		
		filter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), FilterActivity.class);
				startActivity(intent);
			}
		});
		
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
			}
		});
		
		return view;
	}
}
