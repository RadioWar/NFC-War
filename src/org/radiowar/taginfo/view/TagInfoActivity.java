package org.radiowar.taginfo.view;

import org.radiowar.taginfo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class TagInfoActivity extends Activity implements OnClickListener {
	
	private Button btnScan;
	private Button btnOpen;
	private Button btnSetup;
	private Button btnHistory;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.infotag);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.maintitlebar);  // titlebar为自己标题栏的布局

        this.btnScan = (Button)this.findViewById(R.id.btnScan);
        this.btnOpen = (Button)this.findViewById(R.id.btnOpen);
        this.btnSetup = (Button)this.findViewById(R.id.btnSetup);
        this.btnHistory = (Button)this.findViewById(R.id.btnHistory);
        
        this.btnScan.setOnClickListener(this);
        this.btnOpen.setOnClickListener(this);
        this.btnSetup.setOnClickListener(this);
        this.btnHistory.setOnClickListener(this);
    }

	@Override
	public void onClick(View view) {
		if(view == btnScan)
		{
			Intent intent = new Intent(this,ReaderActivity.class);
			this.startActivityForResult(intent, 0);
		}
		else if(view == btnOpen)
		{
//			Intent intent = new Intent(this,CopyActivity.class);
//			this.startActivityForResult(intent, 0);
		}
		else if(view == btnSetup)
		{
			Intent intent = new Intent(this,KeyListActivity.class);
			this.startActivity(intent);
		}
		else if(view == btnHistory)
		{
			
		}
		
	}
}








