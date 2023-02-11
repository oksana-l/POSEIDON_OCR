package com.nnk.springboot.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nnk.springboot.domain.User;

public interface UserService extends UserDetailsService {

	User create(User user);
}
