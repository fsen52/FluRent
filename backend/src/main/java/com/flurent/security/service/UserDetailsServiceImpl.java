package com.flurent.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flurent.domain.User;
import com.flurent.exception.message.ErrorMessage;
import com.flurent.repository.UserRepository;

import lombok.AllArgsConstructor;


//Wenn wir möchten jwt benutzen, müssen wir unsere User details wieder formattieren.

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new UsernameNotFoundException(String.format(
						ErrorMessage.USER_NOT_FOUND_MESSAGE, email)));
		
		
		return UserDetailsImpl.build(user);
	}

}
