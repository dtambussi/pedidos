package com.pedidos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemReporteDePedidos {
	
	String categoria, nombre, tag;
	Integer cantidad;
}
