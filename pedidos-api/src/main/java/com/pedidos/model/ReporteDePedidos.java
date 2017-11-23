package com.pedidos.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class ReporteDePedidos {
	
	private static final String minTag = "min", maxTag = "max";

	private List<ItemReporteDePedidos> items = new LinkedList<>();
	
	public ReporteDePedidos agregarItem(final ItemReporteDePedidos item) {
		this.items.add(item);
		return this;
	}
	
	public ReporteDePedidos marcarMinYMaxPorCategorias() {
		final CategoriaItemDeMenu[] categorias = CategoriaItemDeMenu.values();
		for (final CategoriaItemDeMenu categoria : categorias) {
			final List<ItemReporteDePedidos> itemsCategoria = this.items.stream()
					.filter(item -> item.getCategoria().equals(categoria.name()))
					.collect(Collectors.toList());
			itemsCategoria.stream().reduce((first, next) -> next).map(lastItem -> tag(lastItem, minTag));
			itemsCategoria.stream().findFirst().map(item -> tag(item, maxTag));
		}
		return this;
	}
	
	private ItemReporteDePedidos tag(final ItemReporteDePedidos item, final String tag) {
		item.setTag(tag);
		return item;
	}
}
