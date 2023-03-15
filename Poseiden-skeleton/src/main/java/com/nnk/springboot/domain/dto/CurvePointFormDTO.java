package com.nnk.springboot.domain.dto;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.nnk.springboot.domain.CurvePoint;

public class CurvePointFormDTO {

	private Integer id;
	@NotNull(message = "Curve Id is mandatory") @Min(0)
	private Integer curveId;
	@NotNull(message = "Term is mandatory") @Min(0)
	private Double term;
	@NotNull(message = "Value is mandatory")
	private Double value;
	
	public CurvePointFormDTO() {
		super();
	}

	public CurvePointFormDTO(CurvePoint curvePoint) {
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurvePointFormDTO other = (CurvePointFormDTO) obj;
		return Objects.equals(curveId, other.curveId) && Objects.equals(id, other.id)
				&& Objects.equals(term, other.term) && Objects.equals(value, other.value);
	}
	
}
