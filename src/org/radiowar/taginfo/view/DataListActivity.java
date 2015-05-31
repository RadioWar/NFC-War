package org.radiowar.taginfo.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.pojo.mifare.MifareBlock;
import org.radiowar.taginfo.pojo.mifare.MifareClassCard;
import org.radiowar.taginfo.pojo.mifare.MifareSector;
import org.radiowar.taginfo.util.Converter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
		
		Bundle bundle = getIntent().getExtras(); 
		mifareClassCard = (MifareClassCard)bundle.getSerializable("mifare");
		
		this.dataList = (ListView)this.findViewById(R.id.dataList);
		this.dataList.setVerticalScrollBarEnabled(false);
        setList();
	}

	private void setList() {
		//生成动态数组，加入数据    
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();    
        int blockIndex=0;
        for(int i=0;i<mifareClassCard.getSectorCount();i++)    
        {    
        	MifareSector sector =  mifareClassCard.getSector(i);
        	for(int j=0;j<MifareSector.BLOCKCOUNT;j++)
        	{
        		MifareBlock block = sector.blocks[j];
				if(block!=null){
					byte[] raw = block.getData();
					String data = Converter.getHexString(raw, raw.length);
					HashMap<String, Object> map = new HashMap<String, Object>();    
		            map.put("blockIndex", "Block:"+blockIndex);//block index    
		            map.put("blockData", Html.fromHtml("<font color='red'>" + data + "</font>"));  
		            listItem.add(map);
				}
				blockIndex++;
        	}
        }    
        //生成适配器的Item和动态数组对应的元素    
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源     
            R.layout.datalistcell,//ListItem的XML实现    
            //动态数组与列的对应的子项            
            new String[] {"blockIndex","blockData"
        		},    
            //对应的描述文件
            new int[] {R.id.blockIndex,R.id.blockData//,R.id.ItemText
        		}    
        );
        //添加并且显示    
        this.dataList.setAdapter(listItemAdapter);
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(0, 1, 1, "Save dump file");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item){
	
		switch (item.getItemId())
		{
		 	case 1:
		 		saveDumpFile();
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void saveDumpFile() {
		String PATH = "/sdcard/dumpfile";
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMDDHHmmss");
		Date curTime = new Date(System.currentTimeMillis());
		String time = sf.format(curTime);
		String block0 = Converter.getHexString(this.mifareClassCard.getSector(0).blocks[0].getData(),32);
		String uid = block0.substring(0,14);
		String FILENAME = "/"+uid+"_"+time+".dump";
		
		try
		{
			if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
			{
				File path = new File(PATH);
				
				File file = new File(PATH + FILENAME);
				
				if(!path.exists())
				{
					path.mkdirs();
				}
				
				file.createNewFile();
				
				OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));

				for(int i=0;i<mifareClassCard.getSectorCount();i++)    
		        {    
		        	MifareSector sector =  mifareClassCard.getSector(i);
		        	for(int j=0;j<MifareSector.BLOCKCOUNT;j++)
		        	{
		        		MifareBlock block = sector.blocks[j];
						if(block!=null){
							byte[] raw = block.getData();
							String data = Converter.getHexString(raw, raw.length);
							osw.write(data+"\n");
						}
		        	}
		        }    
				
				osw.flush();
				osw.close();
			}
			
			Toast toast = Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL |Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
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
			
		}
	}
}














