package com.pedidos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "sugerencia")
public class Sugerencia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Status status;
	@Enumerated(EnumType.STRING)
	private EstadoSugerencia estado;
	@OneToOne
	private ItemDeMenu itemDeMenu;
	private String nombre;
	private String descripcion;
	private Double precio;
	private Integer cantidadDisponible, cantidadConsumida;
	private Date fechaCreacion;
	private Date fechaUltimaModificacion;
	private Date fechaInicio;
	private Date fechaFin;
	
	public Sugerencia consumir(int cantidadAConsumir) {
		this.cantidadConsumida += cantidadAConsumir;
		return this;
	}
}