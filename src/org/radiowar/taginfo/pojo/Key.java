package org.radiowar.taginfo.pojo;

public class Key implements java.io.Serializable{
	private int keyId;
	private String keyValue;
	private String keyType;
	
	public Key(){
		// done
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		// 输入不足12位则补齐
    	if(keyValue.length()<12){
    		for(int i=0;keyValue.length()!=12;i++)
    		{
    			keyValue += "0";
    		}
    	}
		this.keyValue = keyValue.toUpperCase();
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	
	
}
