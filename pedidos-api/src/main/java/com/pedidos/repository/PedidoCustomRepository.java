package com.pedidos.repository;

import java.util.List;

import com.pedidos.model.Pedido;
import com.pedidos.model.Usuario;

public interface PedidoCustomRepository {
	
	List<Pedido> obtenerPedidosOrdenadosParaUsuario(final Usuario usuario);
}
