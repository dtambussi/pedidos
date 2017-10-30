package com.pedidos.factory;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.pedidos.model.InfoAdicionalUsuario;
import com.pedidos.model.Rol;
import com.pedidos.model.Usuario;
import com.pedidos.repository.Roles;

@Component
public class UsuarioFactory {
	
	private Roles roles;

	public UsuarioFactory(final Roles roles) {
		this.roles = roles;
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
				.roles(roles.rolesDefaultUsuarioNoRegistrado())
				.build();
	}
}
