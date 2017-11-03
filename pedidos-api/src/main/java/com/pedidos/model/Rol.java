package com.pedidos.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @EqualsAndHashCode(exclude = {"id"})
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {
	
	@Id
	private Long id;
	private String nombre;
}
