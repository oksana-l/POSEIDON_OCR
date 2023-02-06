package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeFormDTO;

public interface TradeService {
	
	List<Trade> findAllTrade();

	Trade create(TradeFormDTO rule);

	TradeFormDTO getTradeById(Integer id);

	Trade update(Integer id, TradeFormDTO ruleDto);
	
	void deleteTradeById(Integer id);
}
