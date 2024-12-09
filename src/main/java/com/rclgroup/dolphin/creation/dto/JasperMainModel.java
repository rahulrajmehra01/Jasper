package com.rclgroup.dolphin.creation.dto;

import java.util.List;

import com.rclgroup.dolphin.utils.dto.IJSListModel;
import com.rclgroup.dolphin.utils.dto.IPVListModel;
import com.rclgroup.dolphin.utils.dto.MNRListModel;
import com.rclgroup.dolphin.utils.dto.STRListModel;
import com.rclgroup.dolphin.utils.dto.SYSListModel;
import com.rclgroup.dolphin.utils.dto.TOSListModel;
import com.rclgroup.dolphin.utils.dto.VMSListModel;

public class JasperMainModel {

	private List<MNRListModel> mnrList;
	private List<STRListModel> strList;
	private List<IJSListModel> ijsList;
	private List<IPVListModel> ipvList;
	private List<TOSListModel> tosList;
	private List<SYSListModel> sysList;
	private List<VMSListModel> vmsList;

	private String reportName;
	private String reportFormat;
	
	public List<VMSListModel> getVmsList() {
		return vmsList;
	}
	
	public void setVmsList(List<VMSListModel> vmsList) {
		this.vmsList = vmsList;
	}
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public List<SYSListModel> getSysList() {
		return sysList;
	}

	public void setSysList(List<SYSListModel> sysList) {
		this.sysList = sysList;
	}

	public List<TOSListModel> getTosList() {
		return tosList;
	}

	public void setTosList(List<TOSListModel> tosList) {
		this.tosList = tosList;
	}

	public List<IPVListModel> getIpvList() {
		return ipvList;
	}

	public void setIpvList(List<IPVListModel> ipvList) {
		this.ipvList = ipvList;
	}

	public List<MNRListModel> getMnrList() {
		return mnrList;
	}

	public void setMnrList(List<MNRListModel> mnrList) {
		this.mnrList = mnrList;
	}

	public List<STRListModel> getStrList() {
		return strList;
	}

	public void setStrList(List<STRListModel> strList) {
		this.strList = strList;
	}

	public List<IJSListModel> getIjsList() {
		return ijsList;
	}

	public void setIjsList(List<IJSListModel> ijsList) {
		this.ijsList = ijsList;
	}

}