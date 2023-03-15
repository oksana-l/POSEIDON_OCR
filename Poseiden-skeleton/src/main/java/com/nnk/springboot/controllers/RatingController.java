package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingFormDTO;
import com.nnk.springboot.services.RatingService;

@Controller
@RequestMapping("/rating")
@SessionAttributes("user")
public class RatingController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	RatingService ratingService;
	
	@ModelAttribute("rating")
	public RatingFormDTO rating() {
		return new RatingFormDTO();
	}
	
    @GetMapping("/list")
    public String ratingList(Model model) {
    	logger.info("Displayng the list of Ratings");
    	model.addAttribute("ratingList", ratingService.findAllRating());
        return "rating/list";
    }

    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
    	logger.info("Displayng the form for adding Rating");
        return "rating/add";
    }

    @PostMapping("/validate")
    public String validate(@ModelAttribute("rating") @Valid RatingFormDTO ratingFormDto, 
    		BindingResult result) {
    	if (result.hasErrors()) {
        	logger.info("The Rating has not added");
            return "rating/add";
        }
    	logger.info("Creating the Rating");
		ratingService.create(ratingFormDto);
        return "redirect:/rating/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, 
    		@Valid RatingFormDTO ratingFormDto, BindingResult result) {
    	if (!ratingService.ifRatingExists(id)) {
        	logger.info("The Rating with id {} is not exists", id);
            FieldError error = new FieldError("rating", "id", "The Rating whit this ID is not exists");
            result.addError(error);
    	} else {
    	logger.info("Displayng the Rating with id {}", id);
    	model.addAttribute("rating", ratingService.getRatingById(id));
    	}
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, 
    						 @Valid RatingFormDTO ratingFormDto, BindingResult result) {
    	if (!ratingService.ifRatingExists(id)) {
        	logger.info("The Rating with id {} is not exists", id);
            FieldError error = new FieldError("rating", "id", "The Rating whit this ID is not exists");
            result.addError(error);
    	}
    	if (result.hasErrors()) {
        	logger.info("The Rating with id {} has not updated", id);
            return "rating/update";
        }
        logger.info("Updating the Rating with id {}", id);
        ratingService.update(id, ratingFormDto);
        return "redirect:/rating/list";
    }

    @GetMapping("delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
    	ratingService.deleteRatingById(id);
    	logger.info("The Rating with id {} has deleted", id);
        return "redirect:/rating/list";
    }
}
