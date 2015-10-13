package com.novitee.knightfrankacution.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.novitee.knightfrankacution.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter{
	
	private Context context;
	private List<String> headerArray;
	private HashMap<String, ArrayList<String>> childArray;
	private LayoutInflater infalInflater;
	 
	// Initialize constructor for array list
	public ExpandableListAdapter(Context context, ArrayList<String> headerArray, HashMap<String, ArrayList<String>> listChildData) {
		this.context = context;
		this.headerArray = headerArray;
		this.childArray = listChildData;
		infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	 
	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this.childArray.get(this.headerArray.get(groupPosition)).get(childPosititon);
	}
	 
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	// Inflate child view
	 
	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	 
		final String childText = (String) getChild(groupPosition, childPosition);
		 
		if (convertView == null) {
			convertView = infalInflater.inflate(R.layout.kf_single_textview_layout, null);
		}
		 
		TextView textViewChild = (TextView) convertView.findViewById(R.id.spinner_text);
		textViewChild.setPadding(50, 10, 10, 10);
		textViewChild.setText(childText);
		
		return convertView;
	}
	    // return number of headers in list
	@Override
	public int getChildrenCount(int groupPosition) {
		return this.childArray.get(this.headerArray.get(groupPosition)).size();
	}
	 
	@Override
	public Object getGroup(int groupPosition) {
		return this.headerArray.get(groupPosition);
	}
	 
	@Override
	public int getGroupCount() {
		return this.headerArray.size();
	}
	 
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	// inflate header view
	 
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			convertView = infalInflater.inflate(R.layout.kf_header_arrow_layout, null);
		}
	 
		TextView textViewHeader = (TextView) convertView.findViewById(R.id.header_textview);
		textViewHeader.setText(headerTitle);
		 
		return convertView;
	}
	 
	@Override
	public boolean hasStableIds() {
		return false;
	}
	 
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
 
}
