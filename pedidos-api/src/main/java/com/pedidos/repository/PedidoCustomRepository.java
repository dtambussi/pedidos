package com.pedidos.repository;

import java.util.Date;
import java.util.List;

import com.pedidos.model.Pedido;
import com.pedidos.model.ReporteDePedidos;
import com.pedidos.model.Usuario;

public interface PedidoCustomRepository {
	
	List<Pedido> obtenerPedidosOrdenadosParaUsuario(final Usuario usuario);
	
	ReporteDePedidos obtenerReporte(final Date fechaDesde, final Date fechaHasta, final String estadoPedido);
}
