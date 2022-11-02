package com.arjstack.tech.config;

import java.awt.print.Pageable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;

import com.arjstack.tech.annotations.AccessControls;
import com.arjstack.tech.annotations.AccessEntry;
import com.arjstack.tech.annotations.Management;
import com.arjstack.tech.annotations.Registration;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Bean
	public Docket registrationDocket() {

		String groupName = "Registration";
		String apiTitle = "Registration API";
		String apiDescription = "This API is for sign-up";

		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo(apiTitle, apiDescription))
				.forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class)
				.ignoredParameterTypes(Pageable.class).ignoredParameterTypes(java.sql.Date.class)
				.ignoredParameterTypes(Principal.class)
				.ignoredParameterTypes(org.springframework.validation.Errors.class)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class).useDefaultResponseMessages(false);

		docket = docket.groupName(groupName).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Registration.class)).build();
		return docket;
	}

	@Bean
	public Docket accessEntryDocket() {
		String groupName = "Authentication";
		String apiTitle = "Authentication API";
		String apiDescription = "This API is to for Sign-in";

		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo(apiTitle, apiDescription))
				.forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class)
				.ignoredParameterTypes(Pageable.class).ignoredParameterTypes(java.sql.Date.class)
				.ignoredParameterTypes(Principal.class)
				.ignoredParameterTypes(org.springframework.validation.Errors.class)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class).useDefaultResponseMessages(false);

		docket = docket.groupName(groupName).select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(AccessEntry.class)).build();
		return docket;
	}

	@Bean
	public Docket accessControlDocket() {

		String groupName = "Access Management";
		String apiTitle = "Access Management API";
		String apiDescription = "This is a group of access management APIs";

		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo(apiTitle, apiDescription))
				.forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class)
				.ignoredParameterTypes(Pageable.class).ignoredParameterTypes(java.sql.Date.class)
				.ignoredParameterTypes(Principal.class)
				.ignoredParameterTypes(org.springframework.validation.Errors.class)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class).useDefaultResponseMessages(false)
				.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()));

		docket = docket.groupName(groupName).select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(AccessControls.class)).build();
		return docket;
	}

	@Bean
	public Docket managementDocket() {

		String groupName = "User Management";
		String apiTitle = "User Management API";
		String apiDescription = "This is a group of user management APIs (Admin is allowed)";

		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo(apiTitle, apiDescription))
				.forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class)
				.ignoredParameterTypes(Pageable.class).ignoredParameterTypes(java.sql.Date.class)
				.ignoredParameterTypes(Principal.class)
				.ignoredParameterTypes(org.springframework.validation.Errors.class)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class).useDefaultResponseMessages(false)
				.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()));

		docket = docket.groupName(groupName).select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(Management.class)).build();
		return docket;
	}

	private ApiInfo apiInfo(String title, String description) {

		Contact contact = new Contact("Ankit Jain", "https://www.arjstack.com/#/ankit", "ankii.jain@gmail.com");
		String termsOfServiceUrl = "";
		String apiVersion = "1.0.0";
		String license = "";
		String licenseUrl = "";
		List<VendorExtension> vext = new ArrayList<>();

		ApiInfo apiInfo = new ApiInfo(title, description, apiVersion, termsOfServiceUrl, contact, license, licenseUrl,
				vext);
		return apiInfo;
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
	}

	private static Predicate<RequestHandler> exactPackage(final String pkg) {
		return input -> input.declaringClass().getPackage().getName().equals(pkg);
	}
}