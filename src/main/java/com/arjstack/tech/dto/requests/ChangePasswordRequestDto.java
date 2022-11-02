package com.arjstack.tech.dto.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;

@ApiModel(value = "Change Password Request")
@Getter
public class ChangePasswordRequestDto {

	@ApiModelProperty(value = "Current Password", dataType = "java.lang.String", required = true, position = 1)
	@NotNull
	private String currentPassword;
	
	@ApiModelProperty(value = "New Password", dataType = "java.lang.String", required = true, position = 2)
	@NotNull
	@Size(max = 100)
	private String newPassword;

}
