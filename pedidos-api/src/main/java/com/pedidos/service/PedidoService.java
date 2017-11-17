package com.pedidos.service;

import static com.pedidos.utils.DateUtils.currentDate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pedidos.dto.CambiarEstadoDePedidoRequest;
import com.pedidos.dto.CambiarEstadoItemDePedidoRequest;
import com.pedidos.dto.GenerarPedidoRequest;
import com.pedidos.dto.GenerarReporteDePedidosRequest;
import com.pedidos.dto.ItemDePedidoRequest;
import com.pedidos.model.EstadoItemDePedido;
import com.pedidos.model.EstadoPedido;
import com.pedidos.model.ItemDePedido;
import com.pedidos.model.Pedido;
import com.pedidos.model.ReporteDePedidos;
import com.pedidos.model.Roles;
import com.pedidos.model.SesionDeUsuario;
import com.pedidos.model.Status;
import com.pedidos.model.Usuario;
import com.pedidos.repository.ItemDeMenuRepository;
import com.pedidos.repository.MenuRepository;
import com.pedidos.repository.PedidoRepository;
import com.pedidos.utils.DateUtils;
import com.pedidos.validator.PedidoValidator;

@Component
public class PedidoService {

	private final PedidoRepository pedidoRepository;
	private final MenuRepository menuRepository;
	private final ItemDeMenuRepository itemDeMenuRepository;
	private PedidoValidator pedidoValidator;
	private SugerenciaService sugerenciaService;

	public PedidoService(final PedidoRepository pedidoRepository, final MenuRepository menuRepository,
			final ItemDeMenuRepository itemDeMenuRepository, final PedidoValidator pedidoValidator,
			final SugerenciaService sugerenciaService) {
		this.pedidoRepository = pedidoRepository;
		this.menuRepository = menuRepository;
		this.itemDeMenuRepository = itemDeMenuRepository;
		this.pedidoValidator = pedidoValidator;
		this.sugerenciaService = sugerenciaService;
	}

	public List<Pedido> obtenerPedidos(final SesionDeUsuario sesionDeUsuario) {
		return this.pedidoRepository.obtenerPedidosOrdenadosParaUsuario(sesionDeUsuario.getUsuario());
	}

	@Transactional
	public synchronized Pedido generarPedido(final GenerarPedidoRequest request, final SesionDeUsuario sesionDeUsuario) {
		final Usuario usuario = sesionDeUsuario.getUsuario();
		// Crear items del pedido
		final List<ItemDePedido> itemsDePedido = request.getItems().stream()
				.map(crearItemDePedidoRequest -> crearItemDePedido(crearItemDePedidoRequest, usuario))
				.collect(Collectors.toList());
		// Crear el pedido asociando sus items y valores correspondientes
		final Pedido nuevoPedido = Pedido.builder()
				.menu(menuRepository.findOne(request.getIdMenu()))
				.items(itemsDePedido)
				.estado(resolverEstadoAsignableANuevoPedido(sesionDeUsuario))
				.destino(request.getDestino())
				.comentario(request.getComentario())
				.abonado(false)
				.cliente(sesionDeUsuario.getUsuario())
				.fechaCreacion(currentDate()).fechaUltimaModificacion(currentDate()).status(Status.Active).build();
		// Validar consistencia del pedido
		this.pedidoValidator.validarPedido(nuevoPedido);
		// Impactar el consumo de sugerencias efectuado por el pedido
		this.sugerenciaService.registrarConsumoDeSugerencias(nuevoPedido);
		// Finalmente registrar el nuevo pedido
		return pedidoRepository.save(nuevoPedido);
	}

	public Pedido cambiarEstadoDePedido(final CambiarEstadoDePedidoRequest request, final SesionDeUsuario sesionDeUsuario) {
		final Usuario usuario = sesionDeUsuario.getUsuario();
		final Pedido pedido = pedidoRepository.findOne(request.getIdPedido());
		pedido.setDestino(request.getDestino());
		pedido.setComentario(request.getComentario());
		pedido.setAbonado(request.getAbonado());
		pedido.setEstado(request.getEstadoPedido());
		pedido.setFechaUltimaModificacion(currentDate());
		if (deberiaAsignarComoPersonalDeAtencion(pedido, usuario)) { pedido.setPersonalAsignado(usuario); }
		// Modificar únicamente los items para los que se solicitó cambio de estado
		request.getCambiosDeEstadoSobreItems().stream()
				.map(cambioEstadoItem -> pedido.obtenerItem(cambioEstadoItem.getIdItemDePedido())
						.map(item -> cambiarEstadoDeItemDePedido(item, cambioEstadoItem)))
				.collect(Collectors.toList());
		// Registrar el cambio de estado del pedido
		return this.pedidoRepository.save(pedido);
	}

	public ReporteDePedidos generarReporteDePedidos(final GenerarReporteDePedidosRequest request) {
		return this.pedidoRepository.obtenerReporte(request.getFechaDesde(),
				DateUtils.endOfDay(request.getFechaHasta()), request.getEstadoPedido());
	}
	
	private ItemDePedido cambiarEstadoDeItemDePedido(final ItemDePedido itemDePedido,
			final CambiarEstadoItemDePedidoRequest request) {
		itemDePedido.setEstado(request.getEstadoItemDePedido());
		itemDePedido.setComentario(request.getComentario());
		return itemDePedido;
	}
	
	private boolean deberiaAsignarComoPersonalDeAtencion(final Pedido pedido, final Usuario usuario) {
		return pedido.getPersonalAsignado() == null
				&& usuario.tieneAlgunoDeLosRoles(Roles.rolesDeAtencionAlCliente());
	}
	
	private EstadoPedido resolverEstadoAsignableANuevoPedido(final SesionDeUsuario sesionDeUsuario) {
		return sesionDeUsuario.getUsuario().tieneRol(Roles.UsuarioRegistrado) ? EstadoPedido.Pendiente
				: EstadoPedido.Generado;
	}

	private EstadoItemDePedido resolverEstadoParaNuevoItemDePedidoSegunUsuario(final Usuario usuario) {
		return usuario.tieneRol(Roles.UsuarioRegistrado) ? EstadoItemDePedido.Pendiente : EstadoItemDePedido.Generado;
	}
	
	private ItemDePedido crearItemDePedido(final ItemDePedidoRequest itemPedido, final Usuario usuario) {
		return ItemDePedido.builder()
			.estado(resolverEstadoParaNuevoItemDePedidoSegunUsuario(usuario))
			.itemDeMenu(itemDeMenuRepository.findOne(itemPedido.getIdItemDeMenu()))
			.cantidad(itemPedido.getCantidad())
			.comentario(itemPedido.getComentario())
			.abonado(false)
			.fechaUltimaModificacion(currentDate()).status(Status.Active).build();
	}
}
