/**
 * 
 */
package com.insonix.klv.utils;

/**
 * @author Nikhil Mahajan
 *
 */
public interface IConstants {
	
	
	public static final String UAS_DATALINK_LOCAL_SET_UNIVERSAL_KEY = "UAS Datalink Local Set Universal Key";

	public static final String TIMESTAMP = "timestamp";

	public static final String UAS_LS_VERSION_NUMBER = "UAS LS Version Number";

	public static final String PLATFORM_HEADING_ANGLE = "platform heading angle";

	public static final String PLATFORM_PITCH_ANGLE = "platform pitch angle";

	public static final String PLATFORM_ROLL_ANGLE = "platform roll angle";

	public static final String IMAGE_SOURCE_SENSOR = "image source sensor";

	public static final String IMAGE_COORDINATE_SYSTEM = "image coordinate system";

	public static final String SENSOR_LATITUDE = "sensor latitude";

	public static final String SENSOR_LONGITUDE = "sensor longitude";

	public static final String SENSOR_TRUE_ALTITUDE = "sensor true altitude";

	public static final String SENSOR_HORIZONTAL_FOV = "sensor horizontal fov";

	public static final String SENSOR_VERTICAL_FOV = "sensor vertical fov";

	public static final String SENSOR_RELATIVE_AZIMUTH_ANGLE = "sensor relative azimuth angle";

	public static final String SENSOR_RELATIVE_ELEVATION_ANGLE = "sensor relative elevation angle";

	public static final String SENSOR_RELATIVE_ROLL_ANGLE = "sensor relative roll angle";

	public static final String SLANT_RANGE = "slant range";

	public static final String TARGET_WIDTH = "target width";

	public static final String FRAME_CENTER_LATITUDE = "frame center latitude";

	public static final String FRAME_CENTER_LONGITUDE = "frame center longitude";

	public static final String FRAME_CENTER_ELEVATION = "frame center elevation";

	public static final String TARGET_LOCATION_LATITUDE = "target location latitude";

	public static final String TARGET_LOCATION_LONGITUDE = "target location longitude";

	public static final String TARGET_LOCATION_ELEVATION = "target location elevation";

	public static final String PLATFORM_GROUND_SPEED = "platform ground speed";

	public static final String GROUND_RANGE = "ground range";

	public static final String CHECKSUM = "checksum";

	public static final String KLV_FILE_PATH = "E:\\Projects\\klvs\\output12.klv";
	
	public static final String FFMPEG_EXTRACT_KLV = "ffmpeg -i udp://@127.0.0.1:2000 -map data-re -codec copy -f data \""+IConstants.KLV_FILE_PATH+"\"";
	
}
