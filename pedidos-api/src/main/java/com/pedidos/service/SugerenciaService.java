package com.pedidos.service;

import static com.pedidos.utils.DateUtils.currentDate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.pedidos.dto.CambiarEstadoDeSugerenciaRequest;
import com.pedidos.dto.GenerarSugerenciaRequest;
import com.pedidos.model.CategoriaItemDeMenu;
import com.pedidos.model.EstadoSugerencia;
import com.pedidos.model.ItemDeMenu;
import com.pedidos.model.ItemDePedido;
import com.pedidos.model.Pedido;
import com.pedidos.model.SesionDeUsuario;
import com.pedidos.model.Status;
import com.pedidos.model.Sugerencia;
import com.pedidos.repository.ItemDeMenuRepository;
import com.pedidos.repository.SugerenciaRepository;

@Component
public class SugerenciaService {

	private SugerenciaRepository sugerenciaRepository;
	private ItemDeMenuRepository itemDeMenuRepository;

	public SugerenciaService(final SugerenciaRepository sugerenciaRepository, final ItemDeMenuRepository itemDeMenuRepository) {
		this.sugerenciaRepository = sugerenciaRepository;
		this.itemDeMenuRepository = itemDeMenuRepository;
	}
	
	public Sugerencia generarSugerencia(final GenerarSugerenciaRequest request, final SesionDeUsuario sesionDeUsuario) {
		final Sugerencia nuevaSugerencia = Sugerencia.builder()
				.status(Status.Active)
				.estado(EstadoSugerencia.Publicado)
				.nombre(request.getNombre())
				.descripcion(request.getDescripcion())
				.precio(request.getPrecio())
				.cantidadDisponible(request.getCantidadDisponible())
				.cantidadConsumida(0)
				.fechaInicio(request.getFechaInicio())
				.fechaFin(request.getFechaFin())
				.fechaCreacion(currentDate())
				.build();
		final ItemDeMenu itemDeMenuAsociadoASugerencia = itemDeMenuAsociadoASugerencia(request);
		nuevaSugerencia.setItemDeMenu(this.itemDeMenuRepository.save(itemDeMenuAsociadoASugerencia));
		return this.sugerenciaRepository.save(nuevaSugerencia);
	}
	
	public Sugerencia cambiarEstadoDeSugerencia(final CambiarEstadoDeSugerenciaRequest request, final SesionDeUsuario sesionDeUsuario) {
		final Sugerencia sugerencia = this.sugerenciaRepository.findOne(request.getIdSugerencia());
		sugerencia.setEstado(request.getEstadoSugerencia());
		return this.sugerenciaRepository.save(sugerencia);
	}
	
	public List<Sugerencia> obtenerSugerenciasVigentes() {
		 // return this.sugerenciaRepository.findAll();
		 return this.sugerenciaRepository.findAllByEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
				EstadoSugerencia.Publicado, currentDate(), currentDate());
	}
	
	public Set<Sugerencia> registrarConsumoDeSugerencias(final Pedido pedido) {
		final Set<Sugerencia> sugerenciasQueRegistranConsumosEnPedido = new HashSet<>();
		final List<ItemDePedido> itemsDePedidoAsociadosASugerencias = pedido.obtenerItemsAsociadosASugerencias();
		for(final ItemDePedido itemDePedido : itemsDePedidoAsociadosASugerencias) {
			final Sugerencia sugerencia = this.sugerenciaRepository.findOneByItemDeMenu(itemDePedido.getItemDeMenu());
			sugerenciasQueRegistranConsumosEnPedido.add(sugerencia);			
			sugerencia.consumir(itemDePedido.getCantidad());
			this.sugerenciaRepository.save(sugerencia);
		}
		return sugerenciasQueRegistranConsumosEnPedido;
	}
	
	public Sugerencia obtenerPorItemDeMenuAsociado(final ItemDeMenu itemDeMenu) {
		return this.sugerenciaRepository.findOneByItemDeMenu(itemDeMenu);
	}
	
	private ItemDeMenu itemDeMenuAsociadoASugerencia(final GenerarSugerenciaRequest request) {
		return ItemDeMenu.builder()
				.nombre(request.getNombre())
				.descripcion(request.getDescripcion())
				.categoria(CategoriaItemDeMenu.Sugerencia)
				.precio(request.getPrecio())
				.fechaCreacion(currentDate())
				.fechaUltimaModificacion(currentDate())
				.status(Status.Active)
				.build();
	}
}
