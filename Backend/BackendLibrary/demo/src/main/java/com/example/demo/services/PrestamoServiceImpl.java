package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.TipoUsuarioNoPermitidoException;
import com.example.demo.exceptions.UsuarioYaTienePrestamoException;
import com.example.demo.interfaces.PrestamoService;
import com.example.demo.models.Prestamo;
import com.example.demo.models.TipoUsuario;
import com.example.demo.repositories.PrestamoRepository;
import com.example.demo.request.PrestamoRequest;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.demo.response.PrestamoResponse;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    public PrestamoResponse crearPrestamo(PrestamoRequest request) {
        List<Prestamo> prestamosUsuario = prestamoRepository.findByIdentificacionUsuario(request.getIdentificacionUsuario());
        if (!prestamosUsuario.isEmpty()) {
            throw new UsuarioYaTienePrestamoException("El usuario con identificación " + request.getIdentificacionUsuario() + " ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo");
        }
        int tipoUsuarioValue = request.getTipoUsuario();
        TipoUsuario tipoUsuario;
        switch (tipoUsuarioValue) {
            case 1:
                tipoUsuario = TipoUsuario.AFILIADO;
                break;
            case 2:
                tipoUsuario = TipoUsuario.EMPLEADO;
                break;
            case 3:
                tipoUsuario = TipoUsuario.INVITADO;
                break;
            default:
                throw new TipoUsuarioNoPermitidoException("Tipo de usuario no permitido en la biblioteca");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int diasPlazo;
        switch (tipoUsuario) {
            case AFILIADO:
                diasPlazo = 10;
                break;
            case EMPLEADO:
                diasPlazo = 8;
                break;
            case INVITADO:
                diasPlazo = 7;
                break;
            default:
                throw new TipoUsuarioNoPermitidoException("Tipo de usuario no permitido en la biblioteca");
        }
        for (int i = 0; i < diasPlazo; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        Prestamo prestamo = new Prestamo();
        prestamo.setIsbn(request.getIsbn());
        prestamo.setIdentificacionUsuario(request.getIdentificacionUsuario());
        prestamo.setTipoUsuario(tipoUsuario);
        prestamo.setFechaDevolucion(cal.getTime());
        prestamoRepository.save(prestamo);
        return PrestamoResponse.successResponse(prestamo);
    }

    @Override
    public PrestamoResponse obtenerPrestamo(Long id) {
    	    Prestamo prestamo = prestamoRepository.findById(id).orElse(null);
    	    if (prestamo == null) {
    	        return PrestamoResponse.errorResponse("Prestamo no encontrado", id);
    	    }
    	    return PrestamoResponse.successResponse(prestamo);
    }
  
}
