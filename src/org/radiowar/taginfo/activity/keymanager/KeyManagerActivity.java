package org.radiowar.taginfo.activity.keymanager;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.dao.KeyInfoDao;
import org.radiowar.taginfo.pojo.Key;
import org.radiowar.taginfo.util.openfiledialog.CallbackBundle;
import org.radiowar.taginfo.util.openfiledialog.OpenFileDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

public class KeyManagerActivity extends Activity implements OnClickListener,OnItemClickListener{

	private Button leftButton;
	private Button rightButton;
	private TextView titleText;
	
	private ListView dataList;
	private List<Key> keyList;
	
	KeyInfoDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		this.setContentView(R.layout.keymanager);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  // titlebar为自己标题栏的布局
		
		this.leftButton = (Button)this.findViewById(R.id.leftButton);
        this.rightButton = (Button)this.findViewById(R.id.rightButton);
        this.titleText = (TextView)this.findViewById(R.id.titleText);
        
        this.leftButton.setOnClickListener(this);
        this.rightButton.setOnClickListener(this);
        this.rightButton.setText("Add");
        this.titleText.setText("Key Manager");
        
        dao = new KeyInfoDao(this);
		
		this.dataList = (ListView)this.findViewById(R.id.dataList);
		this.dataList.setVerticalScrollBarEnabled(false);
		this.dataList.setOnItemClickListener(this);
        setList();
	}
	
	private void setList() {
		//生成动态数组，加入数据    
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();    
        keyList = dao.getAllKeyInfo();
        
        for(Key k :keyList)
        {    
			HashMap<String, Object> map = new HashMap<String, Object>();    
            map.put("key", "key:"+k.getKeyValue());//block index    
            map.put("type", "type:"+k.getKeyType());  
            listItem.add(map);
        }    
        //生成适配器的Item和动态数组对应的元素    
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源     
            R.layout.keymanager_cell,//ListItem的XML实现    
            //动态数组与列的对应的子项            
            new String[] {"key","type"
        		},    
            //对应的描述文件
            new int[] {R.id.key,R.id.type//,R.id.ItemText
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
		else if(view == this.rightButton){
			String type[] = { "input", "from PM3 key file"};
			//String type[] = { "input"};
			new AlertDialog.Builder(this).setTitle("add keys").setItems
			(
					type, 
					new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int index) 
						{			
							if(index == 0)
							{
								showKeyEditView();
							}
							else if(index == 1)
							{
								showDialog(0);
								//Intent intent = new Intent(KeyManagerActivity.this,KeyFileChooserActivity.class);
								//KeyManagerActivity.this.startActivityForResult(intent,0);
							}
						}
					}).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int index) {
					
				}
			}).show();
			
			
		}
	}

	public void showKeyEditView(){
		final EditText inputkey = new EditText(this);
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
		 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("input key").setIcon(android.R.drawable.ic_dialog_info).setView(inputkey)
                .setNegativeButton("Cancel", null);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
            	String keyvalue = inputkey.getText().toString();
            	Key k = new Key();
            	k.setKeyValue(keyvalue);
            	
            	dao.insertKey(k);
            	KeyManagerActivity.this.setList();
             }
        });
        builder.show();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		final Key k = this.keyList.get(position);
		final EditText inputkey = new EditText(this);
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
		
		if(k.getKeyType().equals("default")){
			//default key can't edit/delete
			new AlertDialog.Builder(this) 
		     .setTitle("Notice") 
		     .setMessage("default key can't edit/delete") 
		     .setNegativeButton("OK", null)
		     .show();

			return;
		}
			
		
		new AlertDialog.Builder(this).setTitle("Please select").setItems
		(
				new String[]{"Edit","Delete"}, 
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int index) 
					{			
						if(index == 0)
						{
							inputkey.setText(k.getKeyValue());
							AlertDialog.Builder builder = new AlertDialog.Builder(KeyManagerActivity.this);
					        builder.setTitle("edit key").setIcon(android.R.drawable.ic_dialog_info).setView(inputkey)
					                .setNegativeButton("Cancel", null);
					        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					            public void onClick(DialogInterface dialog, int which) {
					            	String keyvalue = inputkey.getText().toString();
					            	k.setKeyValue(keyvalue);
					            	dao.updateKey(k);
					            	setList();
					             }
					        });
					        
					        builder.show();
						}
						else if(index == 1)
						{
							dao.deleteKey(k);
							setList();
						}
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int index) {
				//疑问：为什么setNeutralButton不要需要dialog.dismiss();则可以关闭dialog
				//dialog.dismiss();
			}
		}).show();// 列表 show
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
			Map<String, Integer> images = new HashMap<String, Integer>();
			// 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
			images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);	// 根目录图标
			images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);	//返回上一层的图标
			images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);	//文件夹图标
			images.put("wav", R.drawable.filedialog_wavfile);	//wav文件图标
			images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
			Dialog dialog = OpenFileDialog.createDialog(id, this, "choose PM3 key file(*.bin)", new CallbackBundle() {
				@Override
				public void callback(Bundle bundle) {
					String filepath = bundle.getString("path");
					// 读取文件，获取keys字符串
					String keys = readKeyFile(filepath);
					
					// 验证keys是否符合规则(验证长度)
					if(keys.length()%12==0){
						// 分割字符串
						List<String> keyList = getKeyAryWithString(keys);
						
						// 插入数据库
						for(String keyvalue : keyList){
							Key k = new Key();
			            	k.setKeyValue(keyvalue);
			            	dao.insertKey(k);
						}
						
						// 刷新界面
		            	KeyManagerActivity.this.setList();
					}
					else{
						Toast toast = Toast.makeText(getApplicationContext()
								,"not PM3 key file"
								, Toast.LENGTH_LONG);
						
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}

			}, 
			".bin;",
			images);
			return dialog;
	}
	
	/**
	 * 根据文件路径读取HEX文件内容
	 * @param filepath 文件路径
	 * @return 返回HEX字符串
	 */
	private String readKeyFile(String filepath) {
		try { 
			File file = new File(filepath); 
	        DataInputStream dps = new DataInputStream(new FileInputStream(file)); 
	        StringBuilder hexData = new StringBuilder();

	        byte bt = 0;
	        for(int i=0;i<file.length();i++) {  
	        	bt = dps.readByte(); // 以十六进制的无符号整数形式返回一个字符串表示形式。

	            String str = Integer.toHexString(bt);

				if(str.length() == 8) { //去掉补位的f
					str = str.substring(6);
				}
				
				if(str.length() == 1) { 
		           str = "0"+str;
				} 
				hexData.append(str.toUpperCase()); 
	        } 
	        //System.out.println(hexData.toString());
	        return hexData.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	
	private List<String> getKeyAryWithString(String keys) {
		List<String> ret=new ArrayList<String>();
		int strLength = keys.length();
		for(int i = 0;i<strLength ;i+=12){
			ret.add(keys.substring(i,i+12));
		}
		return ret;
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
