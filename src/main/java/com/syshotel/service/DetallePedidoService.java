package com.syshotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.syshotel.entitys.DetallePedido;
import com.syshotel.repository.DetallePedidoRepository;

@Service
public class DetallePedidoService {

	

	    @Autowired
	    private DetallePedidoRepository repodetalle;

	    public List<DetallePedido> listar() {
	        return repodetalle.findAllConDetallesDetallePedido();
	    }

	    public DetallePedido registrar(DetallePedido detalle) {
	        return repodetalle.save(detalle);
	    }

	    public DetallePedido obtenerId(@PathVariable Integer id) {
	        return repodetalle.findById(id).orElse(null);
	    }

	    public void eliminar(Integer id) {
	        repodetalle.deleteById(id);
	    }

	    // Actualizar detalle de pedido
	    public DetallePedido actualizar(Integer id, DetallePedido detalleActualizado) {
	        Optional<DetallePedido> detalleOpt = repodetalle.findById(id);
	        if (detalleOpt.isPresent()) {
	            DetallePedido detalleExistente = detalleOpt.get();

	            detalleExistente.setCantidad(detalleActualizado.getCantidad());
	            detalleExistente.setSubtotal(detalleActualizado.getSubtotal());
	            detalleExistente.setProducto(detalleActualizado.getProducto());
	            detalleExistente.setPedido(detalleActualizado.getPedido());

	            return repodetalle.save(detalleExistente);
	        }
	        return null;
	    }
	
}
