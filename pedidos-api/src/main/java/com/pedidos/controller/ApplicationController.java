package com.pedidos.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.model.CategoriaItemDeMenu;
import com.pedidos.model.EstadoItemDePedido;
import com.pedidos.model.EstadoPedido;
import com.pedidos.model.Menu;
import com.pedidos.service.MenuService;

@RestController
public class ApplicationController {
	
	private MenuService menuService;

	public ApplicationController(final MenuService menuService) {
		this.menuService = menuService;
	}
	
	@GetMapping(value = "/estadoPedido")
	public ResponseEntity<List<EstadoPedido>> estadosDePedido() {
		return new ResponseEntity<List<EstadoPedido>>(Arrays.asList(EstadoPedido.values()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/estadoItemDePedido")
	public ResponseEntity<List<EstadoItemDePedido>> estadosItemDePedido() {
		return new ResponseEntity<List<EstadoItemDePedido>>(Arrays.asList(EstadoItemDePedido.values()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/categoriaItemDeMenu")
	public ResponseEntity<List<CategoriaItemDeMenu>> categoriasItemDeMenu() {
		return new ResponseEntity<List<CategoriaItemDeMenu>>(Arrays.asList(CategoriaItemDeMenu.values()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/menu")
	public ResponseEntity<Menu> menu() {
		return new ResponseEntity<Menu>(this.menuService.getCurrentMenu(), HttpStatus.OK);
	}	
}
