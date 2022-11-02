package com.arjstack.tech.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arjstack.tech.annotations.AccessControls;
import com.arjstack.tech.constants.ApplicationConstants;
import com.arjstack.tech.context.UserContext;
import com.arjstack.tech.dto.requests.ChangePasswordRequestDto;
import com.arjstack.tech.dto.requests.TokenRequestDto;
import com.arjstack.tech.dto.responses.AuthenticationResponseDto;
import com.arjstack.tech.enums.ServiceResponseEnum;
import com.arjstack.tech.exceptions.ExceptionResponseList;
import com.arjstack.tech.exceptions.InvalidInputException;
import com.arjstack.tech.exceptions.InvalidTokenException;
import com.arjstack.tech.exceptions.ResourceNotFoundException;
import com.arjstack.tech.exceptions.WrongCredentialsException;
import com.arjstack.tech.services.AuthenticationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Access Management", description = "Access Management API", produces = "application/json", tags = {
		"Access Management" })
@CrossOrigin
@RestController
@RequestMapping("/access")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccessController {

	@Autowired
	private AuthenticationService authenticationService;

	@AccessControls
	@ApiOperation(value = "User Sign-Out", response = Boolean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful retrieval", response = Boolean.class),
			@ApiResponse(code = 400, message = "Bad Input", response = ExceptionResponseList.class),
			@ApiResponse(code = 403, message = "UnAuthenticated Request", response = ExceptionResponseList.class),
			@ApiResponse(code = 404, message = "Resource not found", response = ExceptionResponseList.class),
			@ApiResponse(code = 500, message = "Internal Server error", response = ExceptionResponseList.class) })
	@PostMapping(value = "/signout")
	public ResponseEntity<Boolean> signout(@RequestBody TokenRequestDto tokenDto, Errors errors)
			throws InvalidInputException, ResourceNotFoundException {
		if (errors.hasErrors()) {
			String errMsg = "Authentication details are missing!!";
			throw new InvalidInputException(ServiceResponseEnum.SVC0002.formatResponseMessage(errMsg));
		}

		authenticationService.deleteToken(tokenDto);
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}

	@AccessControls
	@ApiOperation(value = "Refresh Authentication Token", response = AuthenticationResponseDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval", response = AuthenticationResponseDto.class),
			@ApiResponse(code = 400, message = "Bad Input", response = ExceptionResponseList.class),
			@ApiResponse(code = 403, message = "UnAuthenticated", response = ExceptionResponseList.class),
			@ApiResponse(code = 404, message = "Resource not found", response = ExceptionResponseList.class),
			@ApiResponse(code = 500, message = "Internal Server error", response = ExceptionResponseList.class) })
	@PostMapping(value = "/refresh-token")
	public ResponseEntity<AuthenticationResponseDto> refreshToken(@RequestBody TokenRequestDto tokenDto, Errors errors)
			throws InvalidInputException, InvalidTokenException, ResourceNotFoundException {
		if (errors.hasErrors()) {
			String errMsg = "Authentication details are missing!!";
			throw new InvalidInputException(ServiceResponseEnum.SVC0002.formatResponseMessage(errMsg));
		}
		AuthenticationResponseDto tokenResponse = authenticationService.refreshToken(tokenDto);
		return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
	}

	@AccessControls
	@ApiOperation(value = "Change Password", response = Boolean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful retrieval", response = Boolean.class),
			@ApiResponse(code = 400, message = "Bad Input", response = ExceptionResponseList.class),
			@ApiResponse(code = 403, message = "Invalid Credentials", response = ExceptionResponseList.class),
			@ApiResponse(code = 500, message = "Internal Server error", response = ExceptionResponseList.class) })
	@PreAuthorize("hasAuthority('" + ApplicationConstants.ROLE_ADMIN + "') or hasAuthority('"
			+ ApplicationConstants.ROLE_USER + "')")
	@PostMapping(value = "/change-password")
	public ResponseEntity<Boolean> changePassword(Principal principal,
			@RequestBody ChangePasswordRequestDto passwordDto, Errors errors)
			throws InvalidInputException, WrongCredentialsException {
		if (errors.hasErrors()) {
			String errMsg = "Input details are missing!!";
			throw new InvalidInputException(ServiceResponseEnum.SVC0002.formatResponseMessage(errMsg));
		}
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) principal;
		UserContext userContext = (UserContext) authToken.getPrincipal();
		authenticationService.changePassword(userContext.getUserName(), passwordDto.getCurrentPassword(),
				passwordDto.getNewPassword());
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}

}