package com.pedidos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "item_de_menu")
public class ItemDeMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Status status;
	private CategoriaItemDeMenu categoria;
	private String nombre;
	private String descripcion;
	private Double precio;
	private Date fechaCreacion;
	private Date fechaUltimaModificacion;
}
