package com.novitee.knightfrankacution;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
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
    	Intent intent = null;
		NotificationManager notiManager = (NotificationManager) 
				context.getSystemService(Context.NOTIFICATION_SERVICE);
		/*NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(context)
												.setContentTitle("title")
												.setContentText("message")
												.setAutoCancel(true)
												.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
*/
		NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(context)
											.setContentTitle(msg.getString("title"))
											.setContentText(msg.getString("message"))
											.setAutoCancel(true)
											.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		if(msg.getString("type").equalsIgnoreCase("UpdateShortlist")){
//			notiBuilder.setSmallIcon(R.drawable.ic_wheel);
			intent = new Intent(context,ShortListActivity.class);
		}
		/*else if(msg.getString("type").equalsIgnoreCase("competency_wheel")){
			notiBuilder.setSmallIcon(R.drawable.ic_wheel);
			intent = new Intent(context,WheelActivity.class);
		}*/
		
		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 
																  0, 
																  intent, 
																  PendingIntent.FLAG_CANCEL_CURRENT);
		notiBuilder.setContentIntent(pendingIntent);
		
		notiManager.notify(NOTIFICATION_ID , 
		notiBuilder.build());
	}

}
