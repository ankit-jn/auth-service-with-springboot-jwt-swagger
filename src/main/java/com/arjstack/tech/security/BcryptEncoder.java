package com.arjstack.tech.security;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptEncoder implements
		org.springframework.security.crypto.password.PasswordEncoder {

	private static final int LONGROUNDS = 10;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.crypto.password.PasswordEncoder#encode(java
	 * .lang .CharSequence)
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return BCrypt.hashpw((String) rawPassword, BCrypt.gensalt(LONGROUNDS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.crypto.password.PasswordEncoder#matches(
	 * java. lang.CharSequence, java.lang.String)
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return BCrypt.checkpw((String) rawPassword, encodedPassword);
	}
}
