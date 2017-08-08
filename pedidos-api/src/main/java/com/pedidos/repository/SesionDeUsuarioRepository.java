package com.pedidos.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.pedidos.model.SesionDeUsuario;

@Transactional
public interface SesionDeUsuarioRepository extends CrudRepository<SesionDeUsuario, String> {

}
