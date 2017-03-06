package com.pedidos.controller;

import lombok.Data;

@Data
public class ErrorResponse {
	
	private String message;
		
	public ErrorResponse() {}
	
	public ErrorResponse(final String message) {
		this.message = message;
	}
	
}