package com.syshotel.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

	@GetMapping("/")
	public String  inicio() {
		return "inicio"; //template/inicio.html
		
	}
	@GetMapping("/admin")
	public String  admin() {
		return "admin";
		
	}
	
	@GetMapping("/login")
	public String  login() {
		return "login";
	}
	@GetMapping("/publico")
	public String  publico() {
		return "publico";
	}
}
