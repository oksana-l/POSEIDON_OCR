package com.nnk.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidRepository;

@SpringBootTest
public class BidTests {

	@Autowired
	private BidRepository bidListRepository;

	@Test
	public void bidTest() {
		Bid bid = new Bid();
		bid.setAccount("Account Test");
		bid.setTypeAccount("Type Test");
		bid.setBidQuantity(10);
		
		// Save
		bid = bidListRepository.save(bid);
		Assertions.assertNotNull(bid.getId());
		Assertions.assertEquals(bid.getBidQuantity(), 10, 10);

		// Update
		bid.setAccount("Test");
		bid.setBidQuantity(20);
		bid = bidListRepository.save(bid);
		Assertions.assertEquals(bid.getBidQuantity(), 20, 20);

		// Find
		List<Bid> listResult = bidListRepository.findAll();
		Assertions.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getId();
		bidListRepository.delete(bid);
		Optional<Bid> bidList = bidListRepository.findById(id);
		Assertions.assertFalse(bidList.isPresent());
	}
}
