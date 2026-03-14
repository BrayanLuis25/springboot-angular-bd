package com.syshotel.service;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.syshotel.entitys.Rol;
import com.syshotel.entitys.Usuario;
import com.syshotel.repository.RolRepository;
import com.syshotel.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	   @Autowired
	    private UsuarioRepository usuarioRepo;

	    @Autowired
	    private RolRepository  rolRepo;

	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	   
	    // Método para registrar usuario desde Angular
	    public void crearUsuarioDesdeAngular(Usuario usuario) {
	        if (usuarioRepo.findByUsername(usuario.getUsername()).isPresent()) {
	            throw new RuntimeException("El nombre de usuario ya existe");
	        }

	        // Codificar la contraseña
	        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

	        // Asignar rol por defecto si no se especifica ninguno
	        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
	            Rol rolUser = rolRepo.findByNombre("ROLE_USER")
	                .orElseThrow(() -> new RuntimeException("Rol ROLE_USER no existe"));
	            usuario.setRoles(Set.of(rolUser));
	        }

	        // Guardar el usuario en la base de datos
	        usuarioRepo.save(usuario);
	    }
	    
	    

}
	    

