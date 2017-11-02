package com.pedidos.security;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.mysql.jdbc.StringUtils;
import com.pedidos.model.Rol;
import com.pedidos.model.SesionDeUsuario;
import com.pedidos.model.Usuario;
import com.pedidos.repository.SesionDeUsuarioRepository;

@Component
public class SecurityService {
	
	private static final String AUTH_HEADER = "AuthorizationPedidos";
	
	private SesionDeUsuarioRepository sesionDeUsuarioRepository;

	public SecurityService(SesionDeUsuarioRepository sesionDeUsuarioRepository) {
		this.sesionDeUsuarioRepository = sesionDeUsuarioRepository;
	}
		
	/**
	 * Valor del header AuthorizationPedidos debería matchear con un session id válido para un usuario
	 */
	public Usuario validarSesionDeUsuario(final HttpServletRequest request) {
		final String sessionIdHeaderValue = StringUtils.isNullOrEmpty(request.getHeader(AUTH_HEADER)) ? null : request.getHeader(AUTH_HEADER);
		return Optional.of(sessionIdHeaderValue).map(sessionId -> obtenerUsuarioAsociadoASesion(sessionId))
				.orElseThrow(() -> new RuntimeException("Missing header value: " + AUTH_HEADER + " required to validate user action."));
	}
	
	/**
	 * Valida la sesión contra una serie de roles esperados (usuario debe poseer al menos uno de los roles especificados)
	 */
	public Usuario validarSesionDeUsuario(final HttpServletRequest request, final Set<Rol> roles) {
		final Usuario usuario = this.validarSesionDeUsuario(request);
		if (!usuario.tieneAlgunRolContenidoEn(roles)) {  throw new RuntimeException("User should have at least one of these roles: " + roles); }
		return usuario;
	}
	
	private Usuario obtenerUsuarioAsociadoASesion(final String sessionId) {
		final SesionDeUsuario sesion = this.sesionDeUsuarioRepository.findOne(sessionId);
		return Optional.of(sesion).map(sesionActiva -> sesionActiva.getUsuario())
				.orElseThrow(() -> new RuntimeException("No active session with id: " + sessionId + " Try with login."));
	}
}
