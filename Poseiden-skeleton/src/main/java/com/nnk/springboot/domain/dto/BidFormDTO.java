package com.nnk.springboot.domain.dto;

import com.nnk.springboot.domain.Bid;

public class BidFormDTO {

	private String account;
	private String typeAccount;
	private Integer bidQuantity;
	
	public BidFormDTO() {
		
	}
	public BidFormDTO(Bid bid) {
		super();
		this.account = bid.getAccount();
		this.typeAccount = bid.getTypeAccount();
		this.bidQuantity = bid.getBidQuantity();
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String accountName) {
		this.account = accountName;
	}
	public String getTypeAccount() {
		return typeAccount;
	}
	public void setTypeAccount(String accountType) {
		this.typeAccount = accountType;
	}
	public Integer getBidQuantity() {
		return bidQuantity;
	}
	public void setBidQuantity(Integer bidQuantity) {
		this.bidQuantity = bidQuantity;
	}
}
