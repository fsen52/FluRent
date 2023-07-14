package com.flurent.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.flurent.domain.User;
import com.flurent.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	List<UserDTO> map(List<User> user);

}
