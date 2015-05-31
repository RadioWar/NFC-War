package org.radiowar.taginfo.pojo.mifare;

import android.annotation.SuppressLint;

public class MifareBlock implements java.io.Serializable{
	
	public static final String KEYA = "keyA";
	public static final String KEYB = "keyB";
	public static final String KEYAorKeyB = "keyA|keyB";
	public static final String NEVER = "never";
	public MifareSector parentSector;
	
	public MifareBlock(MifareSector _sector){
		this.parentSector = _sector;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getAccessRead() {
		return accessRead;
	}

	public void setAccessRead(String accessRead) {
		this.accessRead = accessRead;
	}

	public String getAccessWrite() {
		return accessWrite;
	}

	public void setAccessWrite(String accessWrite) {
		this.accessWrite = accessWrite;
	}

	public String getAccessIncrement() {
		return accessIncrement;
	}

	public void setAccessIncrement(String accessIncrement) {
		this.accessIncrement = accessIncrement;
	}

	public String getAccessDecrementTransferRestore() {
		return accessDecrementTransferRestore;
	}

	public void setAccessDecrementTransferRestore(
			String accessDecrementTransferRestore) {
		this.accessDecrementTransferRestore = accessDecrementTransferRestore;
	}

	public String getAccessReadAccessBits() {
		return accessReadAccessBits;
	}

	public void setAccessReadAccessBits(String accessReadAccessBits) {
		this.accessReadAccessBits = accessReadAccessBits;
	}

	public String getAccessWriteAccessBits() {
		return accessWriteAccessBits;
	}

	public void setAccessWriteAccessBits(String accessWriteAccessBits) {
		this.accessWriteAccessBits = accessWriteAccessBits;
	}

	public String getAccessReadKeyA() {
		return accessReadKeyA;
	}

	public void setAccessReadKeyA(String accessReadKeyA) {
		this.accessReadKeyA = accessReadKeyA;
	}

	public String getAccessWriteKeyA() {
		return accessWriteKeyA;
	}

	public void setAccessWriteKeyA(String accessWriteKeyA) {
		this.accessWriteKeyA = accessWriteKeyA;
	}

	public String getAccessReadKeyB() {
		return accessReadKeyB;
	}

	public void setAccessReadKeyB(String accessReadKeyB) {
		this.accessReadKeyB = accessReadKeyB;
	}

	public String getAccessWriteKeyB() {
		return accessWriteKeyB;
	}

	public void setAccessWriteKeyB(String accessWriteKeyB) {
		this.accessWriteKeyB = accessWriteKeyB;
	}

	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] dataValue){
		if (dataValue == null || dataValue.length != 16) {
			throw new IllegalArgumentException("Invaid data array");
		}
		System.arraycopy(dataValue, 0, data, 0, dataValue.length);
	}

	private int index;
	
	private final byte[] data=new byte[16]; 
	
	// data block
	private String accessRead;
	
	private String accessWrite;
	
	private String accessIncrement;
	
	private String accessDecrementTransferRestore;
	
	// trailer block
	private String accessReadAccessBits;
	
	private String accessWriteAccessBits;
	
	private String accessReadKeyA;
	
	private String accessWriteKeyA;
	
	private String accessReadKeyB;
	
	private String accessWriteKeyB;
}
