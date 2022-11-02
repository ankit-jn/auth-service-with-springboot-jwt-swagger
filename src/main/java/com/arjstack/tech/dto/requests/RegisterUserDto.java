package com.arjstack.tech.dto.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Registration Request")
@Getter
@Setter
public class RegisterUserDto {

	@ApiModelProperty(value = "User Name", dataType = "java.lang.String", required = true, position = 1)
	@NotNull
	@Size(min = 5, max = 20)
	private String userName;

	@ApiModelProperty(value = "First Name", dataType = "java.lang.String", required = true, position = 2)
	@NotNull
	@Size(min = 1, max = 25)
	private String firstName;

	@ApiModelProperty(value = "Last Name", dataType = "java.lang.String", required = true, position = 3)
	@NotNull
	@Size(min = 1, max = 25)
	private String lastName;

	@ApiModelProperty(value = "Password", dataType = "java.lang.String", required = true, position = 4)
	@NotNull
	@Size(min = 8, max = 100)
	private String password;

}
