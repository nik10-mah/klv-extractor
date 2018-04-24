/**
 * 
 */
package com.insonix.klv.debug;

import java.util.List;
import java.util.Map;

import org.codice.alliance.libs.stanag4609.DecodedKLVMetadataPacket;
import org.codice.alliance.libs.stanag4609.Stanag4609TransportStreamParser;
import org.codice.ddf.libs.klv.KlvContext;
import org.codice.ddf.libs.klv.KlvDataElement;
import org.codice.ddf.libs.klv.data.set.KlvLocalSet;

import com.google.common.io.ByteSource;

/**
 * @author Nikhil
 *
 */
public class Parser {

	public static void printKlvData(byte[] bytes) throws Exception {
		final Stanag4609TransportStreamParser parser = new Stanag4609TransportStreamParser(ByteSource.wrap(bytes));
		final Map<Integer, List<DecodedKLVMetadataPacket>> decodedStreams = parser.parse();
		System.out.println("size  streams " + decodedStreams.size());

		for (Map.Entry<Integer, List<DecodedKLVMetadataPacket>> entry : decodedStreams.entrySet()) {
			final List<DecodedKLVMetadataPacket> decodedPackets = entry.getValue();
			System.out.println("size  decodedPackets " + decodedPackets.size());
			for (DecodedKLVMetadataPacket decodedKLVMetadataPacket : decodedPackets) {
				printDecodedMetadata(decodedKLVMetadataPacket);
			}
		}
	}

	private static void printDecodedMetadata(final DecodedKLVMetadataPacket packet) {
		final KlvContext outerContext = packet.getDecodedKLV();
		
		System.out.println("=======================================================");
		System.out.println("                                                        ");
		
		final KlvContext localSetContext = ((KlvLocalSet) outerContext.getDataElementByName(Stanag4609TransportStreamParser.UAS_DATALINK_LOCAL_SET)).getValue();
		final Map<String, KlvDataElement> localSetDataElements = localSetContext.getDataElements();

		// assertThat(localSetDataElements.size(), is(EXPECTED_VALUES.size()));

		localSetDataElements.forEach((name, dataElement) -> {
			// final Object expectedValue = EXPECTED_VALUES.get(name);
			final Object actualValue = dataElement.getValue();

			if (actualValue instanceof Double) {
				System.out.println(dataElement.getName() + " - " + (Double) actualValue);
			} else {
				System.out.println(dataElement.getName() + " - " + actualValue);

			}
		});
	}

}
