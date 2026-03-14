package com.syshotel.DTO;

import java.util.Set;

public class UsuarioResponse {
	
		private String username;
	    private Set<String> roles;
	    
	    
	    public UsuarioResponse(String username, Set<String> roles) {
	        this.username = username;
	        this.roles = roles;
}

//GETTER AND SETTSERES
		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public Set<String> getRoles() {
			return roles;
		}


		public void setRoles(Set<String> roles) {
			this.roles = roles;
		}
	    
}
