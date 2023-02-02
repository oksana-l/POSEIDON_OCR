package com.nnk.springboot.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nnk.springboot.domain.Rating;

public class RatingFormDTO {

	private Integer id;
	@NotBlank
	String moodysRating; 
	@NotBlank
	String sandPRating; 
	@NotBlank
	String fitchRating; 
	@NotNull @Min(0)
	Integer orderNumber;

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

}
