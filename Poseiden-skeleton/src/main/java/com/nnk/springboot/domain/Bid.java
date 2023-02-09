package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nnk.springboot.domain.dto.BidFormDTO;

@Entity
@Table(name = "bidlist")
public class Bid {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@NotBlank(message = "Account is mandatory")
	@Column
	String account;
	@NotBlank(message = "Type is mandatory")
	@Column
	String typeAccount;
	@Column
	@NotNull(message = "Bid Quantity is mandatory") @Min(0)
	Integer bidQuantity;
	@Column
	Integer askQuantity;
	@Column
	Double bid;
	@Column
	Double ask;
	@Column
	String benchmark; 
	@Column
	Timestamp bidDate; 
	@Column
	String commentary; 
	@Column
	String security;
	@Column
	String status;
	@Column
	String trader;
	@Column
	String book;
	@Column
	String creationName; 
	@Column
	Timestamp creationDate; 
	@Column
	String revisionName; 
	@Column
	Timestamp revisionDate; 
	@Column
	String dealName;
	@Column
	String dealType;
	@Column
	String sourceListId; 
	@Column
	String side;

	public Bid() {
		
	}
	
	public Bid(Integer id, String account, String typeAccount, Integer bidQuantity, Integer askQuantity, Double bid,
			Double ask, String benchmark, Timestamp bidDate, String commentary, String security, String status,
			String trader, String book, String creationName, Timestamp creationDate, String revisionName,
			Timestamp revisionDate, String dealName, String dealType, String sourceListId, String side) {
		super();
		this.id = id;
		this.account = account;
		this.typeAccount = typeAccount;
		this.bidQuantity = bidQuantity;
		this.askQuantity = askQuantity;
		this.bid = bid;
		this.ask = ask;
		this.benchmark = benchmark;
		this.bidDate = bidDate;
		this.commentary = commentary;
		this.security = security;
		this.status = status;
		this.trader = trader;
		this.book = book;
		this.creationName = creationName;
		this.creationDate = creationDate;
		this.revisionName = revisionName;
		this.revisionDate = revisionDate;
		this.dealName = dealName;
		this.dealType = dealType;
		this.sourceListId = sourceListId;
		this.side = side;
	}

	public Bid(BidFormDTO bidDto) {
		this.account = bidDto.getAccount();
		this.typeAccount = bidDto.getTypeAccount();
		this.bidQuantity = bidDto.getBidQuantity();
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

	public String getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(String typeAccount) {
		this.typeAccount = typeAccount;
	}

	public Integer getBidQuantity() {
		return bidQuantity;
	}

	public void setBidQuantity(Integer bidQuantity) {
		this.bidQuantity = bidQuantity;
	}

	public Integer getAskQuantity() {
		return askQuantity;
	}

	public void setAskQuantity(Integer askQuantity) {
		this.askQuantity = askQuantity;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public Timestamp getBidDate() {
		return bidDate;
	}

	public void setBidDate(Timestamp bidDate) {
		this.bidDate = bidDate;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getRevisionName() {
		return revisionName;
	}

	public void setRevisionName(String revisionName) {
		this.revisionName = revisionName;
	}

	public Timestamp getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Timestamp revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getSourceListId() {
		return sourceListId;
	}

	public void setSourceListId(String sourceListId) {
		this.sourceListId = sourceListId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}
	
}
