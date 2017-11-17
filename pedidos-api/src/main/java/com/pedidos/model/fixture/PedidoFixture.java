package com.pedidos.model.fixture;

import static com.pedidos.utils.DateUtils.currentDate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pedidos.model.EstadoItemDePedido;
import com.pedidos.model.EstadoPedido;
import com.pedidos.model.ItemDeMenu;
import com.pedidos.model.ItemDePedido;
import com.pedidos.model.Menu;
import com.pedidos.model.Pedido;
import com.pedidos.model.Status;
import com.pedidos.model.Usuario;

@Component
public class PedidoFixture {
	
	public List<Pedido> pedidosDemo(final Menu menu, final Usuario cliente) {
		return menu.getItems().stream()
				.map(itemDeMenu -> crearPedido(menu, itemDeMenu, cliente))
				.collect(Collectors.toList());
	}
	
	private Pedido crearPedido(final Menu menu, final ItemDeMenu itemDeMenu, final Usuario cliente) {
		final Pedido pedido = Pedido.builder()
			.menu(menu)
			.items(Arrays.asList(crearItemDePedido(itemDeMenu)))
			.estado(EstadoPedido.Entregado)
			.abonado(false)
			.cliente(cliente)
			.fechaCreacion(currentDate()).fechaUltimaModificacion(currentDate()).status(Status.Active).build();
		return pedido;
	}
	
	private ItemDePedido crearItemDePedido(final ItemDeMenu itemDeMenu) {
		return ItemDePedido.builder()
			.estado(EstadoItemDePedido.Entregado)
			.itemDeMenu(itemDeMenu)
			.cantidad(new Double(Math.random() * 10).intValue()) // random 0..10
			.abonado(false)
			.fechaUltimaModificacion(currentDate()).status(Status.Active).build();
	}
}
