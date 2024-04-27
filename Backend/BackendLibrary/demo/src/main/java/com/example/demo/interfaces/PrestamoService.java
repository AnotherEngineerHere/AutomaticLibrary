package com.example.demo.interfaces;

import com.example.demo.models.Prestamo;

public interface PrestamoService {
    Prestamo crearPrestamo(PrestamoRequest request);
    Prestamo obtenerPrestamo(Long id);
}
