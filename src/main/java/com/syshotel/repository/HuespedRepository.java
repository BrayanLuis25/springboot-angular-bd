package com.syshotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syshotel.entitys.Huesped;
import com.syshotel.entitys.Reserva;

public interface HuespedRepository extends JpaRepository<Huesped, Integer> {
    List<Huesped> findByUsuarioUsername(String username);

}
