package com.example.demo.response;

import com.example.demo.models.Prestamo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoResponse {
    private String status;
    private Long code;
    private String message;
    private Prestamo data;
    
    public static PrestamoResponse successResponse(Prestamo prestamo) {
        PrestamoResponse response = new PrestamoResponse();
        response.setStatus("success");
        response.setCode(200L);
        response.setMessage("Solicitud completada con éxito");
        response.setData(prestamo);
        return response;
    }

    public static PrestamoResponse errorResponse(String message, Long code) {
        PrestamoResponse response = new PrestamoResponse();
        response.setStatus("error");
        response.setCode(code);
        response.setMessage(message);
        response.setData(null);
        return response;
    }

	public String getStatus() {
		return status;
	}

	public Long getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Prestamo getData() {
		return data;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Prestamo data) {
		this.data = data;
	}
}
