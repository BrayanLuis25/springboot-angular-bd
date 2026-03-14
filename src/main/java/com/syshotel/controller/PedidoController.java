package com.syshotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syshotel.entitys.Pedido;
import com.syshotel.entitys.Reserva;
import com.syshotel.service.PedidoService;

@CrossOrigin(origins = "http://localhost:4300/")  // Angular por defecto corre en puerto 4200
@RestController
@RequestMapping("/api/pedidos") //rutabase
@Service
public class PedidoController {

	 @Autowired
	    private PedidoService serviciopedido;

	   @PreAuthorize("hasRole('USER')")
	   @GetMapping("/listar/mis-pedidos")
	    public List<Pedido> listarMisPedidos( Authentication authentication) {
	    	   String username = authentication.getName();
	 	      return serviciopedido.ListbuscarPorUsername(username);
	 	      }
	   
	   @PreAuthorize("hasRole('ADMIN')")
	    @GetMapping("/listar")
	    public List<Pedido> listar() {
	        return serviciopedido.listar();
	    }

	    @PreAuthorize("hasRole('USER')")
	    @PostMapping("/registrar/mis-pedidos")
	    public ResponseEntity<Pedido> registrarMisPedidos(
	            @RequestBody Pedido pedido,
	            Authentication authentication) {
	    	
	    	
	        String username = authentication.getName();
	    Pedido nuevo = serviciopedido.registrarMisPedidos(pedido, username);

	        return ResponseEntity.ok(nuevo);
	    }
	    
	    @PreAuthorize("hasRole('ADMIN')")
	    @PostMapping("/registrar")
	    public Pedido registrar(@RequestBody Pedido pedido) {
	        return serviciopedido.registrar(pedido);
	    }
	    @PreAuthorize("hasRole('ADMIN','USER')")
	    @GetMapping("/buscar/{id}")
	    public Pedido obtenerPorId(@PathVariable("id") Integer id) {
	        return serviciopedido.obtenerId(id);
	    }
	    


	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    @DeleteMapping("/eliminar/{id}")
	    public void eliminar(@PathVariable Integer id) {
	       serviciopedido.eliminar(id);
	    }
	    
	 

	    //OBTIENE Y ACTUALIZA POR ID
	    @PreAuthorize("hasRole('ADMIN')")
	    @PutMapping("/actualizar/{id}")
	    public Pedido actualizar(@PathVariable Integer id, @RequestBody Pedido reserva) {
	        return serviciopedido.actualizar(id, reserva);
}
}
