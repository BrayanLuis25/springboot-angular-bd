package com.syshotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.syshotel.entitys.Huesped;
import com.syshotel.entitys.ProductoServicio;
import com.syshotel.entitys.Usuario;
import com.syshotel.repository.HuespedRepository;
import com.syshotel.repository.UsuarioRepository;

@Service
public class HuespedService {
	
	@Autowired
	private HuespedRepository repohuesped;
	  @Autowired
	    private UsuarioRepository usuarioRepository;
	  

	  
    public List<Huesped> listar() {
        return repohuesped.findAll();
    }
    
    public List<Huesped> ListbuscarPorUsername(String username) {
	    return repohuesped.findByUsuarioUsername(username);
    }
    
    public Huesped registrarMisHuespedes(Huesped huesped,  String username) {
        
    	Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

       huesped.setUsuario(usuario);
    	
    	return repohuesped.save(huesped);
    }
    public Huesped registrar(Huesped producto) {
        return repohuesped.save(producto);
    }

    public Huesped obtenerId(@PathVariable  Integer ida) {
        return repohuesped.findById(ida).orElse(null);
    }

    public void eliminar(Integer id) {
        repohuesped.deleteById(id);
    }
    // Actualizar un producto
    
    public Huesped actualizar(Integer id, Huesped huespedActualizado) {
        Optional<Huesped> huespetOpt = repohuesped.findById(id);
        if (huespetOpt.isPresent()) {
           Huesped huespedoExistente = huespetOpt.get();
            // Puedes actualizar los campos aquí si es necesario
            huespedoExistente.setNombre(huespedActualizado.getNombre());
            huespedoExistente.setDni(huespedActualizado.getDni());
            huespedoExistente.setDireccion(huespedActualizado.getDireccion());
            huespedoExistente.setEmail(huespedActualizado.getEmail());
            huespedoExistente.setTelefono(huespedActualizado.getTelefono());


        // Si es necesario

            return repohuesped.save(huespedActualizado); // Guarda el producto actualizado
        }
        return null; // Si no existe el producto, devuelve null
    }

}
