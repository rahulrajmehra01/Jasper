package com.rclgroup.dolphin.util.dto;

public class OldStatusListResponse<T> {
	private T resultContent;
	private String resultStatus;
	private String resultMessage;
	public T getResultContent() {
		return resultContent;
	}
	public void setResultContent(T resultContent) {
		this.resultContent = resultContent;
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
