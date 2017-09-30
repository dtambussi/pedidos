package com.pedidos.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.pedidos.model.EstadoSugerencia;
import com.pedidos.model.Sugerencia;

@Transactional
public interface SugerenciaRepository extends CrudRepository<Sugerencia, Long> {
	
	List<Sugerencia> findAllByEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
			final EstadoSugerencia estadoSugerencia, final Date fechaInicio, final Date fechaFin);
}
