package com.novitee.knightfrankacution;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novitee.knightfrankacution.base.BaseFragmentActivity;
import com.novitee.knightfrankacution.model.News;
import com.novitee.knightfrankacution.util.Preferences;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewsActivity extends BaseFragmentActivity {

	Context context = this;
	
	TextView titleText;
	TextView titleText2;
	ImageView titleImage;
	FragmentTransaction fragmentTran;
	
	ListView listNews;
	ArrayList<News> newsList;
	ArrayList<String> newsLink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		
		setTitleBarAndFooter();
		
		listNews = (ListView) findViewById(R.id.news_list);
		
		newsList = new ArrayList<News>();
		newsLink = new ArrayList<String>();
		
		CustomList customList = new CustomList(context, R.layout.news_listview_layout, newsList);
		listNews.setAdapter(customList);
		
		if (connectionManager.isConnected()) {
			new GetNews().execute();
		}
		else {
			Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
		}
		
		listNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse(newsLink.get(position));
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(launchBrowser);
			}
		});
		
	}//onCreate
	
	public void setTitleBarAndFooter() {
		//title bar
		titleText = (TextView) findViewById(R.id.title_text);
		titleText2 = (TextView) findViewById(R.id.title_text2);
		titleImage = (ImageView) findViewById(R.id.title_image);
		
		titleText2.setVisibility(View.GONE);
		titleImage.setVisibility(View.GONE);
		titleText.setText("News");
		
		fragmentTran = getSupportFragmentManager().beginTransaction();
		fragmentTran.replace(R.id.news_footer, new FooterFragment());
		fragmentTran.commit();
		
		ImageView titleBack = (ImageView) findViewById(R.id.title_back);
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}//setTitleBarAndFooter
	
	private class CustomList extends ArrayAdapter<News> {
		
		Context context;
		ArrayList<News> newsList;

		private CustomList(Context c, int resource, ArrayList<News> newsList) {
			super(c, R.layout.news_listview_layout, newsList);
			this.context = c;
			this.newsList = newsList;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.news_listview_layout, parent, false);
			
			TextView title = (TextView) row.findViewById(R.id.news_title);
			TextView description = (TextView) row.findViewById(R.id.news_description);
			TextView link = (TextView) row.findViewById(R.id.news_link);
			TextView date = (TextView) row.findViewById(R.id.news_date);
			
			News news = newsList.get(position);
			title.setText(news.getTitle());
			description.setText(news.getDescription());
			link.setText(news.getNews_link());
			date.setText(news.getDate());
			
			newsLink.add(news.getNews_link());
			
			return row;
		}
		
	}//CustomList
	
	private class GetNews extends AsyncTask<Void, Void, Void> {
		JSONObject jObj;
		ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewsActivity.this);
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				jObj = api.getNews(Preferences.getInstance(context).getSessionToken());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(pDialog.isShowing()){
				pDialog.dismiss();
			}
			
			try {
				int json_responseCode = jObj.getInt("statusCode");
				int json_status = jObj.getInt("status");
				
				if(json_status == 1 && json_responseCode == 200){
					JSONArray json = jObj.getJSONArray("data");
					News news;
					newsList.clear();
					
					for (int i = 0; i < json.length(); i++) {
						news = new News(json.getJSONObject(i));
						newsList.add(news);
					}
					
					if (newsList.size() > 0) {
						CustomList customList = new CustomList(context, R.layout.news_listview_layout, newsList);
						listNews.setAdapter(customList);
					}
					else {
						Toast.makeText(context, "There is no news", Toast.LENGTH_LONG).show();
					}
				}
				else if(json_status == 2 && json_responseCode == 401) {
					String message = jObj.getString("message");
					Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}//GetNews

}
