package com.arjstack.tech.services;

import com.arjstack.tech.context.UserContext;
import com.arjstack.tech.dto.requests.AuthenticationRequestDto;
import com.arjstack.tech.dto.requests.TokenRequestDto;
import com.arjstack.tech.dto.responses.AuthenticationResponseDto;
import com.arjstack.tech.exceptions.InvalidTokenException;
import com.arjstack.tech.exceptions.ResourceNotFoundException;
import com.arjstack.tech.exceptions.WrongCredentialsException;

public interface AuthenticationService {

	AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationDto)
			throws WrongCredentialsException, ResourceNotFoundException;

	AuthenticationResponseDto refreshToken(TokenRequestDto tokenRequestDto)
			throws ResourceNotFoundException, InvalidTokenException;

	void deleteToken(TokenRequestDto tokenRequestDto) throws ResourceNotFoundException;

	void changePassword(String nickName, String oldPassword, String newPassword) throws WrongCredentialsException;

	void prepareUserContext(UserContext userContext, String userName);
}