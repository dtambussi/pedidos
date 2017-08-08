package com.pedidos.service;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pedidos.dto.LoginUsuarioNoRegistradoRequest;
import com.pedidos.dto.LoginUsuarioRegistradoRequest;
import com.pedidos.factory.UsuarioFactory;
import com.pedidos.model.InfoAdicionalUsuario;
import com.pedidos.model.SesionDeUsuario;
import com.pedidos.model.Usuario;
import com.pedidos.repository.InfoAdicionalUsuarioRepository;
import com.pedidos.repository.SesionDeUsuarioRepository;
import com.pedidos.repository.UsuarioRepository;

import static com.pedidos.utils.DateUtils.currentDate;
import static com.pedidos.utils.UUIDGenerator.newUUID;

@Component
public class LoginService {

	private UsuarioRepository usuarioRepository;
	private SesionDeUsuarioRepository sesionDeUsuarioRepository;
	private UsuarioFactory usuarioFactory;

	public LoginService(
			final UsuarioRepository usuarioRepository,
			final InfoAdicionalUsuarioRepository infoAdicionalUsuarioRepository,
			final SesionDeUsuarioRepository sesionDeUsuarioRepository, 
			final UsuarioFactory usuarioFactory) {
		this.usuarioRepository = usuarioRepository;
		this.sesionDeUsuarioRepository = sesionDeUsuarioRepository;
		this.usuarioFactory = usuarioFactory;
	}

	@Transactional
	public SesionDeUsuario loginUsuarioRegistrado(final LoginUsuarioRegistradoRequest request) {
		final Optional<Usuario> usuarioRegistrado = Optional
				.of(this.usuarioRepository.findOneByNickname(request.getEmail()));
		final Optional<InfoAdicionalUsuario> infoUsuario = usuarioRegistrado
				.map(usuario -> usuario.getInfoAdicional());		
		boolean loginValido = infoUsuario.filter(info -> info.getPassword().equals(request.getPassword())).isPresent();
		if (!loginValido) { throw new RuntimeException("Invalid user credentials"); }
		final SesionDeUsuario nuevaSesion = nuevaSesionDeUsuario(usuarioRegistrado.get());
		return this.sesionDeUsuarioRepository.save(nuevaSesion);
	}

	@Transactional
	public SesionDeUsuario loginUsuarioNoRegistrado(final LoginUsuarioNoRegistradoRequest request) {
		final Usuario usuarioYaAsociadoANickname = this.usuarioRepository.findOneByNickname(request.getNickname());
		if (usuarioYaAsociadoANickname != null) { throw new RuntimeException("Nickname already taken"); }		
		final Usuario nuevoUsuarioNoRegistrado = this.usuarioRepository
				.save(this.usuarioFactory.nuevoUsuarioNoRegistrado(request.getNickname()));
		final SesionDeUsuario nuevaSesion = nuevaSesionDeUsuario(nuevoUsuarioNoRegistrado);
		return this.sesionDeUsuarioRepository.save(nuevaSesion);
	}
	
	private static SesionDeUsuario nuevaSesionDeUsuario(final Usuario usuario) {
		return SesionDeUsuario.builder()
				.id(newUUID().toString())
				.usuario(usuario)
				.fechaCreacion(currentDate())
				.build();
	}
}
