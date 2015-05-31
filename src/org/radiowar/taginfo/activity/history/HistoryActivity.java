package org.radiowar.taginfo.activity.history;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.application.TagInfoApp;
import org.radiowar.taginfo.dao.KeyInfoDao;

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

public class HistoryActivity extends Activity implements OnClickListener {

	private Button leftButton;
	private Button rightButton;
	private TextView titleText;
	private TagInfoApp app;
	private ListView dataList;
	
	KeyInfoDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		this.setContentView(R.layout.history);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  // titlebar为自己标题栏的布局
		
		app = (TagInfoApp)this.getApplication();
		
		this.leftButton = (Button)this.findViewById(R.id.leftButton);
        this.rightButton = (Button)this.findViewById(R.id.rightButton);
        this.titleText = (TextView)this.findViewById(R.id.titleText);
        
        this.leftButton.setOnClickListener(this);
        this.rightButton.setVisibility(View.GONE);

        this.titleText.setText("History");
		
		this.dataList = (ListView)this.findViewById(R.id.historyList);
		this.dataList.setVerticalScrollBarEnabled(false);
        setList();
	}
	
	private void setList() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		
		String PATH = "/sdcard/dumpfile";
		String Extension = "dump";
		
		File path = new File(PATH);
		if(!path.exists())
		{
			path.mkdirs();
		}
		
		File[] files = new File(PATH).listFiles();
	    for (int i = 0; i < files.length; i++)
	    {
	        File f = files[i];
	        if (f.isFile())
	        {
	            if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)){  //判断扩展名
	                System.out.println(f.getPath());
	                
					HashMap<String, Object> map = new HashMap<String, Object>();    
		            map.put("fileName", f.getName()); 
		            listItem.add(map);
	            }
	  
	        }
	    }
	    
      //生成适配器的Item和动态数组对应的元素    
      SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源     
          R.layout.historycell,//ListItem的XML实现    
          //动态数组与列的对应的子项            
          new String[] {"fileName"
      		},    
          //对应的描述文件
          new int[] {R.id.fileName
      		}    
      );
      //添加并且显示    
      this.dataList.setAdapter(listItemAdapter);
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
