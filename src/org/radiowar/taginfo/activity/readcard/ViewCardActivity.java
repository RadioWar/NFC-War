package org.radiowar.taginfo.activity.readcard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.activity.readcard.accesscondition.AccessConditionActivity;
import org.radiowar.taginfo.pojo.mifare.MifareBlock;
import org.radiowar.taginfo.pojo.mifare.MifareClassCard;
import org.radiowar.taginfo.pojo.mifare.MifareSector;
import org.radiowar.taginfo.util.Converter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

public class ViewCardActivity extends Activity implements OnClickListener {

	private Button leftButton;
	private Button rightButton;
	private LinearLayout linearLayout;
	private TextView titleText;
	private ListView listView1;
	private ListView listView2;
	private ListView listView3;

	List<Map<String, Object>> map_list1;
	List<Map<String, Object>> map_list2;
	List<Map<String, Object>> map_list3;
	
	MifareClassCard mifareClassCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.viewcard);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  // titlebar为自己标题栏的布局
        
        this.leftButton = (Button)this.findViewById(R.id.leftButton);
        this.rightButton = (Button)this.findViewById(R.id.rightButton);
        this.titleText = (TextView)this.findViewById(R.id.titleText);
        this.titleText.setText("View Card");
        this.leftButton.setOnClickListener(this);
        
        this.leftButton.setText("back");       
        this.rightButton.setVisibility(View.INVISIBLE);
        
        Bundle bundle = getIntent().getExtras(); 
		mifareClassCard = (MifareClassCard)bundle.getSerializable("mifare");
        
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout1);
        linearLayout.setVerticalScrollBarEnabled(true);
        // 添加List
        this.listView1 = (ListView) this.findViewById(R.id.group1);
        this.listView1.setVerticalScrollBarEnabled(false);
        this.listView1.setOnItemClickListener(new OnItemClickListener1());
        // 添加List
        this.listView2 = (ListView) this.findViewById(R.id.group2);
        this.listView2.setVerticalScrollBarEnabled(false);
        this.listView2.setOnItemClickListener(new OnItemClickListener2());
        // 添加List
        this.listView3 = (ListView) this.findViewById(R.id.group3);
        this.listView3.setVerticalScrollBarEnabled(false);
        this.listView3.setOnItemClickListener(new OnItemClickListener3());

        setList();
		setListViewHeight();
	}

	private void setList(){
		setList1();
        setList2();
        setList3();
	}
	
	private void setList1() {
		
		map_list1 = new ArrayList<Map<String, Object>>();
		Map<String,Object> checkupdate=new HashMap<String, Object>();
		checkupdate.put("cellName", "Tag information");
		map_list1.add(checkupdate);
		SimpleAdapter adapter1 = new SimpleAdapter(this, map_list1,
				R.layout.more_cell, new String[] { "cellName" },
				new int[] { R.id.cellName});
		listView1.setAdapter(adapter1);
	}
	
	private void setList2() {
		map_list2 = new ArrayList<Map<String, Object>>();
		
		Map<String,Object> feedback=new HashMap<String, Object>();
		feedback.put("cellName", "Data(HEX)");
		Map<String,Object> score=new HashMap<String, Object>();
		score.put("cellName", "Access condition");

		map_list2.add(feedback);
		map_list2.add(score);
		SimpleAdapter adapter2 = new SimpleAdapter(this, map_list2,
				R.layout.more_cell, new String[] { "cellName","cellValue" },
				new int[] { R.id.cellName ,R.id.cellValue});
		listView2.setAdapter(adapter2);
	}
	
	private void setList3() {
		map_list3 = new ArrayList<Map<String, Object>>();
		Map<String,Object> tSupport=new HashMap<String, Object>();
		tSupport.put("cellName", "Save Dump");
		Map<String,Object> about=new HashMap<String, Object>();
		about.put("cellName", "Write");
		map_list3.add(tSupport);
		map_list3.add(about);
		SimpleAdapter adapter3 = new SimpleAdapter(this, map_list3,
				R.layout.more_cell, new String[] { "cellName","cellValue" },
				new int[] { R.id.cellName ,R.id.cellValue});
		listView3.setAdapter(adapter3);
	}
	
	class OnItemClickListener1 implements OnItemClickListener {

		ProgressDialog myProgress;
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position ,long arg3) {
			if(position==0){
				// Tag information
				Intent intent = new Intent(ViewCardActivity.this,TagInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("mifare", mifareClassCard);
				intent.putExtras(bundle);
				ViewCardActivity.this.startActivityForResult(intent, 0);
			}
		}
	}
	
	class OnItemClickListener2 implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position ,long arg3) {
			if(position==0){
				// Data(HEX)
				
				Intent dataIntent = new Intent();	
				dataIntent.setClass(ViewCardActivity.this, DataListActivity.class);	
				Bundle bundle = new Bundle();
				bundle.putSerializable("mifare", mifareClassCard);
				dataIntent.putExtras(bundle);
				ViewCardActivity.this.startActivityForResult(dataIntent, 0);
			}
			else if(position==1){
				// Access condition
				
				Intent dataIntent = new Intent();	
				dataIntent.setClass(ViewCardActivity.this, AccessConditionActivity.class);	
				Bundle bundle = new Bundle();
				bundle.putSerializable("mifare", mifareClassCard);
				dataIntent.putExtras(bundle);
				ViewCardActivity.this.startActivityForResult(dataIntent, 0);
			}
		}
	}
	
	class OnItemClickListener3 implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position ,long arg3) {
			if(position==0){
				// Save Dump
				
				saveDumpFile();
			}else if(position==1){
				// Write
				
//				AlertDialog.Builder builder = new AlertDialog.Builder(ViewCardActivity.this);
//				builder.setTitle("Select card type");
				
				Intent intent = new Intent(ViewCardActivity.this, WriteCardActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("mifare", mifareClassCard);
				intent.putExtras(bundle);
				ViewCardActivity.this.startActivityForResult(intent, 0);
			}
			 
		}
	}
	
	private void setListViewHeight() {
        int height1 = 0;
        int num = listView1.getAdapter().getCount();
        for (int i = 0; i < num; i++) {
	        View view = listView1.getAdapter().getView(i, null, listView1);
	        view.measure(0, 0);
	        height1 += view.getMeasuredHeight();
        }
        LayoutParams lp1=listView1.getLayoutParams();
        lp1.height=height1;

        int height2 = 0;
        num = listView2.getAdapter().getCount();
        for (int i = 0; i < num; i++) {
	        View view = listView2.getAdapter().getView(i, null, listView2);
	        view.measure(0, 0);
	        height2 += view.getMeasuredHeight();
        }
        LayoutParams lp2=listView2.getLayoutParams();
        lp2.height=height2;
        
        int height3 = 0;
        num = listView3.getAdapter().getCount();
        for (int i = 0; i < num; i++) {
	        View view = listView3.getAdapter().getView(i, null, listView3);
	        view.measure(0, 0);
	        height3 += view.getMeasuredHeight();
        }
        LayoutParams lp3=listView3.getLayoutParams();
        lp3.height=height3;
       
        LayoutParams lp=linearLayout.getLayoutParams();
        lp.height=height1+height2+height3+100;
        linearLayout.setLayoutParams(lp);
	}


	@Override
	public void onClick(View view) {
		if(view == this.leftButton){
			this.setResult(RESULT_CANCELED);
			this.finish();
		}
	}
	
	private void saveDumpFile() {
		String PATH = "/sdcard/dumpfile";
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMDDHHmmss");
		Date curTime = new Date(System.currentTimeMillis());
		String time = sf.format(curTime);
		String uid = mifareClassCard.getValues().get("UID");
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
			
			Toast toast = Toast.makeText(this, "OK:" + PATH+FILENAME, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL |Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
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
