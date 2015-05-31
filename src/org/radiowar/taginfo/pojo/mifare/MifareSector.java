package org.radiowar.taginfo.pojo.mifare;

import org.radiowar.taginfo.util.Converter;

public class MifareSector implements java.io.Serializable{
	
	/**
	 * default block size is 4.
	 */
	public final static int BLOCKCOUNT=4;
	
	/**
	 * the index of the sector.
	 */
	public int sectorIndex;
	
	/**
	 * blocks in this sector.
	 */
	public MifareBlock[] blocks=new MifareBlock[BLOCKCOUNT];
	
	/**
	 * key A for this sector.
	 */
	public MifareKey keyA;
	
	/**
	 * key B for this sector.
	 */
	public MifareKey keyB;
	
	/**
	 * access condition for this sector.
	 */
	public String accessCondition;
	
	
	public boolean authorized;
	
	public String getBlockData(){
		
		StringBuilder sb = new StringBuilder();
		
		// 判断读取是否成功
		if(this.authorized){
			for(int i=0;i<blocks.length;i++){
				sb.append(Converter.getHexString(blocks[i].getData(), 16));
				
				// 不是最后一项则添加"\n"换行
				if(i!=blocks.length-1)
					sb.append("\n");
			}
		}
		else{
			for(int i=0;i<blocks.length;i++){
				sb.append("Read failed!");
				
				// 不是最后一项则添加"\n"换行
				if(i!=blocks.length-1)
					sb.append("\n");
			}
		}
		
		return sb.toString();
	}
	
}
