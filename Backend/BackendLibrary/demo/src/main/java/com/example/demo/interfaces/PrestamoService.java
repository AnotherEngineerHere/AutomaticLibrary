package com.example.demo.interfaces;

import org.springframework.stereotype.Service;

import com.example.demo.request.PrestamoRequest;
import com.example.demo.response.PrestamoResponse;

@Service
public interface PrestamoService {
    PrestamoResponse crearPrestamo(PrestamoRequest request);
    PrestamoResponse obtenerPrestamo(Long id);
}
