package com.arjstack.tech.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arjstack.tech.annotations.Registration;
import com.arjstack.tech.dto.requests.RegisterUserDto;
import com.arjstack.tech.enums.ServiceResponseEnum;
import com.arjstack.tech.exceptions.ExceptionResponseList;
import com.arjstack.tech.exceptions.InvalidInputException;
import com.arjstack.tech.exceptions.ResourceAlreadyExistException;
import com.arjstack.tech.services.UsersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Registration
@Api(value = "Registration", description = "Registration API", produces = "application/json", tags = { "Registration" })
@CrossOrigin
@RestController
@RequestMapping("/signup")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegistrationController {

	@Autowired
	private UsersService usersService;

	@ApiOperation(value = "User Signup", response = Boolean.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval", response = Boolean.class),
			@ApiResponse(code = 400, message = "Bad Input", response = ExceptionResponseList.class),
			@ApiResponse(code = 500, message = "Internal Server error", response = ExceptionResponseList.class) })
	@PostMapping
	public ResponseEntity<Boolean> registerUser(@RequestBody @Valid RegisterUserDto registrationDto,
			Errors errors) throws ResourceAlreadyExistException, InvalidInputException, Exception {

		if (errors.hasErrors()) {
			String errMsg = "User details are missing or not in correct format";
			throw new InvalidInputException(ServiceResponseEnum.SVC0002.formatResponseMessage(errMsg));
		}

		boolean isUserNameExist = usersService.isUserNameExist(registrationDto.getUserName());
		if (isUserNameExist) {
			String errMsg = String.format("User Name (%s)", registrationDto.getUserName());
			throw new ResourceAlreadyExistException(ServiceResponseEnum.SVC9002.formatResponseMessage(errMsg));
		}

		usersService.registerUser(registrationDto);
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}

}