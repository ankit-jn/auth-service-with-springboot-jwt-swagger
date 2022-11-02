package com.arjstack.tech;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.arjstack.tech.constants.ApplicationConstants;
import com.arjstack.tech.models.Users;
import com.arjstack.tech.repositories.UsersRepository;
import com.arjstack.tech.security.BcryptEncoder;

@Component
public class InitData implements CommandLineRunner {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BcryptEncoder bcryptEncoder;

    @Override
    public void run(String...args) throws Exception {
    	Users user = usersRepository.findUser("admin");

		if (user == null) {
			this.createAdmin();
		}
		
    }
    
    private void createAdmin() {
    	Users user = new Users();
		String encodedPassword = bcryptEncoder.encode("Admin@1234");

		user.setUserName("admin");
		user.setPassword(encodedPassword);

		user.setRole(ApplicationConstants.ROLE_ADMIN);

		user.setFirstName("Ankit");
		user.setLastName("Jain");
		user.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));

		usersRepository.save(user);
    }
}
