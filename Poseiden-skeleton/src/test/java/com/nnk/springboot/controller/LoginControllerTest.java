package com.nnk.springboot.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.LoginController;

@AutoConfigureMockMvc
@ContextConfiguration(classes = LoginController.class)
@WebMvcTest
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldShowLoginTest() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk());
	}
}
