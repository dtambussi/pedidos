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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_de_pedido")
public class ItemDePedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
//	@Transient
//	private Pedido pedido;
	private Status status;
	@Enumerated(EnumType.STRING)
	private EstadoItemDePedido estado;
	@OneToOne
	private ItemDeMenu itemDeMenu;
	private Integer cantidad;
	private String comentario;
	private Boolean abonado;
	private Date fechaUltimaModificacion;
}
