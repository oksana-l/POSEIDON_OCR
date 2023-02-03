package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String addRuleForm(Rule bid) {
        return "rule/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid Rule rule, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rule list
        return "rule/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rule by Id and to model then show to the form
        return "rule/update";
    }

    @PostMapping("/update/{id}")
    public String updateRule(@PathVariable("id") Integer id, @Valid Rule rule,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rule and return Rule list
        return "redirect:/rule/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rule by Id and delete the Rule, return to Rule list
        return "redirect:/rule/list";
    }
}
