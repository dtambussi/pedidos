package com.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pedidos.dto.CambiarEstadoDePedidoRequest;
import com.pedidos.dto.CambiarEstadoItemDePedidoRequest;
import com.pedidos.dto.GenerarPedidoRequest;
import com.pedidos.dto.RecibirPedidoRequest;
import com.pedidos.model.EstadoItemDePedido;
import com.pedidos.model.EstadoPedido;
import com.pedidos.model.ItemDePedido;
import com.pedidos.model.Pedido;
import com.pedidos.model.Roles;
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
				.destino(request.getDestino())
				.comentario(request.getComentario())
				.abonado(false)
				.cliente(sesionDeUsuario.getUsuario())
				.fechaCreacion(currentDate()).fechaUltimaModificacion(currentDate()).status(Status.Active).build();
		return pedidoRepository.save(pedidoRegistrado);
	}

	// TODO: Eliminar si no se usa finalmente
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
		pedido.setDestino(request.getDestino());
		pedido.setComentario(request.getComentario());
		pedido.setAbonado(request.getAbonado());
		pedido.setEstado(request.getEstadoPedido());
		pedido.setFechaUltimaModificacion(currentDate());
		if (deberiaAsignarPersonalDeAtencion(pedido, sesionDeUsuario)) { pedido.setPersonalAsignado(sesionDeUsuario.getUsuario()); }
		// modificar únicamente los items para los que se solicitó cambio de estado
		request.getCambiosDeEstadoSobreItems().stream()
				.map(cambioEstadoItem -> pedido.obtenerItem(cambioEstadoItem.getIdItemDePedido())
						.map(item -> cambiarEstadoDeItemDePedido(item, cambioEstadoItem)))
				.collect(Collectors.toList());
		return pedidoRepository.save(pedido);
	}

	private ItemDePedido cambiarEstadoDeItemDePedido(final ItemDePedido itemDePedido,
			final CambiarEstadoItemDePedidoRequest request) {
		itemDePedido.setEstado(request.getEstadoItemDePedido());
		itemDePedido.setComentario(request.getComentario());
		return itemDePedido;
	}
	
	private boolean deberiaAsignarPersonalDeAtencion(final Pedido pedido, final SesionDeUsuario sesionDeUsuario) {
		return pedido.getPersonalAsignado() == null
				&& sesionDeUsuario.getUsuario().tieneAlgunoDeLosRoles(Roles.rolesDeAtencionAlCliente());
	}
	
	private EstadoPedido resolverEstadoAsignableANuevoPedido(final SesionDeUsuario sesionDeUsuario) {
		return sesionDeUsuario.getUsuario().tieneRol(Roles.UsuarioRegistrado) ? EstadoPedido.Pendiente
				: EstadoPedido.Generado;
	}

	private EstadoItemDePedido resolverEstadoAsignableANuevoItemDePedido(final SesionDeUsuario sesionDeUsuario) {
		return sesionDeUsuario.getUsuario().tieneRol(Roles.UsuarioRegistrado) ? EstadoItemDePedido.Pendiente
				: EstadoItemDePedido.Generado;
	}
}
