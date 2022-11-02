package com.arjstack.tech.utils;

import lombok.Getter;

@Getter
public class ParsedToken {

	private String userName;
	private Long issuedAt;

	public ParsedToken(String userName, Long issuedAt) {
		super();
		this.userName = userName;
		this.issuedAt = issuedAt;
	}
}
