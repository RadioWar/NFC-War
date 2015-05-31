package org.radiowar.taginfo.activity.war;

import java.util.ArrayList;
import java.util.HashMap;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.dao.KeyInfoDao;
import org.radiowar.taginfo.pojo.Key;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class WarActivity extends Activity implements OnClickListener,OnItemClickListener {

	private Button leftButton;
	private Button rightButton;
	private TextView titleText;
	private ListView keyList;
	// 用户设置的keys
	private ArrayList<Key> keys;
	
	// 全部的key
	private ArrayList<String> allKeys;
	
	private KeyInfoDao dao;
	private final int SECTOR_COUNT=16;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.war);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  // titlebar为自己标题栏的布局
        
        this.leftButton = (Button)this.findViewById(R.id.leftButton);
        this.rightButton = (Button)this.findViewById(R.id.rightButton);
        this.titleText = (TextView)this.findViewById(R.id.titleText);
        this.titleText.setText("War(Quick Steal)");
        this.leftButton.setOnClickListener(this);
        this.rightButton.setOnClickListener(this);
        this.rightButton.setText("GO");
        
        this.leftButton.setText("back");       
        dao = new KeyInfoDao(this);
        keys = new ArrayList<Key>();
        allKeys = new ArrayList<String>();
        for(Key k:dao.getAllKeyInfo()){
        	allKeys.add(k.getKeyValue());
        }
        
        Key defaultkey = dao.getAllKeyInfo().get(0);
        
        for(int i=0;i<SECTOR_COUNT;i++){
        	Key tempKey = new Key();
        	tempKey.setKeyValue(defaultkey.getKeyValue());
        	this.keys.add(tempKey);
        }
        
        this.keyList = (ListView)this.findViewById(R.id.keylist);
		this.keyList.setVerticalScrollBarEnabled(false);
		this.keyList.setOnItemClickListener(this);
        setList();
	}
	
	private void setList() {
		//生成动态数组，加入数据    
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<this.keys.size();i++) {    
        	Key key =  this.keys.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();    
            map.put("sectorIndex", "Sector " + i + ":");//block index    
            map.put("keyValue", "current key:" + key.getKeyValue());  
            listItem.add(map);
        }
        //生成适配器的Item和动态数组对应的元素    
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源     
            R.layout.warkeylist_cell,//ListItem的XML实现    
            //动态数组与列的对应的子项            
            new String[] {"sectorIndex","keyValue"
        		},    
            //对应的描述文件
            new int[] {R.id.sectorIndex,R.id.keyValue//,R.id.ItemText
        		}    
        );
        //添加并且显示    
        this.keyList.setAdapter(listItemAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		final Key k = this.keys.get(position);

		// set custom view
		LayoutInflater factory = LayoutInflater.from(WarActivity.this);
		final View dialogView = factory.inflate(R.layout.warkeyselect, null);
		
		final EditText inputkey = (EditText)dialogView.findViewById(R.id.currentkey);
		// 设置当前key
				inputkey.setText(k.getKeyValue());
				
				// 设置允许输入字符的长度
				inputkey.setFilters(new  InputFilter[]{ new  InputFilter.LengthFilter(12)});
				// 设置允许输入的字符
				inputkey.setKeyListener(new NumberKeyListener() {
					// 0无键盘 1英文键盘 2模拟键盘 3数字键盘
					@Override
					public int getInputType() {
						// TODO Auto-generated method stub
						return 1;
					}
					// 返回允许输入的字符
					@Override
					protected char[] getAcceptedChars() {
						// TODO Auto-generated method stub
						char[] c = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','a','b','c','d','e','f'};
						return c;
					}
				});
				
				final Spinner spinner = (Spinner)dialogView.findViewById(R.id.selectkeys);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, this.allKeys); 
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);
				spinner.setSelection(0,false);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> arg0, View view,
							int position, long arg3) {
						String key = allKeys.get(position);
						inputkey.setText(key);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// done
					}
					
				});
		
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("edit key").setIcon(android.R.drawable.ic_dialog_info).setView(dialogView)
	                .setNegativeButton("Cancel", null);
	        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

	            public void onClick(DialogInterface dialog, int which) {
	            	
	            	String keyvalue = inputkey.getText().toString();
	            	k.setKeyValue(keyvalue);
	            	setList();
	             }
	        });
	        builder.show();
	}

	@Override
	public void onClick(View view) {
		if(view == this.leftButton){
			this.setResult(RESULT_CANCELED);
			this.finish();
		}
		else if(view == this.rightButton){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Notice");
			builder.setMessage("Are you ready?");
			builder.setNegativeButton("Cancel", null);
	        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

	            public void onClick(DialogInterface dialog, int which) {
	            	Intent readyIntent = new Intent(WarActivity.this,ReadyActivity.class);
					Bundle bundle = new Bundle();
					//bundle.putStringArrayList("keyList", allKeys);
					bundle.putSerializable("keyList", keys);
					readyIntent.putExtras(bundle);
					WarActivity.this.startActivityForResult(readyIntent, 0);
					WarActivity.this.finish();
	             }
	        });
	        builder.show();
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
