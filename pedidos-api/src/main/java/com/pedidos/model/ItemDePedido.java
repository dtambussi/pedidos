package com.pedidos.model;

import lombok.Data;

@Data
public class ItemDePedido {

	private Long id;
	private Pedido pedido;
	private Status status;
	private EstadoItemDePedido estado;
	private ItemDeMenu itemDeMenu;
	private Integer cantidad;
	private String comentario;
	private Boolean abonado;	
}
