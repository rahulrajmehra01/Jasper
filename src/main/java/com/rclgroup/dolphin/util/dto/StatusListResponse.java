package com.rclgroup.dolphin.util.dto;

import java.util.List;

public class StatusListResponse<T> {
	private List<Object> resultContent;
	private String resultStatus;
	private String resultMessage;
	public List<Object> getResultContent() {
		return resultContent;
	}
	public void setResultContent(List<Object> list) {
		this.resultContent = list;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
}