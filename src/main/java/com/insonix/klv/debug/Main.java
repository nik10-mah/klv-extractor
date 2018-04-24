package com.insonix.klv.debug;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.codice.ddf.libs.klv.KlvContext;
import org.codice.ddf.libs.klv.KlvDataElement;
import org.codice.ddf.libs.klv.KlvDecoder;
import org.codice.ddf.libs.klv.data.Klv.KeyLength;
import org.codice.ddf.libs.klv.data.Klv.LengthEncoding;
import org.codice.ddf.libs.klv.data.numerical.KlvInt;
import org.codice.ddf.libs.klv.data.numerical.KlvIntegerEncodedFloatingPoint;
import org.codice.ddf.libs.klv.data.numerical.KlvLong;
import org.codice.ddf.libs.klv.data.numerical.KlvShort;
import org.codice.ddf.libs.klv.data.numerical.KlvUnsignedByte;
import org.codice.ddf.libs.klv.data.numerical.KlvUnsignedShort;
import org.codice.ddf.libs.klv.data.set.KlvLocalSet;
import org.codice.ddf.libs.klv.data.text.KlvString;

public class Main {
	private static final String UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY = "uas-datalink-local-dataset";

	public static final String UAS_DATALINK_LOCAL_SET = "UAS Datalink Local Set";

	public static final String CHECKSUM = "checksum";

	public static final String TIMESTAMP = "timestamp";

	public static final String MISSION_ID = "mission id";

	public static final String PLATFORM_TAIL_NUMBER = "platform tail number";

	public static final String PLATFORM_DESIGNATION = "platform designation";

	public static final String IMAGE_SOURCE_SENSOR = "image source sensor";

	public static final String IMAGE_COORDINATE_SYSTEM = "image coordinate system";

	public static final String SENSOR_LATITUDE = "sensor latitude";

	public static final String SENSOR_LONGITUDE = "sensor longitude";

	public static final String SENSOR_TRUE_ALTITUDE = "sensor true altitude";

	public static final String SLANT_RANGE = "slant range";

	public static final String TARGET_WIDTH = "target width";

	public static final String FRAME_CENTER_LATITUDE = "frame center latitude";

	public static final String FRAME_CENTER_LONGITUDE = "frame center longitude";

	public static final String FRAME_CENTER_ELEVATION = "frame center elevation";

	public static final String OFFSET_CORNER_LATITUDE_1 = "offset corner latitude 1";

	public static final String OFFSET_CORNER_LONGITUDE_1 = "offset corner longitude 1";

	public static final String OFFSET_CORNER_LATITUDE_2 = "offset corner latitude 2";

	public static final String OFFSET_CORNER_LONGITUDE_2 = "offset corner longitude 2";

	public static final String OFFSET_CORNER_LATITUDE_3 = "offset corner latitude 3";

	public static final String OFFSET_CORNER_LONGITUDE_3 = "offset corner longitude 3";

	public static final String OFFSET_CORNER_LATITUDE_4 = "offset corner latitude 4";

	public static final String OFFSET_CORNER_LONGITUDE_4 = "offset corner longitude 4";

	public static final String TARGET_LOCATION_LATITUDE = "target location latitude";

	public static final String TARGET_LOCATION_LONGITUDE = "target location longitude";

	public static final String TARGET_LOCATION_ELEVATION = "target location elevation";

	public static final String GROUND_RANGE = "ground range";

	public static final String PLATFORM_CALL_SIGN = "platform call sign";

	public static final String EVENT_START_TIME = "event start time";

	public static final String OPERATIONAL_MODE = "operational mode";

	public static final String CORNER_LATITUDE_1 = "corner latitude 1";

	public static final String CORNER_LONGITUDE_1 = "corner longitude 1";

	public static final String CORNER_LATITUDE_2 = "corner latitude 2";

	public static final String CORNER_LONGITUDE_2 = "corner longitude 2";

	public static final String CORNER_LATITUDE_3 = "corner latitude 3";

	public static final String CORNER_LONGITUDE_3 = "corner longitude 3";

	public static final String CORNER_LATITUDE_4 = "corner latitude 4";

	public static final String CORNER_LONGITUDE_4 = "corner longitude 4";

	public static final String SECURITY_LOCAL_METADATA_SET = "security local metadata set";

	public static final String SECURITY_CLASSIFICATION = "security classification";

	public static final String CLASSIFYING_COUNTRY_CODING_METHOD = "country coding method";

	public static final String CLASSIFYING_COUNTRY = "classifying country";

	public static final String OBJECT_COUNTRY_CODING_METHOD = "object country coding method";

	public static final String OBJECT_COUNTRY_CODES = "object country codes";

	public static final String SECURITY_SCI_SHI_INFORMATION = "security sci/shi information";

	public static final String CAVEATS = "caveats";

	public static final String RELEASING_INSTRUCTIONS = "releasing instructions";

	// private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	private static final int MAX_UNSIGNED_SHORT = (1 << 16) - 1;

	private static final long MAX_UNSIGNED_INT = (1L << 32) - 1;

	/*
	 * public static void main(String[] args) throws KlvDecodingException {
	 * byte[] bytes = new byte[(int) new
	 * File("/home/harkomal/Downloads/output.klv").length()]; KlvContext
	 * klvContext = new KlvContext(KeyLength.SixteenBytes, LengthEncoding.BER);
	 * KlvDecoder decoder = new KlvDecoder(klvContext);
	 * System.out.println(decoder.decode(bytes).getDataElements()); }
	 */

	public static void test(byte[] bytes) throws Exception {

		
		System.out.println("=========================================="+bytes.length);
		System.out.println("                                           ");
		// FileInputStream fis = new
		// FileInputStream("/home/harkomal/output12.klv");
		// byte[] bytes = new byte[(int) new
		// File("/home/harkomal/output12.klv").length()];

		// fis.read(bytes);
		KlvContext context = new KlvContext(KeyLength.SixteenBytes, LengthEncoding.BER);
		Set<KlvDataElement> localSetContext = new HashSet<>();
		localSetContext.add(new KlvUnsignedShort(new byte[] { 1 }, CHECKSUM));
		localSetContext.add(new KlvLong(new byte[] { 2 }, TIMESTAMP));
		localSetContext.add(new KlvString(new byte[] { 3 }, MISSION_ID));
		localSetContext.add(new KlvString(new byte[] { 4 }, PLATFORM_TAIL_NUMBER));
		localSetContext.add(new KlvString(new byte[] { 10 }, PLATFORM_DESIGNATION));
		localSetContext.add(new KlvString(new byte[] { 11 }, IMAGE_SOURCE_SENSOR));
		localSetContext.add(new KlvString(new byte[] { 12 }, IMAGE_COORDINATE_SYSTEM));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 13 }, SENSOR_LATITUDE, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -90, 90));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 14 }, SENSOR_LONGITUDE, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -180, 180));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvUnsignedShort(new byte[] { 15 }, SENSOR_TRUE_ALTITUDE), 0, MAX_UNSIGNED_SHORT, -900, 19000));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(new KlvLong(new byte[] { 21 }, SLANT_RANGE), 0,
				MAX_UNSIGNED_INT, 0, 5000000));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(new KlvUnsignedShort(new byte[] { 22 }, TARGET_WIDTH), 0,
				MAX_UNSIGNED_SHORT, 0, 10000));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 23 }, FRAME_CENTER_LATITUDE, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -90, 90));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 24 }, FRAME_CENTER_LONGITUDE, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -180, 180));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvUnsignedShort(new byte[] { 25 }, FRAME_CENTER_ELEVATION), 0, MAX_UNSIGNED_SHORT, -900, 19000));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvShort(new byte[] { 26 }, OFFSET_CORNER_LATITUDE_1, Optional.of((short) 0x8000)),
				Short.MIN_VALUE + 1, Short.MAX_VALUE, -0.075, 0.075));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvShort(new byte[] { 27 }, OFFSET_CORNER_LONGITUDE_1, Optional.of((short) 0x8000)),
				Short.MIN_VALUE + 1, Short.MAX_VALUE, -0.075, 0.075));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvShort(new byte[] { 28 }, OFFSET_CORNER_LATITUDE_2, Optional.of((short) 0x8000)),
				Short.MIN_VALUE + 1, Short.MAX_VALUE, -0.075, 0.075));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvShort(new byte[] { 29 }, OFFSET_CORNER_LONGITUDE_2, Optional.of((short) 0x8000)),
				Short.MIN_VALUE + 1, Short.MAX_VALUE, -0.075, 0.075));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvShort(new byte[] { 30 }, OFFSET_CORNER_LATITUDE_3, Optional.of((short) 0x8000)),
				Short.MIN_VALUE + 1, Short.MAX_VALUE, -0.075, 0.075));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvShort(new byte[] { 31 }, OFFSET_CORNER_LONGITUDE_3, Optional.of((short) 0x8000)),
				Short.MIN_VALUE + 1, Short.MAX_VALUE, -0.075, 0.075));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvShort(new byte[] { 32 }, OFFSET_CORNER_LATITUDE_4, Optional.of((short) 0x8000)),
				Short.MIN_VALUE + 1, Short.MAX_VALUE, -0.075, 0.075));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvShort(new byte[] { 33 }, OFFSET_CORNER_LONGITUDE_4, Optional.of((short) 0x8000)),
				Short.MIN_VALUE + 1, Short.MAX_VALUE, -0.075, 0.075));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 40 }, TARGET_LOCATION_LATITUDE, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -90, 90));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 41 }, TARGET_LOCATION_LONGITUDE, Optional.of(0x80000000)),
				Integer.MIN_VALUE + 1, Integer.MAX_VALUE, -180, 180));

		localSetContext.add(
				new KlvIntegerEncodedFloatingPoint(new KlvUnsignedShort(new byte[] { 42 }, TARGET_LOCATION_ELEVATION),
						0, MAX_UNSIGNED_SHORT, -900, 19000));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(new KlvLong(new byte[] { 57 }, GROUND_RANGE), 0,
				MAX_UNSIGNED_INT, 0, 5000000));

		localSetContext.add(new KlvString(new byte[] { 59 }, PLATFORM_CALL_SIGN));
		localSetContext.add(new KlvLong(new byte[] { 72 }, EVENT_START_TIME));
		localSetContext.add(new KlvUnsignedByte(new byte[] { 77 }, OPERATIONAL_MODE));

		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 82 }, CORNER_LATITUDE_1, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -90, 90));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 83 }, CORNER_LONGITUDE_1, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -180, 180));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 84 }, CORNER_LATITUDE_2, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -90, 90));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 85 }, CORNER_LONGITUDE_2, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -180, 180));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 86 }, CORNER_LATITUDE_3, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -90, 90));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 87 }, CORNER_LONGITUDE_3, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -180, 180));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 88 }, CORNER_LATITUDE_4, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -90, 90));
		localSetContext.add(new KlvIntegerEncodedFloatingPoint(
				new KlvInt(new byte[] { 89 }, CORNER_LONGITUDE_4, Optional.of(0x80000000)), Integer.MIN_VALUE + 1,
				Integer.MAX_VALUE, -180, 180));

		// System.out.println(">>>>>>>>>"+(128<<88));
		final KlvContext klvContext = getKLVContext(localSetContext);
		// 0x06 0x0E 0x2B 0x34 0x02 0x0B 0x01 0x01 0x0E 0x01 0x03 0x01 0x01 0x00
		// 0x00
		// 0x00
		// System.out.println(klvContext.getDataElements().keySet().iterator().next()+">>>>>>>>");
		// System.out.println(bytes.length);
		KlvDecoder decoder = new KlvDecoder(klvContext);
		final KlvContext localSet = ((KlvLocalSet) decoder.decode(bytes).getDataElements()
				.get(UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY)).getValue();
		if (localSet != null && localSet.getDataElements() != null) {
			Iterator<Entry<String, KlvDataElement>> i = localSet.getDataElements().entrySet().iterator();
			while (i.hasNext()) {
				Entry<String, KlvDataElement> el = i.next();
				System.out.println(el.getKey() + " : " + el.getValue().getValue());
			}
		}
		// System.out.println("
		// >>>>>>"+(decoder.decode(bytes).getDataElements().entrySet().iterator().next().getValue().getValue()));

	}

	private static KlvContext getKLVContext(final Set<? extends KlvDataElement> dataElements) {

		final KlvContext localSetContext = new KlvContext(KeyLength.OneByte, LengthEncoding.OneByte, dataElements);

		final KlvLocalSet outerSet = new KlvLocalSet(new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E,
				0x01, 0x03, 0x01, 0x01, 0x00, 0x00, 0x00 }, UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY, localSetContext);

		final Set<KlvDataElement> outerSetContext = Collections.singleton(outerSet);

		return new KlvContext(KeyLength.SixteenBytes, LengthEncoding.BER, outerSetContext);
	}
	/*
	 * private static KlvContext getKLVContext(final Set<? extends
	 * KlvDataElement> dataElements) { final KlvContext localSetContext = new
	 * KlvContext(KeyLength.SixteenBytes, LengthEncoding.BER, dataElements);
	 * 
	 * return new KlvContext(KeyLength.SixteenBytes, LengthEncoding.BER,
	 * dataElements); }
	 */

}
