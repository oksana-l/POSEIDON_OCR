package com.nnk.springboot.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingFormDTO;
import com.nnk.springboot.services.RatingService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = RatingController.class)
@WebMvcTest
public class RatingControllerTest {

		@Autowired
		private MockMvc mockMvc;
		
		@MockBean
		private RatingService ratingService;
		private final static String TEST_USER_AUTH = "Admin";
		
		@Test
		public void shouldShowRatingListTest() throws Exception {
			Rating rating = new Rating();
			rating.setId(1);
			rating.setMoodysRating("TestMoodysRating");
			rating.setSandPRating("TestSandPRating");
			rating.setFitchRating("TestFitchRating");
			rating.setOrderNumber(3);
			List<Rating> ratingList = new ArrayList<Rating>();
			ratingList.add(rating);
			when(ratingService.findAllRating()).thenReturn(ratingList);
			
			mockMvc.perform(get("/rating/list")
					.with(user(TEST_USER_AUTH)))
					.andExpect(status().isOk())
					.andExpect(view().name("rating/list"))
					.andExpect(model().attribute("ratingList", ratingList));
		}
		
		@Test
		public void shouldShowAddRatingFormTest() throws Exception {
			
			mockMvc.perform(get("/rating/add")
					.with(csrf())
					.with(user(TEST_USER_AUTH))
		            .param("moodysRating", "")
		            .param("sandPRating", "")
		            .param("fitchRating", "")
		            .param("orderNumber", ""))
	        		.andExpect(view().name("rating/add"));
		}
		
		@Test
		public void shouldValidateAddRatingFormTest() throws Exception {
			RatingFormDTO ratingDto = new RatingFormDTO();
			ratingDto.setMoodysRating("TestMoodysRating");
			ratingDto.setSandPRating("TestSandPRating");
			ratingDto.setFitchRating("TestFitchRating");
			ratingDto.setOrderNumber(3);
			Rating newRating = new Rating(ratingDto);

			when(ratingService.create(ratingDto)).thenReturn(newRating);
			
			mockMvc.perform(post("/rating/validate")
					.with(csrf())
					.with(user(TEST_USER_AUTH))
		            .param("moodysRating", ratingDto.getMoodysRating())
		            .param("sandPRating", ratingDto.getSandPRating())
		            .param("fitchRating", ratingDto.getFitchRating())
		            .param("orderNumber", ratingDto.getOrderNumber().toString()))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/rating/list"));
			
			verify(ratingService, times(1)).create(ratingDto);
		}

		@Test
		public void shouldValidateAddRatingEmptyFormTest() throws Exception {
			RatingFormDTO ratingDto = new RatingFormDTO();
			BindingResult result = mock(BindingResult.class);

			when(result.hasErrors()).thenReturn(true);

			mockMvc.perform(post("/rating/validate")
					.with(csrf())
					.with(user(TEST_USER_AUTH))
		            .param("moodysRating", "")
		            .param("sandPRating", "")
		            .param("fitchRating", "")
		            .param("orderNumber", ""))
					.andExpect(status().isOk())
					.andExpect(view().name("rating/add"));
			
			verify(ratingService, times(0)).create(ratingDto);
		}
		
		@Test
		public void shouldShowUpdateRatingFormTest() throws Exception {
			RatingFormDTO ratingDto = new RatingFormDTO();
			ratingDto.setMoodysRating("TestMoodysRating");
			ratingDto.setSandPRating("TestSandPRating");
			ratingDto.setFitchRating("TestFitchRating");
			ratingDto.setOrderNumber(3);
			
			when(ratingService.ifRatingExists(anyInt())).thenReturn(true);
			when(ratingService.getRatingById(1)).thenReturn(ratingDto);
			
			mockMvc.perform(get("/rating/update/1")
					.with(csrf())
					.with(user(TEST_USER_AUTH))
		            .param("moodysRating", ratingDto.getMoodysRating())
		            .param("sandPRating", ratingDto.getSandPRating())
		            .param("fitchRating", ratingDto.getFitchRating())
		            .param("orderNumber", ratingDto.getOrderNumber().toString()))
					.andExpect(status().isOk())
	        		.andExpect(view().name("rating/update"))
	        		.andExpect(model().attribute("rating", ratingDto));
			
			verify(ratingService).getRatingById(1);
		}
		
		@Test
		public void shouldUpdateRatingIfNotExistsTest() throws Exception {
			when(ratingService.ifRatingExists(anyInt())).thenReturn(false);
			
			mockMvc.perform(get("/rating/update/1")
					.with(csrf())
					.with(user(TEST_USER_AUTH)))
					.andExpect(status().isOk())
					.andExpect(view().name("rating/update"));
			
			verify(ratingService).ifRatingExists(1);
		}
		
		@Test
		public void shouldUpdateRatingTest() throws Exception {
			RatingFormDTO ratingDto = new RatingFormDTO();
			ratingDto.setId(1);
			ratingDto.setMoodysRating("TestMoodysRating");
			ratingDto.setSandPRating("TestSandPRating");
			ratingDto.setFitchRating("TestFitchRating");
			ratingDto.setOrderNumber(3);
			
			when(ratingService.ifRatingExists(anyInt())).thenReturn(true);
			
			mockMvc.perform(post("/rating/update/1")
					.with(csrf())
					.with(user(TEST_USER_AUTH))
		            .param("moodysRating", ratingDto.getMoodysRating())
		            .param("sandPRating", ratingDto.getSandPRating())
		            .param("fitchRating", ratingDto.getFitchRating())
		            .param("orderNumber", ratingDto.getOrderNumber().toString()))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/rating/list"));
			
			verify(ratingService).update(1, ratingDto);
		}
		
		@Test
		public void shouldUpdateInvalidRatingTest() throws Exception {
			RatingFormDTO ratingDto = new RatingFormDTO();
			BindingResult result = mock(BindingResult.class);

			when(result.hasErrors()).thenReturn(true);

			mockMvc.perform(post("/rating/update/1")
					.with(csrf())
					.with(user(TEST_USER_AUTH))
		            .param("moodysRating", "")
		            .param("sandPRating", "")
		            .param("fitchRating", "")
		            .param("orderNumber", ""))
					.andExpect(status().isOk())
					.andExpect(view().name("rating/update"));
			
			verify(ratingService, times(0)).update(1, ratingDto);
		}
		
		@Test
		public void shouldDeleteRatingTest() throws Exception {

			mockMvc.perform( 							
					get("/rating/delete/{id}", 2)
		            .with(csrf())
					.with(user(TEST_USER_AUTH)))
			        .andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/rating/list"));
			
			verify(ratingService).deleteRatingById(2);
		}
	}
