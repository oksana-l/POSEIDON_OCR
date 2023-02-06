package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeFormDTO;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeServiceImpl implements TradeService {

	private TradeRepository tradeRepository;
	
	public TradeServiceImpl(TradeRepository tradeRepository) {
		super();
		this.tradeRepository = tradeRepository;
	}
	
	@Override
	public List<Trade> findAllTrade() {
		return tradeRepository.findAll();
	}

	@Override
	public Trade create(TradeFormDTO tradeDto) {
		Trade newTrade = new Trade();
		newTrade.setAccount(tradeDto.getAccount());
		newTrade.setType(tradeDto.getType());
		newTrade.setBuyQuantity(tradeDto.getBuyQuantity());
		return tradeRepository.save(newTrade);
	}

	@Override
	public TradeFormDTO getTradeById(Integer id) {
		return new TradeFormDTO(tradeRepository.findById(id).get());
	}

	@Override
	public Trade update(Integer id, TradeFormDTO tradeDto) {
		Trade trade = tradeRepository.findById(id).get();
		trade.setAccount(tradeDto.getAccount());
		trade.setType(tradeDto.getType());
		trade.setBuyQuantity(tradeDto.getBuyQuantity());
		return tradeRepository.save(trade);
	}

	@Override
	public void deleteTradeById(Integer id) {
		tradeRepository.deleteById(id);
	}

}
