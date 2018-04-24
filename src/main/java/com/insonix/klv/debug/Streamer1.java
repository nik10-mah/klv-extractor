package com.insonix.klv.debug;

import java.io.File;
import java.io.FileInputStream;

import com.insonix.klv.KlvParser;
import com.insonix.klv.utils.IConstants;
import com.insonix.klv.utils.Utilities;

public class Streamer1 {
	private static LocalThread t = null;

	private static int packetLength = -1;
	
	private static final byte[] UAS = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x01, 0x00, 0x00, 0x00};
	
	private static final byte FIRST_BYTE_LEN = (byte)0x08;

	public static void main(String[] args) throws Exception {
		int defaultSize = 188;
		String klvFilePath = IConstants.KLV_FILE_PATH;
		KlvParser.init();
		while (true) {
			if (new File(klvFilePath).exists()) {
				if (new File(klvFilePath).length() > defaultSize) {
					int total = 0;
					while (true) {
//						System.out.println("packet len == " + packetLength);
					if (packetLength == -1) {
						byte[] chunk = new byte[defaultSize];
						FileInputStream fis = new FileInputStream(klvFilePath);
						fis.read(chunk);
						//packetLength = calculatePacketLength(chunk);
						packetLength = calculatePayLoadLength(chunk);
						fis.close();
					} else {
						byte[] packet = new byte[packetLength];
						
							FileInputStream fis = new FileInputStream(klvFilePath);
							packet = new byte[packetLength];
							fis.skip(total);
							if (fis.available()>0) {
								fis.read(packet);								
								System.out.println("packet last elem == " + Byte.toUnsignedInt(packet[packetLength-1]));
								if(packet[packetLength-1] ==0) {
									actualLength(packet);
								}else {
									KlvParser.parseKLVSet(packet);
									//Main.test(packet);
									total += packetLength;
									packetLength = calculatePayLoadLength(packet);
								}
								
								
							}
							fis.close();
							Thread.sleep(500);
						}
						// fis.close();
					}
				}
				Thread.sleep(1000);
			}
			Thread.sleep(1000);
		}
	}

	
	public static int calculatePayLoadLength(byte[] chunk) {
		int keyLength = 16;
		int payLoad = 0;
		byte[] streamKey = new byte[keyLength];
		for (int i = 0; i < keyLength; ++i) {
			streamKey[i] = chunk[i];
		}
		for (int i = keyLength; i < chunk.length; ++i) {
			if (streamKey[0] == chunk[i]) {
				boolean match = true;
				//int j=0;
				for (int j = 0; j < 16; ++j) {
//					System.out.println("-------------"+i+" + "+j+"---"+(i + j));
					if (streamKey[j] != chunk[i + j]) {
						match = false;
					}
				}
				if (match) {
					// find first byte of lenth
					byte len =   chunk[i + keyLength + 1];
					
					if(len < FIRST_BYTE_LEN) {
						payLoad = keyLength + 1 + Byte.toUnsignedInt(len);
					}else {
						payLoad = keyLength + 2 + Byte.toUnsignedInt(chunk[i + keyLength + 2]);
					}
					
					return payLoad+1;
				}
			}
		}
		return -1;
	}
	
	public static int calculatePacketLength(byte[] chunk) {
		int keyLength = 16;
		byte[] streamKey = new byte[keyLength];
		for (int i = 0; i < keyLength; ++i) {
			streamKey[i] = chunk[i];
		}
		for (int i = keyLength; i < chunk.length; ++i) {
			if (streamKey[0] == chunk[i]) {
				boolean match = true;
				for (int j = 0; j < 16; ++j) {
					if (streamKey[j] != chunk[i + j]) {
						match = false;
					}
				}
				if (match) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private static final void actualLength(byte[] packet) {
		
		int count = 0;
		for (int index = 0; index < packet.length; index++) 
	    {
	        if(Byte.toUnsignedInt(packet[index]) != 0)
	        {
	            count++;
	        }
	    }
	
	System.out.println("used : "+ count +" : slots "+packet.length);
	}

}
