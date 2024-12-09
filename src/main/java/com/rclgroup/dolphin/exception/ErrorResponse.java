package com.rclgroup.dolphin.exception;

public class ErrorResponse {
    public ErrorResponse(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }
 
    public ErrorResponse() {
		super();
	}
    //General error message about nature of error
    private String message;
 
    //Specific errors in API request processing
    private String details;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
 
   
}