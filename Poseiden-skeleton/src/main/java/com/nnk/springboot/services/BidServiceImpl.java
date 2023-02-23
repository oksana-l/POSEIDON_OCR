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
	public Bid create(BidFormDTO bid) {
		Bid newBid = new Bid();
		newBid.setAccount(bid.getAccount());
		newBid.setTypeAccount(bid.getTypeAccount());
		newBid.setBidQuantity(bid.getBidQuantity());
		return bidRepository.save(newBid);
	}

	@Override
	public BidFormDTO getBidById(Integer id) {
		return new BidFormDTO(bidRepository.getBidById(id));
	}

	@Override
	public Bid update(Integer id, BidFormDTO bidDto) {
		Bid bid = bidRepository.getBidById(id);
		bid.setAccount(bidDto.getAccount());
		bid.setTypeAccount(bidDto.getTypeAccount());
		bid.setBidQuantity(bidDto.getBidQuantity());
		return bidRepository.save(bid);
	}

	@Override
	public void deleteBidById(Integer id) {
		bidRepository.deleteById(id);
	}

	@Override
	public boolean ifBidExists(Integer id) {
		
		return bidRepository.existsById(id);
	}
}
