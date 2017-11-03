package com.pedidos.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.pedidos.model.Menu;

@Transactional
public interface MenuRepository extends CrudRepository<Menu, Long> {
	
	List<Menu> findAll();	
}
