package com.pedidos.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pedidos.model.Rol;

@Repository
@Transactional
public interface RolRepository extends CrudRepository<Rol, Long> {
	
	Rol findOneByNombre(final String nombre);
	
	Set<Rol> findAllByNombreIn(final Set<String> nombres);
	
	List<Rol> save(final List<Rol> roles);
}
