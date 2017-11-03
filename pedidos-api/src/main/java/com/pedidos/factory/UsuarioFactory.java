package com.pedidos.factory;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pedidos.model.InfoAdicionalUsuario;
import com.pedidos.model.Rol;
import com.pedidos.model.Roles;
import com.pedidos.model.Usuario;
import com.pedidos.repository.RolRepository;

@Component
public class UsuarioFactory {
	
	private RolRepository rolRepository;

	public UsuarioFactory(final RolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}
	
	public Usuario nuevoUsuarioRegistrado(final String nickname, final InfoAdicionalUsuario infoAdicionalUsuario, final Set<Rol> rolesDeUsuario) {
		return Usuario.builder()
				.nickname(infoAdicionalUsuario.getEmail())
				.roles(rolesDeUsuario)
				.infoAdicional(infoAdicionalUsuario)
				.build();
	}
	
	public Usuario nuevoUsuarioNoRegistrado(final String nickname) {
		return Usuario.builder()
				.nickname(nickname)
				.roles(rolesDefaultUsuarioNoRegistrado())
				.build();
	}
	
	public Set<Rol> rolesDefaultUsuarioNoRegistrado() {
		return this.rolRepository.findAllByNombreIn(Roles.rolesDefaultUsuarioNoRegistrado().stream()
				.map(rol -> rol.getNombre()).collect(Collectors.toSet()));
	}
}
