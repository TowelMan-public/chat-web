package com.example.demo.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.configurer.UrlConfig;

@Controller
@RequestMapping(UrlConfig.ROOT_URL)
public class UserControl {
	
	@GetMapping("login")
	public String showLoginPage(Model model) {
		return "/login";
	}
}
