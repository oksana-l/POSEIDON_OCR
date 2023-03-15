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

import com.nnk.springboot.domain.dto.CurvePointFormDTO;
import com.nnk.springboot.services.CurvePointService;

@Controller
@RequestMapping("/curvePoint")
@SessionAttributes("user")
public class CurvePointController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurvePointService curvePointService;
	
	@ModelAttribute("curvePoint")
	public CurvePointFormDTO curvePoint() {
		return new CurvePointFormDTO();
	}

    @GetMapping("/list")
    public String curvePointList(Model model) {
    	logger.info("Displayng the list of CurvePoints");
    	model.addAttribute("curvepointlist", curvePointService.findAllCurvePoint());
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addCurvePointForm() {
    	logger.info("Displayng the form for adding CurvePoints");
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@ModelAttribute("curvePoint") @Valid CurvePointFormDTO curvePointFormDto, 
    		BindingResult result) {
    	if (result.hasErrors()) {
        	logger.info("The CurvePoint has not added");
            return "curvePoint/add";
        }
        logger.info("Creating the CurvePoint");
		curvePointService.create(curvePointFormDto);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model,
    		@ModelAttribute("curvePoint") @Valid CurvePointFormDTO curvePointFormDto, 
    		BindingResult result) {
    	if (!curvePointService.ifCurvePointExists(id)) {
        	logger.info("The CurvePoint with id {} is not exists", id);
            FieldError error = new FieldError("curvePoint", "id", "The CurvePoint whit this ID is not exists");
            result.addError(error);
    	} else {
    	logger.info("Displayng the CurvePoint with id {}", id);
    	model.addAttribute("curvePoint", curvePointService.getCurvePointById(id));
    	}
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, 
    	@ModelAttribute @Valid CurvePointFormDTO curvePointFormDto, 
    	BindingResult result) {
    	if (!curvePointService.ifCurvePointExists(id)) {
        	logger.info("The CurvePoint with id {} is not exists", id);
            FieldError error = new FieldError("curvePoint", "id", "The CurvePoint whit this ID is not exists");
            result.addError(error);
    	}
        if (result.hasErrors()) {
        	logger.info("The CurvePoint with id {} has not updated", id);
            return "curvePoint/update";
        }
        logger.info("Updating the CurvePoint with id {}", id);
        curvePointService.update(id, curvePointFormDto);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) {
    	curvePointService.deleteCurvePointById(id);
    	logger.info("The CurvePoint with id {} has deleted", id);
        return "redirect:/curvePoint/list";
    }
}
