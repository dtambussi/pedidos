package com.pedidos.repository;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.data.repository.NoRepositoryBean;

import com.pedidos.model.EstadoPedido;
import com.pedidos.model.Pedido;
import com.pedidos.model.Usuario;

@NoRepositoryBean
public class PedidoRepositoryImpl implements PedidoCustomRepository {
	
	@Resource
	private PedidoRepository pedidoRepository;

	@Override
	public List<Pedido> obtenerPedidosOrdenadosParaUsuario(final Usuario usuario) {
		// un cliente únicamente debe poder visualizar sus propios pedidos
		List<Pedido> pedidos = usuario.esCliente() ? this.pedidoRepository.findAllByCliente(usuario)
				: this.pedidoRepository.findAll();
		return this.ordenarPedidosPorUrgenciaDeAtencion(pedidos);
	}
	
	private List<Pedido> ordenarPedidosPorUrgenciaDeAtencion(final List<Pedido> pedidos) {
		final List<Pedido> pedidosEnOrden = new LinkedList<>();
		pedidosEnOrden.addAll(obtenerPedidosMasAntiguosPorEstado(pedidos, EstadoPedido.Generado));
		pedidosEnOrden.addAll(obtenerPedidosMasAntiguosPorEstado(pedidos, EstadoPedido.Pendiente));
		pedidosEnOrden.addAll(obtenerPedidosMasAntiguosPorEstado(pedidos, EstadoPedido.En_Curso));
		pedidosEnOrden.addAll(obtenerPedidosMasAntiguosPorEstado(pedidos, EstadoPedido.Cancelado));
		pedidosEnOrden.addAll(obtenerPedidosMasAntiguosPorEstado(pedidos, EstadoPedido.Confeccionado));
		pedidosEnOrden.addAll(obtenerPedidosMasAntiguosPorEstado(pedidos, EstadoPedido.Entregado));
		return pedidosEnOrden;
	}
	
	private List<Pedido> obtenerPedidosMasAntiguosPorEstado(final List<Pedido> pedidos, EstadoPedido estadoPedido) {
		return pedidos.stream().filter(pedido -> estadoPedido == pedido.getEstado())
				.sorted(Comparator.comparing(Pedido::getFechaCreacion))
				.collect(Collectors.toList());
	}
}
