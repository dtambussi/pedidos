package com.pedidos.dto;

import com.pedidos.model.EstadoItemDePedido;

import lombok.Data;

@Data
public class CambiarEstadoItemDePedidoRequest {
	
	private Long idItemDePedido;
	private String comentario;
	private EstadoItemDePedido estadoItemDePedido;
}
