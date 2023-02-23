package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccesDeniedController {
	
	@GetMapping("/403")
	public String accesDenied() {
		return "403";
	}
}
