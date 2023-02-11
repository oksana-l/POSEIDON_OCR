package com.nnk.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingFormDTO;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.RatingServiceImpl;

public class RatingServiceImplTest {

	private RatingRepository ratingRepository;
	private RatingService ratingService;
	private RatingFormDTO ratingDto;
	
	@BeforeEach
	public void setUp() {
		ratingRepository = mock(RatingRepository.class);
		ratingService = new RatingServiceImpl(ratingRepository);
		
	}
	
	@Test
	public void shouldFindAllRatingTest() {
		Rating rating = new Rating();
		rating.setMoodysRating("Premium");
		List<Rating> ratingList= new ArrayList<Rating>();
		ratingList.add(rating);
		
		when(ratingRepository.findAll()).thenReturn(ratingList);
		
		List<Rating> newRatingList = ratingService.findAllRating();

		Assertions.assertEquals(ratingList.isEmpty(), newRatingList.isEmpty());
		Assertions.assertEquals(ratingList.size(), newRatingList.size());
		Assertions.assertEquals("Premium", newRatingList.get(0).getMoodysRating());
	}
	
	@Test
	public void shouldCreateTest() {
		ratingDto = new RatingFormDTO();
		ratingDto.setMoodysRating("First");
		ratingDto.setSandPRating("Premium");
		ratingDto.setFitchRating("5");
		ratingDto.setOrderNumber(5);
		
		when(ratingRepository.save(any())).thenReturn(new Rating(ratingDto));
		
		Rating savedRating = ratingService.create(ratingDto);
		
		Assertions.assertEquals("First", savedRating.getMoodysRating());
		Assertions.assertEquals("Premium", savedRating.getSandPRating());
		Assertions.assertEquals("5", savedRating.getFitchRating());
		Assertions.assertEquals(5, savedRating.getOrderNumber());
		
	}
	
	@Test
	public void shouldGetRatingByIdTest() {
		Rating rating = new Rating();
		rating.setId(1);
		rating.setMoodysRating("TestAccount");
		rating.setSandPRating("TestType");
		rating.setFitchRating("3");
		rating.setOrderNumber(3);
		when(ratingRepository.getReferenceById(anyInt())).thenReturn(rating);
		
		RatingFormDTO ratingDto = ratingService.getRatingById(1);
		
		Assertions.assertEquals(1, ratingDto.getId());
		Assertions.assertEquals("TestAccount", ratingDto.getMoodysRating());
		Assertions.assertEquals("TestType", ratingDto.getSandPRating());
		Assertions.assertEquals("3", ratingDto.getFitchRating());	
		Assertions.assertEquals(3, ratingDto.getOrderNumber());	
	}
	
	@Test
	public void shouldUpdateTest() {
		ratingDto = new RatingFormDTO();
		ratingDto.setMoodysRating("First");
		ratingDto.setSandPRating("Premium");
		ratingDto.setFitchRating("5");
		ratingDto.setOrderNumber(1);
		
		Rating rating = new Rating();
		rating.setMoodysRating("TestAccount");
		rating.setSandPRating("TestType");
		rating.setFitchRating("3");
		rating.setOrderNumber(2);
		
		when(ratingRepository.getReferenceById(anyInt())).thenReturn(rating);	
		when(ratingRepository.save(any())).thenReturn(new Rating(ratingDto));
		
		Rating updatedRating = ratingService.update(1, ratingDto);
		
		Assertions.assertEquals("First", updatedRating.getMoodysRating());
		Assertions.assertEquals("Premium", updatedRating.getSandPRating());
		Assertions.assertEquals("5", updatedRating.getFitchRating());
		Assertions.assertEquals(1, ratingDto.getOrderNumber());	
	}
	
	@Test
	public void shouldDeleteBidByIdTest() {
		Rating rating = new Rating();
		rating.setId(1);
		
		ratingService.deleteRatingById(1);
		
		verify(ratingRepository).deleteById(1);
	}
}
