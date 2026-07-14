package com.vgnit.medical.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vgnit.medical.dto.UserDto;
import com.vgnit.medical.entity.User;
import com.vgnit.medical.repository.UserRepository;
import com.vgnit.medical.service.CustomUserDetails;
import com.vgnit.medical.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	// Load the form !!
	@GetMapping("/registration")
	public String getRegistrationPage(Model model)
	{
		UserDto dto = new UserDto();

		model.addAttribute("user", dto);
		return "register";
	}

	// Sign up/Registration !!
	@PostMapping("/registration")
	public String saveUser(@ModelAttribute("user") UserDto userDto, Model model, HttpSession session) {
		boolean checkEmail = userService.checkEmail(userDto.getEmail());
		if (checkEmail)
		{
			session.setAttribute("msg", "This email is already exsits!");
		} 
		else 
		{
			userService.save(userDto);
			session.setAttribute("msg", "User registered successfully!");
		}

		return "redirect:/registration";
	}

	// Login !!
	@GetMapping("/login")
	public String login() 
	{
		return "login";
	}

	// View my profile !!
	@GetMapping("/myProfile")
	public String viewProfile(Model model, Principal principal)
	{
		
		 if(principal!=null)
		 {
			String email = principal.getName();
			User user = userRepository.findByEmail(email);
			model.addAttribute("user", user);
		 }
		
		return "view-profile";
	}
	
	// Edit Account Details !!
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model)
	{
		String email = loggedUser.getUsername();
		User user = userService.getByEmail(email);
		model.addAttribute("user", user);
		
		return "profile-update";
	}
	
	// Save Edited Account Details !!
	@PostMapping("/account/update")
	public String saveDetails(User user, @AuthenticationPrincipal CustomUserDetails loggedUser)
	{
		loggedUser.setFullname(user.getFullname());
		loggedUser.setUsername(user.getEmail());
		
		userService.updateAccount(user);
		
		return "redirect:/myProfile";
	}
}
