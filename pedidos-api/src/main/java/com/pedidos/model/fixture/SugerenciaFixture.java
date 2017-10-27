package com.pedidos.model.fixture;

import static com.pedidos.utils.DateUtils.currentDate;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pedidos.model.CategoriaItemDeMenu;
import com.pedidos.model.EstadoSugerencia;
import com.pedidos.model.ItemDeMenu;
import com.pedidos.model.Status;
import com.pedidos.model.Sugerencia;

@Component
public class SugerenciaFixture {
	
	private MenuFixture menuFixture;

	public SugerenciaFixture(final MenuFixture menuFixture) {
		this.menuFixture = menuFixture;
	}
	
	public Sugerencia comboPicada() {
		 Sugerencia comboPicada = Sugerencia.builder()
				.nombre("Combo Picada")
				.descripcion("Papas fritas + Picada + Cerveza Artesanal 1L")
				.precio(200D)
				.estado(EstadoSugerencia.Publicado)
				.cantidadDisponible(5)
				.fechaCreacion(currentDate())
				.fechaInicio(currentDate())
				.fechaFin(Date.from(Instant.now().plusSeconds(3600 * 2))) // 2 hours from now
				.status(Status.Active)
				.build();
		 comboPicada.setItemDeMenu(itemDeMenuParaSugerencia(comboPicada));
		 return comboPicada;
	}
	
	public Sugerencia comboPuraCerveza() {
		 Sugerencia comboPuraCerveza = Sugerencia.builder()
				.nombre("Combo pura cerveza")
				.descripcion("5 tipos distintos de Cerveza Artesanal (500cc cada una)")
				.precio(150D)
				.estado(EstadoSugerencia.Publicado)
				.cantidadDisponible(10)
				.fechaCreacion(currentDate())
				.fechaInicio(currentDate())
				.fechaFin(Date.from(Instant.now().plusSeconds(3600 * 2))) // 2 hours from now
				.status(Status.Active)
				.build();
		 comboPuraCerveza.setItemDeMenu(itemDeMenuParaSugerencia(comboPuraCerveza));
		 return comboPuraCerveza;
	}
	
	public List<Sugerencia> sugerencias() {
		return Arrays.asList(comboPicada(), comboPuraCerveza());
	}
	
	private ItemDeMenu itemDeMenuParaSugerencia(final Sugerencia sugerencia) {
		return menuFixture.itemDeMenu(sugerencia.getNombre(), sugerencia.getDescripcion(), CategoriaItemDeMenu.Sugerencia, sugerencia.getPrecio());
	}
}
