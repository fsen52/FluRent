package com.flurent.dto.request;

import java.util.Set;

import javax.persistence.Column;

import lombok.Data;

@Data
public class AdminUserUpdateRequest {

	@Column(length = 50, nullable = false)
	private String firstName;

	@Column(length = 50, nullable = false)
	private String lastName;

	@Column(length = 20, nullable = false, unique = true)
	private String email;

	private String password;

	@Column(length = 14, nullable = false)
	private String phoneNumber;

	@Column(length = 120, nullable = false)
	private String address;

	@Column(length = 14, nullable = false)
	private String zipCode;

	private Boolean builtIn;

	private Set<String> roles;

}
