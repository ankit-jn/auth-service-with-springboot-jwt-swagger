package com.arjstack.tech.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.arjstack.tech.constants.ApplicationConstants;
import com.arjstack.tech.dto.UserDto;
import com.arjstack.tech.dto.requests.RegisterUserDto;
import com.arjstack.tech.dto.responses.UserListDto;
import com.arjstack.tech.exceptions.InvalidInputException;
import com.arjstack.tech.models.Users;
import com.arjstack.tech.repositories.UsersRepository;
import com.arjstack.tech.security.BcryptEncoder;
import com.arjstack.tech.utils.DateUtil;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BcryptEncoder bcryptEncoder;

	@Override
	public boolean isUserNameExist(String userName) {
		Users user = usersRepository.findByUserName(userName);
		if (user == null) {
			return false;
		}
		return true;
	}

	@Override
	public void registerUser(RegisterUserDto registrationDto) throws InvalidInputException {

		Users user = new Users();
		String encodedPassword = bcryptEncoder.encode(registrationDto.getPassword());

		user.setUserName(registrationDto.getUserName().toLowerCase());
		user.setPassword(encodedPassword);

		user.setRole(ApplicationConstants.ROLE_USER);

		user.setFirstName(registrationDto.getFirstName());
		user.setLastName(registrationDto.getLastName());
		user.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));

		usersRepository.save(user);
	}

	@Override
	public UserListDto getUsers() {
		UserListDto response = null;

		List<Users> users = usersRepository.getAllUsers();

		if (CollectionUtils.isEmpty(users)) {
			response = new UserListDto(new ArrayList<UserDto>());
			return response;
		}

		List<UserDto> studyLessonDtoList = users.stream().map(user -> {
			UserDto userDto = new UserDto(user.getUserName(), user.getFirstName(), user.getLastName(), user.getRole(),
					DateUtil.getISOLocalDate(user.getRegistrationDate()));

			return userDto;
		}).collect(Collectors.toList());

		response = new UserListDto(studyLessonDtoList);

		return response;
	}

}