package com.nnk.springboot.controllers;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

	@ModelAttribute("user")
    public User user() {
        return new User();
    }
	
	@RolesAllowed("ADMIN")
    @GetMapping("/list")
    public String home(Authentication auth, Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        return "user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
    	
		if (userRepository.findByUsername(user.getUsername()) != null) {
			FieldError error = new FieldError("user", "username", "L'utilisateur existe déjà");
			result.addError(error);
		}
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.create(user);
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/bid/list";
        }
        return "user/add";
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Authentication auth, Model model) {
        User user = userRepository.findById(id).get();
	        model.addAttribute("user", user);
        return "user/update";
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
    		Authentication auth, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Authentication auth, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
