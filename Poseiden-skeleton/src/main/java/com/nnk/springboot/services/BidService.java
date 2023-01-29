package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.dto.BidFormDTO;

public interface BidService {

	List<Bid> findAllBids();

	Bid save(BidFormDTO bid);

	Double getBidById(Integer id);
}
