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

@Entity
@Table(name = "trade")
public class Trade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	Integer id;
	@NotBlank(message = "Account is mandatory")
	@Column
	String account; 
	@Column
	@NotBlank(message = "Type is mandatory")
	String type;
	@Column
	@NotNull(message = "Buy Quantity is mandatory") @Min(0)
	Double buyQuantity; 
	@Column
	Double sellQuantity; 
	@Column
	Double buyPrice; 
	@Column
	Double sellPrice; 
	@Column
	String benchmark; 
	@Column
	Timestamp tradeDate; 
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
	
	public Trade() {
		
	}

	public Trade(Integer id, String account, String accountType, Double buyQuantity, Double sellQuantity, Double buyPrice,
			Double sellPrice, String benchmark, Timestamp tradeDate, String security, String status, String trader,
			String book, String creationName, Timestamp creationDate, String revisionName, Timestamp revisionDate,
			String dealName, String dealType, String sourceListId, String side) {
		super();
		this.id = id;
		this.account = account;
		this.type = accountType;
		this.buyQuantity = buyQuantity;
		this.sellQuantity = sellQuantity;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		this.benchmark = benchmark;
		this.tradeDate = tradeDate;
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
	
	public Trade(String account, String type, Double buyQuantity) {
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
	}

	public Integer getId() {
		return id;
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
	public void setType(String accountType) {
		this.type = accountType;
	}
	public Double getBuyQuantity() {
		return buyQuantity;
	}
	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	public Double getSellQuantity() {
		return sellQuantity;
	}
	public void setSellQuantity(Double sellQuantity) {
		this.sellQuantity = sellQuantity;
	}
	public Double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getBenchmark() {
		return benchmark;
	}
	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}
	public Timestamp getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Timestamp tradeDate) {
		this.tradeDate = tradeDate;
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
