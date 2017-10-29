package com.pedidos.model.fixture;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.pedidos.factory.UsuarioFactory;
import com.pedidos.model.InfoAdicionalUsuario;
import com.pedidos.model.Rol;
import com.pedidos.model.RolesFactory;
import com.pedidos.model.Usuario;

@Component
public class UsuarioFixture {

	private UsuarioFactory usuarioFactory;

	public UsuarioFixture(final UsuarioFactory usuarioFactory) {
		this.usuarioFactory = usuarioFactory;
	}

	public Usuario camarera_sabrina_mesa() {
		return this.camarera(detalleUsuario("Sabrina", "Mesa", "sabrina_mesa@pedidos.com", "sabrina"));
	}
	
	public Usuario barman_esteban_copas() {
		return this.barman(detalleUsuario("Esteban", "Copas", "esteban_copas@pedidos.com", "esteban"));
	}
	
	public Usuario cocinero_carlos_parrilla() {
		return this.cocinero(detalleUsuario("Carlos", "Parrilla", "carlos_parrilla@pedidos.com", "carlos"));
	}
	
	public Usuario dueño_alejandro_moneta() {
		return this.dueño(detalleUsuario("Alejandro", "Moneta", "alejandro_moneta@pedidos.com", "alejandro"));
	}
	
	public Usuario cliente_natali_perez() {
		return this.clienteRegistrado(detalleUsuario("Natali", "Perez", "natali_perez@pedidos.com", "natali"));
	}
	
	private Usuario clienteRegistrado(final InfoAdicionalUsuario infoUsuario) {
		return this.usuario(infoUsuario, RolesFactory.rolesDefaultClienteRegistrado());
	}
	
	private Usuario camarera(final InfoAdicionalUsuario infoUsuario) {
		return this.usuario(infoUsuario, RolesFactory.rolesDefaultCamarera());
	}
	
	private Usuario barman(final InfoAdicionalUsuario infoUsuario) {
		return this.usuario(infoUsuario, RolesFactory.rolesDefaultBarman());
	}
	
	private Usuario cocinero(final InfoAdicionalUsuario infoUsuario) {
		return this.usuario(infoUsuario, RolesFactory.rolesDefaultCocinero());
	}
	
	private Usuario dueño(final InfoAdicionalUsuario infoUsuario) {
		return this.usuario(infoUsuario, RolesFactory.rolesDefaultDueño());
	}
	
	private Usuario usuario(final InfoAdicionalUsuario infoUsuario, Set<Rol> roles) {
		return this.usuarioFactory.nuevoUsuarioRegistrado(infoUsuario.getEmail(), infoUsuario, roles);
	}
	
	private static InfoAdicionalUsuario detalleUsuario(final String nombre, final String apellido, final String email, final String password) {
		return InfoAdicionalUsuario.builder()
				.nombre(nombre)
				.apellido(apellido)
				.email(email)
				.password(password)
				.build();
	}
}
