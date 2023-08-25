package com.flurent.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flurent.dto.UserDTO;
import com.flurent.dto.request.AdminUserUpdateRequest;
import com.flurent.dto.request.UpdatePasswordRequest;
import com.flurent.dto.request.UserUpdateRequest;
import com.flurent.dto.response.FlurentResponse;
import com.flurent.dto.response.ResponseMessage;
import com.flurent.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

	private UserService userService;

	@GetMapping("/auth/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<UserDTO> findSelfUserById(HttpServletRequest request) {

		Long id = (Long) request.getAttribute("id");
		UserDTO userDTO = userService.findUserById(id);
		return ResponseEntity.ok(userDTO);

	}

	@GetMapping("/auth/pages")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<UserDTO>> getAllUserByPage(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam("sort") String prop,
			@RequestParam("direction") Direction direction) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));

		Page<UserDTO> usersDTOPage = userService.getUserByPage(pageable);

		return ResponseEntity.ok(usersDTOPage);
	}
	
	
	@GetMapping("/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDTO> findUserByPathId(@PathVariable Long id) {

		UserDTO userDTO = userService.findUserById(id);
		return ResponseEntity.ok(userDTO);

	}
	
	@PatchMapping("/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FlurentResponse> updateSelfPassword(HttpServletRequest httpServletRequest, @RequestBody UpdatePasswordRequest passwordRequest ){
		Long id = (Long) httpServletRequest.getAttribute("id");
		userService.updatePassword(id, passwordRequest);
		
		FlurentResponse response=new FlurentResponse();
		response.setMessage(ResponseMessage.PASSWORD_CHANGE_MESSAGE);
		response.setSuccess(true);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<FlurentResponse> updateSelfUser(HttpServletRequest httpServletRequest, @Valid @RequestBody UserUpdateRequest userUpdateRequest ){
		Long id = (Long) httpServletRequest.getAttribute("id");
		userService.updateUser(id, userUpdateRequest);
		
		FlurentResponse response=new FlurentResponse();
		response.setMessage(ResponseMessage.UPDATE_RESPONSE_MESSAGE);
		response.setSuccess(true);
		
		return ResponseEntity.ok(response);
		
	}
	
	
	@PutMapping("/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FlurentResponse> adminUpdateUser(@PathVariable Long id, @Valid @RequestBody AdminUserUpdateRequest adminUserUpdateRequest ){
		userService.adminUpdateUser(id, adminUserUpdateRequest);
		
		FlurentResponse response=new FlurentResponse();
		response.setMessage(ResponseMessage.UPDATE_RESPONSE_MESSAGE);
		response.setSuccess(true);
		
		return ResponseEntity.ok(response);
		
	}
	
	@DeleteMapping("/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FlurentResponse> deleteUserById(@PathVariable Long id) {
	
		userService.removeById(id);
		
		
		FlurentResponse response=new FlurentResponse();
		response.setMessage(ResponseMessage.DELETE_RESPONSE_MESSAGE);
		response.setSuccess(true);
		
		return ResponseEntity.ok(response);
		
	}

}
