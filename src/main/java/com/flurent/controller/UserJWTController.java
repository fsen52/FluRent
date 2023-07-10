package com.flurent.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flurent.dto.request.RegisterRequest;
import com.flurent.dto.response.FlurentResponse;
import com.flurent.dto.response.ResponseMessage;
import com.flurent.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserJWTController {
	
	
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<FlurentResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
		
		userService.register(registerRequest);
		
		FlurentResponse response = new FlurentResponse();
		
		response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
		response.setSuccess(true);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}

}
