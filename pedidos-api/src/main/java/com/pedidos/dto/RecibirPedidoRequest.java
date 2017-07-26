package com.pedidos.dto;

import lombok.Data;

@Data
public class RecibirPedidoRequest {
	
	private Long idPedido;
	private String comentario, mesa;
}
