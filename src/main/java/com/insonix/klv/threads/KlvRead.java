/**
 * 
 */
package com.insonix.klv.threads;

import java.io.File;
import java.io.FileInputStream;

import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import com.insonix.klv.KlvParser;
import com.insonix.klv.utils.IConstants;

/**
 * @author Nikhil Mahajan
 *
 */
public class KlvRead implements Runnable {

	private static int packetLength = -1;
	
	private JTextArea textArea;
	
	private JEditorPane editorPane;

	public KlvRead(JEditorPane editorPane) {
		super();
//		this.textArea = textArea;
		this.editorPane = editorPane;
	}

	@Override
	public void run() {

		int defaultPacketLength = 188;
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
									// Main.test(packet);
									String data = KlvParser.parseKLVSet(packet);
//									textArea.append(data);
									
									// for editorpane
									HTMLEditorKit kit = (HTMLEditorKit)editorPane.getEditorKit();
									HTMLDocument doc = (HTMLDocument)editorPane.getDocument();
									kit.insertHTML(doc, doc.getEndPosition().getOffset()-1, data + "\n" , 1,0,null );
									
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

			System.out.println("Exception thrpown in KlvRead " + e.getMessage());
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
