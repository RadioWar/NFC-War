package org.radiowar.taginfo.activity.more;

import org.radiowar.taginfo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class AboutUsActivity extends Activity  implements OnClickListener {
	
	private Button leftButton;
	private Button rightButton;
	private TextView titleText;
	
	public ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.aboutus);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  // titlebar为自己标题栏的布局
        
        this.leftButton = (Button)this.findViewById(R.id.leftButton);
        this.rightButton = (Button)this.findViewById(R.id.rightButton);
        this.titleText = (TextView)this.findViewById(R.id.titleText);
        this.titleText.setText("About Us");
        this.leftButton.setOnClickListener(this);
        
        this.leftButton.setText("back");
        this.rightButton.setVisibility(View.INVISIBLE);
        
        list = (ListView) findViewById(R.id.aboutuslist);
		list.setVerticalScrollBarEnabled(false);
        setList();
	}

	private void setList() {
		AboutUsAdapter listItemAdapter = new AboutUsAdapter(this);
		list.setAdapter(listItemAdapter);
	}

	@Override
	public void onClick(View view) {
		if(view == this.leftButton){
			this.setResult(RESULT_CANCELED);
			this.finish();
		}
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
