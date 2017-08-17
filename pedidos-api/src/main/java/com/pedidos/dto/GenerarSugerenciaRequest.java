package com.pedidos.dto;

import java.util.Date;

import lombok.Data;

@Data
public class GenerarSugerenciaRequest {
	
	private Long idItemDeMenu;
	private String nombre;
	private String descripcion;
	private Double precio;
	private Integer cantidadDisponible;
	private Date fechaInicio;
	private Date fechaFin;
}
