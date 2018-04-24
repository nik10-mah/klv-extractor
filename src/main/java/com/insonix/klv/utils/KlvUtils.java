package com.insonix.klv.utils;

import com.insonix.klv.model.KLVProperties;

/**
 * @author Pramod Maurya
 * @since 29-Mar-2018
 */
public class KlvUtils {

	/**
	 * This method is use for add value to KLVProperties Object.
	 * 
	 * @param key
	 * @param klvProperties
	 * @return
	 */
	public static KLVProperties getKLVPropertiesObj(String key, String value, KLVProperties klvProperties) {
		if (key.contains(IConstants.TIMESTAMP)) {
			klvProperties.setUnixTimestamp(value);
		} else if (key.contains(IConstants.UAS_LS_VERSION_NUMBER)) {
			klvProperties.setUaslsVersionNumber(value);
		} else if (key.contains(IConstants.PLATFORM_HEADING_ANGLE)) {
			klvProperties.setPlatformHeading(value);
		} else if (key.contains(IConstants.PLATFORM_PITCH_ANGLE)) {
			klvProperties.setPlatformPitch(value);
		} else if (key.contains(IConstants.PLATFORM_ROLL_ANGLE)) {
			klvProperties.setPlatformRoll(value);
		} else if (key.contains(IConstants.IMAGE_SOURCE_SENSOR)) {
			klvProperties.setImageSourceSensor(value);
		} else if (key.contains(IConstants.IMAGE_COORDINATE_SYSTEM)) {
			klvProperties.setImageCoordinateSystem(value);
		} else if (key.contains(IConstants.SENSOR_LATITUDE)) {
			klvProperties.setSensorLat(value);
		} else if (key.contains(IConstants.SENSOR_LONGITUDE)) {
			klvProperties.setSensorLon(value);
		} else if (key.contains(IConstants.SENSOR_TRUE_ALTITUDE)) {
			klvProperties.setSensorTrueAlt(value);
		} else if (key.contains(IConstants.SENSOR_HORIZONTAL_FOV)) {
			klvProperties.setSensorHorizontalFov(value);
		} else if (key.contains(IConstants.SENSOR_VERTICAL_FOV)) {
			klvProperties.setSensorVerticalFov(value);
		} else if (key.contains(IConstants.SENSOR_RELATIVE_AZIMUTH_ANGLE)) {
			klvProperties.setSensorRelativeAzimuth(value);
		} else if (key.contains(IConstants.SENSOR_RELATIVE_ELEVATION_ANGLE)) {
			klvProperties.setSensorRelativeElevation(value);
		} else if (key.contains(IConstants.SENSOR_RELATIVE_ROLL_ANGLE)) {
			klvProperties.setSensorRelativeRoll(value);
		} else if (key.contains(IConstants.SLANT_RANGE)) {
			klvProperties.setSlantRange(value);
		} else if (key.contains(IConstants.TARGET_WIDTH)) {
			klvProperties.setTargetWidth(value);
		} else if (key.contains(IConstants.FRAME_CENTER_LATITUDE)) {
			klvProperties.setFrameCenterLat(value);
		} else if (key.contains(IConstants.FRAME_CENTER_LONGITUDE)) {
			klvProperties.setFrameCenterLon(value);
		} else if (key.contains(IConstants.FRAME_CENTER_ELEVATION)) {
			klvProperties.setFrameCenterElevation(value);
		} else if (key.contains(IConstants.TARGET_LOCATION_LATITUDE)) {
			klvProperties.setTargetLocationLat(value);
		} else if (key.contains(IConstants.TARGET_LOCATION_LONGITUDE)) {
			klvProperties.setTargetLocationLon(value);
		} else if (key.contains(IConstants.TARGET_LOCATION_ELEVATION)) {
			klvProperties.setTargetLocationElevation(value);
		} else if (key.contains(IConstants.PLATFORM_GROUND_SPEED)) {
			klvProperties.setPlatformGroundSpeed(value);
		} else if (key.contains(IConstants.GROUND_RANGE)) {
			klvProperties.setGroundRange(value);
		} else if (key.contains(IConstants.CHECKSUM)) {
			klvProperties.setChecksum(value);
		} else {
			// System.out.println(data);
		}
		return klvProperties;
	}

}
