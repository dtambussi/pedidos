package com.pedidos.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RolesFactory {
	
	public static final Rol UsuarioRegistrado = rol(1L, "UsuarioRegistrado");
	public static final Rol UsuarioNoRegistrado = rol(2L, "UsuarioNoRegistrado");
	public static final Rol Camarera = rol(3L, "Camarera");
	public static final Rol Barman = rol(4L, "Barman");
	public static final Rol Cocinero = rol(5L, "Cocinero");
	public static final Rol Cajero = rol(6L, "Cajero");
	public static final Rol Dueño = rol(7L, "Dueño");
	public static final Rol Cliente = rol(8L, "Cliente");
	public static final Rol CrearPedido = rol(9L, "CrearPedido");
	public static final Rol RecibirPedido = rol(10L, "RecibirPedido");
	public static final Rol CambiarEstadoDePedido = rol(11L, "CambiarEstadoDePedido");
	public static final Rol ListarPedidos = rol(12L, "ListarPedidos");
	public static final Rol GenerarReporteDePedidos = rol(13L, "GenerarReporteDePedidos");
	public static final Rol CrearSugerencia = rol(14L, "CrearSugerencia");
	public static final Rol CambiarEstadoDeSugerencia = rol(15L, "CambiarEstadoDeSugerencia");
	
	private static final Rol rol(final Long id, final String nombreRol) {
		return Rol.builder()
				.id(id)
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
		return roles(UsuarioNoRegistrado, Cliente, CrearPedido, ListarPedidos);
	}
	
	public static final Set<Rol> rolesDefaultClienteRegistrado() {
		return roles(UsuarioRegistrado, Cliente, CrearPedido, ListarPedidos);
	}
	
	public static final Set<Rol> rolesDefaultCamarera() {
		return roles(UsuarioRegistrado, Camarera, ListarPedidos, RecibirPedido, CambiarEstadoDePedido);
	}

	public static final Set<Rol> rolesDefaultBarman() {
		return roles(UsuarioRegistrado, Barman, ListarPedidos, RecibirPedido, CambiarEstadoDePedido);
	}
	
	public static final Set<Rol> rolesDefaultCocinero() {
		return roles(UsuarioRegistrado, Cocinero, ListarPedidos, CambiarEstadoDePedido);
	}

	public static final Set<Rol> rolesDefaultCajero() {
		return roles(UsuarioRegistrado, Cajero, ListarPedidos, CambiarEstadoDePedido, GenerarReporteDePedidos);
	}
	
	public static final Set<Rol> rolesDefaultDueño() {
		return roles(UsuarioRegistrado, Dueño, ListarPedidos, CambiarEstadoDePedido, GenerarReporteDePedidos);
	}
	
	public static final Set<Rol> rolesDeAtencionAlCliente() {
		return roles(Camarera, Barman, Cajero, Dueño);
	}
	
	private static Set<Rol> roles(final Rol...roles) {
		return new HashSet<>(Arrays.asList(roles));
	}
}
