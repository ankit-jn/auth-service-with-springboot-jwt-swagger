package com.arjstack.tech.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Token")
@Getter
@Setter
public class TokenDto {

	@ApiModelProperty(value = "Authentication Token", dataType = "java.lang.String", position = 1)
	private String authToken;

	@ApiModelProperty(value = "Refresh Token", dataType = "java.lang.String", position = 2)
	private String refreshToken;

	@ApiModelProperty(value = "Token Issue Time", dataType = "java.lang.Long", position = 3)
	private Long tokenIssueTime;

	@ApiModelProperty(value = "Authentication Token Expiry", dataType = "java.lang.Integer", position = 4)
	private Integer tokenExpiresIn;

	public TokenDto(String authToken, String refreshToken, Long tokenIssueTime, Integer tokenExpiresIn) {
		super();
		this.authToken = authToken;
		this.refreshToken = refreshToken;
		this.tokenIssueTime = tokenIssueTime;
		this.tokenExpiresIn = tokenExpiresIn;
	}

}
