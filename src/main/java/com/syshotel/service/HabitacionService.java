package com.syshotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.syshotel.entitys.Habitacion;
import com.syshotel.entitys.Reserva;
import com.syshotel.entitys.Usuario;
import com.syshotel.repository.HabitacionRepository;
import com.syshotel.repository.UsuarioRepository;

@Service
public class HabitacionService {

    @Autowired
    
    private HabitacionRepository repohabitacion;
    
    @Autowired 
    private UsuarioRepository usuarioRepository;

    public List<Habitacion> ListbuscarPorUsername(String username) {
	    return repohabitacion.findByUsuarioUsername(username);
    }
    
    public List<Habitacion> listar() {
        return repohabitacion.findAll();
        
    }
    
    public Habitacion registrarMisHabitaciones(Habitacion habitacion, String username) {

        if (repohabitacion.existsByNumero(habitacion.getNumero())) {
            throw new RuntimeException("La habitación ya existe");
        }

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        habitacion.setUsuario(usuario);

        return repohabitacion.save(habitacion);
    }

    public Habitacion registrar(Habitacion habitacion) {
    	
        if (repohabitacion.existsByNumero(habitacion.getNumero())) {
            throw new RuntimeException("La habitación ya existe");
        }
        return repohabitacion.save(habitacion);
    }

    public Habitacion obtenerId(@PathVariable Integer id) {
        return repohabitacion.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        repohabitacion.deleteById(id);
    }

    // Actualizar habitación
    public Habitacion actualizar(Integer id, Habitacion habitacionActualizada) {
        Optional<Habitacion> habitacionOpt = repohabitacion.findById(id);
        if (habitacionOpt.isPresent()) {
            Habitacion habitacionExistente = habitacionOpt.get();
            habitacionExistente.setNumero(habitacionActualizada.getNumero());
            habitacionExistente.setTipo(habitacionActualizada.getTipo());
            habitacionExistente.setDescripcion(habitacionActualizada.getDescripcion());
            habitacionExistente.setPrecioNoche(habitacionActualizada.getPrecioNoche());
            habitacionExistente.setEstado(habitacionActualizada.getEstado());

            return repohabitacion.save(habitacionExistente); // Guarda la habitación actualizada
        }
        return null; // Si no existe, devuelve null
    }

}
