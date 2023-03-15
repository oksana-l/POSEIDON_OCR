package com.nnk.springboot.domain.dto;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nnk.springboot.domain.Bid;

public class BidFormDTO {

	private Integer id;
	@NotBlank(message = "Account is mandatory")
	private String account;
	@NotBlank(message = "Type is mandatory")
	private String typeAccount;
	@NotNull(message = "Bid Quantity is mandatory") @Min(0)
	private Integer bidQuantity;
	
	public BidFormDTO() {
		super();
	}
	
	public BidFormDTO(Bid bid) {
		super();
		this.id = bid.getId();
		this.account = bid.getAccount();
		this.typeAccount = bid.getTypeAccount();
		this.bidQuantity = bid.getBidQuantity();
	}

	public BidFormDTO(String account, String typeAccount, Integer bidQuantity) {
		super();
		this.account = account;
		this.typeAccount = typeAccount;
		this.bidQuantity = bidQuantity;
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

	@Override
	public int hashCode() {
		return Objects.hash(account, bidQuantity, id, typeAccount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BidFormDTO other = (BidFormDTO) obj;
		return Objects.equals(account, other.account) && Objects.equals(bidQuantity, other.bidQuantity)
				&& Objects.equals(id, other.id) && Objects.equals(typeAccount, other.typeAccount);
	}
	
}
