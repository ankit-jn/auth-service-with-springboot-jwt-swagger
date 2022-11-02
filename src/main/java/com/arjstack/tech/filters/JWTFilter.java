package com.arjstack.tech.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.arjstack.tech.context.UserContext;
import com.arjstack.tech.enums.ServiceResponseEnum;
import com.arjstack.tech.exceptions.AuthenticationException;
import com.arjstack.tech.exceptions.InvalidTokenException;
import com.arjstack.tech.services.AuthenticationService;
import com.arjstack.tech.services.TokenManager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTFilter extends BasicAuthenticationFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer";

	private static final String ACCESS_API_PREFIX = "/access";
	private static final String MANAGEMENT_API_PREFIX = "/management";

	private ApplicationContext applicationContext;
	private String tokenEnv;

	private List<String> securedUrls = new ArrayList<>();

	public JWTFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		securedUrls.clear();
		securedUrls.add(ACCESS_API_PREFIX);
		securedUrls.add(MANAGEMENT_API_PREFIX);
	}

	@Override
	public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {

		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			String path = httpServletRequest.getServletPath();
			if (isUrlSecured(path)) {
				UsernamePasswordAuthenticationToken authentication = getAuthentication(httpServletRequest);
				if (authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
					filterChain.doFilter(servletRequest, servletResponse);
				}

				HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
				httpResponse.setContentLength(0);
				httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
			} else {
				filterChain.doFilter(servletRequest, servletResponse);
			}
		} catch (AuthenticationException e) {
			HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
			String errMessage = String.format("[ %s ] - %s", ServiceResponseEnum.SVC0002.getResponseCode(),
					e.getMessage());
			httpResponse.setContentLength(errMessage.length());
			httpResponse.sendError(HttpStatus.FORBIDDEN.value(), errMessage);
		} catch (InvalidTokenException e) {
			HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
			String errMessage = String.format("[ %s ] - %s", ServiceResponseEnum.SVC1003.getResponseCode(),
					e.getMessage());
			httpResponse.setContentLength(errMessage.length());
			httpResponse.sendError(HttpStatus.FORBIDDEN.value(), errMessage);
		} catch (Exception e) {
			HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
			String errMessage = String.format("[ %s ] - %s", ServiceResponseEnum.SVC0001.getResponseCode(),
					ServiceResponseEnum.SVC0001.getResponseMessage());
			httpResponse.setContentLength(errMessage.length());
			httpResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), errMessage);
		}
	}

	private String resolveToken(HttpServletRequest request) throws AuthenticationException {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (bearerToken == null) {
			throw new AuthenticationException(ServiceResponseEnum.SVC1002.getResponseMessage());
		}
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(7, bearerToken.length()).trim();
		}
		throw new AuthenticationException(ServiceResponseEnum.SVC1002.getResponseMessage());
	}

	private boolean isUrlSecured(final String url) {

		Integer securedSize = securedUrls.stream().filter(currentUrl -> url.startsWith(currentUrl))
				.collect(Collectors.toList()).size();

		if (securedSize > 0)
			return true;
		return false;
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest httpServletRequest)
			throws AuthenticationException, InvalidTokenException {
		String token = this.resolveToken(httpServletRequest);

		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(tokenEnv)).parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			throw new InvalidTokenException(ServiceResponseEnum.SVC1003.createResponseMessage());
		}
		if (claims == null) {
			throw new InvalidTokenException(ServiceResponseEnum.SVC1003.getResponseMessage());
		}
		String userName = (String) claims.get("userName");
		TokenManager tokenManager = (TokenManager) applicationContext.getBean("tokenManager");
		if (!tokenManager.isTokenValid(userName, token)) {
			throw new AuthenticationException(ServiceResponseEnum.SVC1002.getResponseMessage());
		}

		UserContext userContext = new UserContext();
		AuthenticationService authService = (AuthenticationService) applicationContext.getBean("authenticationService");
		authService.prepareUserContext(userContext, userName);
		List<GrantedAuthority> authorities = buildUserAuthority(userContext.getRole());

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userContext, null,
				authorities);
		return authentication;
	}

	private List<GrantedAuthority> buildUserAuthority(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	public void setTokenEnv(String tokenEnv) {
		this.tokenEnv = tokenEnv;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
