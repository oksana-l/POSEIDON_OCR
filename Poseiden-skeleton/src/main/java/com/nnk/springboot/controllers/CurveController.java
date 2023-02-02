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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurveFormDTO;
import com.nnk.springboot.services.CurvePointService;

@Controller
@RequestMapping("/curvePoint")
@SessionAttributes("user")
public class CurveController {
	
	@Autowired
	private CurvePointService curvePointService;
	
	@ModelAttribute("curvePoint")
	public CurvePoint curvePoint() {
		return new CurvePoint();
	}

    @RequestMapping("/list")
    public String home(Authentication auth, Model model)
    {
    	model.addAttribute("curvepointlist", curvePointService.findAllCurvePoint());
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addCurvePointForm(Authentication auth, Model model) {
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@ModelAttribute("curvePoint") @Valid CurveFormDTO curveFormDto, 
    		BindingResult result, Model model) {
    	if (!result.hasErrors()) {
    		curvePointService.create(curveFormDto);
            model.addAttribute("curvePoint", curvePointService.findAllCurvePoint());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	CurveFormDTO curveFormDto = curvePointService.getCurvePointById(id);
    	if (curveFormDto == null) {
    		// COMPLETER !!!
    	}
    	model.addAttribute("curvePoint", curveFormDto);
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, 
    							   @ModelAttribute @Valid CurveFormDTO curveFormDto, 
    							   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePointService.update(id, curveFormDto);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
    	curvePointService.deleteCurvePointById(id);
        return "redirect:/curvePoint/list";
    }
}
