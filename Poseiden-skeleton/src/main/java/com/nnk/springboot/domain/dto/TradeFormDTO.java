package com.nnk.springboot.domain.dto;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nnk.springboot.domain.Trade;

public class TradeFormDTO {
	
	private Integer id;
	@NotBlank(message = "Account is mandatory")
	private String account;
	@NotBlank(message = "Type is mandatory")
	private String type;
	@NotNull(message = "Buy Quantity is mandatory") @Min(0)
	private Double buyQuantity;
	
	public TradeFormDTO() {
		super();
	}

	public TradeFormDTO(Trade trade) {
		super();
		this.id = trade.getId();
		this.account = trade.getAccount();
		this.type = trade.getType();
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeFormDTO other = (TradeFormDTO) obj;
		return Objects.equals(account, other.account) && Objects.equals(buyQuantity, other.buyQuantity)
				&& Objects.equals(id, other.id) && Objects.equals(type, other.type);
	}
}
