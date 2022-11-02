package com.arjstack.tech.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arjstack.tech.annotations.Management;
import com.arjstack.tech.constants.ApplicationConstants;
import com.arjstack.tech.dto.responses.UserListDto;
import com.arjstack.tech.exceptions.AuthorizationException;
import com.arjstack.tech.exceptions.ExceptionResponseList;
import com.arjstack.tech.services.UsersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Management", description = "User Management API", produces = "application/json", tags = {
		"User Management" })
@CrossOrigin
@RestController
@RequestMapping("/management")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserManagementController {

	@Autowired
	private UsersService userService;

	@Management
	@ApiOperation(value = "Show all users", response = UserListDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval", response = UserListDto.class),			
			@ApiResponse(code = 401, message = "UnAuthorized Request", response = ExceptionResponseList.class),
			@ApiResponse(code = 403, message = "UnAuthenticated Request", response = ExceptionResponseList.class),
			@ApiResponse(code = 500, message = "Internal Server error", response = ExceptionResponseList.class) })
	@PreAuthorize("hasAuthority('" + ApplicationConstants.ROLE_ADMIN + "')")
	@GetMapping(value = "/users")
	public ResponseEntity<UserListDto> refreshToken(Principal principal) throws AuthorizationException {
		UserListDto users = userService.getUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

}