package com.syshotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syshotel.entitys.Pedido;
import com.syshotel.entitys.Reserva;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
    @Query("SELECT p FROM Pedido p JOIN FETCH p.huesped JOIN FETCH p.reserva")
    List<Pedido> findAllConDetallesPedido();
    
    @Query("""
    	    SELECT pe FROM Pedido pe
    	    JOIN FETCH pe.huesped
    	    JOIN FETCH pe.reserva
    	    JOIN FETCH pe.usuario
    	    WHERE pe.usuario.username = :username
    	""")
    	List<Pedido> findByUsuarioConDetalles(String username);
}

