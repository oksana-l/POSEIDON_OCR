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

import com.nnk.springboot.domain.dto.RuleFormDTO;
import com.nnk.springboot.services.RuleService;

@Controller
@RequestMapping("/rule")
@SessionAttributes("user")
public class RuleController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RuleService ruleService;
	
	@ModelAttribute("rule")
	public RuleFormDTO rule() {
		return new RuleFormDTO();
	}

    @GetMapping("/list")
    public String ruleList(Model model) {
    	logger.info("Displayng the list of Rules");
        model.addAttribute("ruleList", ruleService.findAllRule());
        return "rule/list";
    }

    @GetMapping("/add")
    public String addRuleForm() {
    	logger.info("Displayng the form for adding Rules");
        return "rule/add";
    }

    @PostMapping("/validate")
    public String validate(@ModelAttribute("rule") @Valid RuleFormDTO ruleDto,
    		BindingResult result) {
    	if (result.hasErrors()) {
        	logger.info("The Rule has not added");
            return "rule/add";
        }
        logger.info("Creating the Rule");
		ruleService.create(ruleDto);
        return "redirect:/rule/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, 
    		@Valid RuleFormDTO ruleDto, BindingResult result) {
    	if (!ruleService.ifRuleExists(id)) {
        	logger.info("The Rule with id {} is not exists", id);
            FieldError error = new FieldError("rule", "id", "The Rule whit this ID is not exists");
            result.addError(error);
    	} else {
    	logger.info("Displayng the Rule with id {}", id);
        model.addAttribute("rule", ruleService.getRuleById(id));
    	}
        return "rule/update";
    }

    @PostMapping("/update/{id}")
    public String updateRule(@PathVariable("id") Integer id, @Valid RuleFormDTO ruleDto,
                             BindingResult result) {
    	if (!ruleService.ifRuleExists(id)) {
        	logger.info("The Rule with id {} is not exists", id);
            FieldError error = new FieldError("rulr", "id", "The Rule whit this ID is not exists");
            result.addError(error);
    	}
    	if (result.hasErrors()) {
        	logger.info("The Rule with id {} has not updated", id);
            return "rule/update";
        }
        logger.info("Updating the Rule with id {}", id);
        ruleService.update(id, ruleDto);
        return "redirect:/rule/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
    	ruleService.deleteRuleById(id);
    	logger.info("The Rule with id {} has deleted", id);
        return "redirect:/rule/list";
    }
}
