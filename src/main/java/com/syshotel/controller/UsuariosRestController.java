package com.syshotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syshotel.entitys.Usuario;
import com.syshotel.service.UsuarioService;
@RestController
@RequestMapping("api/usuarios")
public class UsuariosRestController {

	  @Autowired
	    private UsuarioService usuarioService;

	    @PostMapping("/registrar")
	    public ResponseEntity<String> registrar(@RequestBody Usuario usuario) {
	        try {
	            usuarioService.crearUsuarioDesdeAngular(usuario);
	            return ResponseEntity.ok("Usuario registrado con éxito");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
	        }
	    }
}
