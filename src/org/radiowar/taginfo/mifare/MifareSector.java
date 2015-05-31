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
* This class stands for mifare sector.
* <hr>
* <b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* 
* @version 1.00, 13/09/11
* @author Guidebee Pty Ltd.
*/
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
	 * authorized or not.
	 */
	public boolean authorized;
	
	
}
