package com.syshotel.entitys;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name= "tb_reserva")
public class Reserva {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_reserva")//la columan se asigana al id
	    private Integer id;

	    @Column(name = "fecha_entrada")
	    private LocalDate fechaEntrada;

	    @Column(name = "fecha_salida")
	    private LocalDate fechaSalida;

	    @Column(name = "estado")
	    private String estado; // Ej: Reservado, Confirmado, Cancelado

	    
	    // 1.1. HUESPED-RESERVA
	    @ManyToOne(fetch = FetchType.EAGER) 
	    @JoinColumn(name = "id_huesped")//caraga a ese huesped, al hacer un get
	    @JsonIgnoreProperties("reservas") // Evita ciclo infinito
	    //@JsonIgnoreProperties("habitacion") // <- Evita cargar habitacion completa (y sus reservas)
	    private Huesped huesped;
	    
	    //2.0. RESERVA -PEDIDOS
	    @OneToMany(mappedBy = "reserva")
	    @JsonIgnore
	    private List<Pedido> pedidos;
	
	    //5.0. BIDIRECIONAL
	    //reserva -HABITACIONES
	    @ManyToOne(fetch = FetchType.EAGER) //dice carga el objeto completo.
	    @JoinColumn(name = "id_habitacion")//Y JoinColumn dice qué ID buscar en la base de datos. tiene al padre
	    @JsonIgnoreProperties("reservas") // Evita ciclo infinito
	    private Habitacion habitacion;

	    
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name="usuario_id")
	    @JsonIgnoreProperties("reservas")
	    private Usuario usuario;
/*EAGER → Hibernate carga los objetos relacionados automáticamente.
LAZY → Hibernate solo carga los IDs hasta que los accedes en la sesión, pero fuera de 
la sesión (por ejemplo en un @RestController) eso puede dar LazyInitializationException.*/
		
	    public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public LocalDate getFechaEntrada() {
			return fechaEntrada;
		}

		public void setFechaEntrada(LocalDate fechaEntrada) {
			this.fechaEntrada = fechaEntrada;
		}

		public LocalDate getFechaSalida() {
			return fechaSalida;
		}

		public void setFechaSalida(LocalDate fechaSalida) {
			this.fechaSalida = fechaSalida;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public Huesped getHuesped() {
			return huesped;
		}

		public void setHuesped(Huesped huesped) {
			this.huesped = huesped;
		}

		public List<Pedido> getPedidos() {
			return pedidos;
		}

		public void setPedidos(List<Pedido> pedidos) {
			this.pedidos = pedidos;
		}

		public Habitacion getHabitacion() {
			return habitacion;
		}

		public void setHabitacion(Habitacion habitacion) {
			this.habitacion = habitacion;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}
	    
	
}
