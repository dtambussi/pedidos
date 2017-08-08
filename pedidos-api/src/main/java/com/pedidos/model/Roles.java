package com.pedidos.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Roles {
	
	public static final Rol UsuarioRegistrado = rol("UsuarioRegistrado");
	public static final Rol UsuarioNoRegistrado = rol("UsuarioNoRegistrado");
	public static final Rol Camarera = rol("Camarera");
	public static final Rol Barman = rol("Barman");
	public static final Rol Cocinero = rol("Cocinero");
	public static final Rol Cajero = rol("Cajero");
	public static final Rol Dueño = rol("Dueño");
	public static final Rol Cliente = rol("Cliente");
	public static final Rol CrearPedido = rol("CrearPedido");
	public static final Rol RecibirPedido = rol("RecibirPedido");
	public static final Rol CambiarEstadoDePedido = rol("CambiarEstadoDePedido");
	public static final Rol ListarPedidos = rol("ListarPedidos");
	public static final Rol GenerarReporteDePedidos = rol("GenerarReporteDePedidos");
	public static final Rol CrearSugerencia = rol("CrearSugerencia");
	public static final Rol CambiarEstadoDeSugerencia = rol("CambiarEstadoDeSugerencia");
	
	private static final Rol rol(final String nombreRol) {
		return Rol.builder()
				.nombre(nombreRol)
				.build();
	}
	
	public static final List<Rol> all() {
		return Arrays.asList(
				UsuarioRegistrado,
				UsuarioNoRegistrado,
				Camarera,
				Barman,
				Cocinero,
				Cajero,
				Dueño,
				Cliente,
				CrearPedido,
				RecibirPedido,
				CambiarEstadoDePedido,
				ListarPedidos,
				GenerarReporteDePedidos,
				CrearSugerencia,
				CambiarEstadoDeSugerencia);
	}
	
	public static final Set<Rol> rolesDefaultUsuarioNoRegistrado() {
		return roles(UsuarioNoRegistrado);
	}
	
	public static final Set<Rol> rolesDefaultClienteRegistrado() {
		return roles(UsuarioRegistrado, Cliente);
	}
	
	public static final Set<Rol> rolesDefaultCamarera() {
		return roles(UsuarioRegistrado, Camarera);
	}

	public static final Set<Rol> rolesDefaultBarman() {
		return roles(UsuarioRegistrado, Barman);
	}
	
	public static final Set<Rol> rolesDefaultCocinero() {
		return roles(UsuarioRegistrado, Cocinero);
	}

	public static final Set<Rol> rolesDefaultCajero() {
		return roles(UsuarioRegistrado, Cajero);
	}
	
	public static final Set<Rol> rolesDefaultDueño() {
		return roles(UsuarioRegistrado, Dueño);
	}
	
	public static final Set<Rol> rolesDefaultCliente() {
		return roles(UsuarioRegistrado, Cliente);
	}
	
	private static Set<Rol> roles(final Rol...roles) {
		return new HashSet<>(Arrays.asList(roles));
	}
}
