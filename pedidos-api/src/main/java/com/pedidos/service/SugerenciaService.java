package com.pedidos.service;

import java.util.List;

import com.pedidos.dto.CambiarEstadoDeSugerenciaRequest;
import com.pedidos.dto.GenerarSugerenciaRequest;
import com.pedidos.model.EstadoSugerencia;
import com.pedidos.model.Status;
import com.pedidos.model.Sugerencia;
import com.pedidos.repository.ItemDeMenuRepository;
import com.pedidos.repository.SugerenciaRepository;

import static com.pedidos.utils.DateUtils.currentDate;

public class SugerenciaService {

	private ItemDeMenuRepository itemDeMenuRepository;
	private SugerenciaRepository sugerenciaRepository;

	public SugerenciaService(final ItemDeMenuRepository itemDeMenuRepository, 
							 final SugerenciaRepository sugerenciaRepository) {
		this.itemDeMenuRepository = itemDeMenuRepository;
		this.sugerenciaRepository = sugerenciaRepository;
	}
	
	public Sugerencia generarSugerencia(final GenerarSugerenciaRequest request) {
		final Sugerencia nuevaSugerencia = Sugerencia.builder()
				.status(Status.Active)
				.estado(EstadoSugerencia.NoPublicado)
				.itemDeMenu(itemDeMenuRepository.findOne(request.getIdItemDeMenu()))
				.nombre(request.getNombre())
				.descripcion(request.getDescripcion())
				.precio(request.getPrecio())
				.cantidadDisponible(request.getCantidadDisponible())
				.fechaInicio(request.getFechaInicio())
				.fechaFin(request.getFechaFin())
				.fechaCreacion(currentDate())
				.build();
		return this.sugerenciaRepository.save(nuevaSugerencia);
	}
	
	public Sugerencia cambiarEstadoDeSugerencia(final CambiarEstadoDeSugerenciaRequest request) {
		final Sugerencia sugerencia = this.sugerenciaRepository.findOne(request.getIdSugerencia());
		sugerencia.setEstado(request.getEstadoSugerencia());
		return this.sugerenciaRepository.save(sugerencia);
	}
	
	public List<Sugerencia> obtenerSugerenciasVigentes() {
		return this.sugerenciaRepository.findAllByEstadoSugerenciaAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
				EstadoSugerencia.Publicado, currentDate(), currentDate());
	}
}
