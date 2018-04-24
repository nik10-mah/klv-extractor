/**
 * 
 */
package com.insonix.klv.debug;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.codice.alliance.libs.stanag4609.DecodedKLVMetadataPacket;
import org.codice.alliance.libs.stanag4609.Stanag4609TransportStreamParser;
import org.codice.ddf.libs.klv.KlvContext;
import org.codice.ddf.libs.klv.KlvDataElement;
import org.codice.ddf.libs.klv.data.set.KlvLocalSet;

import com.google.common.io.ByteSource;
import com.insonix.klv.KlvParser;
import com.insonix.klv.threads.KlvRead;
import com.insonix.klv.utils.Utilities;

/**
 * @author Nikhil Mahajan
 *
 */
public class UdpReaderOld {

	private static final String UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY = "06 0E 2B 34 02 0B 01 01 0E 01 03 01 01 00 00 00";

	private static final byte FIRST_BYTE_LEN = (byte)0x08;
	
	public static void main(String[] args) throws Exception {
		
		
		int packetSize = 183;
		// Step 1 : Create a socket to listen at port 1234
		DatagramSocket ds = new DatagramSocket(2000);
		byte[] receive = new byte[packetSize];

		DatagramPacket DpReceive = null;

		byte[] packet = {}; 
		List<DatagramPacket> aoDtp = new LinkedList<>();


		ds.setSoTimeout(30000);		
		boolean a  = true;
		KlvParser.init();
try {
	

		while (a) {

			// Step 2 : create a DatgramPacket to receive the data.
			DpReceive = new DatagramPacket(receive, receive.length);
			aoDtp.add(new DatagramPacket(receive, receive.length));

			// Step 3 : revieve the data in byte buffer.
			ds.receive(DpReceive);
			
			//byte[] subArray = Arrays.copyOfRange(receive, 10, 187);
			//System.out.println("-----Waiting for KLV------------------------------");
//			System.out.println("Client:-" + data(subArray).toString());

			
//			StringBuilder sb = new StringBuilder();
//			String hex = Utilities.bytesToHexString(receive);
			
			StringBuilder sb = Utilities.bytesToHexString(receive);
			String hex = sb.toString();
			if(hex.contains(UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY)) {
				int keyIndex = hex.indexOf(UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY)/3;
				
				byte[] subArray = Arrays.copyOfRange(receive,keyIndex , packetSize);
				 System.out.println("index "+keyIndex +" packet size  "+subArray.length); 
				 String[] arr = sb.toString().split("\\s");
				System.out.println(hex);
				
				System.out.println("sub arrray   == >"+Utilities.bytesToHexString(subArray));
//				 for (int i=0;i<arr.length;i++) {
//						System.out.println(i + "= "+arr[i]);
//				}
				int klvPackLen = calculatePacketLength(subArray);
					byte[] klvArray = Arrays.copyOfRange(subArray,0 , klvPackLen);
					System.out.println("klv array =>"+Utilities.bytesToHexString(klvArray));
//					Main.test(subArray);
					KlvParser.parseKLVSet(klvArray);
			}
			//System.out.println("hex "+sb);
			
			
//			for (int i=0;i<188;i++) {
////				System.out.println(i + "len "+Byte.toUnsignedLong(receive[i]));
//				System.out.println(i + "len "+Byte.toString(receive[i]));
//			}
			
			//System.out.println("getData "+Utilities.bytesToHexString(DpReceive.getData()));
			
//			DataInputStream din = new DataInputStream(new ByteArrayInputStream(DpReceive.getData(), DpReceive.getOffset(), DpReceive.getLength()));
//			System.out.println("receive len "+receive.length);
//			System.out.println("subArray len "+subArray.length);
			//printKlvData(receive);
			//Main.test(subArray);
//			packet = appendBytes(packet, IOUtils.toByteArray(din));
			
			
//			String str = Stanag4609TransportStreamParser.UAS_DATALINK_LOCAL_SET;
			//printKlvData(receive);
			// Exit the server if the client sends "bye"
//			if (data(receive).toString().equals("bye")) {
//				System.out.println("Client sent bye.....EXITING");
//				break;
//			}

			// Clear the buffer after every message.
			receive = new byte[packetSize];
		}
	}catch(SocketTimeoutException se ) {
		se.printStackTrace();
	}catch(Exception se ) {
		se.printStackTrace();
	}finally {
		ds.close();
		//printKlvData(packet);
		writeToFile(packet);
		
	}
		
	}


	private static void writeToFile(final byte[] bytes) throws IOException {
		FileUtils.writeByteArrayToFile(new File("video.mpg"), bytes);
	}
	public static StringBuilder data(byte[] a) {
		if (a == null)
			return null;
		StringBuilder ret = new StringBuilder();
		int i = 0;
		while (a[i] != 0) {
			ret.append((char) a[i]);
			i++;
		}
		return ret;
	}

	private static void printKlvData(byte[] bytes) throws Exception {
		  final Stanag4609TransportStreamParser parser = new Stanag4609TransportStreamParser(ByteSource.wrap(bytes));
		  final Map<Integer, List<DecodedKLVMetadataPacket>> decodedStreams = parser.parse();
		  System.out.println("size  streams "+decodedStreams.size());
		  
		  for (Map.Entry<Integer, List<DecodedKLVMetadataPacket>> entry : decodedStreams.entrySet()) {
			  final List<DecodedKLVMetadataPacket> decodedPackets =  entry.getValue();
			  System.out.println("size  decodedPackets "+decodedPackets.size());  
			  for (DecodedKLVMetadataPacket decodedKLVMetadataPacket : decodedPackets) {
				  verifyDecodedMetadataPacket(decodedKLVMetadataPacket);
			}
		}
	  }
	
	  private static void verifyDecodedMetadataPacket(final DecodedKLVMetadataPacket packet) {
		    final KlvContext outerContext = packet.getDecodedKLV();
		    //assertThat(outerContext.getDataElements().size(), is(1));

		    //assertThat(
		      //  outerContext.hasDataElement(Stanag4609TransportStreamParser.UAS_DATALINK_LOCAL_SET),
		        //is(true));
		    System.out.println("=======================================================");
		    System.out.println("                                                        ");
		    System.out.println("                                                        ");
		    System.out.println("                                                        ");
		    final KlvContext localSetContext =
		        ((KlvLocalSet)
		                outerContext.getDataElementByName(
		                    Stanag4609TransportStreamParser.UAS_DATALINK_LOCAL_SET))
		            .getValue();
		    final Map<String, KlvDataElement> localSetDataElements = localSetContext.getDataElements();

		    //assertThat(localSetDataElements.size(), is(EXPECTED_VALUES.size()));

		    localSetDataElements.forEach(
		        (name, dataElement) -> {
		   //       final Object expectedValue = EXPECTED_VALUES.get(name);
		          final Object actualValue = dataElement.getValue();

		          if (actualValue instanceof Double) {
//		            assertThat(
//		                String.format("%s is not close to %s", name, expectedValue),
//		                (Double) actualValue,
//		                is(closeTo((Double) expectedValue, 1e-6)));
		        	  System.out.println(dataElement.getName() + " - "+(Double)actualValue);
		          } else {
		        	  System.out.println(dataElement.getName() +" - "+actualValue);
//		            assertThat(
//		                String.format("%s is not %s", name, expectedValue), actualValue, is(expectedValue));
		          }
		        });
		  }
	  
	  
	  private static byte [] appendBytes(byte[] a, byte[] b) {
		  /// create a destination array that is the size of the two arrays
		  byte[] destination = new byte[a.length + b.length];

		  // copy a into start of destination (from pos 0, copy a.length bytes)
		  System.arraycopy(a, 0, destination, 0, a.length);

		  // copy b into end of destination (from pos a.length, copy a.length bytes)
		  System.arraycopy(b, 0, destination, a.length, b.length);
		  
		  return destination;
		  
	  }

	  
	  public static int calculatePacketLength(byte[] chunk) {
			int keyLength = 16;
			int payLoad = 0;
			
			byte len =   chunk[ keyLength + 1];
			if(len < FIRST_BYTE_LEN) {
				payLoad = keyLength + 1 + Byte.toUnsignedInt(len);
			}else {
				payLoad = keyLength + 2 + Byte.toUnsignedInt(chunk[ keyLength + 2]);
			}
			
			System.out.println("payload  "+payLoad);
			return payLoad+1;
		}	
}
