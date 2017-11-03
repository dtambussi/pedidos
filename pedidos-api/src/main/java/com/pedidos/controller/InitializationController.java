package com.pedidos.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.factory.UsuarioFactory;
import com.pedidos.model.InfoAdicionalUsuario;
import com.pedidos.model.Menu;
import com.pedidos.model.Rol;
import com.pedidos.model.Roles;
import com.pedidos.model.Sugerencia;
import com.pedidos.model.Usuario;
import com.pedidos.model.fixture.MenuFixture;
import com.pedidos.model.fixture.SugerenciaFixture;
import com.pedidos.model.fixture.UsuarioFixture;
import com.pedidos.repository.InfoAdicionalUsuarioRepository;
import com.pedidos.repository.ItemDeMenuRepository;
import com.pedidos.repository.MenuRepository;
import com.pedidos.repository.RolRepository;
import com.pedidos.repository.SugerenciaRepository;
import com.pedidos.repository.UsuarioRepository;

@RestController
public class InitializationController {

	private MenuFixture menuFixture;
	private MenuRepository menuRepository;
	private ItemDeMenuRepository itemDeMenuRepository;
	private UsuarioRepository usuarioRepository;
	private RolRepository rolRepository;
	private UsuarioFixture usuarioFixture;
	private InfoAdicionalUsuarioRepository infoAdicionalUsuarioRepository;
	private SugerenciaFixture sugerenciaFixture;
	private SugerenciaRepository sugerenciaRepository;

	public InitializationController(
			final MenuFixture menuFixture,
			final SugerenciaFixture sugerenciaFixture,
			final UsuarioFixture usuarioFixture,
			final MenuRepository menuRepository,
			final SugerenciaRepository sugerenciaRepository,
			final ItemDeMenuRepository itemDeMenuRepository,
			final UsuarioRepository usuarioRepository,
			final InfoAdicionalUsuarioRepository infoAdicionalUsuarioRepository,
			final RolRepository rolRepository,
			final UsuarioFactory usuarioFactory) {
		this.menuFixture = menuFixture;
		this.sugerenciaFixture = sugerenciaFixture;
		this.usuarioFixture = usuarioFixture;
		this.menuRepository = menuRepository;
		this.sugerenciaRepository = sugerenciaRepository;
		this.itemDeMenuRepository = itemDeMenuRepository;
		this.usuarioRepository = usuarioRepository;
		this.infoAdicionalUsuarioRepository = infoAdicionalUsuarioRepository;
		this.rolRepository = rolRepository;
	}

	@GetMapping(value = "/initDB")
	@Transactional
	public ResponseEntity<String> initDB() {
		// Persist default Menu in Database
		final Menu menuDefault = menuFixture.menuDefault();
		this.itemDeMenuRepository.save(menuDefault.getItems());
		this.menuRepository.save(menuDefault);
		// Persist sugerencias
		List<Sugerencia> sugerencias = this.sugerenciaFixture.sugerencias();
		sugerencias.stream().forEach(sugerencia -> this.itemDeMenuRepository.save(sugerencia.getItemDeMenu()));
		this.sugerenciaRepository.save(sugerencias);
		// Persist default roles
		Roles.all().stream().map(rol -> this.rolRepository.save(rol)).collect(Collectors.toList());
		// Persist default users
		this.crearUsuario(this.usuarioFixture.camarera_sabrina_mesa());
		this.crearUsuario(this.usuarioFixture.barman_esteban_copas());
		this.crearUsuario(this.usuarioFixture.cocinero_carlos_parrilla());
		this.crearUsuario(this.usuarioFixture.dueño_alejandro_moneta());
		this.crearUsuario(this.usuarioFixture.cliente_natali_perez());
	
		return new ResponseEntity<String>("DB initialization with test data was successful", HttpStatus.OK);
	}
	
	private Usuario crearUsuario(final Usuario usuario) {
		if (!CollectionUtils.isEmpty(usuario.getRoles())) {
			final Set<String> nombresDeRolesAsociadosAUsuario = usuario.getRoles().stream().map(rol -> rol.getNombre())
					.collect(Collectors.toSet());
			final Set<Rol> rolesAsociadosAUsuario = this.rolRepository.findAllByNombreIn(nombresDeRolesAsociadosAUsuario);
			usuario.setRoles(rolesAsociadosAUsuario);
		}
		if (usuario.getInfoAdicional() != null) {
			final InfoAdicionalUsuario infoAdicionalUsuario = this.infoAdicionalUsuarioRepository.save(usuario.getInfoAdicional());
			usuario.setInfoAdicional(infoAdicionalUsuario);
		}
		return this.usuarioRepository.save(usuario);
	}
}
