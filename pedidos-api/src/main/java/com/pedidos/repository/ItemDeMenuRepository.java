package com.pedidos.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.pedidos.model.ItemDeMenu;

@Transactional
public interface ItemDeMenuRepository extends CrudRepository<ItemDeMenu, Long>{

}
