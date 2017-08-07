package com.pedidos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
public class InfoAdicionalUsuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@OneToOne
	private Usuario usuario;
	private String nombre;
	private String apellido;
	private String email;
	private String password;
	private Date fechaNacimiento;
}
