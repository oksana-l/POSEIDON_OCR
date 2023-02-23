package com.nnk.springboot.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.HomeController;

@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = HomeController.class)
@WebMvcTest
public class HomeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldShowHomeTest() throws Exception {
		mockMvc.perform(get("/home"))
				.andExpect(status().isOk());
	}
}
