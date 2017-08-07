package com.pedidos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.model.Menu;
import com.pedidos.model.Rol;
import com.pedidos.model.Roles;
import com.pedidos.model.fixture.MenuFixture;
import com.pedidos.repository.ItemDeMenuRepository;
import com.pedidos.repository.MenuRepository;
import com.pedidos.repository.RolRepository;
import com.pedidos.repository.UsuarioRepository;

@RestController
public class DBInitializationController {

	private MenuFixture menuFixture;
	private MenuRepository menuRepository;
	private ItemDeMenuRepository itemDeMenuRepository;
	private UsuarioRepository usuarioRepository;
	private RolRepository rolRepository;

	public DBInitializationController(
			final MenuFixture menuFixture, 
			final MenuRepository menuRepository,
			final ItemDeMenuRepository itemDeMenuRepository,
			final UsuarioRepository usuarioRepository,
			final RolRepository rolRepository) {
		this.menuFixture = menuFixture;
		this.menuRepository = menuRepository;
		this.itemDeMenuRepository = itemDeMenuRepository;
		this.usuarioRepository = usuarioRepository;
		this.rolRepository = rolRepository;
	}
	
	@GetMapping(value = "/initDB")
	public ResponseEntity<String> initDB() {
		// Persist default Menu in Database
		final Menu menuDefault = menuFixture.menuDefault();
		this.itemDeMenuRepository.save(menuDefault.getItems());
		this.menuRepository.save(menuDefault);
		
		final List<Rol> rolesDeUsuarios = Roles.all();
		this.rolRepository.save(rolesDeUsuarios);
	
		return new ResponseEntity<String>("DB initialization with test data was successful", HttpStatus.OK);
	}
}
