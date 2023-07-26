package com.flurent.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flurent.dto.UserDTO;
import com.flurent.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	UserService userService;
	
	@InjectMocks
	UserController userController;
	
	
	@Test
	void findSelfUserByIdTest() {
		UserDTO userDTO=new UserDTO();
		
		userDTO.setId(5L);
		userDTO.setEmail("a@b.c");
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		
		when(userService.findUserById(5L)).thenReturn(userDTO);
		
		when(request.getAttribute("id")).thenReturn(5L);
		
		ResponseEntity<UserDTO> userResponse = userController.findSelfUserById(request);

		assertAll(()-> assertNotNull(userResponse.getBody()),
					()->assertEquals(5L, userResponse.getBody().getId()),
					()->assertEquals(HttpStatus.OK, userResponse.getStatusCode()));
		
	
	}
	
	
}
