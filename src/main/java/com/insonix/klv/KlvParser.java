/**
 * 
 */
package com.insonix.klv;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.codice.ddf.libs.klv.KlvContext;
import org.codice.ddf.libs.klv.KlvDataElement;
import org.codice.ddf.libs.klv.KlvDecoder;
import org.codice.ddf.libs.klv.KlvDecodingException;
import org.codice.ddf.libs.klv.data.Klv.KeyLength;
import org.codice.ddf.libs.klv.data.Klv.LengthEncoding;
import org.codice.ddf.libs.klv.data.numerical.KlvByte;
import org.codice.ddf.libs.klv.data.numerical.KlvInt;
import org.codice.ddf.libs.klv.data.numerical.KlvLong;
import org.codice.ddf.libs.klv.data.numerical.KlvShort;
import org.codice.ddf.libs.klv.data.numerical.KlvUnsignedByte;
import org.codice.ddf.libs.klv.data.numerical.KlvUnsignedShort;
import org.codice.ddf.libs.klv.data.raw.KlvBytes;
import org.codice.ddf.libs.klv.data.set.KlvLocalSet;
import org.codice.ddf.libs.klv.data.text.KlvString;

/**
 * @author DELL PC
 *
 */
public class KlvParser {

	private static final String UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY = "UAS Datalink Local Set Universal Key";

	private static final String TIMESTAMP = "timestamp";

	private static final String UAS_LS_VERSION_NUMBER = "UAS LS Version Number";

	private static final String PLATFORM_HEADING_ANGLE = "platform heading angle";

	private static final String PLATFORM_PITCH_ANGLE = "platform pitch angle";

	private static final String PLATFORM_ROLL_ANGLE = "platform roll angle";

	private static final String IMAGE_SOURCE_SENSOR = "image source sensor";

	private static final String IMAGE_COORDINATE_SYSTEM = "image coordinate system";

	private static final String SENSOR_LATITUDE = "sensor latitude";

	private static final String SENSOR_LONGITUDE = "sensor longitude";

	private static final String SENSOR_TRUE_ALTITUDE = "sensor true altitude";

	private static final String SENSOR_HORIZONTAL_FOV = "sensor horizontal fov";

	private static final String SENSOR_VERTICAL_FOV = "sensor vertical fov";

	private static final String SENSOR_RELATIVE_AZIMUTH_ANGLE = "sensor relative azimuth angle";

	private static final String SENSOR_RELATIVE_ELEVATION_ANGLE = "sensor relative elevation angle";

	private static final String SENSOR_RELATIVE_ROLL_ANGLE = "sensor relative roll angle";

	private static final String SLANT_RANGE = "slant range";

	private static final String TARGET_WIDTH = "target width";

	private static final String FRAME_CENTER_LATITUDE = "frame center latitude";

	private static final String FRAME_CENTER_LONGITUDE = "frame center longitude";

	private static final String FRAME_CENTER_ELEVATION = "frame center elevation";

	private static final String TARGET_LOCATION_LATITUDE = "target location latitude";

	private static final String TARGET_LOCATION_LONGITUDE = "target location longitude";

	private static final String TARGET_LOCATION_ELEVATION = "target location elevation";

	private static final String PLATFORM_GROUND_SPEED = "platform ground speed";

	private static final String GROUND_RANGE = "ground range";

	private static final String CHECKSUM = "checksum";

	// private static final Map<String, Object> EXPECTED_VALUES = new HashMap<>();

	private static final Set<KlvDataElement> DATA_ELEMENTS = new HashSet<>();

	public static void init() {

		DATA_ELEMENTS.add(new KlvLong(new byte[] { 0x02 }, TIMESTAMP));
		DATA_ELEMENTS.add(new KlvByte(new byte[] { 0x41 }, UAS_LS_VERSION_NUMBER));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x05 }, PLATFORM_HEADING_ANGLE));
		DATA_ELEMENTS.add(new KlvShort(new byte[] { 0x06 }, PLATFORM_PITCH_ANGLE));
		DATA_ELEMENTS.add(new KlvShort(new byte[] { 0x07 }, PLATFORM_ROLL_ANGLE));
		DATA_ELEMENTS.add(new KlvString(new byte[] { 0x0b }, IMAGE_SOURCE_SENSOR));
		DATA_ELEMENTS.add(new KlvString(new byte[] { 0x0c }, IMAGE_COORDINATE_SYSTEM));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x0d }, SENSOR_LATITUDE));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x0e }, SENSOR_LONGITUDE));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x0f }, SENSOR_TRUE_ALTITUDE));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x10 }, SENSOR_HORIZONTAL_FOV));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x11 }, SENSOR_VERTICAL_FOV));
		DATA_ELEMENTS.add(new KlvLong(new byte[] { 0x12 }, SENSOR_RELATIVE_AZIMUTH_ANGLE));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x13 }, SENSOR_RELATIVE_ELEVATION_ANGLE));
		DATA_ELEMENTS.add(new KlvLong(new byte[] { 0x14 }, SENSOR_RELATIVE_ROLL_ANGLE));
		DATA_ELEMENTS.add(new KlvLong(new byte[] { 0x15 }, SLANT_RANGE));
		// Target width isn't actually a 32-bit int in the UAS Datalink Local Set; it's
		// an unsigned
		// 16-bit int. However, this KLV encodes the target width using 4 bytes (for
		// some reason).
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x16 }, TARGET_WIDTH));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x17 }, FRAME_CENTER_LATITUDE));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x18 }, FRAME_CENTER_LONGITUDE));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x19 }, FRAME_CENTER_ELEVATION));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x28 }, TARGET_LOCATION_LATITUDE));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x29 }, TARGET_LOCATION_LONGITUDE));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x2a }, TARGET_LOCATION_ELEVATION));
		DATA_ELEMENTS.add(new KlvUnsignedByte(new byte[] { 0x38 }, PLATFORM_GROUND_SPEED));
		DATA_ELEMENTS.add(new KlvLong(new byte[] { 0x39 }, GROUND_RANGE));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x01 }, CHECKSUM));
	}

	private static KlvContext getKLVContext(final Set<? extends KlvDataElement> dataElements) {
		final KlvContext localSetContext = new KlvContext(KeyLength.OneByte, LengthEncoding.OneByte, dataElements);

		final KlvLocalSet outerSet = new KlvLocalSet(new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E,
				0x01, 0x03, 0x01, 0x01, 0x00, 0x00, 0x00 }, UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY, localSetContext);

		final Set<KlvDataElement> outerSetContext = Collections.singleton(outerSet);

		return new KlvContext(KeyLength.SixteenBytes, LengthEncoding.BER, outerSetContext);
	}

	/**
	 * Parses KLV encoded file
	 * 
	 * @param klvBytes
	 *            of one packet
	 * @throws KlvDecodingException 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String parseKLVSet(final byte[] klvBytes) throws KlvDecodingException  {
		// byte[] klvBytes;

		// try (final InputStream inputStream =
		// getClass().getClassLoader().getResourceAsStream(filePath)) {
		// klvBytes = IOUtils.toByteArray(inputStream);
		// }

		final KlvContext klvContext = getKLVContext(DATA_ELEMENTS);

		final Map<String, KlvDataElement> decodedDataElements = new KlvDecoder(klvContext).decode(klvBytes)
				.getDataElements();

		System.out.println("Map length "+decodedDataElements.size());
		System.out.println("keys set decoded "+decodedDataElements.keySet());
		
		
		
//		final KlvContext localSet = ((KlvLocalSet) decodedDataElements.values().iterator().next())
//				.getValue();
		
		final KlvContext localSet = ((KlvLocalSet) decodedDataElements.get(UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY))
				.getValue();

		System.out.println("localset "+localSet);
		
		
		//System.out.println("values "+decodedDataElements.values().iterator().next());
		
		StringBuilder sb = new StringBuilder("<hr><br/> ");
		//if (localSet != null && localSet.getDataElements() != null) {
			final Map<String, KlvDataElement> localSetDataElements = localSet.getDataElements();
			System.out.println("localSetDataElements  "+localSetDataElements.keySet());	
		localSetDataElements.forEach((name, dataElement) -> {
			System.out.println(dataElement.getName() + "  : " + dataElement.getValue());
			sb.append("<b>"+dataElement.getName() + "  :</b> " + dataElement.getValue()+"<br/>");
		});
		//}
		return sb.toString();
	}

	private static KlvContext decodeKLV(final KeyLength keyLength, final LengthEncoding lengthEncoding,
			final KlvDataElement dataElement, final byte[] encodedBytes) throws KlvDecodingException {
		final KlvContext klvContext = new KlvContext(keyLength, lengthEncoding);
		klvContext.addDataElement(dataElement);
		return new KlvDecoder(klvContext).decode(encodedBytes);
	}

	private byte[] getValueBytes(final KeyLength keyLength, final LengthEncoding lengthEncoding,
			final byte[] encodedBytes) throws KlvDecodingException {
		final byte[] key = Arrays.copyOf(encodedBytes, keyLength.value());
		final KlvBytes dataElement = new KlvBytes(key, "test");
		final KlvContext decodedKlvContext = decodeKLV(keyLength, lengthEncoding, dataElement, encodedBytes);
		return ((KlvBytes) decodedKlvContext.getDataElementByName("test")).getValue();
	}

}
