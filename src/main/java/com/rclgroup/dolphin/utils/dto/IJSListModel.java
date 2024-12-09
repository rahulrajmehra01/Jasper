package com.rclgroup.dolphin.utils.dto;

public class IJSListModel {
	
	private String fromDate;
	private String toDate;
	private String sjobOrder;
	private String fromLocation;
	private String toLocation;
	private String transportMode;
	private String rateTY;
	
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public String getTransportMode() {
		return transportMode;
	}
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
	public String getRateTY() {
		return rateTY;
	}
	public void setRateTY(String rateTY) {
		this.rateTY = rateTY;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSjobOrder() {
		return sjobOrder;
	}
	public void setSjobOrder(String sjobOrder) {
		this.sjobOrder = sjobOrder;
	}
	
}
