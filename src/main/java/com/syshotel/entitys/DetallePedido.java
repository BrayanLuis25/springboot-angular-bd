package com.syshotel.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_detalle_pedido")
public class DetallePedido {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_detalle")
	    private Integer id;

	   
	    // Cantidad del producto o servicio
	    @Column(name = "cantidad")
	    private Integer cantidad;

	    // Precio total del producto o servicio (puedes calcularlo al momento de agregar el detalle)
	    @Column(name = "subtotal")
	    private Double subtotal;
	    
	    //RELACION CON PRODUCto y PEDIDO
	    @ManyToOne(fetch = FetchType.EAGER) 
	    @JoinColumn(name = "id_producto", nullable = false)
	    private ProductoServicio producto;
	    
	    @ManyToOne(fetch = FetchType.EAGER) 
	    @JoinColumn(name = "id_pedido", nullable = false)
	    private Pedido pedido;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getCantidad() {
			return cantidad;
		}

		public void setCantidad(Integer cantidad) {
			this.cantidad = cantidad;
		}

		public Double getSubtotal() {
			return subtotal;
		}

		public void setSubtotal(Double subtotal) {
			this.subtotal = subtotal;
		}

		public ProductoServicio getProducto() {
			return producto;
		}

		public void setProducto(ProductoServicio producto) {
			this.producto = producto;
		}

		public Pedido getPedido() {
			return pedido;
		}

		public void setPedido(Pedido pedido) {
			this.pedido = pedido;
		}

}
