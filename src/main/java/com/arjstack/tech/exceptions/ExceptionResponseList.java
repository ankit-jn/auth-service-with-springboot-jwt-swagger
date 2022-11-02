package com.arjstack.tech.exceptions;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseList {

	private List<ExceptionResponse> violations;

	public ExceptionResponseList(List<ExceptionResponse> violations) {
		super();
		this.violations = violations;
	}

}
