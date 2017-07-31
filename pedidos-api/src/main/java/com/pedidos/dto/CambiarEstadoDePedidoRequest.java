package com.pedidos.dto;

import java.util.List;

import com.pedidos.model.EstadoPedido;

import lombok.Data;

@Data
public class CambiarEstadoDePedidoRequest {
	
	private Long idPedido;
	private EstadoPedido estadoPedido;
	private String comentario;
	private Boolean abonado;
	private List<CambiarEstadoItemDePedidoRequest> cambiosDeEstadoSobreItems;
}
