package com.nnk.springboot.controllers;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.nnk.springboot.domain.dto.UserDTO;
import com.nnk.springboot.services.UserService;

/**
 * The Class UserController.
 * Main logic for coupling Rest application requests 
 * to the actual database functionality
 */
@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {
    
    @Autowired
    private UserService userService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@ModelAttribute("user")
    public UserDTO user() {
        return new UserDTO();
    }
	
	/**
	 * Function for displayng the list of all Users.
	 *
	 * @param model Model
	 * @return String
	 */
	@RolesAllowed("ADMIN")
    @GetMapping("/list")
    public String userList(Model model) {
    	logger.info("Displayng the list of Users");
        model.addAttribute("users", userService.findAllUsers());
        return "user/list";
    }

    /**
     * Function for displayng the form to adding a new user.
     *
     * @return String
     */
    @GetMapping("/add")
    public String addUser() {
    	logger.info("Displayng the form for adding User");
        return "user/add";
    }

    /**
     * Function for validate the form to adding a new user 
     * and registering in the database
     *
     * @param userDto UserDTO
     * @param result BindingResult
     * @return String
     */   
    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("user") UserDTO userDto, 
    		BindingResult result) {
    	
		if (userService.ifUserExists(userDto)) {
        	logger.info("The User has not added");
			FieldError error = new FieldError("user", "username", "User already exists");
			result.addError(error);
		}
        if (!result.hasErrors()) {
            logger.info("Creating the User");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userDto.setPassword(encoder.encode(userDto.getPassword()));
            userService.create(userDto);
            return "redirect:/bid/list";
        }
        return "user/add";
    }

    /**
     * Function for displayng the form to updating the user.
     *
     * @param id Integer
     * @param model Model
     * @return String
     */
    @RolesAllowed("ADMIN")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	Optional<UserDTO> userDto = userService.findUserById(id);
        if (userDto.isPresent()) {
            logger.info("Displayng the User with id {}", id);
            model.addAttribute("user", userDto.get());
            return "user/update";
        } else {
        	logger.info("The User with id {} is not found", id);
        	return "redirect:/user/list";
        }
    }

    /**
     * Function for validate the form to updating the user 
     * and registering changes in the database
     *
     * @param id Integer
     * @param userDto UserDTO
     * @param result BindingResult
     * @return String
     */
    @RolesAllowed("ADMIN")
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDTO userDto,
    		BindingResult result) {

        if (result.hasErrors()) {
            logger.info("The User with id {} has not updated", id);
            return "user/update";
        }

        logger.info("Updating the User with id {}", id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userService.updateUser(id, userDto);
        return "redirect:/user/list";
    }

    /**
     * Function for deleting a user from the database.
     *
     * @param id Integer
     * @return String
     */
    @RolesAllowed("ADMIN")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
    	logger.info("The User with id {} has deleted", id);
        return "redirect:/user/list";
    }
}
