package com.novitee.knightfrankacution.base;

import com.novitee.knightfrankacution.api.KnightFrankAPI;
import com.novitee.knightfrankacution.util.ConnectionManager;
import com.novitee.knightfrankacution.util.Preferences;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	
	protected Preferences pref;
    protected Context context;
    protected ProgressDialog mDialog;
    protected ConnectionManager connectionManager;
    protected KnightFrankAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        context = getActivity().getApplicationContext();
        
        pref = Preferences.getInstance(context);
        
        connectionManager = new ConnectionManager(context);
        
        api = new KnightFrankAPI();
    }
    
}
