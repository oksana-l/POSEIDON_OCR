package com.nnk.springboot.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nnk.springboot.domain.Trade;

public class TradeFormDTO {
	
	private Integer id;
	@NotBlank
	private String account;
	@NotBlank
	private String type;
	@NotNull @Min(0)
	private Double buyQuantity;
	
	public TradeFormDTO() {
		super();
	}

	public TradeFormDTO(Trade trade) {
		super();
		this.id = trade.getId();
		this.account = trade.getAccount();
		this.type = trade.getAccountType();
		this.buyQuantity = trade.getBuyQuantity();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

}
