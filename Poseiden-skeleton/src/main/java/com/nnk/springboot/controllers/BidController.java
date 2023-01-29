package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.dto.BidFormDTO;
import com.nnk.springboot.services.BidService;

@Controller
@RequestMapping("/bid")
@SessionAttributes("user")
public class BidController {
    
	@Autowired
	private BidService bidService;
	
	@ModelAttribute("bid")
	public Bid bid() {
		return new Bid();
	}
	
    @GetMapping("/list")
    public String home(Authentication auth, Model model) {
    	 model.addAttribute("bidList", bidService.findAllBids());
        return "bid/list";
    }

    @GetMapping("/add")
    public String addBidForm(Authentication auth, Model model) {
        return "bid/add";
    }

    @PostMapping("/validate")
    public String validate(@ModelAttribute("bid") @Valid BidFormDTO bidDto,
    					BindingResult result, Model model) {
		if (bidDto.getAccount() == "") {
			FieldError error = new FieldError("bid", "account", 
											  "Account is mandatory");
			result.addError(error);
			return "bid/add";
		}
		
		if (bidDto.getTypeAccount() == "") {
			FieldError error = new FieldError("bid", "typeAccount",
											  "Type is mandatory");
			result.addError(error);
			return "bid/add";
		}
		else if (!result.hasErrors()) {
        	bidService.save(bidDto);
            model.addAttribute("bid", bidService.findAllBids());
            return "redirect:/bid/list";
        }
		return "bid/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form 
    	bidService.getBidById(id);
        return "bid/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bid/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bid/list";
    }
}
