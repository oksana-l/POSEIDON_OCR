package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.dto.UserDTO;

public interface UserService extends UserDetailsService {

	User create(UserDTO userDto);
	
	boolean ifUserExists(UserDTO userDto);

	List<User> findAllUsers();

	User findUserByUsername(String username);

	Optional<UserDTO> findUserById(Integer id);

	User updateUser(Integer id, UserDTO userDto);

	void deleteUser(Integer id);
}
