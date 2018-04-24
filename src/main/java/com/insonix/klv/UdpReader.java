/**
 * 
 */
package com.insonix.klv;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.codice.ddf.libs.klv.KlvDecodingException;

import com.insonix.klv.utils.Utilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

/**
 * @author Nikhil Mahajan
 * 
 * @since Apr 23, 2018
 */
/**
 * @author Nikhil Mahajan
 * 
 * @since Apr 24, 2018
 */
public class UdpReader implements Runnable {

	private static final String UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY = "06 0E 2B 34 02 0B 01 01 0E 01 03 01 01 00 00 00";

	private static final int packetSize = 183;

	private static final byte FIRST_BYTE_LEN = (byte) 0x08;

	private final JEditorPane editorPane;

	// private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

	private final int port;

	private int klvPackLen;

	private int exit = 1;

	public UdpReader(JEditorPane editorPane, int port) {
		this.editorPane = editorPane;
		this.port = port;
	}

	// public UdpReader(JEditorPane editorPane, int
	// port,EmbeddedMediaPlayerComponent mediaPlayerComponent) {
	// this.port = port;
	// this.editorPane = editorPane;
	// this.mediaPlayerComponent = mediaPlayerComponent;
	// // this.port = 2000;
	// }

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// UdpReader reader = new UdpReader(2000);
		// Thread t = new Thread(reader);
		// t.start();

	}

	@Override
	public void run() {

		// declare datagram variables needed
		DatagramSocket socket = null;
		DatagramPacket datagramPacket = null;
		byte[] mpegPacket = new byte[packetSize];

		// initialize klv fields
		KlvParser.init();

		try {

			// Create a socket to listen at port
			socket = new DatagramSocket(this.port);

			// receve data gram packet while fetch is true
			while (exit > 0) {
				// create a datagram packet to receive data in a byte array
				datagramPacket = new DatagramPacket(mpegPacket, mpegPacket.length);

				// Receive the data in byte buffer.
				socket.receive(datagramPacket);

				// convert byte array of received data to hex string for comparison
				String hexPacket = Utilities.bytesToHexString(mpegPacket).toString();

				// check if the MPEG packet contains KLV data
				if (hexPacket.contains(UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY)) {

					int keyIndex = hexPacket.indexOf(UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY) / 3;
					// Extract klv packet from MPEG packet
					byte[] subPacket = Arrays.copyOfRange(mpegPacket, keyIndex, packetSize);

					// calculate packet length only once
					if (exit == 1) {
						klvPackLen = calculatePacketLength(subPacket);
						exit++;
					}

					byte[] klvArray = Arrays.copyOfRange(subPacket, 0, klvPackLen);

					System.out.println("==============Next packet =============");
					System.out.println("                                       ");
					// send byte array to decode klv data to klv parser
					String decodedData = KlvParser.parseKLVSet(klvArray);
					writeToPane(decodedData);
					// TODO; write to database
					// TODO: use this byte packet array to pass to mediaPlayer to play video instead
					// of directly from URL so that we don;t have to publish stream on two urls

				}

				mpegPacket = new byte[packetSize];
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (KlvDecodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}

	}

	/**
	 * To update klv data on textarea on jPanel
	 * 
	 * @param data
	 *            to be write to text area
	 * @throws BadLocationException
	 * @throws IOException
	 */
	private void writeToPane(String data) throws BadLocationException, IOException {

		HTMLEditorKit kit = (HTMLEditorKit) editorPane.getEditorKit();
		HTMLDocument doc = (HTMLDocument) editorPane.getDocument();
		kit.insertHTML(doc, doc.getEndPosition().getOffset() - 1, data, 1, 0, null);

	}

	// private synchronized void playVideo(byte[] packet) {
	// String fileName = "temp.ts";
	// File tempFile = File.createTempFile("abc", suffix);
	// FileOutputStream fos = new FileOutputStream(tempFile);
	// fos.write(packet);
	// Media media = new RandomAccessFileMedia
	// }

	/**
	 * stop the thread
	 */
	public void stop() {
		this.exit = -1;

	}

	/**
	 * find pay load length from the packet chunk passed Payload = KeyLength +
	 * Packet Length where Packet length can be in 1st or 2nd byte
	 * 
	 * @param KLV
	 *            PES Packet
	 * @return payload length
	 */
	public static int calculatePacketLength(byte[] chunk) {
		int keyLength = 16;
		int payLoad = 0;

		byte len = chunk[keyLength + 1];
		// first byte aftre 16 bit uas key tells the length of packet. if its less tha
		// 128 then 2ng byte will tell the length
		if (len < FIRST_BYTE_LEN) {
			payLoad = keyLength + 1 + Byte.toUnsignedInt(len);
		} else {
			payLoad = keyLength + 2 + Byte.toUnsignedInt(chunk[keyLength + 2]);
		}

		System.out.println("payload  " + payLoad);
		return payLoad + 1;
	}

}
