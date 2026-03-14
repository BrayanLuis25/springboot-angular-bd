package com.syshotel.security;

import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.syshotel.entitys.Usuario;
import com.syshotel.repository.UsuarioRepository;

//(para Spring Security)
@Service
public class UsuarioDetailsServiceImple 
implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow( () -> new
				UsernameNotFoundException("Usuario no encontrado"));
		
		
		return  new org.springframework.security.core.userdetails.User(
				usuario.getUsername(),
				usuario.getPassword(),
				usuario.getRoles().stream().
				map
				(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList())
				
				);
				
		
	}
	
}
