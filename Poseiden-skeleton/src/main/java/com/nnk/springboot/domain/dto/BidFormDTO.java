package com.nnk.springboot.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nnk.springboot.domain.Bid;

public class BidFormDTO {

	private Integer id;
	@NotBlank
	private String account;
	@NotBlank
	private String typeAccount;
	@NotNull @Min(0)
	private Integer bidQuantity;
	
	public BidFormDTO() {
		
	}
	
	public BidFormDTO(Bid bid) {
		super();
		this.id = bid.getId();
		this.account = bid.getAccount();
		this.typeAccount = bid.getTypeAccount();
		this.bidQuantity = bid.getBidQuantity();
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
