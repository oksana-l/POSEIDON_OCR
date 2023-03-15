package com.nnk.springboot.controller;

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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.controllers.CurvePointController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurvePointFormDTO;
import com.nnk.springboot.services.CurvePointService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = CurvePointController.class)
@WebMvcTest
public class CurvePointControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CurvePointService curvePointService;
	private final static String TEST_USER_AUTH = "Admin";
	
	@Test
	public void shouldShowCurvePointListTest() throws Exception {
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setId(1);
		curvePoint.setCurveId(4);
		curvePoint.setTerm(10d);
		curvePoint.setValue(3d);
		List<CurvePoint> curvePointList = new ArrayList<CurvePoint>();
		curvePointList.add(curvePoint);
		when(curvePointService.findAllCurvePoint()).thenReturn(curvePointList);
		
		mockMvc.perform(get("/curvePoint/list")
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/list"))
				.andExpect(model().attribute("curvepointlist", curvePointList));
	}
	
	@Test
	public void shouldShowAddCurvePointFormTest() throws Exception {
		
		mockMvc.perform(get("/curvePoint/add")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("curveId", "")
	            .param("term", "")
	            .param("value", ""))
        		.andExpect(view().name("curvePoint/add"));
	}
	
	@Test
	public void shouldValidateAddCurvePointFormTest() throws Exception {
		CurvePointFormDTO curvePointDto = new CurvePointFormDTO();
		curvePointDto.setCurveId(4);
		curvePointDto.setTerm(10d);
		curvePointDto.setValue(3d);
		CurvePoint newCurvePoint = new CurvePoint(curvePointDto);

		when(curvePointService.create(curvePointDto)).thenReturn(newCurvePoint);
		
		mockMvc.perform(post("/curvePoint/validate")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("curveId", curvePointDto.getCurveId().toString())
	            .param("term", curvePointDto.getTerm().toString())
	            .param("value", curvePointDto.getValue().toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvePoint/list"));
		
		verify(curvePointService, times(1)).create(curvePointDto);
	}

	@Test
	public void shouldValidateAddCurvePointEmptyFormTest() throws Exception {
		CurvePointFormDTO curvePointDto = new CurvePointFormDTO();
		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(true);

		mockMvc.perform(post("/curvePoint/validate")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("curveId", "")
	            .param("term", "")
	            .param("value", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/add"));
		
		verify(curvePointService, times(0)).create(curvePointDto);
	}
	
	@Test
	public void shouldShowUpdateCurvePointFormTest() throws Exception {
		CurvePointFormDTO curvePointDto = new CurvePointFormDTO();
		curvePointDto.setCurveId(4);
		curvePointDto.setTerm(10d);
		curvePointDto.setValue(3d);
		
		when(curvePointService.ifCurvePointExists(anyInt())).thenReturn(true);
		when(curvePointService.getCurvePointById(1)).thenReturn(curvePointDto);
		
		mockMvc.perform(get("/curvePoint/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("curveId", curvePointDto.getCurveId().toString())
	            .param("term", curvePointDto.getTerm().toString())
	            .param("value", curvePointDto.getValue().toString()))
				.andExpect(status().isOk())
        		.andExpect(view().name("curvePoint/update"))
        		.andExpect(model().attribute("curvePoint", curvePointDto));
		
		verify(curvePointService).getCurvePointById(1);
	}
	
	@Test
	public void shouldUpdateCurvePointIfNotExistsTest() throws Exception {
		when(curvePointService.ifCurvePointExists(anyInt())).thenReturn(false);
		
		mockMvc.perform(get("/curvePoint/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update"));
		
		verify(curvePointService).ifCurvePointExists(1);
	}
	
	@Test
	public void shouldUpdateCurvePointTest() throws Exception {
		CurvePointFormDTO curvePointDto = new CurvePointFormDTO();
		curvePointDto.setId(1);
		curvePointDto.setCurveId(4);
		curvePointDto.setTerm(10d);
		curvePointDto.setValue(3d);
		
		when(curvePointService.ifCurvePointExists(anyInt())).thenReturn(true);
		
		mockMvc.perform(post("/curvePoint/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("curveId", curvePointDto.getCurveId().toString())
	            .param("term", curvePointDto.getTerm().toString())
	            .param("value", curvePointDto.getValue().toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvePoint/list"));
		
		verify(curvePointService).update(1, curvePointDto);
	}
	
	@Test
	public void shouldUpdateInvalidCurvePointTest() throws Exception {
		CurvePointFormDTO curvePointDto = new CurvePointFormDTO();
		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(true);

		mockMvc.perform(post("/curvePoint/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
		        .param("curveId", "")
		        .param("term", "")
		        .param("value", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update"));
		
		verify(curvePointService, times(0)).update(1, curvePointDto);
	}
	
	@Test
	public void shouldDeleteCurvePointTest() throws Exception {

		mockMvc.perform( 							
				get("/curvePoint/delete/{id}", 2)
	            .with(csrf())
				.with(user(TEST_USER_AUTH)))
		        .andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvePoint/list"));
		
		verify(curvePointService).deleteCurvePointById(2);
	}
}
