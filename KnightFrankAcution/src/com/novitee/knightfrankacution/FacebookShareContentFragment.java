package com.novitee.knightfrankacution;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;
import java.util.Set;

import android.os.Bundle;
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
		
        LoginManager.getInstance().logInWithPublishPermissions((Activity) context, Arrays.asList("publish_actions"));
        Set<String> accessToken = AccessToken.getCurrentAccessToken().getPermissions();
		ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
				.putString("og:type", "books.book")
				.putString("og:title", "Check the following property from Knight Frank Auction App Out:")
				.putString("og:description", "This is the testing post from KnightFrank")
				.build();
		
		ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
		        .setActionType("books.reads")
		        .putObject("book", object)
		        .build();
		
		ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
		        .setPreviewPropertyName("book")
		        .setAction(action)
		        .build();
		
//		LoginManager.getInstance().logInWithPublishPermissions((Activity) context, Arrays.asList("publish_actions"));
        
        ShareApi.share(content,new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
            	Toast.makeText(context, "Share Success", Toast.LENGTH_SHORT).show();
//                Log.d(Check.TAG, "Story posted! " + result);
            }

            @Override
            public void onCancel() {
//                Log.d(Check.TAG, "canceled");

            }

            @Override
            public void onError(FacebookException e) {
//                Log.d(Check.TAG, "error posting: "+e.getMessage() );
            	Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            	e.printStackTrace();
            }
        });
        
        /*shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
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
                    .build();
            
            shareDialog.show(linkContent);
            
            ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
            		.putString("og:type", "books.book")
            		.putString("og:title", "Check the following property from Knight Frank Auction App Out:")
            		.putString("og:description", "This is the testing post from KnightFrank")
            		.build();
            
            ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
		            .setActionType("books.reads")
		            .putObject("book", object)
		            .build();
            
            ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
		            .setPreviewPropertyName("book")
		            .setAction(action)
		            .build();
            
//            ShareDialog.show((Activity) context, content);
        }*/
	}
	
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    callbackManager.onActivityResult(requestCode, resultCode, data);
	}

}
