/**
 * 
 */
package com.insonix.klv.debug;

import java.io.File;
import java.io.FileInputStream;

import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import com.insonix.klv.KlvParser;
import com.insonix.klv.utils.IConstants;
import com.insonix.klv.utils.Utilities;

/**
 * @author Nikhil Mahajan
 *
 */
public class Streamer {

	private static int packetLength = -1;
	

	public static void main(String[] args) throws Exception {

		int defaultPacketLength = 200;
		String klvFilePath = IConstants.KLV_FILE_PATH;
		System.out.println("KLV Read Started");
		KlvParser.init();
		
		try {
			
			while (true) {
		//		Thread.sleep(5000);
				if (new File(klvFilePath).exists()) {
					if (new File(klvFilePath).length() > defaultPacketLength) {
						if (packetLength == -1) {
							byte[] chunk = new byte[defaultPacketLength];
							FileInputStream fis = new FileInputStream(klvFilePath);
							fis.read(chunk);
							packetLength = calculatePacketLength(chunk);
							System.out.println("Packet length ==> "+packetLength);
							fis.close();
						} else {
							byte[] packet = new byte[packetLength];
							int total = 0;
							while (true) {
								FileInputStream fis = new FileInputStream(klvFilePath);
								packet = new byte[packetLength];
								fis.skip(total);
								if (fis.available() > 0) {
									fis.read(packet);
									System.out.println(Utilities.bytesToHexString(packet));
									// Main.test(packet);
									String data = KlvParser.parseKLVSet(packet);
									System.out.println("data == "+data);
//									textArea.append(data);
									
									// for editorpane
//									HTMLEditorKit kit = (HTMLEditorKit)editorPane.getEditorKit();
//									HTMLDocument doc = (HTMLDocument)editorPane.getDocument();
//									kit.insertHTML(doc, doc.getEndPosition().getOffset()-1, data + "\n" , 1,0,null );
									
									total += packetLength;
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
			
			
		} catch (Exception e) {

			System.out.println("Exception thrpown in streames " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Calculates packet length in the stream
	 * 
	 * @param chunk
	 * @return Packet Length
	 */
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

}
