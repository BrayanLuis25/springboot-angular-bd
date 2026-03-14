package com.syshotel.DTO;

import java.util.List;
import java.util.Set;

public class AuthResponse {
//AuthResponse.java (para devolver token)



	    private String token;
	    private String username;
	    private List<String> roles;

	    
	    public AuthResponse(String token, String username, List<String> roles) {
	        this.token = token;
	        this.username = username;
	        this.roles = roles;
	    }
	    
	    public AuthResponse() {}
	    
	    public String getToken() { return token; }
	    public void setToken(String token) { this.token = token; }

	    public String getUsername() { return username; }
	    public void setUsername(String username) { this.username = username; }

	    public List<String> getRoles() { return roles; }
	    public void setRoles(List<String> roles) { this.roles = roles; }
	

}


