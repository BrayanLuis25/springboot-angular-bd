package com.syshotel.entitys;

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
@Table(name = "tb_producto_servicio")
public class ProductoServicio {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_producto")
	    private Integer id;

	    @Column(name = "nombre_producto")
	    private String nombre;

	    @Column(name = "precio_producto")
	    private Double precio;

	    @Column(name = "tipo")
	    private String tipo; // Ej. comida, bebida, lavandería


	  //4.0 PRODUCTO-DETALLEPEDIDO Relación uno a muchos con detalle pedido
	  @OneToMany(mappedBy = "producto")
	  @JsonIgnore
	  private List<DetallePedido> detalles;



	//6 PEDIDO-PRODUCTOSERVICO
	  @OneToMany(mappedBy = "producto")
	  @JsonIgnore
	  private List<Pedido> pedido;
	  
	  
	  
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


	  public Double getPrecio() {
		  return precio;
	  }


	  public void setPrecio(Double precio) {
		  this.precio = precio;
	  }


	  public String getTipo() {
		  return tipo;
	  }


	  public void setTipo(String tipo) {
		  this.tipo = tipo;
	  }


	  public List<DetallePedido> getDetalles() {
		  return detalles;
	  }


	  public void setDetalles(List<DetallePedido> detalles) {
		  this.detalles = detalles;
	  }


	  
	  
	  public Usuario getUsuario() {
		  return usuario;
	  }


	  public void setUsuario(Usuario usuario) {
		  this.usuario = usuario;
	  }
	  
	  
	  
}
