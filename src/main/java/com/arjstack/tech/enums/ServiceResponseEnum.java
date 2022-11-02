package com.arjstack.tech.enums;

public enum ServiceResponseEnum {

	SVC0001("SVC0001", "OOPS! There is some error, Please contact support team!!"), 
	SVC0002("SVC0002", "Bad Request - %s"), 
	
	SVC1001("SVC1001", "Invalid Credentials"), 
	SVC1002("SVC1002", "Authentication Token is missing!!"),
	SVC1003("SVC1003", "Authentication Token is Invalid!!"),
	SVC1004("SVC1003", "Refresh Token is Invalid!!"),
	SVC1005("SVC1004", "Invalid/Expired Password Reset Token"), 
	SVC1006("SVC1005", "Restricted - %s"),
	SVC1007("SVC1007", "Operation Disabled!!!"),
	
	SVC9001("SVC9001", "Resource unavailable - %s"), 
	SVC9002("SVC9002", "Resource already exist for %s");
	
	
	private String responseCode;
	private String responseMessage;

	private ServiceResponseEnum(String responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String createResponseMessage() {
		return responseMessage;
	}

	public String formatResponseMessage(String errMsg) {
		String errorResponse = String.format(responseMessage, errMsg);
		return errorResponse;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

}
