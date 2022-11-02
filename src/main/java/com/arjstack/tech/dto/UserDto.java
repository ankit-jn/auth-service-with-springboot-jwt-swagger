package com.arjstack.tech.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Token")
@Getter
@Setter
public class UserDto {

	@ApiModelProperty(value = "User Name", dataType = "java.lang.String", required = true, position = 1)
	private String userName;

	@ApiModelProperty(value = "First Name", dataType = "java.lang.String", required = true, position = 2)
	private String firstName;

	@ApiModelProperty(value = "Last Name", dataType = "java.lang.String", required = true, position = 3)
	private String lastName;

	@ApiModelProperty(value = "Role", dataType = "java.lang.String", required = true, position = 4)
	private String role;

	@ApiModelProperty(value = "Registration Date", dataType = "java.lang.String", position = 5)
	private String registrationDate;

	public UserDto(String userName, String firstName, String lastName, String role, String registrationDate) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.registrationDate = registrationDate;
	}

}
