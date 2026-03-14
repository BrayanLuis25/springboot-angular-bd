package com.syshotel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.syshotel.entitys.Habitacion;
import com.syshotel.entitys.Huesped;
import com.syshotel.entitys.Reserva;
import com.syshotel.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;
    
    @Test
    void registrarReserva_sinChoque_ok() {
        // Datos
        Habitacion habitacion = new Habitacion();
        habitacion.setId(1);

        Reserva reserva = new Reserva();
        reserva.setHabitacion(habitacion);
        reserva.setFechaEntrada(LocalDate.of(2025, 6, 10));
        reserva.setFechaSalida(LocalDate.of(2025, 6, 15));

        // Simulación
        when(reservaRepository.existeChoque(
                eq(1),
                any(LocalDate.class),
                any(LocalDate.class)
        )).thenReturn(Collections.emptyList());

        when(reservaRepository.save(reserva)).thenReturn(reserva);

        // Ejecución
        Reserva resultado = reservaService.registrar(reserva);

        // Verificación
        assertNotNull(resultado);
        verify(reservaRepository).save(reserva);
    }

}