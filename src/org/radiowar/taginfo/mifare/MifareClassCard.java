//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 13SEP2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package org.radiowar.taginfo.mifare;

//--------------------------------- IMPORTS ------------------------------------
import org.radiowar.taginfo.util.Converter;

import android.util.Log;


//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//13SEP2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* This class stands for mifare class card.
* <hr>
* <b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* 
* @version 1.00, 13/09/11
* @author Guidebee Pty Ltd.
*/
public class MifareClassCard implements java.io.Serializable {
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructor.
	 * @param sectorSize size of the sectors.
	 */
	public MifareClassCard(int sectorSize){
		sectors=new MifareSector[sectorSize];
		SECTORCOUNT=sectorSize;
		initializeCard();
	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * initial card 
	 */
	public void initializeCard(){
		for(int i=0;i<SECTORCOUNT;i++){
			sectors[i]=new MifareSector();
			sectors[i].sectorIndex=i;
			sectors[i].keyA=new MifareKey();
			sectors[i].keyB=new MifareKey();
			for(int j=0;j<4;j++){
				sectors[i].blocks[j]=new MifareBlock();
				sectors[i].blocks[j].blockIndex=i*4+j;
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * get sector at given index.
	 * @param index the index of the sector.
	 * @return the sector at given index;
	 */
	public MifareSector getSector(int index){
		if(index>=SECTORCOUNT){
			throw new IllegalArgumentException("Invaid index for sector"); 
		}
		return sectors[index];
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* set sector at given index.
	* @param index the index of the sector.
	*/
	public void setSector(int index, MifareSector sector) {
		if (index >= SECTORCOUNT) {
			throw new IllegalArgumentException("Invaid index for sector");
		}
		sectors[index]=sector;
	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * get the count of the sector.
	 * @return the count of the sector.
	 */
	public int getSectorCount(){
		return SECTORCOUNT;
		
	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * set the new sector count
	 * @param newCount net sector count
	 */
	public void setSectorCount(int newCount){
		if(SECTORCOUNT<newCount){
			sectors=new MifareSector[newCount];
			initializeCard();
		}
		SECTORCOUNT=newCount;
		
	}

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * debug print information.
	 */
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
