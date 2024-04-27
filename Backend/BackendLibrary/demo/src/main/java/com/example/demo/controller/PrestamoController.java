package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.interfaces.PrestamoService;
import com.example.demo.request.PrestamoRequest;
import com.example.demo.response.PrestamoResponse;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {
    
    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody PrestamoRequest request) {
        try {
            PrestamoResponse response = prestamoService.crearPrestamo(request);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPrestamo(@PathVariable("id") Long id) {
        PrestamoResponse prestamo = prestamoService.obtenerPrestamo(id);
        if (prestamo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(prestamo);
    }
}
