/**
 * 
 */
package com.insonix.klv.debug;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.insonix.klv.utils.IConstants;

/**
 * @author DELL PC
 *
 */
public class Demo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		
		byte[] myBuffer = new byte[163];
		int bytesRead = 0;
		FileInputStream fis = new FileInputStream(IConstants.KLV_FILE_PATH);
		while ((bytesRead = fis.read(myBuffer)) != -1)
		{
		 
			System.out.println("first " +Byte.toUnsignedInt(myBuffer[0]));
			System.out.println("second " +Byte.toUnsignedInt(myBuffer[1]));
			System.out.println("3rd " +Byte.toUnsignedInt(myBuffer[2]));
			System.out.println("last elem " +Byte.toUnsignedInt(myBuffer[myBuffer.length-1]));
			System.out.println("penultimate elem " +Byte.toUnsignedInt(myBuffer[myBuffer.length-2]));
			
			System.out.println("                        ");
		}

	}

}
