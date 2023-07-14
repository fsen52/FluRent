package com.flurent.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flurent.domain.Role;
import com.flurent.domain.User;
import com.flurent.domain.enums.RoleType;
import com.flurent.dto.UserDTO;
import com.flurent.dto.mapper.UserMapper;
import com.flurent.dto.request.RegisterRequest;
import com.flurent.exception.ConflictException;
import com.flurent.exception.ResourceNotFoundException;
import com.flurent.exception.message.ErrorMessage;
import com.flurent.repository.RoleRepository;
import com.flurent.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	
	private UserRepository userRepository;
	
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private UserMapper userMapper;
	
	public void register(RegisterRequest registerRequest) {
		
		if(userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, registerRequest.getEmail()));
		}
	
		String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
		
		Role role = roleRepository.findByName(RoleType.ROLE_CUSTOMER).orElseThrow(
				()-> new ResourceNotFoundException(
						String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_CUSTOMER.name())));
	
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		
		
		User user  = new User();
		
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encodedPassword);
		user.setAddress(registerRequest.getAddress());
		user.setPhoneNumber(registerRequest.getPhoneNumber());
		user.setZipCode(registerRequest.getZipCode());
		user.setRoles(roles);
		
		userRepository.save(user);
		
		}

	public List<UserDTO> getAllUsers(){
		List<User> users = userRepository.findAll();
		return userMapper.map(users);
	}
	
	public UserDTO findUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> 
			new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));
		return userMapper.userToUserDTO(user);
	}
}
