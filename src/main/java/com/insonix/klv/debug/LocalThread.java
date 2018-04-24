package com.insonix.klv.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.insonix.klv.utils.IConstants;

public class LocalThread {
	
	public static void main(String[] args) {
		
		Process pr=null;
		try {
			pr = Runtime.getRuntime().exec(IConstants.FFMPEG_EXTRACT_KLV);
			System.out.println("cmd "+IConstants.FFMPEG_EXTRACT_KLV);
			//ffmpeg -i udp://@127.0.0.1:2000 -map data-re -codec copy -f data "E:\TIMESHEETS\output12.klv"
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			pr.waitFor();
			System.out.println("ok!");
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pr.destroy();
		}
		
	}

}
