package com.flurent.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flurent.domain.Role;
import com.flurent.domain.User;
import com.flurent.domain.enums.RoleType;
import com.flurent.dto.UserDTO;
import com.flurent.dto.mapper.UserMapper;
import com.flurent.dto.request.RegisterRequest;
import com.flurent.dto.request.UpdatePasswordRequest;
import com.flurent.dto.request.UserUpdateRequest;
import com.flurent.exception.BadRequestException;
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

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, registerRequest.getEmail()));
		}

		String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

		Role role = roleRepository.findByName(RoleType.ROLE_CUSTOMER).orElseThrow(() -> new ResourceNotFoundException(
				String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_CUSTOMER.name())));

		Set<Role> roles = new HashSet<>();
		roles.add(role);

		User user = new User();

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

	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		return userMapper.map(users);
	}

	public Page<UserDTO> getUserByPage(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);

		Page<UserDTO> dtoPage = users.map(new Function<User, UserDTO>() {

			@Override
			public UserDTO apply(User user) {
				return userMapper.userToUserDTO(user);
			}
		});

		return dtoPage;

	}

	public UserDTO findUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));
		return userMapper.userToUserDTO(user);
	}

	public void updatePassword(Long id, UpdatePasswordRequest passwordRequest) {

		User user = userRepository.findById(id).get();

		if (user.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMITTED_PROCESS_MESSAGE);
		}

		if (!BCrypt.hashpw(passwordRequest.getOldPassword(), user.getPassword()).equals(user.getPassword())) {
			throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED);

		}

		String hashedPassword = passwordEncoder.encode(passwordRequest.getNewPassword());

		user.setPassword(hashedPassword);

		userRepository.save(user);

	}

	@Transactional
	public void updateUser(Long id, UserUpdateRequest userUpdateRequest) {
		boolean emailExist = userRepository.existsByEmail( userUpdateRequest.getEmail());
		
		User user = userRepository.findById(id).get();
		
		if (user.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMITTED_PROCESS_MESSAGE);
		}
		
		if(emailExist && !userUpdateRequest.getEmail().equals(user.getEmail())) {
			throw new ConflictException(ErrorMessage.EMAIL_ALREADY_EXIST);
		}
		
		userRepository.update(id, userUpdateRequest.getFirstName(), userUpdateRequest.getLastName(), 
							userUpdateRequest.getEmail(), userUpdateRequest.getPhoneNumber(),
							userUpdateRequest.getAddress(), userUpdateRequest.getZipCode());
		
		
	}
	
	public void removeById(Long id) {
		User user = userRepository.findById(id).orElseThrow(()->new
				ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		
		if (user.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMITTED_PROCESS_MESSAGE);
		}
		
		userRepository.deleteById(id);
		
	}

	
	
}
