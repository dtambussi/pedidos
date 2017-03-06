package com.pedidos.service;

import org.springframework.stereotype.Component;

import com.pedidos.model.Menu;
import com.pedidos.repository.MenuRepository;

@Component
public class MenuService {
	
	private MenuRepository menuRepository;

	public MenuService(final MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}
	
	public Menu getCurrentMenu() {
		return this.menuRepository.findAll().stream().findFirst().get();
	}
}
