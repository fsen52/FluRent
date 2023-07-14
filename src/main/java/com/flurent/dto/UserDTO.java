package com.flurent.dto;

import java.util.HashSet;
import java.util.Set;

import com.flurent.domain.Role;
import com.flurent.domain.enums.RoleType;

import lombok.Data;

@Data
public class UserDTO {

private Long id;
	
	private String firstName;
	

	private String lastName;
	
	private String email;
	
	private String phoneNumber;
	
	private String address;
	
	private String zipCode;
	
	private Boolean builtIn;
	
	private Set<String> roles;
	
	
	public void setRoles(Set<Role> roles) {
		
		Set<String> rolesStr = new HashSet<>();
		
		roles.forEach(r->{
			if(r.getName().equals(RoleType.ROLE_ADMIN))
				rolesStr.add("Administrator");
			else
				rolesStr.add("Customer");
		});
	}
	
	
}
