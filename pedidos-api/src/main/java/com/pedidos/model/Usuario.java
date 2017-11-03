package com.pedidos.model;

import static com.pedidos.model.RolesFactory.rolesDeAtencionAlCliente;

import java.util.Collections;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nickname;
	@ManyToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Rol> roles;
	@OneToOne
	private InfoAdicionalUsuario infoAdicional;
	
	public boolean tieneAlgunoDeLosRoles(final Set<Rol> roles) {
		// disjoint implicaría que no tienen elementos en común (disjuntos)
		return !Collections.disjoint(this.roles,  roles);
	}
	
	public boolean tieneRol(final Rol rol) {
		// disjoint implicaría que no tienen elementos en común (disjuntos)
		return this.roles.contains(rol);
	}
	
	public boolean esPersonalDeAtencionAlCliente() {
		return this.tieneAlgunoDeLosRoles(rolesDeAtencionAlCliente());
	}
	
	public boolean esPersonalDeCocina() {
		return this.tieneRol(RolesFactory.Cocinero);
	}
	
	public boolean esCliente() {
		return this.tieneRol(RolesFactory.Cliente);
	}
}