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
import com.syshotel.entitys.Huesped;
import com.syshotel.entitys.ProductoServicio;
import com.syshotel.service.HuespedService;

@CrossOrigin("http://localhost:4300/")
@RestController
@RequestMapping("/api/huespeds")
public class HuespedController {

	@Autowired
	
	HuespedService servicehuesp;
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/listar")
	public List<Huesped>listaHuesped(){
		return servicehuesp.listar();
	}
	
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/listar/mis-huespedes")
    public List<Huesped> listarMisProductos(Authentication authentication) {
    	
        String username = authentication.getName();
        
        return servicehuesp.ListbuscarPorUsername(username);
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/registrar/mis-huespedes")
    public ResponseEntity<Huesped> registrarMisHuespedes(
            @RequestBody Huesped huesped,
            Authentication authentication) {

        String username = authentication.getName();

        Huesped nueva = servicehuesp.registrarMisHuespedes(huesped, username);

        return ResponseEntity.ok(nueva);
    }
    @PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/registrar")//crear
	public Huesped obtenerHuesped(@RequestBody Huesped huesped)
	
	{
		return servicehuesp.registrar(huesped);
	}
	
	//BUSCAR POR ID
    @PreAuthorize("hasRole('ADMIN','USER')")
	@GetMapping("/buscar/{id}")
	public Huesped obtenerPorId(@PathVariable Integer id ) {
		return servicehuesp.obtenerId(id);
	}
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	@DeleteMapping("/eliminar/{id}")
	public void eliminarHuesped(@PathVariable Integer id )
	{ servicehuesp.eliminar(id);
	}
    @PreAuthorize("hasRole('ADMIN')")
	//actualizar y obtener por id
	@PutMapping("/actualizar/{id}")
	public Huesped actualizarHuesped(@PathVariable Integer id, @RequestBody Huesped nuevohuesped)
	{
		return servicehuesp.actualizar(id, nuevohuesped);
	}
}
