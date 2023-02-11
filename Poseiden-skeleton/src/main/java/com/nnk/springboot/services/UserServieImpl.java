package com.nnk.springboot.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
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
	        throw new UsernameNotFoundException("User not authorized.");
	    }
	    GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
	    
		return new org.springframework.security.core.userdetails.User(
				username, user.getPassword(), Arrays.asList(authority));
	}

	@Override
	public User create(User user) {
		User newUser = new User(user.getUsername(), user.getPassword(),  user.getFullname(),
								user.getRole());
		return userRepository.save(newUser);
	}

}
