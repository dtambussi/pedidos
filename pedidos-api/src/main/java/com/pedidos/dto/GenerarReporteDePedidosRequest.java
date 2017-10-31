package com.pedidos.dto;

import java.util.Date;

import lombok.Data;

@Data
public class GenerarReporteDePedidosRequest {
	
	private Date fechaDesde, fechaHasta;
	private String estadoPedido;
}
