package com.arjstack.tech.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {

	private String code;
	private String message;
	private String details;

	public ExceptionResponse(String code, String message, String details) {
		this.code = code;
		this.message = message;
		this.details = details;
	}
	
}
