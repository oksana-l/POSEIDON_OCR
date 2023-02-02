package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    
	@Autowired
	RatingService ratingService;
	
	@ModelAttribute("rating")
	public Rating rating() {
		return new Rating();
	}
	
    @RequestMapping("/list")
    public String home(Authentication auth, Model model)
    {
    	model.addAttribute("ratingList", ratingService.findAllRating());
        return "rating/list";
    }

    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/validate")
    public String validate(@ModelAttribute("rating") @Valid RatingFormDTO ratingFormDto, BindingResult result, 
    		Model model) {
    	if (!result.hasErrors()) {
    		ratingService.create(ratingFormDto);
            model.addAttribute("rating", ratingService.findAllRating());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	RatingFormDTO ratingFormDto = ratingService.getRatingById(id);
    	model.addAttribute("rating", ratingFormDto);
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, 
    						 @Valid RatingFormDTO ratingFormDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        ratingService.update(id, ratingFormDto);
        return "redirect:/rating/list";
    }

    @GetMapping("delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
    	ratingService.deleteRatingById(id);
        return "redirect:/rating/list";
    }
}
