/**
 * 
 */
package com.insonix.klv.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.insonix.klv.utils.IConstants;

/**
 * @author Nikhil Mahajan
 * 
 * @since Apr 21, 2018
 */
public class KlvWrite implements Runnable {

	private String udpUrl;

	public KlvWrite(String udpUrl) {
		this.udpUrl = udpUrl;
	}

	@Override
	public void run() {
		final String FFMPEG_EXTRACT_KLV = "ffmpeg -i " + udpUrl + " -map data-re -codec copy -f data \""
				+ IConstants.KLV_FILE_PATH + "\"";
		Process pr = null;
		try {
			pr = Runtime.getRuntime().exec(FFMPEG_EXTRACT_KLV);
			System.out.println("cmd " + FFMPEG_EXTRACT_KLV);
			// ffmpeg -i udp://@127.0.0.1:2000 -map data-re -codec copy -f data
			// "E:\TIMESHEETS\output12.klv"
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			
			while ((line = in.readLine()) != null) {
				Thread.sleep(1000);
				//System.out.println(line);
			}
			pr.waitFor();
			System.out.println("ok!");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception thrpown in KlvRead IO Exception" + e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception thrpown in KlvRead " + e.getMessage());
		} finally {
			pr.destroy();
		}

	}

}
