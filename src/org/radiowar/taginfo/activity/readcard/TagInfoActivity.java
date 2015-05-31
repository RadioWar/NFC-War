package org.radiowar.taginfo.activity.readcard;

import java.util.HashMap;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.pojo.mifare.MifareClassCard;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class TagInfoActivity extends Activity implements OnClickListener  {
	private Button leftButton;
	private Button rightButton;
	private TextView titleText;
	private TextView editText;
	
	MifareClassCard mifareClassCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		this.setContentView(R.layout.taginformation);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  // titlebar为自己标题栏的布局
		
		this.leftButton = (Button)this.findViewById(R.id.leftButton);
        this.rightButton = (Button)this.findViewById(R.id.rightButton);
        this.titleText = (TextView)this.findViewById(R.id.titleText);
        this.editText = (TextView)this.findViewById(R.id.editText1);
        
        this.titleText.setText("Tag Information");
        this.leftButton.setOnClickListener(this);
        this.rightButton.setOnClickListener(this);
        this.rightButton.setVisibility(View.GONE);
        
        Bundle bundle = getIntent().getExtras(); 
		mifareClassCard = (MifareClassCard)bundle.getSerializable("mifare");
		
		HashMap<String,String> values = mifareClassCard.getValues();
		
		StringBuilder temp = new StringBuilder();
		temp.append("UID:");
		temp.append(values.get("UID"));
		temp.append("\n\n");
		temp.append("TechList:\n");
		temp.append(values.get("TechList"));
		
		editText.setText(temp.toString());
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


