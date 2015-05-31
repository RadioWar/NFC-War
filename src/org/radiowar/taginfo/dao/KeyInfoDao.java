package org.radiowar.taginfo.dao;

import java.util.ArrayList;
import java.util.List;

import org.radiowar.taginfo.pojo.Key;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class KeyInfoDao {
	public static final String DB_NAME = "TagInfoDB.db";
	public static final String TABLE_KeyInfo = "KeyInfo";
	public static final int DB_VERSION = 1;  
	String[] COLS = {"keyID","keyValue", "keyType"};
	private DBHelper helper;
	
	public KeyInfoDao(Context context){
		helper = new DBHelper(context,DB_NAME,null,DB_VERSION);
	}
	
	public Key getKeyInfoByType(String type){
		SQLiteDatabase db = helper.getReadableDatabase();
		Key keyInfo = null;
		Cursor cursor = db.query(TABLE_KeyInfo, COLS, "keyType=?", new String[]{type}, null, null, null);
		if(cursor.moveToNext())
		{
			keyInfo = new Key();
			keyInfo.setKeyId(cursor.getInt(0));
			keyInfo.setKeyValue(cursor.getString(1));
			keyInfo.setKeyType(cursor.getString(2));
		}
		db.close();
		return keyInfo;
	}
	
	public List<Key> getAllKeyInfo(){
		List<Key> keyInfoList = new ArrayList<Key>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_KeyInfo, COLS, null, null, null, null, null);
		while(cursor.moveToNext())
		{
			Key keyInfo = new Key();
			keyInfo.setKeyId(cursor.getInt(0));
			keyInfo.setKeyValue(cursor.getString(1));
			keyInfo.setKeyType(cursor.getString(2));
			keyInfoList.add(keyInfo);
		}
		db.close();
		return keyInfoList;
	}
	
	public boolean insertKey(Key key)
	{
		SQLiteDatabase db = helper.getReadableDatabase();
		
		// 先去重复再插入：
		Cursor cursor = db.query(TABLE_KeyInfo, COLS, "keyValue=?", new String[]{key.getKeyValue()}, null, null, null);
		if(cursor.getCount()==0)
		{
			ContentValues values = new ContentValues();
			values.put("keyValue", key.getKeyValue());
			values.put("keyType", "custom");
			long number = db.insert(TABLE_KeyInfo, null, values);
			return (number>0);
		}
		else
		{
			return false;
		}
	}
	
	public boolean updateKey(Key key)
	{
		SQLiteDatabase db = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("keyValue", key.getKeyValue());
		long number = db.update(TABLE_KeyInfo, values, "keyID=?", new String[]{String.valueOf(key.getKeyId())});
		return (number>0);
	}
	
	public boolean deleteKey(Key key)
	{
		SQLiteDatabase db = helper.getReadableDatabase();
		long number = db.delete(TABLE_KeyInfo, "keyID=?", new String[]{String.valueOf(key.getKeyId())});
		return (number>0);
	}
}






