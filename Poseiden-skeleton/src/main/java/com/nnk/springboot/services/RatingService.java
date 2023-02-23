package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingFormDTO;

public interface RatingService {

	List<Rating> findAllRating();

	Rating create(RatingFormDTO rating);

	void deleteRatingById(Integer id);

	Rating update(Integer id, RatingFormDTO ratingDto);

	RatingFormDTO getRatingById(Integer id);
	
	boolean ifRatingExists(Integer id);
}
