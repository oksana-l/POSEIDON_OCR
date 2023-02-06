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

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.domain.dto.RuleFormDTO;
import com.nnk.springboot.services.RuleService;

@Controller
@RequestMapping("/rule")
@SessionAttributes("user")
public class RuleController {
    
	@Autowired
	private RuleService ruleService;
	
	@ModelAttribute("rule")
	public Rule rule() {
		return new Rule();
	}

    @RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("ruleList", ruleService.findAllRule());
        return "rule/list";
    }

    @GetMapping("/add")
    public String addRuleForm(Authentication auth, Model model) {
        return "rule/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid RuleFormDTO ruleDto, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
    		ruleService.create(ruleDto);
            model.addAttribute("rule", ruleService.findAllRule());
            return "redirect:/rule/list";
        }
        return "rule/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("rule", ruleService.getRuleById(id));
        return "rule/update";
    }

    @PostMapping("/update/{id}")
    public String updateRule(@PathVariable("id") Integer id, @Valid RuleFormDTO ruleDto,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rule and return Rule list
        if (result.hasErrors()) {
            return "rule/update";
        }
        
        ruleService.update(id, ruleDto);
        return "redirect:/rule/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
    	ruleService.deleteRuleById(id);
        return "redirect:/rule/list";
    }
}
