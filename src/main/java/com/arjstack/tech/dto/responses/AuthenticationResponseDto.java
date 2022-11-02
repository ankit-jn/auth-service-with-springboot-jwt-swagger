package com.arjstack.tech.dto.responses;

import com.arjstack.tech.dto.TokenDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value = "Authentication Response")
@Getter
public class AuthenticationResponseDto {

	@ApiModelProperty(value = "User Name", dataType = "java.lang.String", position = 1)
	private String userName;
	
	@ApiModelProperty(value = "Roles", dataType = "java.lang.String", position = 2)
	private String role;

	@ApiModelProperty(value = "Token", dataType = "TokenDto;", position = 3)
	private TokenDto token;

	public AuthenticationResponseDto(String userName, String role, TokenDto token) {
		super();
		this.userName = userName;
		this.role = role;
		this.token = token;
	}

}
