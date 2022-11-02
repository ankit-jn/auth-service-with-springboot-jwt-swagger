package com.arjstack.tech.exceptions.handlers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.arjstack.tech.enums.ServiceResponseEnum;
import com.arjstack.tech.exceptions.AuthorizationException;
import com.arjstack.tech.exceptions.ExceptionResponse;
import com.arjstack.tech.exceptions.ExceptionResponseList;
import com.arjstack.tech.exceptions.InvalidInputException;
import com.arjstack.tech.exceptions.InvalidTokenException;
import com.arjstack.tech.exceptions.ResourceAlreadyExistException;
import com.arjstack.tech.exceptions.ResourceNotFoundException;
import com.arjstack.tech.exceptions.WrongCredentialsException;
import com.arjstack.tech.utils.LogWrapper;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);
	private List<ExceptionResponse> exceptionResponseList = null;

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponseList> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ServiceResponseEnum.SVC0001.getResponseCode(),
				ex.getMessage(), request.getDescription(false));
		String errMessage = String.format("Error -> [%s] - %s", ServiceResponseEnum.SVC0001.getResponseCode(),
				ex.getMessage());
		LogWrapper.getInstance().log(logger, Level.ERROR, ex, errMessage);
		exceptionResponseList = new ArrayList<>();
		exceptionResponseList.add(exceptionResponse);
		ExceptionResponseList exceptionResponseView = new ExceptionResponseList(exceptionResponseList);

		return new ResponseEntity<>(exceptionResponseView, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ExceptionResponseList> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ServiceResponseEnum.SVC1006.getResponseCode(),
				ex.getMessage(), request.getDescription(false));
		String errMessage = String.format("Error -> [%s] - %s", ServiceResponseEnum.SVC1006.getResponseCode(),
				ex.getMessage());
		LogWrapper.getInstance().log(logger, Level.ERROR, ex, errMessage);
		exceptionResponseList = new ArrayList<>();
		exceptionResponseList.add(exceptionResponse);
		ExceptionResponseList exceptionResponseView = new ExceptionResponseList(exceptionResponseList);

		return new ResponseEntity<>(exceptionResponseView, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public final ResponseEntity<ExceptionResponseList> handleInvalidInputException(InvalidInputException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ServiceResponseEnum.SVC0002.getResponseCode(),
				ex.getMessage(), request.getDescription(false));
		String errMessage = String.format("Error -> [%s] - %s", ServiceResponseEnum.SVC0002.getResponseCode(),
				ex.getMessage());
		LogWrapper.getInstance().log(logger, Level.ERROR, ex, errMessage);
		exceptionResponseList = new ArrayList<>();
		exceptionResponseList.add(exceptionResponse);
		ExceptionResponseList exceptionResponseView = new ExceptionResponseList(exceptionResponseList);
		return new ResponseEntity<>(exceptionResponseView, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(WrongCredentialsException.class)
	public final ResponseEntity<ExceptionResponseList> handleWrongCredentialsException(WrongCredentialsException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ServiceResponseEnum.SVC1001.getResponseCode(),
				ex.getMessage(), request.getDescription(false));
		String errMessage = String.format("Error -> [%s] - %s", ServiceResponseEnum.SVC1001.getResponseCode(),
				ex.getMessage());
		LogWrapper.getInstance().log(logger, Level.ERROR, ex, errMessage);
		exceptionResponseList = new ArrayList<ExceptionResponse>();
		exceptionResponseList.add(exceptionResponse);
		ExceptionResponseList exceptionResponseView = new ExceptionResponseList(exceptionResponseList);
		return new ResponseEntity<>(exceptionResponseView, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public final ResponseEntity<ExceptionResponseList> handleInvalidTokenException(InvalidTokenException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ServiceResponseEnum.SVC1004.getResponseCode(),
				ex.getMessage(), request.getDescription(false));
		String errMessage = String.format("Error -> [%s] - %s", ServiceResponseEnum.SVC1004.getResponseCode(),
				ex.getMessage());
		LogWrapper.getInstance().log(logger, Level.ERROR, ex, errMessage);
		exceptionResponseList = new ArrayList<ExceptionResponse>();
		exceptionResponseList.add(exceptionResponse);
		ExceptionResponseList exceptionResponseView = new ExceptionResponseList(exceptionResponseList);
		return new ResponseEntity<>(exceptionResponseView, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AuthorizationException.class)
	public final ResponseEntity<ExceptionResponseList> handleAuthorizationException(AuthorizationException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ServiceResponseEnum.SVC1006.getResponseCode(),
				ex.getMessage(), request.getDescription(false));
		String errMessage = String.format("Error -> [%s] - %s", ServiceResponseEnum.SVC1006.getResponseCode(),
				ex.getMessage());
		LogWrapper.getInstance().log(logger, Level.ERROR, ex, errMessage);
		exceptionResponseList = new ArrayList<ExceptionResponse>();
		exceptionResponseList.add(exceptionResponse);
		ExceptionResponseList exceptionResponseView = new ExceptionResponseList(exceptionResponseList);
		return new ResponseEntity<>(exceptionResponseView, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponseList> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ServiceResponseEnum.SVC9001.getResponseCode(),
				ex.getMessage(), request.getDescription(false));
		String errMessage = String.format("Error -> [%s] - %s", ServiceResponseEnum.SVC9001.getResponseCode(),
				ex.getMessage());
		LogWrapper.getInstance().log(logger, Level.ERROR, ex, errMessage);
		exceptionResponseList = new ArrayList<ExceptionResponse>();
		exceptionResponseList.add(exceptionResponse);
		ExceptionResponseList exceptionResponseView = new ExceptionResponseList(exceptionResponseList);
		return new ResponseEntity<>(exceptionResponseView, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceAlreadyExistException.class)
	public final ResponseEntity<ExceptionResponseList> handleResourceAlreadyExistException(
			ResourceAlreadyExistException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ServiceResponseEnum.SVC9002.getResponseCode(),
				ex.getMessage(), request.getDescription(false));
		String errMessage = String.format("Error -> [%s] - %s", ServiceResponseEnum.SVC9002.getResponseCode(),
				ex.getMessage());
		LogWrapper.getInstance().log(logger, Level.ERROR, ex, errMessage);
		exceptionResponseList = new ArrayList<ExceptionResponse>();
		exceptionResponseList.add(exceptionResponse);
		ExceptionResponseList exceptionResponseView = new ExceptionResponseList(exceptionResponseList);
		return new ResponseEntity<>(exceptionResponseView, HttpStatus.BAD_REQUEST);
	}

}
