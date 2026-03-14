package com.syshotel.entitys;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name= "tb_huesped")
public class Huesped {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_huesped")
	    private Integer id;

	    @Column(name = "nombre_huesped", nullable = false, length = 100)
	    private String nombre;

	    @Column(name = "dni_huesped", unique = true, nullable = false, length = 8)
	    private String dni;

	    @Column(name = "direccion", length = 150)
	    private String direccion;

	    @Column(name = "email", length = 100)
	    private String email;

	    @Column(name = "telefono", length = 15)
	    private String telefono;
	    

	    // 1.1 HUESPED-RESERVA
	    @OneToMany(mappedBy = "huesped")
	    @JsonIgnore 
	    private List<Reserva> reservas;

		   @ManyToOne
		    @JoinColumn(name="usuario_id")
		    @JsonIgnoreProperties("reservas")
		    private Usuario usuario;

	    
	    
		public Integer getId() {
			return id;
		}


		public void setId(Integer id) {
			this.id = id;
		}


		public String getNombre() {
			return nombre;
		}


		public void setNombre(String nombre) {
			this.nombre = nombre;
		}


		public String getDni() {
			return dni;
		}


		public void setDni(String dni) {
			this.dni = dni;
		}


		public String getDireccion() {
			return direccion;
		}


		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getTelefono() {
			return telefono;
		}


		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}


		public List<Reserva> getReservas() {
			return reservas;
		}


		public void setReservas(List<Reserva> reservas) {
			this.reservas = reservas;
		}


		public Usuario getUsuario() {
			return usuario;
		}


		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}
	    
	    
	

}
