package com.pedidos.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.pedidos.dto.GenerarReporteDePedidosRequest;
import com.pedidos.dto.GenerarSugerenciaRequest;
import com.pedidos.dto.LoginUsuarioNoRegistradoRequest;
import com.pedidos.dto.LoginUsuarioRegistradoRequest;
import com.pedidos.model.CategoriaItemDeMenu;
import com.pedidos.model.EstadoItemDePedido;
import com.pedidos.model.EstadoPedido;
import com.pedidos.model.Menu;
import com.pedidos.model.Pedido;
import com.pedidos.model.ReporteDePedidos;
import com.pedidos.model.Rol;
import com.pedidos.model.Roles;
import com.pedidos.model.SesionDeUsuario;
import com.pedidos.model.Sugerencia;
import com.pedidos.security.SesionDeUsuarioValidator;
import com.pedidos.service.LoginService;
import com.pedidos.service.MenuService;
import com.pedidos.service.PedidoService;
import com.pedidos.service.SugerenciaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApplicationController {
	
	private MenuService menuService;
	private PedidoService pedidoService;
	private LoginService loginService;
	private SugerenciaService sugerenciaService;
	private SesionDeUsuarioValidator validadorDeSesionDeUsuarioService;

	public ApplicationController(
			final LoginService loginService,
			final MenuService menuService, 
			final PedidoService pedidoService,
			final SugerenciaService sugerenciaService,
			final SesionDeUsuarioValidator validadorDeSesionDeUsuarioService) {
		this.menuService = menuService;
		this.pedidoService = pedidoService;
		this.loginService = loginService;
		this.sugerenciaService = sugerenciaService;
		this.validadorDeSesionDeUsuarioService = validadorDeSesionDeUsuarioService;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/estadoPedido")
	public List<EstadoPedido> estadosDePedido(final HttpServletRequest request, final HttpServletResponse response) {
		return Arrays.asList(EstadoPedido.values());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/estadoItemDePedido")
	public List<EstadoItemDePedido> estadosItemDePedido(final HttpServletRequest request, final HttpServletResponse response) {
		return Arrays.asList(EstadoItemDePedido.values());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/categoriaItemDeMenu")
	public List<CategoriaItemDeMenu> categoriasItemDeMenu(final HttpServletRequest request, final HttpServletResponse response) {
		return Arrays.asList(CategoriaItemDeMenu.values());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/loginUsuarioNoRegistrado")
	public SesionDeUsuario loginUsuarioNoRegistrado(
			final @RequestBody LoginUsuarioNoRegistradoRequest loginUsuarioNoRegistradoRequest,
			final HttpServletRequest request, final HttpServletResponse response) {
		return this.loginService.loginUsuarioNoRegistrado(loginUsuarioNoRegistradoRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/loginUsuarioRegistrado")
	public SesionDeUsuario loginUsuarioRegistrado(
			final @RequestBody LoginUsuarioRegistradoRequest loginUsuarioRegistradoRequest,
			final HttpServletRequest request, final HttpServletResponse response) {
		return this.loginService.loginUsuarioRegistrado(loginUsuarioRegistradoRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/logout")
	public void logout(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			final SesionDeUsuario sesionDeUsuario = validarSesionParaLogoutDeUsuario(request);
			this.loginService.logout(sesionDeUsuario);
		} catch (final Throwable throwable) {
			log.error("Logout error: " + throwable.getMessage(), throwable);
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/menu")
	public Menu menu(final HttpServletRequest request, final HttpServletResponse response) {
		return this.menuService.getCurrentMenu();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/pedidos")
	public List<Pedido> pedidos(final HttpServletRequest request, final HttpServletResponse response) {
		final SesionDeUsuario sesionDeUsuario = validarSesionDeUsuario(request, Roles.ListarPedidos);
		return this.pedidoService.obtenerPedidos(sesionDeUsuario);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/pedidos")
	public Pedido generarPedido(@RequestBody final GenerarPedidoRequest generarPedidoRequest,
			final HttpServletRequest request, final HttpServletResponse response) {
		final SesionDeUsuario sesionDeUsuario = validarSesionDeUsuario(request, Roles.CrearPedido);
		return this.pedidoService.generarPedido(generarPedidoRequest, sesionDeUsuario);
	}
		
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/pedido/{id}/cambiarEstadoDePedidoRequest")
	public Pedido cambiarEstadoDePedido(@PathVariable("id") Long id,
			final @RequestBody CambiarEstadoDePedidoRequest cambiarEstadoDePedidoRequest,
			final HttpServletRequest request, final HttpServletResponse response) {
		final SesionDeUsuario sesionDeUsuario = validarSesionDeUsuario(request, Roles.CambiarEstadoDePedido);
		return this.pedidoService.cambiarEstadoDePedido(cambiarEstadoDePedidoRequest, sesionDeUsuario);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/sugerencias")
	public Sugerencia generarSugerencia(final @RequestBody GenerarSugerenciaRequest generarSugerenciaRequest,
			final HttpServletRequest request, final HttpServletResponse response) {
		final SesionDeUsuario sesionDeUsuario = validarSesionDeUsuario(request, Roles.CrearSugerencia);
		return this.sugerenciaService.generarSugerencia(generarSugerenciaRequest, sesionDeUsuario);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/estadoDeSugerencia")
	public Sugerencia cambiarEstadoDeSugerencia(
			final @RequestBody CambiarEstadoDeSugerenciaRequest cambiarEstadoDeSugerenciaRequest,
			final HttpServletRequest request, final HttpServletResponse response) {
		final SesionDeUsuario sesionDeUsuario = validarSesionDeUsuario(request, Roles.CambiarEstadoDeSugerencia);
		return this.sugerenciaService.cambiarEstadoDeSugerencia(cambiarEstadoDeSugerenciaRequest, sesionDeUsuario);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/sugerenciasVigentes")
	public List<Sugerencia> obtenerSugerenciasVigentes(final HttpServletRequest request, final HttpServletResponse response) {
		return this.sugerenciaService.obtenerSugerenciasVigentes();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/reporteDePedidosRequest")
	public ReporteDePedidos generarReporteDePedidos(
			final @RequestBody GenerarReporteDePedidosRequest generarReporteDePedidosRequest,
			final HttpServletRequest request, final HttpServletResponse response) {
		validarSesionDeUsuario(request, Roles.GenerarReporteDePedidos);
		return this.pedidoService.generarReporteDePedidos(generarReporteDePedidosRequest);
	}
	
	private SesionDeUsuario validarSesionDeUsuario(final HttpServletRequest request, final Rol... roles) {
		return this.validadorDeSesionDeUsuarioService.validarSesionDeUsuario(request, new HashSet<>(Arrays.asList(roles)));
	}
	
	private SesionDeUsuario validarSesionParaLogoutDeUsuario(final HttpServletRequest request) {
		return this.validadorDeSesionDeUsuarioService.validarSesionDeUsuario(request, Roles.cualquierUsuario());
	}
}
