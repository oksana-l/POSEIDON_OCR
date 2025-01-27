package com.nnk.springboot.domain.dto;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nnk.springboot.domain.Rating;

public class RatingFormDTO {

	private Integer id;
	@NotBlank(message = "Moody's Rating is mandatory")
	private String moodysRating; 
	@NotBlank(message = "SandP Rating is mandatory")
	private String sandPRating; 
	@NotBlank(message = "Fitch Rating is mandatory")
	private String fitchRating; 
	@NotNull(message = "Order Number is mandatory") @Min(0)
	private Integer orderNumber;

	public RatingFormDTO() {
		super();
	}
	
	public RatingFormDTO(Rating raiting) {
		super();
		this.id = raiting.getId();
		this.moodysRating = raiting.getMoodysRating();
		this.sandPRating = raiting.getSandPRating();
		this.fitchRating = raiting.getFitchRating();
		this.orderNumber = raiting.getOrderNumber();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMoodysRating() {
		return moodysRating;
	}

	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}

	public String getSandPRating() {
		return sandPRating;
	}

	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
	}

	public String getFitchRating() {
		return fitchRating;
	}

	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fitchRating, id, moodysRating, orderNumber, sandPRating);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		RatingFormDTO other = (RatingFormDTO) obj;
		return Objects.equals(fitchRating, other.fitchRating) && Objects.equals(id, other.id)
				&& Objects.equals(moodysRating, other.moodysRating) && Objects.equals(orderNumber, other.orderNumber)
				&& Objects.equals(sandPRating, other.sandPRating);
	}
}
