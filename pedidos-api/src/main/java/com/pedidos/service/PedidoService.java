package com.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pedidos.dto.CambiarEstadoDePedidoRequest;
import com.pedidos.dto.GenerarPedidoRequest;
import com.pedidos.dto.RecibirPedidoRequest;
import com.pedidos.model.EstadoItemDePedido;
import com.pedidos.model.EstadoPedido;
import com.pedidos.model.ItemDePedido;
import com.pedidos.model.Pedido;
import com.pedidos.model.RolesFactory;
import com.pedidos.model.SesionDeUsuario;
import com.pedidos.model.Status;
import com.pedidos.repository.ItemDeMenuRepository;
import com.pedidos.repository.MenuRepository;
import com.pedidos.repository.PedidoRepository;

import static com.pedidos.utils.DateUtils.currentDate;

@Component
public class PedidoService {

	private final PedidoRepository pedidoRepository;
	private final MenuRepository menuRepository;
	private final ItemDeMenuRepository itemDeMenuRepository;

	public PedidoService(final PedidoRepository pedidoRepository, final MenuRepository menuRepository,
			final ItemDeMenuRepository itemDeMenuRepository) {
		this.pedidoRepository = pedidoRepository;
		this.menuRepository = menuRepository;
		this.itemDeMenuRepository = itemDeMenuRepository;
	}

	public List<Pedido> obtenerPedidos(final SesionDeUsuario sesionDeUsuario) {
		return this.pedidoRepository.obtenerPedidosOrdenadosParaUsuario(sesionDeUsuario.getUsuario());
	}

	public Pedido generarPedido(final GenerarPedidoRequest request, final SesionDeUsuario sesionDeUsuario) {
		final List<ItemDePedido> itemsDePedido = request.getItems().stream()
				.map(itemPedido -> ItemDePedido.builder()
						.estado(resolverEstadoAsignableANuevoItemDePedido(sesionDeUsuario))
						.itemDeMenu(itemDeMenuRepository.findOne(itemPedido.getIdItemDeMenu()))
						.cantidad(itemPedido.getCantidad())
						.comentario(itemPedido.getComentario())
						.abonado(false)
						.fechaUltimaModificacion(currentDate()).status(Status.Active).build())
				.collect(Collectors.toList());
		final Pedido pedidoRegistrado = Pedido.builder()
				.menu(menuRepository.findOne(request.getIdMenu()))
				.items(itemsDePedido)
				.estado(resolverEstadoAsignableANuevoPedido(sesionDeUsuario))
				.comentario(request.getComentario())
				.abonado(false)
				.cliente(sesionDeUsuario.getUsuario())
				.fechaCreacion(currentDate()).fechaUltimaModificacion(currentDate()).status(Status.Active).build();
		return pedidoRepository.save(pedidoRegistrado);
	}

	public Pedido recibirPedido(final RecibirPedidoRequest request, final SesionDeUsuario sesionDeUsuario) {
		final Pedido pedido = pedidoRepository.findOne(request.getIdPedido());
		pedido.setEstado(EstadoPedido.Pendiente);
		pedido.setDestino(request.getDestino());
		pedido.setPersonalAsignado(sesionDeUsuario.getUsuario());
		pedido.agregarComentario(request.getComentario());
		pedido.setFechaUltimaModificacion(currentDate());
		final Pedido pedidoRecibido = pedidoRepository.save(pedido);
		return pedidoRecibido;
	}

	public Pedido cambiarEstadoDePedido(final CambiarEstadoDePedidoRequest request, final SesionDeUsuario sesionDeUsuario) {
		final Pedido pedido = pedidoRepository.findOne(request.getIdPedido());
		pedido.agregarComentario(request.getComentario());
		pedido.setAbonado(request.getAbonado());
		pedido.setEstado(request.getEstadoPedido());
		// modificar únicamente los items para los que se solicitó cambio de estado
		request.getCambiosDeEstadoSobreItems().stream()
				.map(cambio -> pedido.obtenerItem(cambio.getIdItemDePedido())
						.map(item -> cambiarEstadoDeItemDePedido(item, cambio.getEstadoItemDePedido())))
				.collect(Collectors.toList());
		return pedidoRepository.save(pedido);
	}

	// ojo cambio de estado es lo de abonado y demas también
	private ItemDePedido cambiarEstadoDeItemDePedido(final ItemDePedido itemDePedido, final EstadoItemDePedido estado) {
		itemDePedido.setEstado(estado);
		return itemDePedido;
	}
	
	private EstadoPedido resolverEstadoAsignableANuevoPedido(final SesionDeUsuario sesionDeUsuario) {
		return sesionDeUsuario.getUsuario().tieneRol(RolesFactory.UsuarioRegistrado) ? EstadoPedido.Pendiente
				: EstadoPedido.Generado;
	}

	private EstadoItemDePedido resolverEstadoAsignableANuevoItemDePedido(final SesionDeUsuario sesionDeUsuario) {
		return sesionDeUsuario.getUsuario().tieneRol(RolesFactory.UsuarioRegistrado) ? EstadoItemDePedido.Pendiente
				: EstadoItemDePedido.Generado;
	}
}
