package com.syshotel.security;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//SecurityConfig (register filter, stateless)
	
//SecurityConfig permite acceso
    
    @Autowired private JwtRequestFilter jwtFilter;



	//bean:REGISTRANDO OBJETO
	@Bean 
	public SecurityFilterChain   filterchain (HttpSecurity http )throws Exception {
		
		http.
		csrf(cs -> cs.disable())
		 .cors(cors -> {}). // habilita CORS 
		 //- Login/Register públicos. Todo lo demás requiere token
		authorizeHttpRequests(auth -> auth.
				requestMatchers( "/api/auth/**").permitAll().
				requestMatchers("/api/habitacion/listar").authenticated().
				requestMatchers("/api/habitacion/registrar").authenticated().
				requestMatchers("/huespeds/listar/mis-huespedes").authenticated()
				.requestMatchers("/huespeds/registrar/mis-huespedes").hasRole("USER")
				.requestMatchers("/api/pedidos/registrar/mis-pedidos").hasRole("USER")
				.requestMatchers("/api/pedidos/registrar").hasRole("ADMIN")
				//auth
				//.requestMatchers("/admin").hasRole("ADMIN")
				//requestMatchers( "/api/auth/**").permitAll()
				.anyRequest().authenticated()
				).
		sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowedOrigins(List.of("http://localhost:4300")); // Angular
	    config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
	    config.setAllowedHeaders(List.of("*"));

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}
	
	/*es el gestor de autenticación de 
	 * Spring Security.Se usa para verificar
 si username + password son correctos.*/
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) 
	throws Exception{
	return 	config.getAuthenticationManager();
	}

}
