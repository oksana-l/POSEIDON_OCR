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

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.dto.BidFormDTO;
import com.nnk.springboot.repositories.BidRepository;
import com.nnk.springboot.services.BidService;
import com.nnk.springboot.services.BidServiceImpl;

public class BidServiceImplTest {

	private BidRepository bidRepository;
	private BidService bidService;
	private BidFormDTO bidDto;
	
	@BeforeEach
	public void setUp() {
		bidRepository = mock(BidRepository.class);
		bidService = new BidServiceImpl(bidRepository);
		
	}
	
	@Test
	public void shouldFindAllBids() {
		Bid bid = new Bid();
		bid.setAccount("Premium");
		List<Bid> bidList= new ArrayList<Bid>();
		bidList.add(bid);
		
		when(bidRepository.findAll()).thenReturn(bidList);
		
		List<Bid> newBidList = bidService.findAllBids();

		Assertions.assertEquals(bidList.isEmpty(), newBidList.isEmpty());
		Assertions.assertEquals(bidList.size(), newBidList.size());
		Assertions.assertEquals("Premium", newBidList.get(0).getAccount());
	}
	
	@Test
	public void shouldCreate() {
		bidDto = new BidFormDTO();
		bidDto.setAccount("First");
		bidDto.setTypeAccount("Premium");
		bidDto.setBidQuantity(5);
		
		when(bidRepository.save(any())).thenReturn(new Bid(bidDto));
		
		Bid savedBid = bidService.create(bidDto);
		
		Assertions.assertEquals("First", savedBid.getAccount());
		Assertions.assertEquals("Premium", savedBid.getTypeAccount());
		Assertions.assertEquals(5, savedBid.getBidQuantity());
		
	}
	
	@Test
	public void shouldGetBidById() {
		Bid bid = new Bid();
		bid.setId(1);
		bid.setAccount("TestAccount");
		bid.setTypeAccount("TestType");
		bid.setBidQuantity(3);
		
		when(bidRepository.getBidById(anyInt())).thenReturn(Optional.of(bid));
		
		BidFormDTO bidDto = bidService.getBidById(1);
		
		Assertions.assertEquals(1, bidDto.getId());
		Assertions.assertEquals("TestAccount", bidDto.getAccount());
		Assertions.assertEquals("TestType", bidDto.getTypeAccount());
		Assertions.assertEquals(3, bidDto.getBidQuantity());		
	}
	
	@Test
	public void shouldUpdate() {
		bidDto = new BidFormDTO();
		bidDto.setAccount("First");
		bidDto.setTypeAccount("Premium");
		bidDto.setBidQuantity(5);
		
		Bid bid = new Bid();
		bid.setAccount("TestAccount");
		bid.setTypeAccount("TestType");
		bid.setBidQuantity(3);
		
		when(bidRepository.getBidById(anyInt())).thenReturn(Optional.of(bid));	
		when(bidRepository.save(any())).thenReturn(new Bid(bidDto));
		
		Bid updatedBid = bidService.update(1, bidDto);
		
		Assertions.assertEquals("First", updatedBid.getAccount());
		Assertions.assertEquals("Premium", updatedBid.getTypeAccount());
		Assertions.assertEquals(5, updatedBid.getBidQuantity());
	}
	
	@Test
	public void shouldDeleteBidById() {
		Bid bid = new Bid();
		bid.setId(1);
		
		bidService.deleteBidById(1);
		
		verify(bidRepository).deleteById(1);
	}
}
