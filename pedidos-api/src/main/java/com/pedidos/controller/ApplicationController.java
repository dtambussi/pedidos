package com.pedidos.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.dto.CambiarEstadoDePedidoRequest;
import com.pedidos.dto.CambiarEstadoDeSugerenciaRequest;
import com.pedidos.dto.GenerarPedidoRequest;
import com.pedidos.dto.GenerarSugerenciaRequest;
import com.pedidos.dto.LoginUsuarioNoRegistradoRequest;
import com.pedidos.dto.LoginUsuarioRegistradoRequest;
import com.pedidos.dto.RecibirPedidoRequest;
import com.pedidos.model.CategoriaItemDeMenu;
import com.pedidos.model.EstadoItemDePedido;
import com.pedidos.model.EstadoPedido;
import com.pedidos.model.ItemDeMenu;
import com.pedidos.model.ItemReporteDePedidos;
import com.pedidos.model.Menu;
import com.pedidos.model.Pedido;
import com.pedidos.model.ReporteDePedidos;
import com.pedidos.model.SesionDeUsuario;
import com.pedidos.model.Sugerencia;
import com.pedidos.service.LoginService;
import com.pedidos.service.MenuService;
import com.pedidos.service.PedidoService;
import com.pedidos.service.SugerenciaService;

@RestController
public class ApplicationController {
	
	private MenuService menuService;
	private PedidoService pedidoService;
	private LoginService loginService;
	private SugerenciaService sugerenciaService;

	public ApplicationController(
			final LoginService loginService,
			final MenuService menuService, 
			final PedidoService pedidoService,
			final SugerenciaService sugerenciaService) {
		this.menuService = menuService;
		this.pedidoService = pedidoService;
		this.loginService = loginService;
		this.sugerenciaService = sugerenciaService;
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
		return this.menuService.getCurrentMenu();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/pedidos")
	public List<Pedido> pedidos() {
		return this.pedidoService.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/pedidos")
	public Pedido generarPedido(@RequestBody final GenerarPedidoRequest generarPedidoRequest) {
		return this.pedidoService.generarPedido(generarPedidoRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/pedido/{id}/recibirPedidoRequest")
	public Pedido recibirPedido(@PathVariable("id") Long id, final @RequestBody RecibirPedidoRequest recibirPedidoRequest) {
		return this.pedidoService.recibirPedido(recibirPedidoRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/pedido/{id}/cambiarEstadoDePedidoRequest")
	public Pedido cambiarEstadoDePedido(@PathVariable("id") Long id, final @RequestBody CambiarEstadoDePedidoRequest cambiarEstadoDePedidoRequest) {
		return this.pedidoService.cambiarEstadoDePedido(cambiarEstadoDePedidoRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/loginUsuarioNoRegistrado")
	public SesionDeUsuario loginUsuarioNoRegistrado(final @RequestBody LoginUsuarioNoRegistradoRequest loginUsuarioNoRegistradoRequest) {
		return this.loginService.loginUsuarioNoRegistrado(loginUsuarioNoRegistradoRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/loginUsuarioRegistrado")
	public SesionDeUsuario loginUsuarioRegistrado(final @RequestBody LoginUsuarioRegistradoRequest loginUsuarioRegistradoRequest) {
		return this.loginService.loginUsuarioRegistrado(loginUsuarioRegistradoRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/sugerencias")
	public Sugerencia generarSugerencia(final @RequestBody GenerarSugerenciaRequest generarSugerenciaRequest) {
		return this.sugerenciaService.generarSugerencia(generarSugerenciaRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/estadoDeSugerencia")
	public Sugerencia cambiarEstadoDeSugerencia(final @RequestBody CambiarEstadoDeSugerenciaRequest cambiarEstadoDeSugerenciaRequest) {
		return this.sugerenciaService.cambiarEstadoDeSugerencia(cambiarEstadoDeSugerenciaRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/sugerenciasVigentes")
	public List<Sugerencia> obtenerSugerenciasVigentes() {
		return this.sugerenciaService.obtenerSugerenciasVigentes();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/reporteDePedidosRequest")
	public ReporteDePedidos generarReporteDePedidos() { // mocked response to allow ui development
		final ReporteDePedidos reporte = new ReporteDePedidos();
		final List<ItemReporteDePedidos> items = Arrays.asList(
					new ItemReporteDePedidos(CategoriaItemDeMenu.Bebidas.name(), "Cerveza", 80),
					new ItemReporteDePedidos(CategoriaItemDeMenu.Bebidas.name(), "Coca Cola", 50),
					new ItemReporteDePedidos(CategoriaItemDeMenu.PlatosPrincipales.name(), "Papas fritas", 70),
					new ItemReporteDePedidos(CategoriaItemDeMenu.PlatosPrincipales.name(), "Pollo rebozado", 30)
				); 
		reporte.setItems(items);
		return reporte;
	}
}
