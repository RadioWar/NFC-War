package org.radiowar.taginfo.pojo.mifare;

import android.nfc.tech.MifareClassic;

public class MifareKey implements java.io.Serializable{

	public MifareKey(byte[] keyValue) {
		if (keyValue == null || keyValue.length != 6) {
			throw new IllegalArgumentException("Invaid key");
		}
		key=new byte[6];
		System.arraycopy(keyValue, 0,key, 0, key.length);

	}
	
	
	public MifareKey() {
		key=MifareClassic.KEY_DEFAULT;

	}
	
	public byte[] getKey(){
		return key;
	}
	
	public void setKey(byte[] keyValue){
		if (keyValue == null || keyValue.length != 6) {
			throw new IllegalArgumentException("Invaid key");
		}
		key=new byte[6];
		System.arraycopy(keyValue, 0,key, 0, key.length);
	}

	private byte[] key;
}
