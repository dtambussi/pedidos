package com.pedidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.model.Menu;
import com.pedidos.model.fixture.MenuFixture;
import com.pedidos.repository.ItemDeMenuRepository;
import com.pedidos.repository.MenuRepository;

@RestController
public class DBInitializationController {

	private MenuFixture menuFixture;
	private MenuRepository menuRepository;
	private ItemDeMenuRepository itemDeMenuRepository;

	public DBInitializationController(final MenuFixture menuFixture, final MenuRepository menuRepository,
			final ItemDeMenuRepository itemDeMenuRepository) {
		this.menuFixture = menuFixture;
		this.menuRepository = menuRepository;
		this.itemDeMenuRepository = itemDeMenuRepository;
	}
	
	@GetMapping(value = "/initDB")
	public ResponseEntity<String> initDB() {
		// Persist default Menu in Database
		final Menu menuDefault = menuFixture.menuDefault();
		menuDefault.getItems().stream().map(item -> this.itemDeMenuRepository.save(item));
		this.menuRepository.save(menuDefault);
	
		return new ResponseEntity<String>("DB initialization with test data was successful", HttpStatus.OK);
	}
}
