package com.syshotel.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.syshotel.DTO.AuthResponse;
import com.syshotel.DTO.LoginRequest;
import com.syshotel.DTO.RegisterRequest;
import com.syshotel.DTO.UsuarioResponse;
import com.syshotel.entitys.Rol;
import com.syshotel.entitys.Usuario;
import com.syshotel.repository.RolRepository;
import com.syshotel.repository.UsuarioRepository;
import com.syshotel.security.JwtUtil;
@Service
public class AuthService {
//AuthService + AuthController (registro + login)
	
	  @Autowired private UsuarioRepository userRepo;
	  @Autowired private RolRepository rolRepo;
	  @Autowired private BCryptPasswordEncoder encoder;
	  @Autowired private AuthenticationManager authManager;
	  @Autowired private JwtUtil jwtUtil;

	  public UsuarioResponse register(RegisterRequest dto) {
	    
	      if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
	            throw new RuntimeException("Usuario ya existe");
	        }


		    // 2. Crear usuario
		  Usuario u = new Usuario();
	    u.setUsername(dto.getUsername()); //TRAE EL USUARIO Y LO GUARDA EN EN SET
	    // 3. ENCRIPTAR PASSWORD
	    u.setPassword(encoder.encode(dto.getPassword()));
	    // 4. Asignar rol
	    Rol r = rolRepo.findByNombre("ROLE_" + dto.getRole().toUpperCase())
	            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
	    
	    u.setRoles(Collections.singleton(r));
	    // 5. Guardar
	    userRepo.save(u);

	    // 6. Respuesta
	    return new UsuarioResponse(u.getUsername(), u.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet()));
	  }

	  public AuthResponse login(LoginRequest dto) {
	    Authentication auth = authManager.authenticate
(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
	    
	    UserDetails ud = (UserDetails) auth.getPrincipal();
	    String token = jwtUtil.generateToken(ud);
	    
	    //Set<String> roles = ud.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.toSet());
	    
	    List<String> roles = ud.getAuthorities().stream()
	            .map(a -> a.getAuthority())
	            .collect(Collectors.toList()); // <-- List
	    return new AuthResponse(token, ud.getUsername(), roles);
	  }
	
	  // ---------------------------
	    // OBTENER USUARIO LOGEADO
	    // ---------------------------
	    public UsuarioResponse getPrincipal(Authentication authentication) {

	        if (authentication == null || !authentication.isAuthenticated()) {
	            throw new RuntimeException("No hay usuario autenticado");
	        }

	        UserDetails ud = (UserDetails) authentication.getPrincipal();

	        Usuario u = userRepo.findByUsername(ud.getUsername())
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        return new UsuarioResponse(
	                u.getUsername(),
	                u.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet())
	        );
	    }
}
