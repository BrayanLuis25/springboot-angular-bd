package com.syshotel.controller;

import java.util.stream.Collectors;   // Para .collect(Collectors.toList())
import java.util.List;        

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syshotel.DTO.AuthResponse;
import com.syshotel.DTO.LoginRequest;
import com.syshotel.DTO.RegisterRequest;
import com.syshotel.DTO.UsuarioResponse;
import com.syshotel.security.JwtUtil;
import com.syshotel.service.AuthService;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequestMapping("/api/auth")
public class AuthentificationController {
	 @Autowired 
	 private AuthService authService;
	 
	 @Autowired
	 private AuthenticationManager authenticationManager;
	 
	 @Autowired private JwtUtil jwtProvider;
	 
	 
	  @PostMapping("/register")
	  public ResponseEntity<UsuarioResponse> register(@RequestBody RegisterRequest dto){
		    System.out.println(dto.getRole());
		  return ResponseEntity.ok(authService.register(dto))
	    		;
	    
	  }

	  @PostMapping("/login")
	  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest dto){
		  Authentication auth = authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
			    );

		    UserDetails userDetails = (UserDetails) auth.getPrincipal();
			    String token = jwtProvider.generateToken(userDetails);

			    AuthResponse response = new AuthResponse();
			    response.setToken(token);
			    response.setUsername(dto.getUsername());
			    response.setRoles(auth.getAuthorities().stream()
			        .map(a -> a.getAuthority())
			        .collect(Collectors.toList()));

			    
			    return ResponseEntity.ok(response);   
			    
	  }
}
