package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nnk.springboot.domain.dto.RatingFormDTO;

@Entity
@Table(name = "rating")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	Integer id;
	@Column
	@NotBlank
	String moodysRating; 
	@Column
	@NotBlank
	String sandPRating; 
	@Column
	@NotBlank
	String fitchRating; 
	@Column
	@NotNull
	Integer orderNumber;
	
	public Rating() {
		
	}

	public Rating(Integer id, String moodysRating, String sandPRating,
			String fitchRating, Integer orderNumber) {
		super();
		this.id = id;
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	public Rating(RatingFormDTO ratingDto) {
		this.id = ratingDto.getId();
		this.moodysRating = ratingDto.getMoodysRating();
		this.sandPRating = ratingDto.getSandPRating();
		this.fitchRating = ratingDto.getFitchRating();
		this.orderNumber = ratingDto.getOrderNumber();
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
