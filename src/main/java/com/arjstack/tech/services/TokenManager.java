package com.arjstack.tech.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.arjstack.tech.dto.TokenDto;
import com.arjstack.tech.dto.requests.TokenRequestDto;
import com.arjstack.tech.enums.ServiceResponseEnum;
import com.arjstack.tech.exceptions.InvalidTokenException;
import com.arjstack.tech.models.LoginToken;
import com.arjstack.tech.models.Users;
import com.arjstack.tech.repositories.LoginTokensRepository;
import com.arjstack.tech.utils.JwtTokenUtil;
import com.arjstack.tech.utils.ParsedToken;

@Service("tokenManager")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TokenManager {


	@Autowired
	private LoginTokensRepository tokenRepository;

	@Autowired
	private JwtTokenUtil tokenUtil;

	// 60 minutes for auth token
	private int AUTH_TOKEN_EXPIRY = 1 * 1000 * 60 * 60;
	
	// 48 hours for refresh token
	private int REFRESH_TOKEN_EXPIRY = 1 * 1000 * 60 * 60 * 48;

	public TokenDto generateToken(Users user) {
		LocalDateTime tokenIssuedAt = LocalDateTime.now();
		Timestamp tokenIssuedTimestamp = Timestamp.valueOf(tokenIssuedAt);
		Date tokenIssueTime = new Date(tokenIssuedTimestamp.getTime());
		Date authTokenExpiryTime = new Date(tokenIssuedTimestamp.getTime() + AUTH_TOKEN_EXPIRY);
		Date refreshTokenExpiryTime = new Date(tokenIssuedTimestamp.getTime() + REFRESH_TOKEN_EXPIRY);
		
		String authToken = tokenUtil.generateAuthToken(user.getUserName(), tokenIssueTime, authTokenExpiryTime);
		String refreshToken = tokenUtil.generateRefreshToken(user.getUserName(), tokenIssueTime, refreshTokenExpiryTime);
		
		LoginToken token = tokenRepository.findByUserName(user.getUserName());
		if (token == null) {
			token = new LoginToken();
		}
		token.setUser(user);
		token.setAuthToken(authToken);
		token.setRefreshToken(refreshToken);
		token.setStale("N");
		token.setIssuedAt(tokenIssueTime.getTime());

		TokenDto tokenDto = new TokenDto(authToken, refreshToken, tokenIssueTime.getTime(), AUTH_TOKEN_EXPIRY);

		tokenRepository.save(token);

		return tokenDto;
	}
	
	public TokenDto refreshToken(Users user, TokenRequestDto tokenRequestDto) throws InvalidTokenException {
		ParsedToken receivedToken = tokenUtil.unsignToken(tokenRequestDto.getToken());
		if (receivedToken == null || !tokenRequestDto.getUserName().equals(receivedToken.getUserName())) {
			throw new InvalidTokenException(ServiceResponseEnum.SVC1004.getResponseMessage());
		}

		LoginToken dbToken = tokenRepository.findByUserName(tokenRequestDto.getUserName());
		if (dbToken == null) {
			throw new InvalidTokenException(ServiceResponseEnum.SVC1004.getResponseMessage());
		}

		if (!dbToken.getRefreshToken().equals(tokenRequestDto.getToken())) {
			throw new InvalidTokenException(ServiceResponseEnum.SVC1004.getResponseMessage());
		}

		TokenDto newToken = this.generateToken(user);

		dbToken.setAuthToken(newToken.getAuthToken());
		dbToken.setRefreshToken(newToken.getRefreshToken());
		dbToken.setIssuedAt(newToken.getTokenIssueTime());
		dbToken.setStale("N");
		tokenRepository.save(dbToken);

		return newToken;

	}
	
	public Boolean deleteToken(TokenRequestDto tokenRequestDto) {
		LoginToken dbToken = tokenRepository.findByUserName(tokenRequestDto.getUserName());
		if (dbToken == null) {
			return Boolean.TRUE;
		}

		if (dbToken.getAuthToken().equals(tokenRequestDto.getToken())) {
			tokenRepository.delete(dbToken);
		}
		return Boolean.TRUE;
	}
	
	public boolean isTokenValid(String userName, String token) throws InvalidTokenException {

		LoginToken dbToken = tokenRepository.findByUserName(userName);
		if (dbToken == null) {
			return false;
		}

		return dbToken.getAuthToken().equals(token);

	}
}
