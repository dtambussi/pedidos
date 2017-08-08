package com.pedidos.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.pedidos.model.Usuario;

@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	public Usuario findOneByNickname(final String nickname);
}
