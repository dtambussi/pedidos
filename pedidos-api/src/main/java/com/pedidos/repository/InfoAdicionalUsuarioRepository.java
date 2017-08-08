package com.pedidos.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.pedidos.model.InfoAdicionalUsuario;

@Transactional
public interface InfoAdicionalUsuarioRepository extends CrudRepository<InfoAdicionalUsuario, Long> {

}
