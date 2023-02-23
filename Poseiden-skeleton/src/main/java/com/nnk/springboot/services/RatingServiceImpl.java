package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingFormDTO;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService {
	
	private RatingRepository ratingRepository;
	
	public RatingServiceImpl(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	@Override
	public List<Rating> findAllRating() {
		return ratingRepository.findAll();
	}

	@Override
	public Rating create(RatingFormDTO ratingDto) {
		Rating newRating = new Rating();
		newRating.setFitchRating(ratingDto.getFitchRating());
		newRating.setMoodysRating(ratingDto.getMoodysRating());
		newRating.setOrderNumber(ratingDto.getOrderNumber());
		newRating.setSandPRating(ratingDto.getSandPRating());
		return ratingRepository.save(newRating);
	}

	@Override
	public RatingFormDTO getRatingById(Integer id) {
		return new RatingFormDTO(ratingRepository.getReferenceById(id));
	}

	@Override
	public Rating update(Integer id, RatingFormDTO ratingDto) {
		Rating newRating = ratingRepository.getReferenceById(id);
		newRating.setFitchRating(ratingDto.getFitchRating());
		newRating.setMoodysRating(ratingDto.getMoodysRating());
		newRating.setOrderNumber(ratingDto.getOrderNumber());
		newRating.setSandPRating(ratingDto.getSandPRating());
		return ratingRepository.save(newRating);
	}

	@Override
	public void deleteRatingById(Integer id) {
		ratingRepository.deleteById(id);
	}

	@Override
	public boolean ifRatingExists(Integer id) {
		
		return ratingRepository.existsById(id);
	}
}
