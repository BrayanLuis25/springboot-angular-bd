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

import com.syshotel.entitys.ProductoServicio;
import com.syshotel.service.ProductoServiServicie;

@CrossOrigin(origins = "http://localhost:4300/")  // Angular por defecto corre en puerto 4200
@RestController
@RequestMapping("/api/productos")//rutabase

//@PreAuthorize("hasRole('ADMIN')") 
public class ProductoController {


    @Autowired
    private ProductoServiServicie serviciosprod;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/listar/mis-productos")
    public List<ProductoServicio> listarMisProductos(Authentication authentication) {
        String username = authentication.getName();
        
        return serviciosprod.ListbuscarPorUsername(username);
    }

    // Listar todos los productos
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public List<ProductoServicio> listarProductos() {
        return serviciosprod.listar();
    }
    
    
    // =========================
    // USER → registrar mis productos
    // =========================
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/registrar/mis-productos")
    public ResponseEntity<ProductoServicio> registrarMisProductos(
            @RequestBody ProductoServicio producto,
            Authentication authentication) {

        String username = authentication.getName();
        ProductoServicio nuevo = serviciosprod.registrarMisProductos(producto, username);

        return ResponseEntity.ok(nuevo);
    }
    //EL CUERPO DE LA PETICION DESERIALZIADA A JAVA
    //Cuando usas @RequestBody en un método de un controlador en Spring, estás indicando que el cuerpo de la solicitud HTTP (generalmente en formato JSON) 
    //debe ser deserializado automáticamente por Spring y convertido en un objeto Java.
    //el parámetro producto es una inyección automática de Spring, basada en el contenido JSON del cuerpo de la petición. Spring lo convierte (deserializa) 
    
    // Registrar nuevo producto o actualizar si ya existe (según ID)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public ProductoServicio guardarProducto(@RequestBody ProductoServicio producto) {
        return serviciosprod.registrar(producto);
    }

    // Buscar producto por ID
    //Usado para capturar el valor de una variable directamente desde la URL.

    //Obtener, buscar por id 
    @PreAuthorize("hasRole('ADMIN','USER')")
    @GetMapping("/buscar/{id}")
    public ProductoServicio buscarProductoPorId(@PathVariable("id") Integer id) {
        return serviciosprod.obtenerId(id);
    }
   
    
    // Eliminar producto por ID
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/eliminar/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
        serviciosprod.eliminar(id);
    }
    //BUSCAR POR ID Y ACTAULIZAR
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ProductoServicio actualizar(@RequestBody ProductoServicio produc, @PathVariable Integer id) {

    return serviciosprod.actualizar(id,produc);
    }
    
/*RQUESTBOYD INYECCION:
 * @RequestBody ProductoServicio producto: El cuerpo de la petición HTTP (por ejemplo, un JSON como {"nombre":"Laptop", "precio":1500}) se deserializa en un objeto Java.

Es una forma de inyección, pero del cuerpo de la petición, no de parámetros individuales ni del modelo de la vista.*/
}
