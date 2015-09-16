package com.novitee.knightfrankacution;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class FacebookShareContentFragment extends Activity {
	
	CallbackManager callbackManager;
	ShareDialog shareDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook_share_content_fragment);
		
	}//onCreate
	
	public void share(final Context context) {
		FacebookSdk.sdkInitialize(context);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog((Activity) context);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(context, "Share Success", Toast.LENGTH_SHORT).show();
//                Log.d("DEBUG", "SHARE SUCCESS");
            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "Share Cancelled", Toast.LENGTH_SHORT).show();
//                Log.d("DEBUG", "SHARE CACELLED");
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("DEBUG", "Share: " + exception.getMessage());
                exception.printStackTrace();
            }

        });
        
        if (ShareDialog.canShow(ShareLinkContent.class)) {
        	
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Check the following property from Knight Frank Auction App Out:")
                    .setContentDescription("This is the testing post from KnightFrank")
                    .setContentUrl(Uri.parse("http://www.knightfrank.com.sg"))
                    .build();
            
            shareDialog.show(linkContent);
        }
	}
	
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    callbackManager.onActivityResult(requestCode, resultCode, data);
	}

}
