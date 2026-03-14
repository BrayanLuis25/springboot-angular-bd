package com.syshotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syshotel.entitys.ProductoServicio;

public interface ProductoServicioRepository extends JpaRepository<ProductoServicio, Integer> {
   
	List<ProductoServicio> findByUsuarioUsername(String username);
}
