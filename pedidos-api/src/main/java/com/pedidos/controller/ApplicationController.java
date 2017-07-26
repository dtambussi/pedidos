package com.pedidos.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.dto.CambiarEstadoDePedidoRequest;
import com.pedidos.dto.GenerarPedidoRequest;
import com.pedidos.dto.RecibirPedidoRequest;
import com.pedidos.model.CategoriaItemDeMenu;
import com.pedidos.model.EstadoItemDePedido;
import com.pedidos.model.EstadoPedido;
import com.pedidos.model.Menu;
import com.pedidos.model.Pedido;
import com.pedidos.service.MenuService;
import com.pedidos.service.PedidoService;

@RestController
public class ApplicationController {
	
	private MenuService menuService;
	private PedidoService pedidoService;

	public ApplicationController(final MenuService menuService, final PedidoService pedidoService) {
		this.menuService = menuService;
		this.pedidoService = pedidoService;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/estadoPedido")
	public List<EstadoPedido> estadosDePedido() {
		return Arrays.asList(EstadoPedido.values());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/estadoItemDePedido")
	public List<EstadoItemDePedido> estadosItemDePedido() {
		return Arrays.asList(EstadoItemDePedido.values());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/categoriaItemDeMenu")
	public List<CategoriaItemDeMenu> categoriasItemDeMenu() {
		return Arrays.asList(CategoriaItemDeMenu.values());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/menu")
	public Menu menu() {
		return menuService.getCurrentMenu();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/pedidos")
	public List<Pedido> pedidos() {
		return pedidoService.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/pedidos")
	public Pedido generarPedido(final GenerarPedidoRequest generarPedidoRequest) {
		return pedidoService.generarPedido(generarPedidoRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/pedido/{id}/recibirPedidoRequest")
	public Pedido recibirPedido(@PathVariable("id") Long id, final RecibirPedidoRequest recibirPedidoRequest) {
		return pedidoService.recibirPedido(recibirPedidoRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/pedido/{id}/cambiarEstadoDePedidoRequest")
	public Pedido cambiarEstadoDePedido(@PathVariable("id") Long id, final CambiarEstadoDePedidoRequest cambiarEstadoDePedidoRequest) {
		return null;
	}
}
