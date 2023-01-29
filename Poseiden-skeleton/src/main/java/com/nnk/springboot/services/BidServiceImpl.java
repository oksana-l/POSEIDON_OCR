package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.dto.BidFormDTO;
import com.nnk.springboot.repositories.BidRepository;

@Service
public class BidServiceImpl implements BidService {

	private BidRepository bidRepository;
	
	
	public BidServiceImpl(BidRepository bidRepository) {
		super();
		this.bidRepository = bidRepository;
	}

	@Override
	public List<Bid> findAllBids() {
		
		return bidRepository.findAll();
	}

	@Override
	public Bid save(BidFormDTO bid) {
		Bid newBid = new Bid(bid.getAccount(), bid.getTypeAccount(),
							 bid.getBidQuantity(), null, null, null, null,
							 null, null, null, null, null, null, null, null,
							 null, null, null, null, null, null);
		return bidRepository.save(newBid);
	}

	@Override
	public Double getBidById(Integer id) {

		return bidRepository.getBidById(id).get().getBid();
	}

}
