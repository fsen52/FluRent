package com.flurent.dto.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterRequest {

	@Size(max = 50)
	@NotNull(message = "Please provide your first name")
	private String firstName;
	

	@Size(max = 50)
	@NotNull(message = "Please provide your last name")
	private String lastName;
	
	@Email(message = "Please provide valid e-mail")
	@Size(min = 5, max = 150)
	@NotNull(message = "Please provide your e-mail")	
	private String email;
	
	@Size(min=4, max = 120, message = "Please use min 4 max 20 character for password" )
	@NotNull(message = "Please provide your password")
	private String password;
	
	@Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", 
			message = "Please provide valid phone number")
	@Size(min = 14, max = 14)
	@NotNull(message = "Please provide your phone number")
	private String phoneNumber;
	
	@Size(min=5, max = 250)
	@NotNull(message = "Please provide your address")
	private String address;
	
	@Size(min=5, max = 20, message = "Incorrect zip code")
	@NotNull(message = "Please provide your zip code")
	private String zipCode;
}
