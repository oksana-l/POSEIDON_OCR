package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	Integer id;
	@Column
	Integer curveId; 
	@Column
	Timestamp asOfDate; 
	@Column
	Double term;
	@Column
	Double curveValue;
	@Column
	Timestamp creationDate;	
	
	public CurvePoint() {
		
	}

	public CurvePoint(Timestamp asOfDate, Double term, Double curveValue,
			Timestamp creationDate) {
		super();
		this.asOfDate = asOfDate;
		this.term = term;
		this.curveValue = curveValue;
		this.creationDate = creationDate;
	}

	// a voir si vraiment besoin
	public CurvePoint(Integer id, Double term, Double curveValue) {
		this.term = term;
		this.curveValue = curveValue;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCurveId() {
		return curveId;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public Timestamp getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getCurveValue() {
		return curveValue;
	}

	public void setCurveValue(Double curveValue) {
		this.curveValue = curveValue;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}	
	
}
