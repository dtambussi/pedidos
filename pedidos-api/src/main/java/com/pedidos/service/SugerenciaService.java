package com.pedidos.service;

import static com.pedidos.utils.DateUtils.currentDate;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pedidos.dto.CambiarEstadoDeSugerenciaRequest;
import com.pedidos.dto.GenerarSugerenciaRequest;
import com.pedidos.model.CategoriaItemDeMenu;
import com.pedidos.model.EstadoSugerencia;
import com.pedidos.model.ItemDeMenu;
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
	
	public Sugerencia generarSugerencia(final GenerarSugerenciaRequest request) {
		final Sugerencia nuevaSugerencia = Sugerencia.builder()
				.status(Status.Active)
				.estado(EstadoSugerencia.Publicado)
				.nombre(request.getNombre())
				.descripcion(request.getDescripcion())
				.precio(request.getPrecio())
				.cantidadDisponible(request.getCantidadDisponible())
				.fechaInicio(request.getFechaInicio())
				.fechaFin(request.getFechaFin())
				.fechaCreacion(currentDate())
				.build();
		final ItemDeMenu itemDeMenuAsociadoASugerencia = itemDeMenuAsociadoASugerencia(request);
		nuevaSugerencia.setItemDeMenu(this.itemDeMenuRepository.save(itemDeMenuAsociadoASugerencia));
		return this.sugerenciaRepository.save(nuevaSugerencia);
	}
	
	public Sugerencia cambiarEstadoDeSugerencia(final CambiarEstadoDeSugerenciaRequest request) {
		final Sugerencia sugerencia = this.sugerenciaRepository.findOne(request.getIdSugerencia());
		sugerencia.setEstado(request.getEstadoSugerencia());
		return this.sugerenciaRepository.save(sugerencia);
	}
	
	public List<Sugerencia> obtenerSugerenciasVigentes() {
		// return this.sugerenciaRepository.findAllByEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
		//		EstadoSugerencia.Publicado, currentDate(), currentDate());
		return this.sugerenciaRepository.findAll();
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
