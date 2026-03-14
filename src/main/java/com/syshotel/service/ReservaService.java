package com.syshotel.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.syshotel.entitys.Habitacion;
import com.syshotel.entitys.Huesped;
import com.syshotel.entitys.Reserva;
import com.syshotel.entitys.Usuario;
import com.syshotel.repository.HabitacionRepository;
import com.syshotel.repository.HuespedRepository;
import com.syshotel.repository.ReservaRepository;
import com.syshotel.repository.UsuarioRepository;

@Service
public class ReservaService {
	//✔ SIEMPRE que haces findById(id) recibes un objeto COMPLETO, incluyendo sus relaciones EAGER.
	  @Autowired
	    private ReservaRepository reporeserva;

	  @Autowired
	    private UsuarioRepository usuarioRepository;
	  
	  @Autowired
	    private HuespedRepository repohuesped;

	  @Autowired
	    private HabitacionRepository repohabitacion;
	  //LISTAR RESERVAS POR USUARIO
	
	  public List<Reserva> ListbuscarPorUsername(String username) {
		    return reporeserva.findByUsuarioConDetalles(username);
		}
	  //LISTAR RESERVAS CON TODOS LOS DATOS PARA 
	
	    public List<Reserva> listar() {
	        return reporeserva.findAllConDetalles();
	    }
	  
	    //REGISTRAR RESERVAS POR USUARIO
	    public Reserva registrarMisReservas(Reserva reserva, String username) {

	   Huesped huesped = repohuesped.findById(reserva.getHuesped().getId())
	    	            .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));

	   Habitacion habitacion = repohabitacion.findById(reserva.getHabitacion().getId())
	    	            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

	   Usuario usuario = usuarioRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	    	    
	    	    reserva.setHuesped(huesped);
	    	    reserva.setHabitacion(habitacion);
	    	    
	    	    //reserva tiene un nuevo valor de usaurio, setenado,luegovalida
	        reserva.setUsuario(usuario);
	        validarChoque(reserva);

	        return reporeserva.save(reserva);
	    }
	
	    public Reserva registrar(Reserva reserva) {
	    	
	        Huesped huesped = repohuesped.findById(reserva.getHuesped().getId())
	                .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));

	        Habitacion habitacion = repohabitacion.findById(reserva.getHabitacion().getId())
	                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

	        reserva.setHuesped(huesped);
	        reserva.setHabitacion(habitacion);
	        
	    			validarChoque(reserva);
	    			
	    			
	       return reporeserva.save(reserva);
	    }
	    
	    private void validarChoque(Reserva reserva) {
	    	List<Reserva>ocupadas =reporeserva.existeChoque(
	    			reserva.getHabitacion().getId()
	    			, reserva.getFechaEntrada(),
	    			reserva.getFechaSalida());
	    	if(!ocupadas.isEmpty()) {
	    		throw new RuntimeException("La habitacion ya esta reservada en estas fechas");
	    }
	    	
	    }

	    public Reserva obtenerId(@PathVariable Integer id) {
	        return reporeserva.findById(id).orElse(null);
	    }
	    /*POR EJEMPLO:✔ Al hacer findById() recibes:fechaEntrada,fechaSalida,estado
			,huesped (objeto completo)
			,habitacion (objeto completo)*/

	    public void eliminar(Integer id) {
	        reporeserva.deleteById(id);
	    }

	    // Actualizar una reserva
	    public Reserva actualizar(Integer id, Reserva reservaActualizada) {
	    	
	        Optional<Reserva> reservaOpt = reporeserva.findById(id);
	        if(reservaOpt.isEmpty()) {
	        	return null;
	        	
	        }
	        //Primera para vlaidareservaExistente = la versión real en BD.
	        //reservaActualizada = lo que el usuario quiere cambiar.Usas la segunda para actualizar
	        Reserva reservaExistente = reservaOpt.get();
	        	
	        if (reservaOpt.isPresent()) {
	            // 1. Validar choque de fechas
	            List<Reserva> ocupadas = reporeserva.existeChoque(
	                reservaActualizada.getHabitacion().getId(),
	                reservaActualizada.getFechaEntrada(),
	                reservaActualizada.getFechaSalida()
	            );
	            // Ignorar la misma reserva
	            ocupadas.removeIf(r -> r.getId().equals(id));

	            if (!ocupadas.isEmpty()) {
	                throw new RuntimeException("La habitación ya está reservada en estas fechas.");
	            }
	            reservaExistente.setFechaEntrada(reservaActualizada.getFechaEntrada());
	            reservaExistente.setFechaSalida(reservaActualizada.getFechaSalida());
	            reservaExistente.setEstado(reservaActualizada.getEstado());
	             // huesped asociado

	            // (Si quieres también actualizar huesped o habitacion)
	            reservaExistente.setHabitacion(reservaActualizada.getHabitacion());
	            reservaExistente.setHuesped(reservaActualizada.getHuesped());
	            
	            return reporeserva.save(reservaExistente);
	        }
	        return null;
	    }
	    
	    public List<Reserva> existeChoque(Integer idHabitacion, LocalDate entrada, LocalDate salida) {
	        return reporeserva.existeChoque(idHabitacion, entrada, salida);
	    }

	    
}
