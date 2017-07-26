package com.pedidos.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GenerarPedidoRequest {
	
	private Long idMenu;
	private Date fechaUltimaModificacionMenu;
	private String comentario;
	private List<ItemDePedidoRequest> items;
}