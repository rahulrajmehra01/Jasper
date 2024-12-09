package com.rclgroup.dolphin.utils;

public class CommonRestResponse {
	
	public String resultStatus;
	public String resultCode;
	public String resultMessage;
	public Object resultContent;


	public static CommonRestResponse getResponse(String resultCode, String resultMessage, Object resultContent) {
		CommonRestResponse response = new CommonRestResponse();
		response.setResultStatus(getResultStatusFromCode(resultCode));
		response.setResultCode(resultCode);
		response.setResultMessage(resultMessage); 
		response.setResultContent(resultContent);
		return response;
	}
	
    public enum ResultStatusEnum {
        SUCCESS("success"),
        FAIL("fail");

        private final String value;

        ResultStatusEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

	public static String getResultStatusFromCode(String resultCode) {
		String status = null;
		switch (resultCode)
		{
		case "200":
			status = ResultStatusEnum.SUCCESS.getValue();
			break;
		default:
			status = ResultStatusEnum.FAIL.getValue();
			break;
		}
		return status;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public Object getResultContent() {
		return resultContent;
	}

	public void setResultContent(Object resultContent) {
		this.resultContent = resultContent;
	}
	
	
}
