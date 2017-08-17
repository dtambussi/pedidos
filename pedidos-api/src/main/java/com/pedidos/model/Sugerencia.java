package com.pedidos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder @AllArgsConstructor
@Entity
@Table(name = "sugerencia")
public class Sugerencia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Status status;
	private EstadoSugerencia estado;
	@OneToOne
	private ItemDeMenu itemDeMenu;
	private String nombre;
	private String descripcion;
	private Double precio;
	private Integer cantidadDisponible;
	private Date fechaCreacion;
	private Date fechaUltimaModificacion;
	private Date fechaInicio;
	private Date fechaFin;
}
