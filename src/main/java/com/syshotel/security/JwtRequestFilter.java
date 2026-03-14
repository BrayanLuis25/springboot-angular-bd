package com.syshotel.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
//Extrae el token.
	  @Autowired private JwtUtil jwtUtil;
	  @Autowired private UsuarioDetailsServiceImple userDetailsService;
	  
	  @Override
	    protected boolean shouldNotFilter(HttpServletRequest request) {
	        return request.getServletPath().startsWith("/api/auth/");
	    }
	  @Override
	  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
	      throws ServletException, IOException {
		  
	    final String authHeader = req.getHeader("Authorization");
	    String username = null, jwt = null;
	    
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	      jwt = authHeader.substring(7);
	      username = jwtUtil.extractUsername(jwt);
	    }
	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	      UserDetails ud = userDetailsService.loadUserByUsername(username);
	      
	      if (jwtUtil.validateToken(jwt, ud)) {
	        UsernamePasswordAuthenticationToken authtoken = 
	        		new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
	        authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
	        //Si es válido:
	        SecurityContextHolder.getContext().setAuthentication(authtoken);
	      }
	    }
	    chain.doFilter(req, res);
	  }
	
	
}
