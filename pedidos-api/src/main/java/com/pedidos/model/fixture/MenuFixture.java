package com.pedidos.model.fixture;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pedidos.model.CategoriaItemDeMenu;
import com.pedidos.model.ItemDeMenu;
import com.pedidos.model.Menu;
import com.pedidos.model.Status;

import static com.pedidos.utils.DateUtils.currentDate;

@Component
public class MenuFixture {
	
	public Menu menuDefault() {
		return Menu.builder()
				.nombre("Menú 2017")
				.status(Status.Active)
				.vigente(true)
				.fechaCreacion(currentDate())
				.fechaUltimaModificacion(currentDate())
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
				.nombre(nombre)
				.categoria(categoria)
				.precio(precio)
				.fechaCreacion(currentDate())
				.fechaUltimaModificacion(currentDate())
				.status(Status.Active)
				.build();
	}
}
