package com.pedidos.security;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.mysql.jdbc.StringUtils;
import com.pedidos.controller.error.UnauthorizedUserActionException;
import com.pedidos.model.Rol;
import com.pedidos.model.SesionDeUsuario;
import com.pedidos.repository.SesionDeUsuarioRepository;

@Component
public class SesionDeUsuarioValidator {
	
	private static final String AUTH_HEADER = "AuthorizationPedidos";
	private static final String MISSING_AUTH_HEADER_MSG = "Missing header value: " + AUTH_HEADER + " required to validate user action.";
	
	private SesionDeUsuarioRepository sesionDeUsuarioRepository;

	public SesionDeUsuarioValidator(SesionDeUsuarioRepository sesionDeUsuarioRepository) {
		this.sesionDeUsuarioRepository = sesionDeUsuarioRepository;
	}
	
	/**
	 * Valida la sesión contra una serie de roles esperados (usuario debe poseer al menos uno de los roles especificados)
	 */
	public SesionDeUsuario validarSesionDeUsuario(final HttpServletRequest request, final Set<Rol> roles) {
		final SesionDeUsuario sesionDeUsuario = this.validarSesionDeUsuario(request);
		if (!sesionDeUsuario.getUsuario().tieneAlgunoDeLosRoles(roles)) {
			throw new RuntimeException("User should have at least one of these roles: " + roles);
		}
		return sesionDeUsuario;
	}
	
	/**
	 * Valor del header AuthorizationPedidos debería matchear con un session id válido para un usuario
	 */
	private SesionDeUsuario validarSesionDeUsuario(final HttpServletRequest request) {
		final String sessionIdHeaderValue = StringUtils.isNullOrEmpty(request.getHeader(AUTH_HEADER)) ? null
				: request.getHeader(AUTH_HEADER);
		return Optional.of(sessionIdHeaderValue).map(sessionId -> obtenerSesionDeUsuarioValida(sessionId))
				.orElseThrow(() -> new UnauthorizedUserActionException(MISSING_AUTH_HEADER_MSG));
	}
	
	private SesionDeUsuario obtenerSesionDeUsuarioValida(final String sessionId) {
		final SesionDeUsuario sesion = this.sesionDeUsuarioRepository.findOne(sessionId);
		return Optional.of(sesion).map(Function.identity()).orElseThrow(
				() -> new UnauthorizedUserActionException("No active session with id: " + sessionId + " Try with login."));
	}
}
