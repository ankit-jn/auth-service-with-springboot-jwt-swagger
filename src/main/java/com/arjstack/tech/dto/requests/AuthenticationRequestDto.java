package com.arjstack.tech.dto.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Authentication Request")
@Getter
@Setter
public class AuthenticationRequestDto {

	@ApiModelProperty(value = "User Name", dataType = "java.lang.String", required = true, position = 1)
	private String userName;
	
	@ApiModelProperty(value = "Password", dataType = "java.lang.String", required = true, position = 2)
	private String password;

}
