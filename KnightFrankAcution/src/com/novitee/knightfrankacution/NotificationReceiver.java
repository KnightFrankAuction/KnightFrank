package com.novitee.knightfrankacution;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

	public static final int NOTIFICATION_ID = 1;
    NotificationCompat.Builder builder;
    Context ctx;
    public static final String TAKE_MESSAGE = "Take_Message";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle msg = intent.getExtras();
		if(msg != null){
			showNotification(context, msg);
		}
		
	}//onReceive
	
	public void showNotification(Context context,Bundle msg){		
		NotificationCompat.Builder mBuilder =
									        new NotificationCompat.Builder(context)
									        .setSmallIcon(R.drawable.kf_noti_icon)
									        .setContentTitle(msg.getString("title"))
									        .setContentText(msg.getString("subject"))
									        .setAutoCancel(true);
		
		Intent resultIntent = null;
		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		
		if(msg.getString("type").equalsIgnoreCase("NewListing")){ 
			// Creates an explicit intent for an Activity in your app
			resultIntent = new Intent(context, ShortListActivity.class);
			
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(ShortListActivity.class);
		}
		else if(msg.getString("type").equalsIgnoreCase("UpdateShortlist")){ 
			resultIntent = new Intent(context, ShortListActivity.class);
			stackBuilder.addParentStack(ShortListActivity.class);
		}
		else if(msg.getString("type").equalsIgnoreCase("Starbuys")){ 
			resultIntent = new Intent(context, StarbuysActivity.class);
			stackBuilder.addParentStack(StarbuysActivity.class);
		}
		else if(msg.getString("type").equalsIgnoreCase("News")){ 
			resultIntent = new Intent(context, NewsActivity.class);
			stackBuilder.addParentStack(NewsActivity.class);
		}
		else if(msg.getString("type").equalsIgnoreCase("AuctionRemainder")){ 
			resultIntent = new Intent(context, AuctionListingsActivity.class);
			stackBuilder.addParentStack(AuctionListingsActivity.class);
		}
		else if(msg.getString("type").equalsIgnoreCase("NewAuctions")){ 
			resultIntent = new Intent(context, ShortListActivity.class);
			stackBuilder.addParentStack(ShortListActivity.class);
		}
		
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);

		PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// NOTIFICATION_ID allows you to update the notification later on.
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

}
