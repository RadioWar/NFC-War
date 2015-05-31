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

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//13SEP2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* This class stands for mifare block.
* <hr>
* <b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* 
* @version 1.00, 13/09/11
* @author Guidebee Pty Ltd.
*/
public class MifareBlock implements java.io.Serializable{
	
	/**
	 * the index of the block.
	 */
	public int blockIndex;
	
	/**
	 * need to read or not
	 */
	public boolean needRead=true;
	
	
	/**
	 * need to write or not
	 */
	public boolean needWrite=false;

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructor.
	 * @param dataValue the data value.
	 */
	public MifareBlock(byte[] dataValue) {
		if (dataValue == null || dataValue.length != 16) {
			throw new IllegalArgumentException("Invaid data array");
		}
		System.arraycopy(dataValue, 0, data, 0, dataValue.length);

	}
	
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Default constructor.
	 * 
	 */
	public MifareBlock() {

	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Get the data value.
	 * @return the byte array of the block.
	 */
	public byte[] getData(){
		return data;
	}

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Set block data 
	 * @param dataValue new block data
	 */
	public void setData(byte[] dataValue){
		if (dataValue == null || dataValue.length != 16) {
			throw new IllegalArgumentException("Invaid data array");
		}
		System.arraycopy(dataValue, 0, data, 0, dataValue.length);
	}
	
	/**
	 * this is the data values (6 bytes)
	 */
	private final byte[] data=new byte[16];
}
