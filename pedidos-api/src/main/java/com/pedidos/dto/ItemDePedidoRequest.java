package com.pedidos.dto;

import lombok.Data;

@Data
public class ItemDePedidoRequest {

	private Long idItemDeMenu;
	private Integer cantidad;
	private String comentario;
}
