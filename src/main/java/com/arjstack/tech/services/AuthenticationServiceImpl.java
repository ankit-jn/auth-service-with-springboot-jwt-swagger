package com.arjstack.tech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.arjstack.tech.context.UserContext;
import com.arjstack.tech.dto.TokenDto;
import com.arjstack.tech.dto.requests.AuthenticationRequestDto;
import com.arjstack.tech.dto.requests.TokenRequestDto;
import com.arjstack.tech.dto.responses.AuthenticationResponseDto;
import com.arjstack.tech.enums.ServiceResponseEnum;
import com.arjstack.tech.exceptions.InvalidTokenException;
import com.arjstack.tech.exceptions.ResourceNotFoundException;
import com.arjstack.tech.exceptions.WrongCredentialsException;
import com.arjstack.tech.models.Users;
import com.arjstack.tech.repositories.UsersRepository;
import com.arjstack.tech.security.BcryptEncoder;

@Service("authenticationService")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private BcryptEncoder bcryptEncoder;

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private TokenManager tokenManager;

	@Override
	public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationDto)
			throws WrongCredentialsException, ResourceNotFoundException {

		Users user = userRepository.findUser(authenticationDto.getUserName().toLowerCase());

		if (user == null) {
			throw new WrongCredentialsException(ServiceResponseEnum.SVC1001.getResponseMessage());
		}

		if (!bcryptEncoder.matches(authenticationDto.getPassword(), user.getPassword())) {
			throw new WrongCredentialsException(ServiceResponseEnum.SVC1001.getResponseMessage());
		}

		TokenDto token = tokenManager.generateToken(user);

		AuthenticationResponseDto tokenResponse = new AuthenticationResponseDto(user.getUserName(), user.getRole(),
				token);

		return tokenResponse;
	}

	public void deleteToken(TokenRequestDto tokenRequestDto) throws ResourceNotFoundException {

		Users user = userRepository.findUser(tokenRequestDto.getUserName());

		if (user == null) {
			String errMsg = String.format("User (%s)", tokenRequestDto.getUserName());
			throw new ResourceNotFoundException(ServiceResponseEnum.SVC9001.formatResponseMessage(errMsg));
		}

		tokenManager.deleteToken(tokenRequestDto);
	}

	public AuthenticationResponseDto refreshToken(TokenRequestDto tokenRequestDto)
			throws ResourceNotFoundException, InvalidTokenException {

		Users user = userRepository.findUser(tokenRequestDto.getUserName());

		if (user == null) {
			String errMsg = String.format("User (%s)", tokenRequestDto.getUserName());
			throw new ResourceNotFoundException(ServiceResponseEnum.SVC9001.formatResponseMessage(errMsg));
		}

		TokenDto token = tokenManager.refreshToken(user, tokenRequestDto);
		AuthenticationResponseDto response = new AuthenticationResponseDto(user.getUserName(), user.getRole(), token);
		return response;

	}

	@Override
	public void changePassword(String userName, String oldPassword, String newPassword)
			throws WrongCredentialsException {
		Users user = userRepository.findByUserName(userName);
		if (!bcryptEncoder.matches(oldPassword, user.getPassword())) {
			throw new WrongCredentialsException(ServiceResponseEnum.SVC1001.getResponseMessage());
		}
		String encodedPassword = bcryptEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}

	@Override
	public void prepareUserContext(UserContext userContext, String userName) {
		Users user = userRepository.findByUserName(userName);
		userContext.setUserName(userName);
		userContext.setRole(user.getRole());
	}

}
