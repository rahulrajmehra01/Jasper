package com.rclgroup.dolphin.utils.dto;

public class IPVListModel {
	private String ipvFromDate;
	private String ipvToDate;
	private String service;
	private String vessel;
	private String voyage;
	private String port;
	private String terminal;
	private String module;
	private String userID;
	private String fromDate;
	private String toDate;
	private String vendorCode;
	private String ipvStatus;
	private String fsc;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getModule() {
		return module;
	}
	
	public void setModule(String module) {
		this.module = module;
	}

	public String getIpvStatus() {
		return ipvStatus;
	}

	public void setIpvStatus(String ipvStatus) {
		this.ipvStatus = ipvStatus;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVessel() {
		return vessel;
	}

	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	public String getFsc() {
		return fsc;
	}

	public void setFsc(String fsc) {
		this.fsc = fsc;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getIpvFromDate() {
		return ipvFromDate;
	}

	public void setIpvFromDate(String ipvFromDate) {
		this.ipvFromDate = ipvFromDate;
	}

	public String getIpvToDate() {
		return ipvToDate;
	}

	public void setIpvToDate(String ipvToDate) {
		this.ipvToDate = ipvToDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

}
