package com.insonix.klv.model;

/**
 * @author Pramod Maurya
 * @since 24 April-2018
 */
public class KLVProperties {

	private Long id;

	private String unixTimestamp;

	private String uaslsVersionNumber;

	private String platformHeading;

	private String platformPitch;

	private String platformRoll;

	private String imageSourceSensor;

	private String imageCoordinateSystem;

	private String sensorLat;

	private String sensorLon;

	private String sensorTrueAlt;

	private String sensorHorizontalFov;

	private String sensorVerticalFov;

	private String sensorRelativeAzimuth;

	private String sensorRelativeElevation;

	private String sensorRelativeRoll;

	private String slantRange;

	private String targetWidth;

	private String frameCenterLat;

	private String frameCenterLon;

	private String frameCenterElevation;

	private String targetLocationLat;

	private String targetLocationLon;

	private String targetLocationElevation;

	private String platformGroundSpeed;

	private String groundRange;

	private String checksum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnixTimestamp() {
		return unixTimestamp;
	}

	public void setUnixTimestamp(String unixTimestamp) {
		this.unixTimestamp = unixTimestamp;
	}

	public String getUaslsVersionNumber() {
		return uaslsVersionNumber;
	}

	public void setUaslsVersionNumber(String uaslsVersionNumber) {
		this.uaslsVersionNumber = uaslsVersionNumber;
	}

	public String getPlatformHeading() {
		return platformHeading;
	}

	public void setPlatformHeading(String platformHeading) {
		this.platformHeading = platformHeading;
	}

	public String getPlatformPitch() {
		return platformPitch;
	}

	public void setPlatformPitch(String platformPitch) {
		this.platformPitch = platformPitch;
	}

	public String getPlatformRoll() {
		return platformRoll;
	}

	public void setPlatformRoll(String platformRoll) {
		this.platformRoll = platformRoll;
	}

	public String getImageSourceSensor() {
		return imageSourceSensor;
	}

	public void setImageSourceSensor(String imageSourceSensor) {
		this.imageSourceSensor = imageSourceSensor;
	}

	public String getImageCoordinateSystem() {
		return imageCoordinateSystem;
	}

	public void setImageCoordinateSystem(String imageCoordinateSystem) {
		this.imageCoordinateSystem = imageCoordinateSystem;
	}

	public String getSensorLat() {
		return sensorLat;
	}

	public void setSensorLat(String sensorLat) {
		this.sensorLat = sensorLat;
	}

	public String getSensorLon() {
		return sensorLon;
	}

	public void setSensorLon(String sensorLon) {
		this.sensorLon = sensorLon;
	}

	public String getSensorTrueAlt() {
		return sensorTrueAlt;
	}

	public void setSensorTrueAlt(String sensorTrueAlt) {
		this.sensorTrueAlt = sensorTrueAlt;
	}

	public String getSensorHorizontalFov() {
		return sensorHorizontalFov;
	}

	public void setSensorHorizontalFov(String sensorHorizontalFov) {
		this.sensorHorizontalFov = sensorHorizontalFov;
	}

	public String getSensorVerticalFov() {
		return sensorVerticalFov;
	}

	public void setSensorVerticalFov(String sensorVerticalFov) {
		this.sensorVerticalFov = sensorVerticalFov;
	}

	public String getSensorRelativeAzimuth() {
		return sensorRelativeAzimuth;
	}

	public void setSensorRelativeAzimuth(String sensorRelativeAzimuth) {
		this.sensorRelativeAzimuth = sensorRelativeAzimuth;
	}

	public String getSensorRelativeElevation() {
		return sensorRelativeElevation;
	}

	public void setSensorRelativeElevation(String sensorRelativeElevation) {
		this.sensorRelativeElevation = sensorRelativeElevation;
	}

	public String getSensorRelativeRoll() {
		return sensorRelativeRoll;
	}

	public void setSensorRelativeRoll(String sensorRelativeRoll) {
		this.sensorRelativeRoll = sensorRelativeRoll;
	}

	public String getSlantRange() {
		return slantRange;
	}

	public void setSlantRange(String slantRange) {
		this.slantRange = slantRange;
	}

	public String getTargetWidth() {
		return targetWidth;
	}

	public void setTargetWidth(String targetWidth) {
		this.targetWidth = targetWidth;
	}

	public String getFrameCenterLat() {
		return frameCenterLat;
	}

	public void setFrameCenterLat(String frameCenterLat) {
		this.frameCenterLat = frameCenterLat;
	}

	public String getFrameCenterLon() {
		return frameCenterLon;
	}

	public void setFrameCenterLon(String frameCenterLon) {
		this.frameCenterLon = frameCenterLon;
	}

	public String getFrameCenterElevation() {
		return frameCenterElevation;
	}

	public void setFrameCenterElevation(String frameCenterElevation) {
		this.frameCenterElevation = frameCenterElevation;
	}

	public String getTargetLocationLat() {
		return targetLocationLat;
	}

	public void setTargetLocationLat(String targetLocationLat) {
		this.targetLocationLat = targetLocationLat;
	}

	public String getTargetLocationLon() {
		return targetLocationLon;
	}

	public void setTargetLocationLon(String targetLocationLon) {
		this.targetLocationLon = targetLocationLon;
	}

	public String getTargetLocationElevation() {
		return targetLocationElevation;
	}

	public void setTargetLocationElevation(String targetLocationElevation) {
		this.targetLocationElevation = targetLocationElevation;
	}

	public String getPlatformGroundSpeed() {
		return platformGroundSpeed;
	}

	public void setPlatformGroundSpeed(String platformGroundSpeed) {
		this.platformGroundSpeed = platformGroundSpeed;
	}

	public String getGroundRange() {
		return groundRange;
	}

	public void setGroundRange(String groundRange) {
		this.groundRange = groundRange;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	@Override
	public String toString() {
		return "KLVProperties [id=" + id + ", unixTimestamp=" + unixTimestamp + ", uaslsVersionNumber="
				+ uaslsVersionNumber + ", platformHeading=" + platformHeading + ", platformPitch=" + platformPitch
				+ ", platformRoll=" + platformRoll + ", imageSourceSensor=" + imageSourceSensor
				+ ", imageCoordinateSystem=" + imageCoordinateSystem + ", sensorLat=" + sensorLat + ", sensorLon="
				+ sensorLon + ", sensorTrueAlt=" + sensorTrueAlt + ", sensorHorizontalFov=" + sensorHorizontalFov
				+ ", sensorVerticalFov=" + sensorVerticalFov + ", sensorRelativeAzimuth=" + sensorRelativeAzimuth
				+ ", sensorRelativeElevation=" + sensorRelativeElevation + ", sensorRelativeRoll=" + sensorRelativeRoll
				+ ", slantRange=" + slantRange + ", targetWidth=" + targetWidth + ", frameCenterLat=" + frameCenterLat
				+ ", frameCenterLon=" + frameCenterLon + ", frameCenterElevation=" + frameCenterElevation
				+ ", targetLocationLat=" + targetLocationLat + ", targetLocationLon=" + targetLocationLon
				+ ", targetLocationElevation=" + targetLocationElevation + ", platformGroundSpeed="
				+ platformGroundSpeed + ", groundRange=" + groundRange + ", checksum=" + checksum + "]";
	}

}
