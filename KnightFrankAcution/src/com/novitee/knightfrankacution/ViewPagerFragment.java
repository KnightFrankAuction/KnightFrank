package com.novitee.knightfrankacution;

import com.novitee.knightfrankacution.adapter.ViewPagerAdapter;
import com.novitee.knightfrankacution.util.Preferences;
import com.viewpagerindicator.CirclePageIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewPagerFragment extends Fragment {
	
	ViewPager viewPager;
	CirclePageIndicator circleIndicator;
	Button btnLogin;
	Button btnSignUp;
	
	FragmentTransaction fragmentTran;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.view_pager_fragment, container, false);
		
		btnLogin = (Button) view.findViewById(R.id.btnLogin);
		btnSignUp = (Button) view.findViewById(R.id.btnSignup);
		viewPager = (ViewPager) view.findViewById(R.id.splashViewPager);
		circleIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		
		ViewPagerAdapter viewpagerAdapter = new ViewPagerAdapter(((MainActivity)getActivity()).getSupportFragmentManager());
		viewPager.setAdapter(viewpagerAdapter);
		circleIndicator.setViewPager(viewPager);
		
		/*String username = Preferences.getUserName(getActivity());
		String password = Preferences.getPassword(getActivity());
		if(!username.equals("") && !password.equals("")) {
			startLogin();
		}*/
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
//				startLogin();
			}
		});
		
		btnSignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), SignUpActivity.class);
				startActivity(intent);
			}
		});
		
		return view;
	}

	public void startLogin() {
		fragmentTran = getActivity().getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.place_holder, new LoginFragment());
		fragmentTran.commit();
	}
}
