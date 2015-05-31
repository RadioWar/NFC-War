package org.radiowar.taginfo.activity.main;

import org.radiowar.taginfo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.umeng.analytics.MobclickAgent;

public class SplashActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);	
		
		new Handler().postDelayed(new Runnable(){
		
		@Override
		public void run() {
		Intent intent = new Intent(SplashActivity.this,MainActivity.class);
		startActivity(intent);			   
		SplashActivity.this.finish();
		}
		                       
		}, 2500);
	}
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
}
