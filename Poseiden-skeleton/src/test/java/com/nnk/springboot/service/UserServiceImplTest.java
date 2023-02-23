package com.nnk.springboot.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.services.UserServieImpl;

public class UserServiceImplTest {

	private UserRepository userRepository;
	private UserService userService;
	User user;
	
	@BeforeEach
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userService = new UserServieImpl(userRepository);
		user = new User("testName", "password",  "testFullName", "USER");
		
	}
	
	@Test
	public void shouldLoadUserByUsernameTest() {
		when(userRepository.findByUsername(anyString())).thenReturn(user);
		
		UserDetails userTest = userService.loadUserByUsername("testName");
		
		Assertions.assertNotNull(userTest.getAuthorities());
		Assertions.assertEquals(new HashSet<>(Arrays.asList(new SimpleGrantedAuthority(user.getRole()))), userTest.getAuthorities());
		Assertions.assertTrue(userTest.getAuthorities().contains(new SimpleGrantedAuthority(user.getRole())));
		Assertions.assertEquals(user.getUsername(), userTest.getUsername());
		Assertions.assertEquals(user.getPassword(), userTest.getPassword());
	}
	
	@Test
	public void shouldLoadUserWithExceptionTest() {

		when(userRepository.findByUsername(anyString())).thenReturn(null);

	    assertThatExceptionOfType(UsernameNotFoundException.class)
	        .isThrownBy(() -> userService.loadUserByUsername("testName"))
	        .withMessage("User is not exists.")
	        .withNoCause();	
	}
	
	@Test
	public void shouldCreateUserTest() {
		
		when(userRepository.save(any())).thenReturn(user);
		
		User newUser = userService.create(user);
		
		Assertions.assertEquals("testName", newUser.getUsername());
		Assertions.assertEquals("password", newUser.getPassword());
		Assertions.assertEquals("testFullName", newUser.getFullname());
		Assertions.assertEquals("USER", newUser.getRole());
	}
	
	@Test
	public void shouldVerifIsUserExists() {
		when(userRepository.existsById(anyInt())).thenReturn(true);
		
		Assertions.assertTrue(userService.ifUserExists(anyInt()));
	}
}
