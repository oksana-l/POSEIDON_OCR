package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeFormDTO;
import com.nnk.springboot.services.TradeService;

@Controller
@RequestMapping("/trade")
@SessionAttributes("user")
public class TradeController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private TradeService tradeService;

	@ModelAttribute("trade")
	public Trade trade() {
		return new Trade();
	}
	
    @RequestMapping("/list")
    public String home(Model model) {
    	model.addAttribute("tradeList", tradeService.findAllTrade());
   	 	logger.info("The list of Trade is displayed");
        return "trade/list";
    }

    @GetMapping("/add")
    public String addUser(Authentication auth, Model model) {
    	logger.info("The form for adding Trade is displayed");
        return "trade/add";
    }

    @PostMapping("/validate")
    public String validate(@ModelAttribute("trade") @Valid TradeFormDTO tradeDto, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
    		tradeService.create(tradeDto);
            model.addAttribute("trade", tradeService.findAllTrade());
            logger.info("The Trade has added");
            return "redirect:/trade/list";
        }
    	logger.info("The Trade has not added");
        return "trade/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("trade", tradeService.getTradeById(id));
        logger.info("The Trade with id " + id + " is displayed");
        return "trade/update";
    }

    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeFormDTO tradeDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        tradeService.update(id, tradeDto);
        model.addAttribute("trade", tradeService.getTradeById(id));
        logger.info("The Trade with id " + id + " has updated");
        return "redirect:/trade/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTradeById(id);
    	logger.info("The Trade with id " + id + " has deleted");
        return "redirect:/trade/list";
    }
}
