package com.pedidos.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.pedidos.model.Rol;

@Transactional
public interface RolRepository extends CrudRepository<Rol, Long> {
	
	public Rol findOneByNombre(final String nombre);
	
	public List<Rol> findAllByNombreIn(final List<String> nombres);
	
	public List<Rol> save(final List<Rol> roles);
}
