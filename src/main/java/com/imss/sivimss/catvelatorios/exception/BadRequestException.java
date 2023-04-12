package com.imss.sivimss.catvelatorios.exception;

import org.springframework.http.HttpStatus;

/**
 * Clase principal para manejar las excepciones BadRequestException de la aplicacion
 *
 * @author Pablo Nolasco
 * @puesto dev
 * @date 24 nov. 2022
 */
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus codigo;
	
	private final String mensaje;

	private final boolean error;

	public BadRequestException(HttpStatus codigo, String mensaje) {
		super(mensaje);
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.error=true;
	}

	public HttpStatus getEstado() {
		return codigo;
	}


	public String getMensaje() {
		return mensaje;
	}

}