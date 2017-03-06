package com.pedidos.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Pedido {
	
	private Long id;
	private Status status;
	private Menu menu;
	private EstadoPedido estado;
	private String comentario;
	private Boolean abonado;
	private String mesa;
	private List<ItemDePedido> items;
	private Date fechaCreacion;
	private Date fechaUltimaModificacion;
}
