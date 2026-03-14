package com.syshotel.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syshotel.entitys.ProductoServicio;
import com.syshotel.entitys.Reserva;
import com.syshotel.repository.ReservaRepository;
import com.syshotel.service.ReservaService;


@CrossOrigin("http://localhost:4300/") //Permite que el navegador no bloquee las peticiones hechas desde otro origen (en este caso, desde Angular)
@RestController
@RequestMapping("/api/reservas") //Define la ruta base común del controlador
public class ReservaController {

	   @Autowired
	    private ReservaService servicioreserva;
	   
	    @PreAuthorize("hasRole('USER')")
	   @GetMapping("/listar/mis-reservas")
	    public List<Reserva> listarMisReservas( Authentication authentication) {
	    	   String username = authentication.getName();
	 	      return servicioreserva.ListbuscarPorUsername(username);
	 	      }
	    
	    @PreAuthorize("hasRole('ADMIN')")
	   @GetMapping("/listar") //	Definen la acción específica y se agregan a la base
	    public List<Reserva> listar() {
	       return   servicioreserva.listar();

	    }

	    @PreAuthorize("hasRole('USER')")
	    @PostMapping("/registrar/mis-reservas")
	    public ResponseEntity<Reserva> registrarMisReservas(
	            @RequestBody Reserva reserva,
	            Authentication authentication) {
	    	
	    	
	        String username = authentication.getName();
	       Reserva nuevo = servicioreserva.registrarMisReservas(reserva, username);

	        return ResponseEntity.ok(nuevo);
	    }

	    @PreAuthorize("hasRole('ADMIN')")
	    @PostMapping("/registrar")
	    public Reserva registrar(@RequestBody Reserva reserva) {
	        return servicioreserva.registrar(reserva);
	    }
	    @PreAuthorize("hasRole('ADMIN','USER')")
	    @GetMapping("/buscar/{id}")
	    public Reserva obtenerPorId(@PathVariable Integer id) {
	        return servicioreserva.obtenerId(id);
	    }
	    @PreAuthorize("hasAnyRole('USER','ADMIN')")
	    @DeleteMapping("/eliminar/{id}")
	    public void eliminar(@PathVariable Integer id) {
	       servicioreserva.eliminar(id);
	    }

	    //OBTIENE Y ACTUALIZA POR ID
	    @PreAuthorize("hasRole('ADMIN')")
	    @PutMapping("/actualizar/{id}")
	    public Reserva actualizar(@PathVariable Integer id, @RequestBody Reserva reserva) {
	        return servicioreserva.actualizar(id, reserva);
	    
	}
	    @GetMapping("/validar-choque")
	    public boolean validarChoque(@RequestParam Integer idHabitacion,
	                                 @RequestParam LocalDate entrada,
	                                 @RequestParam LocalDate salida) {
	        List<Reserva> ocupadas = servicioreserva.existeChoque(idHabitacion, entrada, salida);
	        return !ocupadas.isEmpty();
	    }
	    
}
