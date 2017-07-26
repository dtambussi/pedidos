package com.pedidos.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.pedidos.model.Pedido;

@Transactional
public interface PedidoRepository extends CrudRepository<Pedido, Long> {
	
	public List<Pedido> findAll();	
}
