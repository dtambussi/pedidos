package com.pedidos.validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pedidos.controller.error.PedidoInvalidoException;
import com.pedidos.model.ItemDeMenu;
import com.pedidos.model.ItemDePedido;
import com.pedidos.model.Pedido;
import com.pedidos.model.Sugerencia;
import com.pedidos.service.SugerenciaService;

@Component
public class PedidoValidator {
	
	private static final String STOCK_MISSING_MSG = "La sugerencia no tiene unidades disponibles para satisfacer la cantidad solicitada en el pedido";
	private static final String STOCK_REMAINING_MSG = " En este momento quedan: ";
	private static final String PROMO_EXPIRED_MSG = "La sugerencia %s ya no corresponde a una promo válida";
	
	private SugerenciaService sugerenciaService;

	public PedidoValidator(final SugerenciaService sugerenciaService) {
		this.sugerenciaService = sugerenciaService;
	}
	
	public void validarPedido(final Pedido pedido) {
		final List<ItemDePedido> itemsDePedidoAsociadosASugerencias = pedido.obtenerItemsAsociadosASugerencias();
		if (itemsDePedidoAsociadosASugerencias.isEmpty()) { 
			validarVigenciaYStockParaSugerenciasDelPedido(itemsDePedidoAsociadosASugerencias); 
		}
	}
	
	private void validarVigenciaYStockParaSugerenciasDelPedido(final List<ItemDePedido> itemsDePedidoAsociadosASugerencias) {
		final List<Sugerencia> sugerenciasVigentes = this.sugerenciaService.obtenerSugerenciasVigentes();
		final Map<ItemDeMenu, Integer> detalleDeSugerenciasEnPedido = obtenerDetalleDeSugerencias(itemsDePedidoAsociadosASugerencias);
		for(final ItemDeMenu itemDeMenu : detalleDeSugerenciasEnPedido.keySet()) { 
			final Sugerencia sugerencia = this.sugerenciaService.obtenerPorItemDeMenuAsociado(itemDeMenu);
			validarSugerenciaVigente(sugerencia, sugerenciasVigentes);
			int cantidadRemanente = sugerencia.getCantidadDisponible() - sugerencia.getCantidadConsumida();
			int cantidadSolicitadaEnPedido = detalleDeSugerenciasEnPedido.get(itemDeMenu);
			int cantidadConsumidaAceptandoPedido = cantidadSolicitadaEnPedido + sugerencia.getCantidadConsumida();
			boolean existeStockParaCantidadSolicitada = sugerencia.getCantidadDisponible() >= cantidadConsumidaAceptandoPedido;
			if (!existeStockParaCantidadSolicitada) {
				throw new PedidoInvalidoException(STOCK_MISSING_MSG + STOCK_REMAINING_MSG + cantidadRemanente);
			}
		}
	}
	
	private void validarSugerenciaVigente(final Sugerencia sugerenciaAsociadaAPedido, final List<Sugerencia> sugerenciasVigentes) {
		if (!sugerenciasVigentes.contains(sugerenciaAsociadaAPedido)) {
			throw new PedidoInvalidoException(String.format(PROMO_EXPIRED_MSG, sugerenciaAsociadaAPedido.getNombre()));
		}
	}
	
	private Map<ItemDeMenu, Integer> obtenerDetalleDeSugerencias(final List<ItemDePedido> itemsDePedidoAsociadosASugerencias) {
		final Map<ItemDeMenu, Integer> detalleDeSugerenciasDelPedido = new LinkedHashMap<>();
		for(final ItemDePedido itemDePedido : itemsDePedidoAsociadosASugerencias) {
			final ItemDeMenu itemDeMenu = itemDePedido.getItemDeMenu();
			int cantidadSolicitada = detalleDeSugerenciasDelPedido.putIfAbsent(itemDeMenu, 0);
			detalleDeSugerenciasDelPedido.put(itemDeMenu, cantidadSolicitada + itemDePedido.getCantidad());
		}
		return detalleDeSugerenciasDelPedido;
	}
}
