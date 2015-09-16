package com.novitee.knightfrankacution;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.novitee.knightfrankacution.model.Property;

public class ShareWithEmail {

	public void shareProperty(ArrayList<Property> propertyList, Context context) {
		List<Property> shareList = new ArrayList<Property>();
		
		for(Property p : propertyList) {
			if(p.getBgcolor().equals("#000000")) {
				shareList.add(p);
			}
		}
		
		String body = "Hi,\n\nI would like to share the following property from KnightFrank action app with you:\n\n";
		for(int i=0; i < shareList.size(); i++) {
			Property p = shareList.get(i);
			
			body = body + String.valueOf(i+1) +".";
			body = body + p.getBuilding_name() +"\n";
			body = body + p.getDistrict() +" / "+ p.getAuction_type() +" / "+ p.getBuilding_type() +"\n";
			body = body + p.getFloor_area() +" Sqft / "+ p.getBedroom() +" Bedroom, "+ p.getBath() +" Bath\n";
			body = body + p.getPrice() +" / $"+ p.getPsf() +" psf" +"\n";
			body = body + p.getTenure() +"\n";
			body = body + "Postal Code "+ p.getTenure() +"\n\n\n";
		}
		
		body = body + "This email is generated via KnightFrank Auction App \n\n";
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setData(Uri.parse("mailto:"));
	    i.setType("text/plain");
//	    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"suthwethwetun@gmail.com"});
//		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"tranminh.thy@novitee.com"});
//		i.putExtra(Intent.EXTRA_CC, new String[]{"suthwethwetun@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "KnightFrank Auction Property Share");
		i.putExtra(Intent.EXTRA_TEXT, body);
		try {
		    context.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
		
	}//shareProperty
	
	public void shareDetailProperty(Property p, Context context) {
		String body = "Hi,\n\nI would like to share the following property from KnightFrank action app with you:\n\n";

		body = body + "1.";
		body = body + p.getBuilding_name() +"\n";
		body = body + p.getDistrict() +" / "+ p.getAuction_type() +" / "+ p.getBuilding_type() +"\n";
		body = body + p.getFloor_area() +" Sqft / "+ p.getBedroom() +" Bedroom, "+ p.getBath() +" Bath\n";
		body = body + p.getPrice() +" / $"+ p.getPsf() +" psf" +"\n";
		body = body + p.getTenure() +"\n";
		body = body + "Postal Code "+ p.getTenure() +"\n\n\n";
		
		body = body + "This email is generated via KnightFrank Auction App \n\n";
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setData(Uri.parse("mailto:"));
	    i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT, "KnightFrank Auction Property Share");
		i.putExtra(Intent.EXTRA_TEXT, body);
		try {
		    context.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
