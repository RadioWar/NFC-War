package org.radiowar.taginfo.activity.readcard.accesscondition;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.pojo.mifare.MifareClassCard;
import org.radiowar.taginfo.pojo.mifare.MifareSector;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class AccessConditionActivity extends Activity implements
		OnClickListener {

	private Button leftButton;
	private Button rightButton;
	private TextView titleText;
	private TextView textView;
	
	MifareClassCard mifareClassCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		this.setContentView(R.layout.accesscondition);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  // titlebar为自己标题栏的布局
		
		this.leftButton = (Button)this.findViewById(R.id.leftButton);
        this.rightButton = (Button)this.findViewById(R.id.rightButton);
        this.titleText = (TextView)this.findViewById(R.id.titleText);
        this.textView = (TextView)this.findViewById(R.id.acTextView);
        this.textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        
        this.titleText.setText("Access condition");
        this.leftButton.setOnClickListener(this);
        this.rightButton.setOnClickListener(this);
        this.rightButton.setVisibility(View.GONE);
        
        Bundle bundle = getIntent().getExtras(); 
		mifareClassCard = (MifareClassCard)bundle.getSerializable("mifare");
		
		StringBuilder temp = new StringBuilder();
		for(int i=0;i<mifareClassCard.getSectorCount();i++){
			MifareSector sector = mifareClassCard.getSector(i);
			temp.append("Sector "+sector.sectorIndex+":\n");
			if(sector.accessCondition==null)
				temp.append("Could not read access conditions!");
			else
				temp.append(sector.accessCondition);
			
			temp.append("\n\n");
		}
		this.textView.setText(temp.toString());
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
