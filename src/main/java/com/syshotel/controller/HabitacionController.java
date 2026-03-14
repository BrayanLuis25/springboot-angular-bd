package com.syshotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syshotel.entitys.Habitacion;
import com.syshotel.entitys.Reserva;
import com.syshotel.service.HabitacionService;

@CrossOrigin(origins = "http://localhost:4300")  // Angular por defecto corre en puerto 4200
@RestController
@RequestMapping("/api/habitacion") //rutabase
public class HabitacionController {

	

	    @Autowired
	    private HabitacionService servicioshabitacion;
	    
	    @PreAuthorize("hasRole( 'USER' )")
	    @GetMapping("/listar/mis-habitaciones")
	    public List<Habitacion> listarUsuarios( Authentication authentication) {
	    	   String username = authentication.getName();
	 	      return servicioshabitacion.ListbuscarPorUsername(username);
	    
	    }
	    @PreAuthorize("hasRole('ADMIN')")
	    @GetMapping("/listar")
	    public List<Habitacion> listar() {
	        return servicioshabitacion.listar();
	    }
	    @PreAuthorize("hasRole('USER' )")
	    @PostMapping("/registrar/mis-habitaciones")
	    public ResponseEntity<Habitacion> registrarMisHabitaciones(
	            @RequestBody Habitacion habitacion,
	            Authentication authentication) {

	        String username = authentication.getName();

	        Habitacion nueva = servicioshabitacion.registrarMisHabitaciones(habitacion, username);

	        return ResponseEntity.ok(nueva);
	    }
	    @PreAuthorize("hasRole('ADMIN')")
	    @PostMapping("/registrar")
	    public Habitacion registrar(@RequestBody Habitacion habitacion) {
	        return servicioshabitacion.registrar(habitacion);
	        
	        
	    }
	    @PreAuthorize("hasRole('ADMIN','USER')")
	    @GetMapping("/buscar/{id}")
	    public Habitacion obtenerPorId(@PathVariable Integer id) {
	        return servicioshabitacion.obtenerId(id);
	    }
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    @DeleteMapping("/eliminar/{id}")
	    public void eliminar(@PathVariable Integer id) {
	        servicioshabitacion.eliminar(id);
	    }
	    @PreAuthorize("hasRole('ADMIN')")
	    @PutMapping("/actualizar/{id}")
	    public Habitacion actualizar(@PathVariable Integer id, @RequestBody Habitacion habitacion) {
	        return servicioshabitacion.actualizar(id, habitacion);
	    }
	
}
