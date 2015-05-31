package org.radiowar.taginfo.activity.readcard;

import java.util.ArrayList;
import java.util.HashMap;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.pojo.mifare.MifareClassCard;
import org.radiowar.taginfo.pojo.mifare.MifareSector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class DataListActivity extends Activity implements OnClickListener{

	private Button leftButton;
	private Button rightButton;
	private TextView titleText;
	
	private ListView dataList;
	private MifareClassCard mifareClassCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		this.setContentView(R.layout.datalist);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  // titlebar为自己标题栏的布局
		
		this.leftButton = (Button)this.findViewById(R.id.leftButton);
        this.rightButton = (Button)this.findViewById(R.id.rightButton);
        this.titleText = (TextView)this.findViewById(R.id.titleText);
        
        this.leftButton.setOnClickListener(this);
        this.rightButton.setOnClickListener(this);
        this.rightButton.setVisibility(View.GONE);
        this.titleText.setText("Data(HEX)");
		
		Bundle bundle = getIntent().getExtras(); 
		mifareClassCard = (MifareClassCard)bundle.getSerializable("mifare");
		
		this.dataList = (ListView)this.findViewById(R.id.dataList);
		this.dataList.setVerticalScrollBarEnabled(false);
        setList();
	}

	private void setList() {
		//生成动态数组，加入数据    
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<mifareClassCard.getSectorCount();i++)    
        {    
        	MifareSector sector =  mifareClassCard.getSector(i);
        	
			if(sector!=null){
				String data = sector.getBlockData();
				HashMap<String, Object> map = new HashMap<String, Object>();    
	            map.put("sectorIndex", "Sector "+sector.sectorIndex+":");//block index    
	            map.put("sectorData", data);  
	            listItem.add(map);
			}
        }    
        //生成适配器的Item和动态数组对应的元素    
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源     
            R.layout.datalistcell_sector,//ListItem的XML实现    
            //动态数组与列的对应的子项            
            new String[] {"sectorIndex","sectorData"
        		},    
            //对应的描述文件
            new int[] {R.id.sectorIndex,R.id.sectorData//,R.id.ItemText
        		}    
        );
        //添加并且显示    
        this.dataList.setAdapter(listItemAdapter);
	}

	@Override
	public void onClick(View view) {
		if(view == this.leftButton)
		{
			setResult(RESULT_OK, getIntent());
			finish();
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














