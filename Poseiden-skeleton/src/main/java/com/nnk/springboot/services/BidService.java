package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.dto.BidFormDTO;

public interface BidService {

	List<Bid> findAllBids();

	Bid create(BidFormDTO bid);

	BidFormDTO getBidById(Integer id);

	void deleteBidById(Integer id);

	Bid update(Integer id, BidFormDTO bidDto);
}
