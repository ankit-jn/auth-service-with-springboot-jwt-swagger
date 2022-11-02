package com.arjstack.tech.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "login_tokens")
public class LoginToken {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "login_tokens_seq_gen")
	@TableGenerator(name = "login_tokens_seq_gen", table = "entity_generator", pkColumnName = "entity_name", valueColumnName = "entity_value", pkColumnValue = "login_tokens_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@Column(name = "auth_token")
	private String authToken;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "issued_at")
	private Long issuedAt;
	
	@Column(name = "stale")
	private String stale;
}
