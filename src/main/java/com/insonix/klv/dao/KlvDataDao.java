package com.insonix.klv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.insonix.klv.model.KLVProperties;

/**
 * @author Pramod Maurya
 * @since 24 Apr 2018
 *
 */
public class KlvDataDao {

	private final String query = "INSERT INTO KLV_PROPERTIES"
			+ " ([CHECKSUM], [UNIX_TIMESTAMP] ,[UAS_LS_VERSION_NUMBER] ,[PLATFORM_HEADING] ,[PLATFORM_PITCH] ,[PLATFORM_ROLL] ,[IMAGE_SOURCE_CENTER] ,[IMAGE_CORDINATE_SYSTEM] ,[SENSOR_LAT] ,[SENSOR_LON] ,[SENSOR_TRUE_ALT] ,[SENSOR_HORIZONTAL_FOV] ,[SENSOR_VERTICAL_FOV] ,[SENSOR_RELATIVE_AZIMUTH] ,[SENSOR_RELATIVE_ELEVATION] ,[SENSOR_RELATIVE_ROLL] ,[SLANT_RANGE] ,[TARGET_WIDTH] ,[FRAME_CENTER_LAT] ,[FRAME_CENTER_LON] ,[FRAME_CENTER_ELEVATION] ,[TARGET_LOCATION_LAT] ,[TARGET_LOCATION_LON] ,[TARGET_LOCATION_ELEVATION] ,[PLATFOREM_GROUP_SPEED] ,[GROUP_RANGE])"
			+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	/**
	 * This method is use for save the KLVPropeties object to database.
	 * 
	 * @param klvProperties
	 */
	public void saveKlvData(KLVProperties klvProperties) {
		Connection connection = SQLServerConnection.getConnection();
		if (null != connection) {
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement = getPreparedStatmentForKLVProperties(preparedStatement, klvProperties);
				preparedStatement.executeUpdate();
				SQLServerConnection.closePreparedStatement(preparedStatement);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method is use for get PrepareStatement object with KLVProperties.
	 * 
	 * @param preparedStatement
	 * @param klvProperties
	 * @return
	 */
	private PreparedStatement getPreparedStatmentForKLVProperties(PreparedStatement preparedStatement,
			KLVProperties klvProperties) {
		if (null != preparedStatement) {
			try {
				// preparedStatement.setLong(1, klvProperties.getId());
				preparedStatement.setString(2, klvProperties.getUnixTimestamp());
				preparedStatement.setString(3, klvProperties.getUaslsVersionNumber());
				preparedStatement.setString(4, klvProperties.getPlatformHeading());
				preparedStatement.setString(5, klvProperties.getPlatformPitch());
				preparedStatement.setString(6, klvProperties.getPlatformRoll());
				preparedStatement.setString(7, klvProperties.getImageSourceSensor());
				preparedStatement.setString(8, klvProperties.getImageCoordinateSystem());
				preparedStatement.setString(9, klvProperties.getSensorLat());
				preparedStatement.setString(10, klvProperties.getSensorLon());
				preparedStatement.setString(11, klvProperties.getSensorTrueAlt());
				preparedStatement.setString(12, klvProperties.getSensorHorizontalFov());
				preparedStatement.setString(13, klvProperties.getSensorVerticalFov());
				preparedStatement.setString(14, klvProperties.getSensorRelativeAzimuth());
				preparedStatement.setString(15, klvProperties.getSensorRelativeElevation());
				preparedStatement.setString(16, klvProperties.getSensorRelativeRoll());
				preparedStatement.setString(17, klvProperties.getSlantRange());
				preparedStatement.setString(18, klvProperties.getTargetWidth());
				preparedStatement.setString(19, klvProperties.getFrameCenterLat());
				preparedStatement.setString(20, klvProperties.getFrameCenterLon());
				preparedStatement.setString(21, klvProperties.getFrameCenterElevation());
				preparedStatement.setString(22, klvProperties.getTargetLocationLat());
				preparedStatement.setString(23, klvProperties.getTargetLocationLon());
				preparedStatement.setString(24, klvProperties.getTargetLocationElevation());
				preparedStatement.setString(25, klvProperties.getPlatformGroundSpeed());
				preparedStatement.setString(26, klvProperties.getGroundRange());
				preparedStatement.setString(1, klvProperties.getChecksum());

				preparedStatement.addBatch();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return preparedStatement;
	}

}
