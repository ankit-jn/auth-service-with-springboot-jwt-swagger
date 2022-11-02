package com.arjstack.tech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.arjstack.tech.filters.JWTFilter;
import com.arjstack.tech.security.BcryptEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private static final String GENERAL_API = "/**";

	private static final String AUTHENTICATION_API = "/auth/**";
	private static final String ACCESS_API = "/access/**";
	private static final String REGISTRATION_API = "/signup/**";
	private static final String MANAGEMENT_API = "/management/**";

	private static final String[] AUTH_WHITELIST = {
			// -- swagger ui
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**" };

	@Value("${security.jwt.secret}")
	private String tokenEnv;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BcryptEncoder bcryptEncoder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter #configure(org.springframework.security.config.
	 * annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		JWTFilter filter = new JWTFilter(authenticationManager());
		filter.setTokenEnv(tokenEnv);
		filter.setApplicationContext(applicationContext);

		http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, AUTHENTICATION_API)
				.permitAll()

				.antMatchers(HttpMethod.GET, GENERAL_API).permitAll()
				.antMatchers(HttpMethod.POST, AUTHENTICATION_API).permitAll()
				.antMatchers(HttpMethod.POST, ACCESS_API).permitAll()
				.antMatchers(HttpMethod.POST, REGISTRATION_API).permitAll()
				.antMatchers(HttpMethod.GET, MANAGEMENT_API).permitAll()
				.antMatchers(AUTH_WHITELIST).permitAll()

				.anyRequest().authenticated().and().addFilter(filter).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bcryptEncoder);
	}

	/**
	 * @return
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
		corsConfiguration.addAllowedMethod(HttpMethod.PUT);
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

}
