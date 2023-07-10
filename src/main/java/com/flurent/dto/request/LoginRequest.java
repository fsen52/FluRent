package com.flurent.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
	
	@Email(message="Please provide a correct email")
	private String email;
	
	@NotNull(message="Please provide a password")
	private String password;
	

}
