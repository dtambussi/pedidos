package com.pedidos.model;

import java.util.Date;

import javax.persistence.Entity;
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
@Table(name = "usuario")
public class SesionDeUsuario {
	
	@Id
	private String id;
	@OneToOne
	private Usuario usuario;
	private Date fechaCreacion;
}
