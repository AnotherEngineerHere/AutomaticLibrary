package com.example.demo.response;

import lombok.Data;

@Data
public class DataResponse {
    Long id;
    String isbn;
    String identificacionUsuario;
    int tipoUsuario;
    String fechaMaximaDevolucion;

    public DataResponse() {
    }

    public DataResponse(Long id, String isbn, String identificacionUsuario, int tipoUsuario, String fechaMaximaDevolucion) {
        this.id = id;
        this.isbn = isbn;
        this.identificacionUsuario = identificacionUsuario;
        this.tipoUsuario = tipoUsuario;
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
    }
}
