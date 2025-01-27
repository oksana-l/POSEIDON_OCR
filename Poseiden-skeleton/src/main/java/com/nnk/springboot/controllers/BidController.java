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

import com.nnk.springboot.domain.dto.BidFormDTO;
import com.nnk.springboot.services.BidService;

@Controller
@RequestMapping("/bid")
@SessionAttributes("user")
public class BidController {
    
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BidService bidService;
	
	@ModelAttribute("bid")
	public BidFormDTO bid() {
		return new BidFormDTO();
	}

    @GetMapping("/list")
    public String bidList(Model model) {   	
    	logger.info("Displayng the list of Bids");   	
    	model.addAttribute("bidList", bidService.findAllBids());
        return "bid/list";
    }

    @GetMapping("/add")
    public String addBidForm() {
    	logger.info("Displayng the form for adding Bid");  	
        return "bid/add";
    }

    @PostMapping("/validate")
    public String validate(@ModelAttribute("bid") @Valid BidFormDTO bidDto,
    		BindingResult result) {
    	
    	if (result.hasErrors()) {
        	logger.info("The Bid has not added");
        	return "bid/add";
        }
        logger.info("Creating the Bid");
        bidService.create(bidDto);
        return "redirect:/bid/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, 
    		@ModelAttribute("bid") @Valid BidFormDTO bidDto, 
    		BindingResult result, Model model) {      
        if (!bidService.ifBidExists(id)) {
        	logger.info("The Bid with id {} is not exists", id);
            FieldError error = new FieldError("bid", "id", "The Bid whit this ID is not exists");
            result.addError(error);
        } else {
	        logger.info("Displayng the Bid with id {}", id);
	        model.addAttribute("bid", bidService.getBidById(id));
        }
        return "bid/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@ModelAttribute("bid") @Valid BidFormDTO bidDto,
    		BindingResult result, @PathVariable("id") Integer id) { 
        if (!bidService.ifBidExists(id)) {
        	logger.info("The Bid with id {} is not exists", id);
            FieldError error = new FieldError("bid", "id", "The Bid whit this ID is not exists");
            result.addError(error);
        }
        if (result.hasErrors()) {
            logger.info("The Bid with id {} has not updated", id);
            return "bid/update";
        } else {
	        logger.info("Updating the Bid with id {}", id);
	        bidService.update(id, bidDto);
	        return "redirect:/bid/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
    	bidService.deleteBidById(id);
    	logger.info("The Bid with id {} has deleted", id);
        return "redirect:/bid/list";
    }
}
