package com.pedidos.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserActionException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UnauthorizedUserActionException() { }

	public UnauthorizedUserActionException(final String message) {
		super(message);
	}

	public UnauthorizedUserActionException(final String message, Throwable cause) {
		super(message, cause);
	}
}
