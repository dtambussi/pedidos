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
				muzzarelitasEnMilanesa(),
				papasEspeciales(),
				milanesaDeLomoConGuarinición(),
				bifeDeChorizcoConGuarnicion(),
				hamburguesaCompletaConFritas(),
				flanCasero(),
				helado(),
				agua(),
				gaseosa(),
				cervezaDeLaCasa(),
				cafeEnJarrito(),
				cafeConLeche(),
				te()
		);
	}

	public ItemDeMenu muzzarelitasEnMilanesa() {
		return itemDeMenu("Muzzarelitas en milanesa", "", CategoriaItemDeMenu.Entradas, 50D);
	}
	
	public ItemDeMenu papasEspeciales() {
		return itemDeMenu("Papas especiales", "Papas fritas con 4 quesos y panceta", CategoriaItemDeMenu.Entradas, 80D);
	}
	
	public ItemDeMenu milanesaDeLomoConGuarinición() {
		return itemDeMenu("Milanesa de lomo con guarnición", "Con puré, ensalada o fritas",
				CategoriaItemDeMenu.PlatosPrincipales, 100D);
	}
	
	public ItemDeMenu bifeDeChorizcoConGuarnicion() {
		return itemDeMenu("Bife de chorizo con guarnición", "Con puré, ensalada o fritas",
				CategoriaItemDeMenu.PlatosPrincipales, 130D);
	}
	
	public ItemDeMenu hamburguesaCompletaConFritas() {
		return itemDeMenu("Hamburguesa completa con fritas", "Con tomate, lechuga, queso y jamón",
				CategoriaItemDeMenu.PlatosPrincipales, 100D);
	}
	
	public ItemDeMenu flanCasero() {
		return itemDeMenu("Flan casero", "DDL o crema sin costo adicional", CategoriaItemDeMenu.Postres, 50D);
	}
	
	public ItemDeMenu helado() {
		return itemDeMenu("Helado", "Bocha a elección (DDL, frutilla, vainilla o chocolate)",
				CategoriaItemDeMenu.Postres, 50D);
	}
	
	public ItemDeMenu gaseosa() {
		return itemDeMenu("Gaseosa 500 cc", "", CategoriaItemDeMenu.Bebidas, 45D);
	}
	
	public ItemDeMenu agua() {
		return itemDeMenu("Agua 500 cc", "", CategoriaItemDeMenu.Bebidas, 30D);
	}
	
	public ItemDeMenu cervezaDeLaCasa() {
		return itemDeMenu("Pinta de cerveza artesanal", "", CategoriaItemDeMenu.Bebidas, 40D);
	}
	
	public ItemDeMenu cafeEnJarrito() {
		return itemDeMenu("Café en jarrito", "", CategoriaItemDeMenu.Cafeteria, 30D);
	}
	
	public ItemDeMenu cafeConLeche() {
		return itemDeMenu("Taza de café con leche", "", CategoriaItemDeMenu.Cafeteria, 30D);
	}
	
	public ItemDeMenu te() {
		return itemDeMenu("Taza de te", "",  CategoriaItemDeMenu.Cafeteria, 25D);
	}
	
	public ItemDeMenu itemDeMenu(final String nombre, final String descripcion, final CategoriaItemDeMenu categoria,
			final Double precio) {
		return ItemDeMenu.builder()
				.nombre(nombre)
				.descripcion(descripcion)
				.categoria(categoria)
				.precio(precio)
				.fechaCreacion(currentDate())
				.fechaUltimaModificacion(currentDate())
				.status(Status.Active)
				.build();
	}
}
