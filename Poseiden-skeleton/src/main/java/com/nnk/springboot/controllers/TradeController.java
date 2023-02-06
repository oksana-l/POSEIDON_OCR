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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeFormDTO;
import com.nnk.springboot.services.TradeService;

@Controller
@RequestMapping("/trade")
@SessionAttributes("user")
public class TradeController {

	@Autowired
	private TradeService tradeService;

	@ModelAttribute("trade")
	public Trade trade() {
		return new Trade();
	}
	
    @RequestMapping("/list")
    public String home(Model model) {
    	model.addAttribute("tradeList", tradeService.findAllTrade());
        return "trade/list";
    }

    @GetMapping("/add")
    public String addUser(Authentication auth, Model model) {
        return "trade/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid TradeFormDTO tradeDto, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
    		tradeService.create(tradeDto);
            model.addAttribute("trade", tradeService.findAllTrade());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	model.addAttribute("trade", tradeService.getTradeById(id));
        return "trade/update";
    }

    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeFormDTO tradeDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        
        tradeService.update(id, tradeDto);
        return "redirect:/trade/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTradeById(id);
        return "redirect:/trade/list";
    }
}
