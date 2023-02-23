package com.nnk.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeFormDTO;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.TradeServiceImpl;

public class TradeServiceImplTest {

	private TradeRepository tradeRepository;
	private TradeService tradeService;
	private TradeFormDTO tradeDto;
	
	@BeforeEach
	public void setUp() {
		tradeRepository = mock(TradeRepository.class);
		tradeService = new TradeServiceImpl(tradeRepository);
		
	}
	
	@Test
	public void shouldFindAllTradeTest() {
		Trade trade = new Trade();
		trade.setAccount("Premium");
		List<Trade> tradeList= new ArrayList<Trade>();
		tradeList.add(trade);
		
		when(tradeRepository.findAll()).thenReturn(tradeList);
		
		List<Trade> newTradeList = tradeService.findAllTrade();

		Assertions.assertEquals(tradeList.isEmpty(), newTradeList.isEmpty());
		Assertions.assertEquals(tradeList.size(), newTradeList.size());
		Assertions.assertEquals("Premium", newTradeList.get(0).getAccount());
	}
	
	@Test
	public void shouldCreateTest() {
		tradeDto = new TradeFormDTO();
		tradeDto.setAccount("First");
		tradeDto.setType("Premium");
		tradeDto.setBuyQuantity(5d);
		
		when(tradeRepository.save(any())).thenReturn(new Trade(tradeDto));
		
		Trade savedTrade = tradeService.create(tradeDto);
		
		Assertions.assertEquals("First", savedTrade.getAccount());
		Assertions.assertEquals("Premium", savedTrade.getType());
		Assertions.assertEquals(5d, savedTrade.getBuyQuantity());
		
	}
	
	@Test
	public void shouldGetTradeByIdTest() {
		Trade trade = new Trade();
		trade.setId(1);
		trade.setAccount("TestAccount");
		trade.setType("TestType");
		trade.setBuyQuantity(3d);
		
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));
		
		TradeFormDTO tradeDto = tradeService.getTradeById(1);
		
		Assertions.assertEquals(1, tradeDto.getId());
		Assertions.assertEquals("TestAccount", tradeDto.getAccount());
		Assertions.assertEquals("TestType", tradeDto.getType());
		Assertions.assertEquals(3d, tradeDto.getBuyQuantity());		
	}
	
	@Test
	public void shouldUpdateTest() {
		tradeDto = new TradeFormDTO();
		tradeDto.setAccount("First");
		tradeDto.setType("Premium");
		tradeDto.setBuyQuantity(5d);
		
		Trade trade = new Trade();
		trade.setAccount("TestAccount");
		trade.setType("TestType");
		trade.setBuyQuantity(3d);
		
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));	
		when(tradeRepository.save(any())).thenReturn(new Trade(tradeDto));
		
		Trade updatedTrade = tradeService.update(1, tradeDto);
		
		Assertions.assertEquals("First", updatedTrade.getAccount());
		Assertions.assertEquals("Premium", updatedTrade.getType());
		Assertions.assertEquals(5d, updatedTrade.getBuyQuantity());
	}
	
	@Test
	public void shouldDeleteTradeByIdTest() {
		Trade trade = new Trade();
		trade.setId(1);
		
		tradeService.deleteTradeById(1);
		
		verify(tradeRepository).deleteById(1);
	}
	
	@Test
	public void shouldVerifIsTradeExists() {
		when(tradeRepository.existsById(anyInt())).thenReturn(true);
		
		Assertions.assertTrue(tradeService.ifTradeExists(anyInt()));
	}
}
