package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingFormDTO;

public interface RatingService {

	List<Rating> findAllRating();

	Rating create(RatingFormDTO bid);

	void deleteRatingById(Integer id);

	Rating update(Integer id, RatingFormDTO bidDto);

	RatingFormDTO getRatingById(Integer id);
}
