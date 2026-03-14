package com.syshotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syshotel.entitys.Habitacion;
import com.syshotel.entitys.Reserva;

public interface HabitacionRepository  extends JpaRepository<Habitacion, Integer>{

    boolean existsByNumero(String numero);
    
    List<Habitacion> findByUsuarioUsername(String username);
}
