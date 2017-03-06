package com.pedidos.model.fixture;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pedidos.model.CategoriaItemDeMenu;
import com.pedidos.model.ItemDeMenu;
import com.pedidos.model.Menu;
import com.pedidos.model.Status;

@Component
public class MenuFixture {
	
	public Menu menuDefault() {
		return Menu.builder()
				.nombre("Menú 2017")
				.status(Status.Active)
				.vigente(true)
				.fechaCreacion(now())
				.fechaUltimaModificacion(now())
				.items(itemsMenuDefault())
				.build();
	}
	
	public List<ItemDeMenu> itemsMenuDefault() {
		return Arrays.asList(
				milanesaConPure()
		);
	}
	
	public ItemDeMenu milanesaConPure() {
		return itemDeMenu("Milanesa con Puré", CategoriaItemDeMenu.PlatosPrincipales, 100D);
	}
	
	private ItemDeMenu itemDeMenu(final String nombre, final CategoriaItemDeMenu categoria, final Double precio) {
		return ItemDeMenu.builder()
				.nombre("Milanesa con Puré")
				.categoria(CategoriaItemDeMenu.PlatosPrincipales)
				.precio(100D)
				.fechaCreacion(now())
				.fechaUltimaModificacion(now())
				.status(Status.Active)
				.build();
	}
	
	private Date now() { return new Date(); }
}
