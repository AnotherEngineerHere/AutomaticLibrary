package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.exceptions.UsuarioYaTienePrestamoException;
import com.example.demo.interfaces.PrestamoService;
import com.example.demo.models.Prestamo;
import com.example.demo.models.TipoUsuario;
import com.example.demo.repositories.PrestamoRepository;
import com.example.demo.request.PrestamoRequest;
import com.example.demo.response.PrestamoResponse;

@SpringBootTest
public class PrestamoServiceTests {

    @Autowired
    private PrestamoService prestamoService;

    @MockBean
    private PrestamoRepository prestamoRepository;

    @Test
    public void testCrearPrestamoExitoso() {
        PrestamoRequest request = new PrestamoRequest();
        request.setIsbn("DASD154212");
        request.setIdentificacionUsuario("154515485");
        request.setTipoUsuario(1);

        when(prestamoRepository.findByIdentificacionUsuario(anyString())).thenReturn(Collections.emptyList());
        when(prestamoRepository.save(any(Prestamo.class))).thenAnswer(invocation -> {
            Prestamo prestamo = invocation.getArgument(0);
            prestamo.setId(1L);
            return prestamo;
        });

        PrestamoResponse response = prestamoService.crearPrestamo(request);

        assertNotNull(response);
        assertEquals(1L, response.getData().getId());
        assertNotNull(response.getData().getFechaDevolucion());
    }

    @Test
    public void testCrearPrestamoUsuarioYaTienePrestamo() {
        PrestamoRequest request = new PrestamoRequest();
        request.setIsbn("DASD154212");
        request.setIdentificacionUsuario("154515485");
        request.setTipoUsuario(1);

        Prestamo prestamoExistente = new Prestamo();
        prestamoExistente.setId(2L);

        when(prestamoRepository.findByIdentificacionUsuario(anyString())).thenReturn(Collections.singletonList(prestamoExistente));

        assertThrows(UsuarioYaTienePrestamoException.class, () -> prestamoService.crearPrestamo(request));
    }

    @Test
    public void testObtenerPrestamoExitoso() {
        Long id = 1L;
        Prestamo prestamo = new Prestamo();
        prestamo.setId(id);
        prestamo.setIsbn("DASD154212");
        prestamo.setIdentificacionUsuario("154515485");
        prestamo.setTipoUsuario(TipoUsuario.AFILIADO);
        prestamo.setFechaDevolucion(new Date());

        when(prestamoRepository.findById(id)).thenReturn(Optional.of(prestamo));

        PrestamoResponse response = prestamoService.obtenerPrestamo(id);

        assertNotNull(response);
        assertEquals(id, response.getData().getId());
        assertNotNull(response.getData().getFechaDevolucion());
    }

    @Test
    public void testObtenerPrestamoNoEncontrado() {
        Long id = 1L;
        when(prestamoRepository.findById(id)).thenReturn(Optional.empty());

        PrestamoResponse response = prestamoService.obtenerPrestamo(id);

        assertNull(response);
    }
}
