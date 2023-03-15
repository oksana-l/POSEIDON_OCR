package com.nnk.springboot.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.dto.UserDTO;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.services.UserServieImpl;

public class UserServiceImplTest {

	private UserRepository userRepository;
	private UserService userService;
	UserDTO userDto;
	Optional<User> user;
	
	@BeforeEach
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userService = new UserServieImpl(userRepository);
		userDto = new UserDTO("testName", "password",  "testFullName", "USER");
		user = Optional.ofNullable(new User(userDto));
	}
	
	@Test
	public void shouldLoadUserByUsernameTest() {
		when(userRepository.findByUsername(anyString())).thenReturn(user.get());
		
		UserDetails userTest = userService.loadUserByUsername("testName");
		
		Assertions.assertNotNull(userTest.getAuthorities());
		Assertions.assertEquals(new HashSet<>(Arrays.asList(new SimpleGrantedAuthority(userDto.getRole()))), userTest.getAuthorities());
		Assertions.assertTrue(userTest.getAuthorities().contains(new SimpleGrantedAuthority(userDto.getRole())));
		Assertions.assertEquals(userDto.getUsername(), userTest.getUsername());
		Assertions.assertEquals(userDto.getPassword(), userTest.getPassword());
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
		
		when(userRepository.save(any())).thenReturn(user.get());
		
		User newUser = userService.create(userDto);
		
		Assertions.assertEquals("testName", newUser.getUsername());
		Assertions.assertEquals("password", newUser.getPassword());
		Assertions.assertEquals("testFullName", newUser.getFullname());
		Assertions.assertEquals("USER", newUser.getRole());
	}
	
	@Test
	public void shouldFindAllUsersTest() {
		List<User> userlist = new ArrayList<User>();
		userlist.add(user.get());
		
		when(userRepository.findAll()).thenReturn(userlist);
		
		List<User> newuserlist = userService.findAllUsers();
		
		Assertions.assertEquals(userlist.isEmpty(), newuserlist.isEmpty());
		Assertions.assertEquals(userlist.size(), newuserlist.size());
		Assertions.assertEquals("testName", newuserlist.get(0).getUsername());
		Assertions.assertEquals("password", newuserlist.get(0).getPassword());
		Assertions.assertEquals("testFullName", newuserlist.get(0).getFullname());
		Assertions.assertEquals("USER", newuserlist.get(0).getRole());
	}
	
	@Test
	public void shouldFindUserByIdTest( ) {
		when(userRepository.findById(anyInt())).thenReturn(user);
		
		Optional<UserDTO> userDTO = userService.findUserById(1);
		
		Assertions.assertEquals("testName", userDTO.get().getUsername());
		Assertions.assertEquals("password", userDTO.get().getPassword());
		Assertions.assertEquals("testFullName", userDTO.get().getFullname());
		Assertions.assertEquals("USER", userDTO.get().getRole());
	}
	
	@Test
	public void shouldFindUserByNameTest( ) {
		when(userRepository.findByUsername(any())).thenReturn(user.get());
		
		User user = userService.findUserByUsername("testName");
		
		Assertions.assertEquals("testName", user.getUsername());
		Assertions.assertEquals("password", user.getPassword());
		Assertions.assertEquals("testFullName", user.getFullname());
		Assertions.assertEquals("USER", user.getRole());
	}
	
	@Test
	public void shouldUpdateUserTest() {
		UserDTO newUserDto = new UserDTO();
		newUserDto.setUsername("First");
		newUserDto.setPassword("pass");
		newUserDto.setFullname("Premium");
		newUserDto.setRole("ADMIN");
		
		when(userRepository.findById(anyInt())).thenReturn(user);
		when(userRepository.save(any())).thenReturn(new User(newUserDto));
		
		User updatedUser = userService.updateUser(1, newUserDto);
		
		Assertions.assertEquals("First", updatedUser.getUsername());
		Assertions.assertEquals("pass", updatedUser.getPassword());
		Assertions.assertEquals("Premium", updatedUser.getFullname());
		Assertions.assertEquals("ADMIN", updatedUser.getRole());
	}
	
	@Test
	public void shouldDeleteUserByIdTest() {
		User user = new User();
		user.setId(1);
		
		userService.deleteUser(1);
		
		verify(userRepository).deleteById(1);
	}
	
	@Test
	public void shouldVerifIsUserExists() {
		userDto.setId(1);
		when(userRepository.existsById(1)).thenReturn(true);
		
		Assertions.assertTrue(userService.ifUserExists(userDto));
	}
}
