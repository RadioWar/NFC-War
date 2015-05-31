package org.radiowar.taginfo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("onCreate");  
		database.execSQL("CREATE TABLE KeyInfo (keyID integer PRIMARY KEY AUTOINCREMENT, keyValue varchar(12,0), keyType varchar(50, 0))");  
		
		// init
		this.initDataBase(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		System.out.println("onUpgrade");  
		database.execSQL("DROP TABLE IF EXISTS KeyInfo");  
        onCreate(database);  
	}
	
	public void initDataBase(SQLiteDatabase database){
		System.out.println("init data");
		
		// insert default keys
		database.execSQL( "insert into KeyInfo (keyValue, keyType) values ('FFFFFFFFFFFF','default');" );
		database.execSQL( "insert into KeyInfo (keyValue, keyType) values ('B5FF67CBA951','default');" );
		database.execSQL( "insert into KeyInfo (keyValue, keyType) values ('000000000000','default');" );
		database.execSQL( "insert into KeyInfo (keyValue, keyType) values ('A0A1A2A3A4A5','default');" );
		database.execSQL( "insert into KeyInfo (keyValue, keyType) values ('B0B1B2B3B4B5','default');" );
		database.execSQL( "insert into KeyInfo (keyValue, keyType) values ('4D3A99C351DD','default');" );
		database.execSQL( "insert into KeyInfo (keyValue, keyType) values ('1A982C7E459A','default');" );
		database.execSQL( "insert into KeyInfo (keyValue, keyType) values ('AABBCCDDEEFF','default');" );
		database.execSQL( "insert into KeyInfo (keyValue, keyType) values ('EE9BD361B01B','default');" );
		
		}
}
