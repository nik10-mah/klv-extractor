/**
 * 
 */
package com.insonix.klv.utils;

/**
 * @author Nikhil Mahajan
 *
 */
public interface IConstants {

	public static final String KLV_FILE_PATH = "E:\\Projects\\klv_kennedy\\klvs\\output12.klv";
	
	public static final String FFMPEG_EXTRACT_KLV = "ffmpeg -i udp://@127.0.0.1:2000 -map data-re -codec copy -f data \""+IConstants.KLV_FILE_PATH+"\"";
	
}
