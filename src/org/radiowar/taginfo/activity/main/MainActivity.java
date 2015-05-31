package org.radiowar.taginfo.activity.main;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.activity.history.HistoryActivity;
import org.radiowar.taginfo.activity.keymanager.KeyManagerActivity;
import org.radiowar.taginfo.activity.more.MoreActivity;
import org.radiowar.taginfo.activity.readcard.ReadCardActivity;
import org.radiowar.taginfo.activity.war.WarActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.NotificationType;
import com.umeng.fb.UMFeedbackService;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends Activity implements OnClickListener{
	private Button btnReadCard;
	private Button btnKeyManager;
	private Button btnHistory;
	private Button btnWar;
	private Button btnMore;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
		 
		    // 推送更新
		 	UmengUpdateAgent.setUpdateAutoPopup(true);
		 	UmengUpdateAgent.setUpdateOnlyWifi(false);
		 	UmengUpdateAgent.update(this);
		 	
		 	// 反馈提示
		 	UMFeedbackService.enableNewReplyNotification(this, NotificationType.NotificationBar);
		 	
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	        setContentView(R.layout.main);
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.maintitlebar);  // titlebar为自己标题栏的布局
	        
	        btnReadCard = (Button)this.findViewById(R.id.btnReadCard);
	        btnKeyManager = (Button)this.findViewById(R.id.btnKeyManager);
	        btnHistory = (Button)this.findViewById(R.id.btnHistory);
	        btnWar = (Button)this.findViewById(R.id.btnWar);
	        btnMore = (Button)this.findViewById(R.id.btnMore);
	        
	        btnReadCard.setOnClickListener(this);
	        btnKeyManager.setOnClickListener(this);
	        btnHistory.setOnClickListener(this);
	        btnWar.setOnClickListener(this);
	        btnMore.setOnClickListener(this);
	        
	        this.checkNFC();
	 }

	@Override
	public void onClick(View view) {
		if(view == btnReadCard) {
			Intent intent = new Intent(this,ReadCardActivity.class);
			this.startActivityForResult(intent, 0);
		}
		else if(view == btnKeyManager) {
			Intent intent = new Intent(this,KeyManagerActivity.class);
			this.startActivityForResult(intent, 0);
		}
		else if(view == btnHistory) {
			Intent intent = new Intent(this,HistoryActivity.class);
			this.startActivityForResult(intent, 0);
		}
		else if(view == btnWar) {
			Intent intent = new Intent(this,WarActivity.class);
			this.startActivityForResult(intent, 0);
		}
		else if(view == btnMore) {
			Intent intent = new Intent(this,MoreActivity.class);
			this.startActivityForResult(intent, 0);
		}
	}
	 
	public void checkNFC(){
		NfcAdapter a = NfcAdapter.getDefaultAdapter(this);
        if(!a.isEnabled()){
        	AlertDialog.Builder alertbBuilder=new AlertDialog.Builder(this); 
        	
            alertbBuilder.setTitle("NFC is off").setMessage("Click \"nfc setting\" to change NFC setting.")
            .setPositiveButton("nfc setting", new DialogInterface.OnClickListener(){ 
                public void onClick(DialogInterface dialog, int which) {   
                	startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                } 
            })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() { 
        		public void onClick(DialogInterface dialog, int which){ 
        			
                        dialog.cancel(); 
                } 
        	}).create(); 
            alertbBuilder.show();
        }
	}
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	    this.checkNFC();
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
	
}
































