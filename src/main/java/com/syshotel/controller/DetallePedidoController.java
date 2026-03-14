package com.syshotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syshotel.entitys.DetallePedido;
import com.syshotel.service.DetallePedidoService;

@CrossOrigin(origins = "http://localhost:4300/")  // Angular por defecto corre en puerto 4200
@RestController
@RequestMapping("/api/detallepedido") //rutabase
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService serviciodetalle;

    @GetMapping("/listar")
    public List<DetallePedido> listar() {
        return serviciodetalle.listar();
    }

    @PostMapping("/registrar")
    public DetallePedido registrar(@RequestBody DetallePedido detalle) {
        return serviciodetalle.registrar(detalle);
    }

    @GetMapping("/buscar/{id}")
    public DetallePedido obtenerPorId(@PathVariable Integer id) {
        return serviciodetalle.obtenerId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        serviciodetalle.eliminar(id);
    }
    
    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/actualizar/{id}")
    public DetallePedido actualizar(@PathVariable Integer id, @RequestBody DetallePedido detalle) {
        return serviciodetalle.actualizar(id, detalle);
    }

}
