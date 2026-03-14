package com.syshotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syshotel.entitys.DetallePedido;

public interface DetallePedidoRepository  extends JpaRepository<DetallePedido, Integer>{

    @Query("SELECT dp FROM DetallePedido dp JOIN FETCH dp.producto JOIN FETCH dp.pedido")
    List<DetallePedido> findAllConDetallesDetallePedido();
}


