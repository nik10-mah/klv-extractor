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

import com.insonix.klv.model.KLVProperties;
import com.insonix.klv.threads.KlvDataSave;
import com.insonix.klv.utils.IConstants;
import com.insonix.klv.utils.KlvUtils;

/**
 * @author DELL PC
 *
 */
public class KlvParser {

	// private static final Map<String, Object> EXPECTED_VALUES = new HashMap<>();

	private static final Set<KlvDataElement> DATA_ELEMENTS = new HashSet<>();

	public static void init() {

		DATA_ELEMENTS.add(new KlvLong(new byte[] { 0x02 }, IConstants.TIMESTAMP));
		DATA_ELEMENTS.add(new KlvByte(new byte[] { 0x41 }, IConstants.UAS_LS_VERSION_NUMBER));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x05 }, IConstants.PLATFORM_HEADING_ANGLE));
		DATA_ELEMENTS.add(new KlvShort(new byte[] { 0x06 }, IConstants.PLATFORM_PITCH_ANGLE));
		DATA_ELEMENTS.add(new KlvShort(new byte[] { 0x07 }, IConstants.PLATFORM_ROLL_ANGLE));
		DATA_ELEMENTS.add(new KlvString(new byte[] { 0x0b }, IConstants.IMAGE_SOURCE_SENSOR));
		DATA_ELEMENTS.add(new KlvString(new byte[] { 0x0c }, IConstants.IMAGE_COORDINATE_SYSTEM));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x0d }, IConstants.SENSOR_LATITUDE));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x0e }, IConstants.SENSOR_LONGITUDE));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x0f }, IConstants.SENSOR_TRUE_ALTITUDE));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x10 }, IConstants.SENSOR_HORIZONTAL_FOV));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x11 }, IConstants.SENSOR_VERTICAL_FOV));
		DATA_ELEMENTS.add(new KlvLong(new byte[] { 0x12 }, IConstants.SENSOR_RELATIVE_AZIMUTH_ANGLE));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x13 }, IConstants.SENSOR_RELATIVE_ELEVATION_ANGLE));
		DATA_ELEMENTS.add(new KlvLong(new byte[] { 0x14 }, IConstants.SENSOR_RELATIVE_ROLL_ANGLE));
		DATA_ELEMENTS.add(new KlvLong(new byte[] { 0x15 }, IConstants.SLANT_RANGE));
		// Target width isn't actually a 32-bit int in the UAS Datalink Local Set; it's
		// an unsigned
		// 16-bit int. However, this KLV encodes the target width using 4 bytes (for
		// some reason).
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x16 }, IConstants.TARGET_WIDTH));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x17 }, IConstants.FRAME_CENTER_LATITUDE));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x18 }, IConstants.FRAME_CENTER_LONGITUDE));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x19 }, IConstants.FRAME_CENTER_ELEVATION));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x28 }, IConstants.TARGET_LOCATION_LATITUDE));
		DATA_ELEMENTS.add(new KlvInt(new byte[] { 0x29 }, IConstants.TARGET_LOCATION_LONGITUDE));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x2a }, IConstants.TARGET_LOCATION_ELEVATION));
		DATA_ELEMENTS.add(new KlvUnsignedByte(new byte[] { 0x38 }, IConstants.PLATFORM_GROUND_SPEED));
		DATA_ELEMENTS.add(new KlvLong(new byte[] { 0x39 }, IConstants.GROUND_RANGE));
		DATA_ELEMENTS.add(new KlvUnsignedShort(new byte[] { 0x01 }, IConstants.CHECKSUM));
	}

	private static KlvContext getKLVContext(final Set<? extends KlvDataElement> dataElements) {
		final KlvContext localSetContext = new KlvContext(KeyLength.OneByte, LengthEncoding.OneByte, dataElements);

		final KlvLocalSet outerSet = new KlvLocalSet(new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E,
				0x01, 0x03, 0x01, 0x01, 0x00, 0x00, 0x00 }, IConstants.UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY,
				localSetContext);

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
	public static String parseKLVSet(final byte[] klvBytes) throws KlvDecodingException {
		// byte[] klvBytes;

		// try (final InputStream inputStream =
		// getClass().getClassLoader().getResourceAsStream(filePath)) {
		// klvBytes = IOUtils.toByteArray(inputStream);
		// }

		final KlvContext klvContext = getKLVContext(DATA_ELEMENTS);

		final Map<String, KlvDataElement> decodedDataElements = new KlvDecoder(klvContext).decode(klvBytes)
				.getDataElements();

		System.out.println("Map length " + decodedDataElements.size());
		System.out.println("keys set decoded " + decodedDataElements.keySet());

		// final KlvContext localSet = ((KlvLocalSet)
		// decodedDataElements.values().iterator().next())
		// .getValue();

		final KlvContext localSet = ((KlvLocalSet) decodedDataElements
				.get(IConstants.UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY)).getValue();

		System.out.println("localset " + localSet);

		// System.out.println("values "+decodedDataElements.values().iterator().next());

		StringBuilder sb = new StringBuilder("<hr><br/> ");
		// if (localSet != null && localSet.getDataElements() != null) {
		final Map<String, KlvDataElement> localSetDataElements = localSet.getDataElements();
		System.out.println("localSetDataElements  " + localSetDataElements.keySet());
		KLVProperties klvProperties = new KLVProperties();

		for (Map.Entry<String, KlvDataElement> entry : localSetDataElements.entrySet()) {
			System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
			KlvDataElement klvDataElement = entry.getValue();
			sb.append("<b>" + klvDataElement.getName() + "  :</b> " + klvDataElement.getValue() + "<br/>");
			klvProperties = KlvUtils.getKLVPropertiesObj(klvDataElement.getName(), klvDataElement.getValue().toString(),
					klvProperties);
		}
		saveKLVPropertiesObjet(klvProperties);
		return sb.toString();
	}

	/**
	 * This method is use for execute separate thread for saving user data.
	 * 
	 * @param klvProperties
	 */
	private static void saveKLVPropertiesObjet(KLVProperties klvProperties) {
		KlvDataSave klvDataSave = new KlvDataSave(klvProperties);
		Thread t = new Thread(klvDataSave);
		t.start();
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
