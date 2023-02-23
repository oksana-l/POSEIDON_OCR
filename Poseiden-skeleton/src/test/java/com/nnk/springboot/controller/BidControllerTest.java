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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.controllers.BidController;
import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.dto.BidFormDTO;
import com.nnk.springboot.services.BidService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = BidController.class)
@WebMvcTest
public class BidControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BidService bidService;
	private final static String TEST_USER_AUTH = "Admin";
	
	@Test
	public void shouldShowBidListTest() throws Exception {
		Bid bid = new Bid();
		bid.setId(1);
		bid.setAccount("TestAccount");
		bid.setTypeAccount("TestType");
		bid.setBidQuantity(3);
		List<Bid> bidList = new ArrayList<Bid>();
		bidList.add(bid);
		when(bidService.findAllBids()).thenReturn(bidList);
		
		mockMvc.perform(get("/bid/list")
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk())
				.andExpect(view().name("bid/list"))
				.andExpect(model().attribute("bidList", bidList))
				.andExpect(content()
						.contentType(MediaType.valueOf("text/html;charset=UTF-8")));
	}
	
	@Test
	public void shouldShowAddBidFormTest() throws Exception {
		
		mockMvc.perform(get("/bid/add")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("account", "")
	            .param("typeAccount", "")
	            .param("bidQuantity", ""))
        		.andExpect(view().name("bid/add"));
	}
	
	@Test
	public void shouldValidateAddBidFormTest() throws Exception {
		BidFormDTO bidDto = new BidFormDTO("AccountTest", "TypeAccountTest", 3);
		Bid newBid = new Bid(bidDto);

		when(bidService.create(bidDto)).thenReturn(newBid);
		
		mockMvc.perform(post("/bid/validate")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("account", bidDto.getAccount())
	            .param("typeAccount", bidDto.getTypeAccount())
	            .param("bidQuantity", bidDto.getBidQuantity().toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bid/list"));
		
		verify(bidService, times(1)).create(bidDto);
	}

	@Test
	public void shouldValidateAddBidEmptyFormTest() throws Exception {
		BidFormDTO bidDto = new BidFormDTO("AccountTest", "TypeAccountTest", 3);
		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(true);

		mockMvc.perform(post("/bid/validate")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
		        .param("account", "")
		        .param("typeAccount", "")
		        .param("bidQuantity", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("bid/add"));
		
		verify(bidService, times(0)).create(bidDto);
	}
	
	@Test
	public void shouldShowUpdateBidFormTest() throws Exception {
		BidFormDTO bidDto = new BidFormDTO("AccountTest", "TypeAccountTest", 3);
		
		when(bidService.ifBidExists(anyInt())).thenReturn(true);
		when(bidService.getBidById(1)).thenReturn(bidDto);
		
		mockMvc.perform(get("/bid/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("account", bidDto.getAccount())
	            .param("typeAccount", bidDto.getTypeAccount())
	            .param("bidQuantity", bidDto.getBidQuantity().toString()))
				.andExpect(status().isOk())
        		.andExpect(view().name("bid/update"))
        		.andExpect(model().attribute("bid", bidDto));
		
		verify(bidService).getBidById(1);
	}
	
	@Test
	public void shouldUpdateBidIfNotExistsTest() throws Exception {
		when(bidService.ifBidExists(anyInt())).thenReturn(false);
		
		mockMvc.perform(get("/bid/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk())
				.andExpect(view().name("bid/update"));
		
		verify(bidService).ifBidExists(1);
	}
	
	@Test
	public void shouldUpdateBidTest() throws Exception {
		BidFormDTO bidDto = new BidFormDTO("AccountTest2", "TypeAccountTest2", 4);
		bidDto.setId(1);

		when(bidService.ifBidExists(anyInt())).thenReturn(true);
		
		mockMvc.perform(post("/bid/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("account", bidDto.getAccount())
	            .param("typeAccount", bidDto.getTypeAccount())
	            .param("bidQuantity", bidDto.getBidQuantity().toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bid/list"));
		
		verify(bidService).update(1, bidDto);
	}
	
	@Test
	public void shouldUpdateInvalidBidTest() throws Exception {
		BidFormDTO bidDto = new BidFormDTO("AccountTest2", "TypeAccountTest2", 4);
		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(true);

		mockMvc.perform(post("/bid/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
		        .param("account", "")
		        .param("typeAccount", "")
		        .param("bidQuantity", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("bid/update"));
		
		verify(bidService, times(0)).update(1, bidDto);
	}
	
	@Test
	public void shouldDeleteBidTest() throws Exception {

		mockMvc.perform( 							
				get("/bid/delete/{id}", 2)
	            .with(csrf())
				.with(user(TEST_USER_AUTH)))
		        .andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bid/list"));
		
		verify(bidService).deleteBidById(2);
	}

}
