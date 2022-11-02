package com.arjstack.tech.dto.responses;

import java.util.List;

import com.arjstack.tech.dto.UserDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "User List Response")
@Getter
@Setter
public class UserListDto {

	@ApiModelProperty(value = "List of Users", dataType = "java.lang.List<UserDto>")
	private List<UserDto> userList;

	public UserListDto(List<UserDto> userList) {
		super();
		this.userList = userList;
	}

}
