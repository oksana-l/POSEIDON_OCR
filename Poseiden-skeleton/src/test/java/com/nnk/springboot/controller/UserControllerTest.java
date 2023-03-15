package com.nnk.springboot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.dto.UserDTO;
import com.nnk.springboot.services.UserService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = UserController.class)
@WebMvcTest
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	private final static String TEST_USER_AUTH = "Admin";
	Optional<UserDTO> userDto;
	
	@BeforeEach
	public void setUp() {
		userDto = Optional.ofNullable(new UserDTO());
		userDto.get().setUsername("TestUserName");
		userDto.get().setPassword("TestPassword123!@£");
		userDto.get().setFullname("TestFullName");
		userDto.get().setRole("TestRole");
	}

	@Test
	public void shouldShowUserListTest() throws Exception {
		User user = new User();
		user.setUsername("TestUserName");
		user.setPassword("TestPassword");
		user.setFullname("TestFullName");
		user.setRole("TestRole");
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		when(userService.findAllUsers()).thenReturn(userList);
		
		mockMvc.perform(get("/user/list")
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk())
				.andExpect(view().name("user/list"))
				.andExpect(model().attribute("users", userList));
	}
	
	@Test
	public void shouldShowAddUserFormTest() throws Exception {
		
		mockMvc.perform(get("/user/add")
				.with(csrf())
				.with(user(TEST_USER_AUTH)))
        		.andExpect(view().name("user/add"));
	}
	
	@Test
	public void shouldValidateAddUserFormTest() throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		final ArgumentCaptor<UserDTO> userDtoCaptor =
			    ArgumentCaptor.forClass(UserDTO.class);
		UserDTO userDto = new UserDTO();
		userDto.setUsername("TestUserName");
		userDto.setPassword("TestPassword123!@£");
		userDto.setFullname("TestFullName");
		userDto.setRole("TestRole");
		User newUser = new User(userDto);

		when(userService.findUserByUsername("TestUserName")).thenReturn(null);
		when(userService.create(userDto)).thenReturn(newUser);
		
		mockMvc.perform(post("/user/validate")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("username", userDto.getUsername())
	            .param("password", userDto.getPassword())
	            .param("fullname", userDto.getFullname())
	            .param("role", userDto.getRole()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bid/list"));
		
		verify(userService, times(1)).create(userDtoCaptor.capture());
		
		Boolean result = passwordEncoder.matches("TestPassword123!@£", userDtoCaptor
				.getValue().getPassword());
		
		Assertions.assertEquals("TestUserName", userDtoCaptor.getValue().getUsername());
		Assertions.assertEquals("TestFullName", userDtoCaptor.getValue().getFullname());
		Assertions.assertEquals("TestRole", userDtoCaptor.getValue().getRole());
		Assertions.assertTrue(result);
	}

	@Test
	public void shouldAddUserIfAlredyExistsTest() throws Exception {
		
		when(userService.ifUserExists(any())).thenReturn(true);
		
		mockMvc.perform(
        		post("/user/validate", userDto.get())
        		.with(csrf())
				.with(user(TEST_USER_AUTH)
						.password("pass"))
				.param("username", userDto.get().getUsername())
		        .param("password", userDto.get().getPassword())
		        .param("fullname", userDto.get().getFullname())
		        .param("role", userDto.get().getRole()))
				.andExpect(status().isOk());
	}
	
	@Test
	public void shouldValidateAddUserEmptyFormTest() throws Exception {
		UserDTO userDto = new UserDTO();
		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(true);

		mockMvc.perform(post("/user/validate")
				.with(csrf())
				.with(user(TEST_USER_AUTH)
						.password("pass"))
	            .param("username", "")
	            .param("password", "")
	            .param("fullname", "")
	            .param("role", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("user/add"));
		
		verify(userService, times(0)).create(userDto);
	}
	
	@Test
	public void shouldShowUpdateUserFormTest() throws Exception {
		
		when(userService.ifUserExists(any())).thenReturn(true);
		when(userService.findUserById(1)).thenReturn(userDto);
		
		mockMvc.perform(get("/user/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("username", userDto.get().getUsername())
	            .param("password", userDto.get().getPassword())
	            .param("fullname", userDto.get().getFullname())
	            .param("role", userDto.get().getRole()))
				.andExpect(status().isOk())
        		.andExpect(view().name("user/update"))
        		.andExpect(model().attribute("user", userDto.orElseThrow(null)));
		
		verify(userService).findUserById(1);
	}
	
	@Test
	public void shouldUpdateUserIfNotExistsTest() throws Exception {
		
		when(userService.findUserById(anyInt())).thenReturn(userDto);
		
		mockMvc.perform(get("/user/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk())
        		.andExpect(view().name("user/update"));              
	}
	
	@Test
	public void shouldUpdateUserTest() throws Exception {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);
			
		mockMvc.perform(post("/user/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("username", userDto.get().getUsername())
	            .param("password", userDto.get().getPassword())
	            .param("fullname", userDto.get().getFullname())
	            .param("role", userDto.get().getRole())
	            .param("result", ""))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/user/list"));
	}
	
	@Test
	public void shouldUpdateInvalidUserTest() throws Exception {		

		mockMvc.perform(post("/user/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("username", "")
	            .param("password", userDto.get().getPassword())
	            .param("fullname", userDto.get().getFullname())
	            .param("role", userDto.get().getRole()))
				.andExpect(status().isOk())
				.andExpect(view().name("user/update"));
		
		verify(userService, times(0)).updateUser(1, userDto.get());
	}
	
	@Test
	public void shouldDeleteUserTest() throws Exception {

		when(userService.findUserById(2)).thenReturn(userDto);
		
		mockMvc.perform( 							
				get("/user/delete/{id}", 2)
	            .with(csrf())
				.with(user(TEST_USER_AUTH)))
		        .andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/user/list"));
		
		verify(userService).deleteUser(2);
	}
}
