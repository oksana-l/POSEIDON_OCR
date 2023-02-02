package com.nnk.springboot.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.nnk.springboot.domain.CurvePoint;

public class CurveFormDTO {

	private Integer id;
	@NotNull @Min(0)
	Integer curveId;
	@NotNull @Min(0)
	Double term;
	@NotNull
	Double value;
	
	public CurveFormDTO() {
		super();
	}

	public CurveFormDTO(CurvePoint curvePoint) {
		super();
		this.id = curvePoint.getId();
		this.curveId = curvePoint.getCurveId();
		this.term = curvePoint.getTerm();
		this.value = curvePoint.getValue();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurveId() {
		return curveId;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
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
	
	
}
