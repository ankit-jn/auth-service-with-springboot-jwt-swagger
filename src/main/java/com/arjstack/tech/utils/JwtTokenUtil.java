package com.arjstack.tech.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arjstack.tech.enums.ServiceResponseEnum;
import com.arjstack.tech.exceptions.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JwtTokenUtil {

	private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@Value("${security.jwt.secret}")
	private String jwtSecret;

	@Value("${security.jwt.subject}")
	private String jwtSubject;

	@Value("${security.jwt.issuer}")
	private String jwtIssuer;

	@Value("${security.jwt.Id}")
	private String jwtId;

	@Value("${security.jwt.audience}")
	private String jwtAudience;

	public String generateAuthToken(String userName, Date tokenIssueTime, Date tokenExpiryTime) {
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder().setId(jwtId).setIssuer(jwtIssuer).setSubject(jwtSubject)
				.claim("userName", userName).setIssuedAt(tokenIssueTime).setExpiration(tokenExpiryTime)
				.signWith(signatureAlgorithm, signingKey);
		return builder.compact();
	}
	
	public String generateRefreshToken(String userName, Date tokenIssueTime, Date tokenExpiryTime) {
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder().setId(jwtId).setIssuer(jwtIssuer).setSubject(jwtSubject)
				.claim("userName", userName).setIssuedAt(tokenIssueTime).setExpiration(tokenExpiryTime)
				.signWith(signatureAlgorithm, signingKey);
		return builder.compact();
	}

	public ParsedToken unsignToken(String token) throws InvalidTokenException {
		ParsedToken parsedToken = null;

		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
					.parseClaimsJws(token).getBody();
			String nickName = (String) claims.get("nickName");

			Long issuedAt = claims.getIssuedAt().getTime();

			if (nickName == null || issuedAt == null) {
				return parsedToken;
			}
			parsedToken = new ParsedToken(nickName, issuedAt);
		} catch (Exception e) {
			throw new InvalidTokenException(ServiceResponseEnum.SVC1004.getResponseMessage());
		}
		return parsedToken;
	}


}
