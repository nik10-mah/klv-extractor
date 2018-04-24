package com.insonix.klv;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.commons.io.IOUtils;
import org.codice.alliance.libs.stanag4609.DecodedKLVMetadataPacket;
import org.codice.alliance.libs.stanag4609.Stanag4609TransportStreamParser;
import org.codice.ddf.libs.klv.KlvContext;
import org.codice.ddf.libs.klv.KlvDataElement;
import org.codice.ddf.libs.klv.data.set.KlvLocalSet;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.io.ByteSource;

public class Stanag4609TransportStreamParserTest {
  private static final Map<String, Object> EXPECTED_VALUES = new HashMap<>();

  @BeforeClass
  public static void setUpClass() {
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.TIMESTAMP, 1245257585099653L);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.IMAGE_SOURCE_SENSOR, "EON");
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.IMAGE_COORDINATE_SYSTEM, "Geodetic WGS84");
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.SENSOR_LATITUDE, 54.681323);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.SENSOR_LONGITUDE, -110.168559);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.SENSOR_TRUE_ALTITUDE, 1532.272831);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.SLANT_RANGE, 10928.624544);

    // In this test file, target width is encoded in 4 bytes, but the standard says it should be
    // encoded in 2.
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.TARGET_WIDTH, 0.0);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.FRAME_CENTER_LATITUDE, 54.749123);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.FRAME_CENTER_LONGITUDE, -110.046638);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.FRAME_CENTER_ELEVATION, -4.522774);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.TARGET_LOCATION_LATITUDE, 54.749123);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.TARGET_LOCATION_LONGITUDE, -110.046638);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.TARGET_LOCATION_ELEVATION, -4.522774);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.GROUND_RANGE, 10820.674945);
    EXPECTED_VALUES.put(Stanag4609TransportStreamParser.CHECKSUM, 7263);
  }

  private Stanag4609TransportStreamParser getParser() throws IOException {
//    final ByteSource byteSource =
//        ByteSource.wrap(
//            IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("CM160_Jackson_Hole_1.ts")));
	  
	  InputStream input = new URL("udp://@127.0.0.1:2000").openStream();
	  final ByteSource byteSource =  ByteSource.wrap(IOUtils.toByteArray(input));

    return new Stanag4609TransportStreamParser(byteSource);
  }

  //@Test
  public void testParseTransportStreamWithKLVCallback() throws Exception {
    final Stanag4609TransportStreamParser parser = getParser();

    // Mockito can't spy anonymous classes.
    final BiConsumer<Integer, DecodedKLVMetadataPacket> callback =
        new BiConsumer<Integer, DecodedKLVMetadataPacket>() {
          @Override
          public void accept(Integer integer, DecodedKLVMetadataPacket decodedKLVMetadataPacket) {}
        };
    final BiConsumer<Integer, DecodedKLVMetadataPacket> callbackSpy = spy(callback);

    parser.parse(callbackSpy);

    final ArgumentCaptor<DecodedKLVMetadataPacket> decodedPacketCaptor =
        ArgumentCaptor.forClass(DecodedKLVMetadataPacket.class);
    // The packet ID of the metadata stream in this file is 497.
    verify(callbackSpy, times(1)).accept(eq(497), decodedPacketCaptor.capture());
    verifyDecodedMetadataPacket(decodedPacketCaptor.getValue());
  }

  //@Test
  public void testParseTransportStreamWithKLVAll() throws Exception {
    final Stanag4609TransportStreamParser parser = getParser();

    final Map<Integer, List<DecodedKLVMetadataPacket>> decodedStreams = parser.parse();

    assertThat(decodedStreams.size(), is(1));
    // The packet ID of the metadata stream in this file is 497.
    assertThat(decodedStreams, hasKey(497));
    final List<DecodedKLVMetadataPacket> decodedPackets = decodedStreams.get(497);
    assertThat(decodedPackets.size(), is(1));
    verifyDecodedMetadataPacket(decodedPackets.get(0));
  }

  @Test
  public void printKlvData() throws Exception {
	  final Stanag4609TransportStreamParser parser = getParser();
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
  
  private void verifyDecodedMetadataPacket(final DecodedKLVMetadataPacket packet) {
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
          final Object expectedValue = EXPECTED_VALUES.get(name);
          final Object actualValue = dataElement.getValue();

          if (actualValue instanceof Double) {
//            assertThat(
//                String.format("%s is not close to %s", name, expectedValue),
//                (Double) actualValue,
//                is(closeTo((Double) expectedValue, 1e-6)));
        	  System.out.println(dataElement.getName() + " - "+(Double)actualValue);
          } else {
        	  System.out.println(dataElement.getName() +" - "+actualValue);
//            assertThat(
//                String.format("%s is not %s", name, expectedValue), actualValue, is(expectedValue));
          }
        });
  }
}
