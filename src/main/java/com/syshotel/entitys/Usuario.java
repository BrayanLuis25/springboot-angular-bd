package com.syshotel.entitys;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Usuario {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long id;
	  
	  @Column(unique=true, nullable=false)
	  private String username;
	  
	  @Column(nullable=false)

	  private String password;


		@ManyToMany(fetch =FetchType.EAGER)
		@JoinTable(name ="usuario_roles",
		joinColumns=@JoinColumn(name ="usuario_id"),
		inverseJoinColumns = @JoinColumn(name="rol_id"))
		 // @JsonIgnoreProperties("reservas")
		private Set <Rol> roles;// EJ: "ROLE_USER" o "ROLE_ADMIN"
		
		  @OneToMany(mappedBy = "usuario")
		  @JsonIgnore 
		  private List<Reserva> reservas;

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Set<Rol> getRoles() {
		return roles;
	}


	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	
	
}
