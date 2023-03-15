package com.nnk.springboot.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.dto.UserDTO;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserServieImpl implements UserService {

	private UserRepository userRepository;
	
	@Autowired
	public UserServieImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
	    if(user == null){
	        throw new UsernameNotFoundException("User is not exists.");
	    }
	    GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
	    
		return new org.springframework.security.core.userdetails.User(
				username, user.getPassword(), Arrays.asList(authority));
	}

	@Override
	public User create(UserDTO userDto) {
		User newUser = new User(userDto);
		return userRepository.save(newUser);
	}

	@Override
	public boolean ifUserExists(UserDTO userDto) {
		if (userDto.getId() == null) {
			
		return userRepository.findByUsername(userDto.getUsername()) != null ||
				userRepository.findByFullname(userDto.getFullname()) != null;
		}
		return userRepository.existsById(userDto.getId());
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<UserDTO> findUserById(Integer id) {
		return userRepository.findById(id).map(user -> new UserDTO(user));
	}

	@Override
	public User updateUser(Integer id, UserDTO userDto) {
		User user = userRepository.findById(id).get();
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		user.setFullname(userDto.getFullname());
		user.setRole(userDto.getRole());
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
}
