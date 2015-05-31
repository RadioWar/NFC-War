package org.radiowar.taginfo.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.dao.KeyInfoDao;
import org.radiowar.taginfo.pojo.Key;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class KeyListActivity extends Activity implements OnClickListener{

	private Button leftButton;
	private Button rightButton;
	private TextView titleText;
	
	private ListView dataList;
	private KeyInfoDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		this.setContentView(R.layout.datalist);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  // titlebar为自己标题栏的布局
		
		dao = new KeyInfoDao(this);
		
		this.leftButton = (Button)this.findViewById(R.id.leftButton);
        this.rightButton = (Button)this.findViewById(R.id.rightButton);
        this.titleText = (TextView)this.findViewById(R.id.titleText);
        
        this.rightButton.setText("add");
        
        this.leftButton.setOnClickListener(this);
        this.rightButton.setOnClickListener(this);
		
		this.dataList = (ListView)this.findViewById(R.id.dataList);
		this.dataList.setVerticalScrollBarEnabled(false);
        setList();
	}

	private void setList() {
		//生成动态数组，加入数据    
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();    
        List<Key> keylist = dao.getAllKeyInfo();
        
        for(int i=0;i<keylist.size();i++)    
        {    
			String data = keylist.get(i).getKeyValue();
			HashMap<String, Object> map = new HashMap<String, Object>();    
            map.put("keyData",data);  
            listItem.add(map);
        }
        
        //生成适配器的Item和动态数组对应的元素    
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源     
            R.layout.singlecell,//ListItem的XML实现    
            //动态数组与列的对应的子项            
            new String[] {"keyData"
        		},    
            //对应的描述文件
            new int[] {R.id.keyData//,R.id.ItemText
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
		else if(view == this.rightButton)
		{
			 final EditText inputkey = new EditText(this);
			 //inputServer.setKeyListener(DigitsKeyListener.getInstance("ABCDEF0123456789"));
			 
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle("Server").setIcon(android.R.drawable.ic_dialog_info).setView(inputkey)
		                .setNegativeButton("Cancel", null);
		        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

		            public void onClick(DialogInterface dialog, int which) {
		            	String key = inputkey.getText().toString();
		            	//dao.insertKey(key);
		             }
		        });
		        builder.show();
		}
	}
}














