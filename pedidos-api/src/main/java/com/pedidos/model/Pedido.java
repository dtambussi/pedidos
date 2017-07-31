package com.pedidos.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Status status;
	@OneToOne
	private Menu menu;
	private EstadoPedido estado;
	private String comentario;
	private Boolean abonado;
	private String mesa;
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ItemDePedido> items;
	private Date fechaCreacion;
	private Date fechaUltimaModificacion;
	
	public String agregarComentario(final String nuevoComentario) {
		final String comentarioAgregado = nuevoComentario != null ? nuevoComentario : "";
		this.comentario = this.comentario != null ? this.comentario + " " + comentarioAgregado : comentarioAgregado;
		return this.comentario;
	}
	
	public Optional<ItemDePedido> obtenerItem(final Long itemId) {
		return this.items != null ? this.items.stream()
				.filter(item -> item.getId() == itemId).findFirst() : Optional.empty();
	}
}
