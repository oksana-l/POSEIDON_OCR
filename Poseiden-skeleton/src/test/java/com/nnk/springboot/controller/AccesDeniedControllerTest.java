package com.nnk.springboot.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.AccesDeniedController;

@AutoConfigureMockMvc
@ContextConfiguration(classes = AccesDeniedController.class)
@WebMvcTest
public class AccesDeniedControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	private final static String TEST_USER_AUTH = "Admin";
	
	@Test
	public void shouldShowAccesDeniedTest() throws Exception {
		mockMvc.perform(get("/403")
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk());
	}
}
