package com.arjstack.tech.services;

import com.arjstack.tech.dto.requests.RegisterUserDto;
import com.arjstack.tech.dto.responses.UserListDto;
import com.arjstack.tech.exceptions.InvalidInputException;

public interface UsersService {

	boolean isUserNameExist(String userName);

	void registerUser(RegisterUserDto userRegistration) throws InvalidInputException;

	UserListDto getUsers();

}