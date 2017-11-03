package com.pedidos.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.pedidos.model.Pedido;
import com.pedidos.model.Usuario;

@Transactional
public interface PedidoRepository extends CrudRepository<Pedido, Long>, PedidoCustomRepository {
	
	List<Pedido> findAll();

	List<Pedido> findAllByCliente(final Usuario usuario);	
}
