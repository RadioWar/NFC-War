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
package org.radiowar.taginfo.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//13SEP2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* Convert help class.
* <hr>
* <b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* 
* @version 1.00, 13/09/11
* @author Guidebee Pty Ltd.
*/
public class Converter {

	// Hex help
	private static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1',
			(byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
			(byte) '7', (byte) '8', (byte) '9', (byte) 'A', (byte) 'B',
			(byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F' };

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * convert a byte arrary to hex string
	 * @param raw byte arrary
	 * @param len lenght of the arrary.
	 * @return hex string.
	 */
	public static String getHexString(byte[] raw, int len) {
		byte[] hex = new byte[2 * len];
		int index = 0;
		int pos = 0;

		for (byte b : raw) {
			if (pos >= len)
				break;

			pos++;
			int v = b & 0xFF;
			hex[index++] = HEX_CHAR_TABLE[v >>> 4];
			hex[index++] = HEX_CHAR_TABLE[v & 0xF];
		}

		return new String(hex);
	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 13SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * calculate the MD5 hash value.
	 * @param input input bytes
	 * @return MD5 hash value.
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] calculateHash(byte[] input)
			throws NoSuchAlgorithmException {
		MessageDigest digester = MessageDigest.getInstance("MD5");
		digester.update(input, 0, input.length);
		byte[] digest = digester.digest();
		return digest;
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    s = s.replace(" ", "").trim();
	    //Log.d("NFC", "hexStringToByteArray.s=" + s);
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	              + Character.digit(s.charAt(i + 1), 16));
	    }
	    return data;
	  }
	
	public static String getNextKey(String keyA)
	{
		BigInteger number = new BigInteger(keyA, 16);
		BigInteger number2 = new BigInteger("1", 16);
		number = number.add(number2);
		return addZero(number.toString(16));
	}
	
	private static String addZero(String number)
	{
		if(number.length()<12)
		{
			int count = 12-number.length();
			String temp = "";
			for(int i=0;i<count;i++){
				temp+="0";
			}
			return temp+number;
		}
		else
		{
			return number;
		}
	}
	
	public static String getHexString(byte[] b) {  
       String result = "";  
       for (int i=0; i < b.length; i++) {  
         result +=  
               Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );  
       }  
       return result;  
     }
}
