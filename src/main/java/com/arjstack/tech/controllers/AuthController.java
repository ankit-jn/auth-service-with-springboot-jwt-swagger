package com.arjstack.tech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arjstack.tech.annotations.AccessEntry;
import com.arjstack.tech.dto.requests.AuthenticationRequestDto;
import com.arjstack.tech.dto.responses.AuthenticationResponseDto;
import com.arjstack.tech.enums.ServiceResponseEnum;
import com.arjstack.tech.exceptions.ExceptionResponseList;
import com.arjstack.tech.exceptions.InvalidInputException;
import com.arjstack.tech.exceptions.ResourceNotFoundException;
import com.arjstack.tech.exceptions.WrongCredentialsException;
import com.arjstack.tech.services.AuthenticationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Authencation", description = "Authentication API", produces = "application/json", tags = {
		"Authentication" })
@CrossOrigin
@RestController
@RequestMapping("/auth")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuthController {

	@Autowired
	private AuthenticationService authenticationService;

	@AccessEntry
	@ApiOperation(value = "User Sign-In", response = AuthenticationResponseDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval", response = AuthenticationResponseDto.class),
			@ApiResponse(code = 400, message = "Bad Input", response = ExceptionResponseList.class),
			@ApiResponse(code = 403, message = "Invalid Credentials", response = ExceptionResponseList.class),
			@ApiResponse(code = 404, message = "Resoutrce Not available", response = ExceptionResponseList.class),
			@ApiResponse(code = 500, message = "Internal Server error", response = ExceptionResponseList.class) })
	@PostMapping(value = "/signin")
	public ResponseEntity<AuthenticationResponseDto> autheticate(
			@RequestBody AuthenticationRequestDto authenticationDto)
			throws InvalidInputException, ResourceNotFoundException, WrongCredentialsException {
		if (StringUtils.isEmpty(authenticationDto) || StringUtils.isEmpty(authenticationDto.getUserName())
				|| StringUtils.isEmpty(authenticationDto.getPassword())) {
			String errMsg = "Authentication Details are missing!!";
			throw new InvalidInputException(ServiceResponseEnum.SVC0002.formatResponseMessage(errMsg));
		}
		AuthenticationResponseDto tokenResponse = authenticationService.authenticate(authenticationDto);

		return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
	}
}