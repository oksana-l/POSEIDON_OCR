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

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeFormDTO;
import com.nnk.springboot.services.TradeService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = TradeController.class)
@WebMvcTest
public class TradeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TradeService tradeService;
	private final static String TEST_USER_AUTH = "Admin";
	
	@Test
	public void shouldShowTradeListTest() throws Exception {
		Trade trade = new Trade();
		trade.setId(1);
		trade.setAccount("TestAccount");
		trade.setType("TestTypeAccount");
		trade.setBuyQuantity(3d);
		List<Trade> tradeList = new ArrayList<Trade>();
		tradeList.add(trade);
		when(tradeService.findAllTrade()).thenReturn(tradeList);
		
		mockMvc.perform(get("/trade/list")
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/list"))
				.andExpect(model().attribute("tradeList", tradeList));
	}
	
	@Test
	public void shouldShowAddTradeFormTest() throws Exception {
		
		mockMvc.perform(get("/trade/add")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("account", "")
	            .param("type", "")
	            .param("buyQuantity", ""))
        		.andExpect(view().name("trade/add"));
	}
	
	@Test
	public void shouldValidateAddTradeFormTest() throws Exception {
		TradeFormDTO tradeDto = new TradeFormDTO();
		tradeDto.setAccount("TestAccount");
		tradeDto.setType("TestTypeAccount");
		tradeDto.setBuyQuantity(3d);
		Trade newTrade = new Trade(tradeDto);

		when(tradeService.create(tradeDto)).thenReturn(newTrade);
		
		mockMvc.perform(post("/trade/validate")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("account", tradeDto.getAccount())
	            .param("type", tradeDto.getType())
	            .param("buyQuantity", tradeDto.getBuyQuantity().toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trade/list"));
		
		verify(tradeService, times(1)).create(tradeDto);
	}

	@Test
	public void shouldValidateAddTradeEmptyFormTest() throws Exception {
		TradeFormDTO tradeDto = new TradeFormDTO();
		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(true);

		mockMvc.perform(post("/trade/validate")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("account", "")
	            .param("type", "")
	            .param("buyQuantity", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/add"));
		
		verify(tradeService, times(0)).create(tradeDto);
	}
	
	@Test
	public void shouldShowUpdateTradeFormTest() throws Exception {
		TradeFormDTO tradeDto = new TradeFormDTO();
		tradeDto.setAccount("TestAccount");
		tradeDto.setType("TestTypeAccount");
		tradeDto.setBuyQuantity(3d);
		
		when(tradeService.ifTradeExists(anyInt())).thenReturn(true);
		when(tradeService.getTradeById(1)).thenReturn(tradeDto);
		
		mockMvc.perform(get("/trade/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("account", tradeDto.getAccount())
	            .param("type", tradeDto.getType())
	            .param("buyQuantity", tradeDto.getBuyQuantity().toString()))
				.andExpect(status().isOk())
        		.andExpect(view().name("trade/update"))
        		.andExpect(model().attribute("trade", tradeDto));
		
		verify(tradeService).getTradeById(1);
	}
	
	@Test
	public void shouldUpdateTradeIfNotExistsTest() throws Exception {
		when(tradeService.ifTradeExists(anyInt())).thenReturn(false);
		
		mockMvc.perform(get("/trade/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/update"));
		
		verify(tradeService).ifTradeExists(1);
	}
	
	@Test
	public void shouldUpdateTradeTest() throws Exception {
		TradeFormDTO tradeDto = new TradeFormDTO();
		tradeDto.setId(1);
		tradeDto.setAccount("TestAccount");
		tradeDto.setType("TestTypeAccount");
		tradeDto.setBuyQuantity(3d);
		
		when(tradeService.ifTradeExists(anyInt())).thenReturn(true);
		
		mockMvc.perform(post("/trade/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("account", tradeDto.getAccount())
	            .param("type", tradeDto.getType())
	            .param("buyQuantity", tradeDto.getBuyQuantity().toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trade/list"));
		
		verify(tradeService).update(1, tradeDto);
	}
	
	@Test
	public void shouldUpdateInvalidTradeTest() throws Exception {
		TradeFormDTO tradeDto = new TradeFormDTO();
		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(true);

		mockMvc.perform(post("/trade/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
		        .param("account", "")
		        .param("type", "")
		        .param("buyQuantity", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/update"));
		
		verify(tradeService, times(0)).update(1, tradeDto);
	}
	
	@Test
	public void shouldDeleteTradeTest() throws Exception {

		mockMvc.perform( 							
				get("/trade/delete/{id}", 2)
	            .with(csrf())
				.with(user(TEST_USER_AUTH)))
		        .andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trade/list"));
		
		verify(tradeService).deleteTradeById(2);
	}
}
