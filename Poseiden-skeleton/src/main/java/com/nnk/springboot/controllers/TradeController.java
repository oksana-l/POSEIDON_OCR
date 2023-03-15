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

import com.nnk.springboot.domain.dto.TradeFormDTO;
import com.nnk.springboot.services.TradeService;

@Controller
@RequestMapping("/trade")
@SessionAttributes("user")
public class TradeController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TradeService tradeService;

	@ModelAttribute("trade")
	public TradeFormDTO trade() {
		return new TradeFormDTO();
	}
	
    @RequestMapping("/list")
    public String home(Model model) {
   	 	logger.info("Displayng the list of Trade");
    	model.addAttribute("tradeList", tradeService.findAllTrade());
        return "trade/list";
    }

    @GetMapping("/add")
    public String addUser() {
    	logger.info("Displayng the form for adding Trade");
        return "trade/add";
    }

    @PostMapping("/validate")
    public String validate(@ModelAttribute("trade") @Valid TradeFormDTO tradeDto, 
    		BindingResult result, Model model) {
    	if (result.hasErrors()) {
        	logger.info("The Trade has not added");
            return "trade/add";
        }
        logger.info("Creating the Trade");
		tradeService.create(tradeDto);
        return "redirect:/trade/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model,
    		@ModelAttribute("trade") @Valid TradeFormDTO tradeDto, BindingResult result) {
    	if (!tradeService.ifTradeExists(id)) {
        	logger.info("The Trade with id {} is not exists", id);
            FieldError error = new FieldError("trade", "id", 
            		"The Trade whit this ID is not exists");
            result.addError(error);
    	} else {
	    	model.addAttribute("trade", tradeService.getTradeById(id));
	        logger.info("The Trade with id " + id + " is displayed");
	    }
        return "trade/update";
    }

    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeFormDTO tradeDto,
                             BindingResult result, Model model) {
    	if (!tradeService.ifTradeExists(id)) {
        	logger.info("The Trade with id {} is not exists", id);
            FieldError error = new FieldError("trade", "id", 
            		"The Trade whit this ID is not exists");
            result.addError(error);
    	}
        if (result.hasErrors()) {
        	logger.info("The Trade with id {} has not updated", id);
            return "trade/update";
        }
        logger.info("Updating the Trade with id {}", id);
        tradeService.update(id, tradeDto);
        return "redirect:/trade/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTradeById(id);
    	logger.info("The Trade with id {} has deleted", id);
        return "redirect:/trade/list";
    }
}
