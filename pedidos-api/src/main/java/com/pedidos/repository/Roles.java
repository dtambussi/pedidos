package com.pedidos.repository;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pedidos.model.Rol;
import com.pedidos.model.RolesFactory;

@Component
public class Roles {
	
	private RolRepository rolRepository;

	public Roles(final RolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}
	
	public Set<Rol> rolesDefaultUsuarioNoRegistrado() {
		return this.rolRepository.findAllByNombreIn(RolesFactory.rolesDefaultUsuarioNoRegistrado().stream()
				.map(rol -> rol.getNombre()).collect(Collectors.toSet()));
	}
}
