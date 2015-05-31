package org.radiowar.taginfo.pojo.mifare;

import java.util.HashMap;

import org.radiowar.taginfo.pojo.mifare.MifareSector;
import org.radiowar.taginfo.util.Converter;

import android.util.Log;

public class MifareClassCard implements java.io.Serializable {

	private HashMap<String,String> values;
	
	public MifareClassCard(int sectorSize){
		sectors=new MifareSector[sectorSize];
		SECTORCOUNT=sectorSize;
		initializeCard();
	}
	
	public void initializeCard(){
		for(int i=0;i<SECTORCOUNT;i++){
			sectors[i]=new MifareSector();
			sectors[i].sectorIndex=i;
			sectors[i].keyA=new MifareKey();
			sectors[i].keyB=new MifareKey();
			for(int j=0;j<4;j++){
				sectors[i].blocks[j]=new MifareBlock(sectors[i]);
				sectors[i].blocks[j].setIndex(i*4+j);
			}
		}
	}
	
	public MifareSector getSector(int index){
		if(index>=SECTORCOUNT){
			throw new IllegalArgumentException("Invaid index for sector"); 
		}
		return sectors[index];
	}
	
	public void setSector(int index, MifareSector sector) {
		if (index >= SECTORCOUNT) {
			throw new IllegalArgumentException("Invaid index for sector");
		}
		sectors[index]=sector;
	}
	
	public int getSectorCount(){
		return SECTORCOUNT;
		
	}
	
	
	public void setSectorCount(int newCount){
		if(SECTORCOUNT<newCount){
			sectors=new MifareSector[newCount];
			initializeCard();
		}
		SECTORCOUNT=newCount;
		
	}
	
	public void debugPrint() {
		int blockIndex=0;
		for (int i = 0; i < SECTORCOUNT; i++) {
			MifareSector sector = sectors[i];
			
			if (sector != null) {
				Log.i(TAG, "Sector " + i);
				for (int j = 0; j < MifareSector.BLOCKCOUNT; j++) {
					MifareBlock block = sector.blocks[j];
					if(block!=null){
						byte[] raw = block.getData();
						String hexString = "  Block "+ j +" "
								+Converter.getHexString(raw, raw.length)
								+"  ("+blockIndex+")";
						Log.i(TAG, hexString);
					}
					blockIndex++;

				}
			}
		}
	}
	
	public void addValue(String key,String value){
		if(this.values==null)
			values = new HashMap<String,String>();
		values.put(key, value);
	}
	
	public HashMap<String,String> getValues(){
		return this.values;
	}
	
	/**
	 * the size of the sector.
	 */
	private int SECTORCOUNT=16;
	
	/**
	 * Log TAG.
	 */
	protected String TAG="MifareCardInfo";
	
	/**
	 * sectors.
	 */
	private MifareSector[] sectors;
	
}
