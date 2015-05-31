package org.radiowar.taginfo.application;

import java.util.ArrayList;
import java.util.List;

import org.radiowar.taginfo.pojo.mifare.MifareClassCard;

import android.app.Application;

public class TagInfoApp extends Application {

	ArrayList<MifareClassCard> history;
	
	@Override
    public void onCreate() {
		System.out.println("Application onCreate");
		history = new ArrayList<MifareClassCard>();
	}
}
