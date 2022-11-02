package com.arjstack.tech.dto.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;

@ApiModel(value = "Token Request")
@Getter
public class TokenRequestDto {

	@ApiModelProperty(value = "User Name", dataType = "java.lang.String", required = true, position = 1)
	@NotNull
	@Size(max = 13)
	private String userName;
	
	@ApiModelProperty(value = "Token", dataType = "java.lang.String", required = true, position = 2)
	@NotNull
	@Size(max = 20)
	private String token;

}
