package com.syshotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syshotel.entitys.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer>{
   //Trae todas las reservas de la base junto con los datos 
	//completos de huesped y habitacion (gracias al JOIN FETCH).
	
	/*JOIN FETCH le dice a JPA/Hibernate: “Cargar también los objetos Huesped y Habitacion asociados”.*/
	
	@Query(
			"SELECT r FROM Reserva r JOIN FETCH r.huesped JOIN FETCH r.habitacion"
			)
    List<Reserva> findAllConDetalles();
    
    //CONSULTA: Busca reservas con choque de fechas para una habitación:
    @Query("""
    	    SELECT r FROM Reserva r
    	    WHERE r.habitacion.id = :idHabitacion
    	    AND (
    	        (:entrada BETWEEN r.fechaEntrada AND r.fechaSalida)
    	        OR
    	        (:salida BETWEEN r.fechaEntrada AND r.fechaSalida)
    	        OR
    	        (r.fechaEntrada BETWEEN :entrada AND :salida)
    	    )
    	""")
    List<Reserva>existeChoque(
    		Integer idHabitacion,
    		LocalDate entrada,
    		LocalDate salida);
    
    @Query("""
    	    SELECT re FROM Reserva re
    	    JOIN FETCH re.huesped
    	    JOIN FETCH re.habitacion
    	    JOIN FETCH re.usuario
    	    WHERE re.usuario.username = :username
    	""")
    	List<Reserva> findByUsuarioConDetalles(String username);
}

