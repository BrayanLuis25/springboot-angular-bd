package com.syshotel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.syshotel.entitys.Habitacion;
import com.syshotel.repository.HabitacionRepository;

@ExtendWith(MockitoExtension.class)
class HabitacionServiceTest {

    @Mock
    private HabitacionRepository habitacionRepository;

    @InjectMocks
    private HabitacionService habitacionService;

    @Test
    void noDebeRegistrarHabitacionDuplicada() {

        Habitacion habitacion = new Habitacion();
        habitacion.setNumero("101");

        // Simulamos que YA existe
        when(habitacionRepository.existsByNumero("101"))
            .thenReturn(true);

        // Validamos que lance excepción
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> habitacionService.registrar(habitacion)
        );

        assertEquals("La habitación ya existe", exception.getMessage());

        // Verificamos que NO guarde
        verify(habitacionRepository, never()).save(any());
    }
    
}
