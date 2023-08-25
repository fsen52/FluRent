package com.flurent.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flurent.domain.User;
import com.flurent.dto.UserDTO;
import com.flurent.dto.request.AdminUserUpdateRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	List<UserDTO> map(List<User> user);
	
	UserDTO userToUserDTO(User user);

	@Mapping(target="id", ignore=true)
	@Mapping(target="roles", ignore=true)
	User adminUserUpdateRequestToUser(AdminUserUpdateRequest request);
	
}
