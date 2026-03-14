package com.syshotel.entitys;

import java.time.LocalDateTime;
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
@Table(name="tb_pedido")
public class Pedido {
	//VOY A DARLE 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;

    

    // Fecha y hora del pedido (puede ser un pedido inmediato o programado)
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    // Estado del pedido (ej. Pendiente, En Proceso, Entregado, Cancelado)
    @Column(name = "estado")
    private String estado;

 // 2.0. RESERVA-PEDIDO
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_reserva")
    @JsonIgnoreProperties("pedidos")
    private Reserva reserva;
    
    //3.0 HUESPED-PEDIDO
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_huesped")
    @JsonIgnoreProperties("reservas")
    private Huesped huesped;
    
    
    
    // 4.0. PEDIDO-DETALLEPEDIDO-Relación OneToMany con el detalle del pedido (un pedido puede tener muchos detalles de productos o servicios)
    @OneToMany(mappedBy = "pedido")
    @JsonIgnore
    private List<DetallePedido> detalles;


    
    //6.0 Prodcuto-service-pedido

    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "id_producto")//caraga a ese huesped, al hacer un get
    @JsonIgnoreProperties("pedido") // Evita ciclo infinito
    //@JsonIgnoreProperties("habitacion") // <- Evita cargar habitacion completa (y sus reservas)
    private ProductoServicio producto;
    

    
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



	public LocalDateTime getFechaHora() {
		return fechaHora;
	}



	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public Reserva getReserva() {
		return reserva;
	}



	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}



	public Huesped getHuesped() {
		return huesped;
	}



	public void setHuesped(Huesped huesped) {
		this.huesped = huesped;
	}



	public List<DetallePedido> getDetalles() {
		return detalles;
	}



	public void setDetalles(List<DetallePedido> detalles) {
		this.detalles = detalles;
	}


	

	public ProductoServicio getProducto() {
		return producto;
	}



	public void setProducto(ProductoServicio producto) {
		this.producto = producto;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


}
