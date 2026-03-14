package com.syshotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.syshotel.entitys.Habitacion;
import com.syshotel.entitys.Huesped;
import com.syshotel.entitys.Pedido;
import com.syshotel.entitys.ProductoServicio;
import com.syshotel.entitys.Reserva;
import com.syshotel.entitys.Usuario;
import com.syshotel.repository.HuespedRepository;
import com.syshotel.repository.PedidoRepository;
import com.syshotel.repository.ProductoServicioRepository;
import com.syshotel.repository.ReservaRepository;
import com.syshotel.repository.UsuarioRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repopedido;
    @Autowired
    private HuespedRepository repohuesped;
    @Autowired
    private ReservaRepository reporeserva;

    @Autowired
    private ProductoServicioRepository repoproducto;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
  //LISTAR Pedido POR USUARIO

  public List<Pedido> ListbuscarPorUsername(String username) {
	    return repopedido.findByUsuarioConDetalles(username);
	}
    
    public List<Pedido> listar() {
        return repopedido.findAllConDetallesPedido();
    }
    

    //REGISTRAR RESERVAS POR USUARIO
    public Pedido registrarMisPedidos(Pedido pedido, String username) {

    	 Usuario usuario = usuarioRepository.findByUsername(username)
                 .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    	 
  	  Huesped huesped = repohuesped.findById(pedido.getHuesped().getId())
		        .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));

		    Reserva reserva = reporeserva.findById(pedido.getReserva().getId())
		        .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

		    ProductoServicio producto = repoproducto.findById(pedido.getProducto().getId())
    	            .orElseThrow(() -> new RuntimeException("Producto no encontrada"));
		    
		    pedido.setHuesped(huesped);
		    pedido.setReserva(reserva);
		    pedido.setProducto(producto);
		    
		    pedido.setUsuario(usuario);

        return repopedido.save(pedido);
    }


    public Pedido registrar(Pedido pedido) {
    	  Huesped huesped = repohuesped.findById(pedido.getHuesped().getId())
    		        .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));

    		    Reserva reserva = reporeserva.findById(pedido.getReserva().getId())
    		        .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

    		    ProductoServicio producto = repoproducto.findById(pedido.getProducto().getId())
        	            .orElseThrow(() -> new RuntimeException("Producto no encontrada"));
    		    
    		    pedido.setProducto(producto);
    		    pedido.setHuesped(huesped);
    		    pedido.setReserva(reserva);

    		 //   Pedido guardado = repopedido.save(pedido);
    		 // return ResponseEntity.ok(guardado);
    	return repopedido.save(pedido);
    }

    public Pedido obtenerId(@PathVariable  Integer ped) {
        return repopedido.findById(ped).orElse(null);
    }

    public void eliminar(Integer id) {
        repopedido.deleteById(id);
    }
    // Actualizar un producto
    
    public Pedido actualizar(Integer id, Pedido pedidoActualizado) { //entra un pedidoactualizado
       Optional<Pedido> pedidoOpt = repopedido.findById(id);
       
       
    	if (pedidoOpt.isPresent()) {
    	    Pedido productoExistente = pedidoOpt.get();
    	    // Puedes actualizar los campos aquí si es necesario
    	    productoExistente.setFechaHora(pedidoActualizado.getFechaHora());
    	    productoExistente.setEstado(pedidoActualizado.getEstado());

    	    if (pedidoActualizado.getHuesped() != null) {
                Huesped huesped = repohuesped.findById(pedidoActualizado.getHuesped().getId())
                    .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));
                productoExistente.setHuesped(huesped);
            }

            if (pedidoActualizado.getReserva() != null) {
                Reserva reserva = reporeserva.findById(pedidoActualizado.getReserva().getId())
                    .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
                productoExistente.setReserva(reserva);
            }

            if (pedidoActualizado.getProducto() != null) {
              ProductoServicio producto= repoproducto.findById(pedidoActualizado.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
                productoExistente.setProducto(producto);;
            }
            
            // (Si quieres también actualizar huesped o habitacion)
           productoExistente.setReserva(pedidoActualizado.getReserva());
          productoExistente.setHuesped(pedidoActualizado.getHuesped());
          productoExistente.setProducto(pedidoActualizado.getProducto());

    	// Si es necesario

    	    return repopedido.save(productoExistente); // Guarda el producto actualizado
    	}
    	return null; // Si no existe el producto, devuelve null

}

}

/*
*   	Pedido pedidoactual= repopedido.findById(id).orElseThrow(()-> new RuntimeException("No encontrado"));
    		
    	pedidoactual.setFechaHora(pedidoActualizado.getFechaHora());
    	pedidoactual.setEstado(pedidoActualizado.getEstado());
    	return repopedido.save(pedidoactual);
    }*/
