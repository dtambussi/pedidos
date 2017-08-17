package com.pedidos.dto;

import com.pedidos.model.EstadoSugerencia;

import lombok.Data;

@Data
public class CambiarEstadoDeSugerenciaRequest {
	
	private Long idSugerencia;
	private EstadoSugerencia estadoSugerencia;
}
