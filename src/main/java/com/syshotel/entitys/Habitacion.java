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
@Table(name ="tb_habitacion")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitacion")
    private Integer id;

    @Column(name = "numero_habitacion" , unique = true, nullable = false, length = 8)
    private String numero; // Número de la habitación (Ej: 101, 102, etc.)

    @Column(name = "tipo_habitacion")
    private String tipo; // Ej: Individual, Doble, Suite

    @Column(name = "descripcion")
    private String descripcion; // Descripción de la habitación (Ej: "Hab. con vista al mar")

    @Column(name = "precio_noche")
    private Double precioNoche; // Precio por noche de la habitación

    @Column(name = "estado")
    private String estado; // Estado de la habitación (Ej: Disponible, Ocupada, Reservada)

 //5.0 habitacion-reserva
  
    
    // Relación OneToMany con la entidad Reserva (una habitación puede tener muchas reservas)
    @OneToMany(mappedBy ="habitacion")
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecioNoche() {
		return precioNoche;
	}

	public void setPrecioNoche(Double precioNoche) {
		this.precioNoche = precioNoche;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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
