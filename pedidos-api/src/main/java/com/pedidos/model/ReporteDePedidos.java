package com.pedidos.model;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class ReporteDePedidos {

	private List<ItemReporteDePedidos> items = new LinkedList<>();
	
	public ReporteDePedidos agregarItem(final ItemReporteDePedidos item) {
		this.items.add(item);
		return this;
	}
}
