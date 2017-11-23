package com.pedidos.repository;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.data.repository.NoRepositoryBean;

import com.mysql.jdbc.StringUtils;
import com.pedidos.model.EstadoPedido;
import com.pedidos.model.ItemReporteDePedidos;
import com.pedidos.model.Pedido;
import com.pedidos.model.ReporteDePedidos;
import com.pedidos.model.Usuario;

@SuppressWarnings("unchecked")
@NoRepositoryBean
public class PedidoRepositoryImpl implements PedidoCustomRepository {
	
	private static final int categoria = 0, nombre = 1, cantidad = 2;
	private static final String emptyTag = "";
	
	@Resource
	private PedidoRepository pedidoRepository;
	
    @PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<Pedido> obtenerPedidosOrdenadosParaUsuario(final Usuario usuario) {
		List<Pedido> pedidos = this.pedidoRepository.findAll();
		if (usuario.esPersonalDeCocina()) {
			// los pedidos generados pero no aprobados por sistema/mesera no son de interés para la cocina
			pedidos = this.obtenerPedidosDeInteresParaCocina(pedidos);
		} else if (usuario.esCliente()) {
			// un cliente únicamente debe poder visualizar sus propios pedidos
			pedidos = this.pedidoRepository.findAllByCliente(usuario);
		}
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
	
	private List<Pedido> obtenerPedidosDeInteresParaCocina(final List<Pedido> pedidos) {
		return pedidos.stream().filter(pedido -> EstadoPedido.Generado != pedido.getEstado())
				.collect(Collectors.toList());
	}
	
	private List<Pedido> obtenerPedidosMasAntiguosPorEstado(final List<Pedido> pedidos, final EstadoPedido estadoPedido) {
		return pedidos.stream().filter(pedido -> estadoPedido == pedido.getEstado())
				.sorted(Comparator.comparing(Pedido::getFechaCreacion))
				.collect(Collectors.toList());
	}

	@Override
	public ReporteDePedidos obtenerReporte(final Date fechaDesde, final Date fechaHasta, final String estadoPedido) {
		final ReporteDePedidos reporte = new ReporteDePedidos();
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT im.categoria, im.nombre, sum(ip.cantidad) AS total_item " +
				"FROM pedido p " +
				"INNER JOIN pedido_items pi ON pi.pedido_id = p.id " +
				"INNER JOIN item_de_pedido ip ON ip.id = pi.items_id " +
				"INNER JOIN item_de_menu im ON im.id = ip.item_de_menu_id " +
				"WHERE p.fecha_creacion BETWEEN :fechaDesde AND :fechaHasta ");
		if (!StringUtils.isNullOrEmpty(estadoPedido)) { queryBuilder.append("AND ip.estado = '" + estadoPedido + "'"); }
		queryBuilder.append("GROUP BY im.id, im.categoria ORDER BY categoria, total_item desc");
		// crear query, indicar params de fecha desde/hasta
		final Query reporteDePedidosQuery = this.entityManager.createNativeQuery(queryBuilder.toString());
		reporteDePedidosQuery.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
		reporteDePedidosQuery.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
		// mapear los resultados a items de reporte
		final List<Object[]> rows = reporteDePedidosQuery.getResultList();
		for (final Object[] row : rows) {
			reporte.agregarItem(new ItemReporteDePedidos(row[categoria].toString(), row[nombre].toString(), emptyTag, cantidad(row)));
		}
		return reporte.marcarMinYMaxPorCategorias();
	}
	
	private int cantidad(final Object[] row) {
		return Integer.valueOf(row[cantidad].toString());
	}
}
