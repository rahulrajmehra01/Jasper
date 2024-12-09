package com.rclgroup.dolphin.response;

public class RCLResponseModel<T> {

	public static final String DEFAULT_CODE = "200";
	public static final String DEFAULT_STATUS = "S";

	private String resultCode;
	private T resultContent;
	private String resultMessage;
	private String resultStatus;

	public RCLResponseModel() {

	}

	public RCLResponseModel(T content) {
		this.resultCode = DEFAULT_CODE;
		this.resultContent = content;
		this.resultStatus = DEFAULT_STATUS;
	}

	public RCLResponseModel(String resultCode, T content, String resultMessage, String resultStatus) {
		this.resultCode = resultCode;
		this.resultContent = content;
		this.resultMessage = resultMessage;
		this.resultStatus = resultStatus;
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

	public T getResultContent() {
		return resultContent;
	}

	public void setResultContent(T resultContent) {
		this.resultContent = resultContent;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
}
