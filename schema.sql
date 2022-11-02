DROP DATABASE IF EXISTS `auth-service`;

CREATE DATABASE `auth-service`;

USE `auth-service`;

-- Create tables

CREATE TABLE entity_generator (
	entity_name varchar(50) DEFAULT NULL,
	entity_value int(11) DEFAULT NULL
);

CREATE TABLE users (
	id bigint, 
	user_name varchar(20),
	password varchar(100),
	first_name varchar(25), 
	last_name varchar(25),
	role varchar(20),
	registration_date datetime
);

CREATE TABLE login_tokens (
	id bigint, 
	user_id bigint, 
	auth_token varchar(1000),
	refresh_token varchar(1000),
	issued_at bigint, 
	stale char(1)
);


ALTER TABLE users
ADD CONSTRAINT PK_users PRIMARY KEY (id);

ALTER TABLE login_tokens
ADD CONSTRAINT PK_login_tokens PRIMARY KEY (id);
