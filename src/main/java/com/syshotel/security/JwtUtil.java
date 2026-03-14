package com.syshotel.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



@Component
public class JwtUtil {

	  @Value("${jwt.secret}") private String secret;
	  @Value("${jwt.expirationMs}") private long expirationMs;

	  public String generateToken(UserDetails user) {
	    Map<String,Object> claims = new HashMap<>();
	    claims.put("roles", user.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.toList()));
	    return Jwts.builder()
	      .setClaims(claims)
	      .setSubject(user.getUsername())
	    
	      .setIssuedAt(new Date())
	      .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
	      .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
	      .compact();
	  }

	  public String extractUsername(String token) { return Jwts.parserBuilder().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build()
	      .parseClaimsJws(token).getBody().getSubject(); }

	  public boolean validateToken(String token, UserDetails userDetails) {
	    try {
	      final String username = extractUsername(token);
	      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    } catch (JwtException | IllegalArgumentException e) {
	      return false;
	    }
	  }

	  private boolean isTokenExpired(String token) {
	    Date exp = Jwts.parserBuilder().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build()
	      .parseClaimsJws(token).getBody().getExpiration();
	    return exp.before(new Date());
	  }
	

}
