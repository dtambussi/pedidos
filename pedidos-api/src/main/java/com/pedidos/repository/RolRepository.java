package com.pedidos.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.pedidos.model.Rol;

@Transactional
public interface RolRepository extends CrudRepository<Rol, Long> {

}
