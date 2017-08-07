package com.pedidos.service;

import java.util.Optional;

import com.pedidos.dto.LoginUsuarioNoRegistradoRequest;
import com.pedidos.dto.LoginUsuarioRegistradoRequest;
import com.pedidos.model.SesionDeUsuario;
import com.pedidos.model.Usuario;
import com.pedidos.repository.SesionDeUsuarioRepository;
import com.pedidos.repository.UsuarioRepository;

import static com.pedidos.utils.DateUtils.now;
import static com.pedidos.utils.UUIDGenerator.newUUID;

public class LoginService {
	
	private UsuarioRepository usuarioRepository;
	private SesionDeUsuarioRepository sesionDeUsuarioRepository;
	
	public LoginService(
			final UsuarioRepository usuarioRepository,
			final SesionDeUsuarioRepository sesionDeUsuarioRepository) {
		this.usuarioRepository = usuarioRepository;
		this.sesionDeUsuarioRepository = sesionDeUsuarioRepository;
	}
	
	public SesionDeUsuario loginUsuarioRegistrado(final LoginUsuarioRegistradoRequest request) {
		final Usuario usuarioRegistrado = Optional.of(this.usuarioRepository.findOneByEmailAndPassword(request.getEmail(), request.getPassword()))
			.orElseThrow(() -> new RuntimeException("Invalid user credentials"));
		final SesionDeUsuario nuevaSesion = SesionDeUsuario.builder()
												.id(newUUID().toString())
												.usuario(usuarioRegistrado)
												.fechaCreacion(now())
												.build();
		return this.sesionDeUsuarioRepository.save(nuevaSesion);
	}
	
	public SesionDeUsuario loginUsuarioNoRegistrado(final LoginUsuarioNoRegistradoRequest request) {
		return null;
	}
}
