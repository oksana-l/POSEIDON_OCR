package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	Integer id;
	@NotNull(message = "Curve Id is mandatory")
	@Column
	Integer curveId; 
	@Column
	Timestamp asOfDate;
	@NotNull(message = "Term is mandatory")
	@Column
	Double term;
	@NotNull(message = "Value is mandatory")
	@Column
	Double value;
	@Column
	Timestamp creationDate;	
	
	public CurvePoint() {
		
	}

	public CurvePoint(Timestamp asOfDate, Double term, Double value,
			Timestamp creationDate) {
		super();
		this.asOfDate = asOfDate;
		this.term = term;
		this.value = value;
		this.creationDate = creationDate;
	}

	// a voir si vraiment besoin
	public CurvePoint(Integer id, Double term, Double curveValue) {
		this.term = term;
		this.value = curveValue;
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}	
	
}
