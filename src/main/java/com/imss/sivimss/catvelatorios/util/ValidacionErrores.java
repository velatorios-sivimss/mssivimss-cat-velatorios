package com.imss.sivimss.catvelatorios.util;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * Clase ValidacionErrores para regresar la respuesta de las excepeciones, cuando se valida las propiedades del dto
 *
 * @author Pablo Nolasco
 * @puesto dev
 * @date 24 nov. 2022
 */
public class ValidacionErrores {

	private Map<String, String> errores;

	private String mensaje;

	private long codigo;

	private boolean error;

	private Date fecha;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public ValidacionErrores(Map<String, String> errores, Date fecha) {
		super();
		this.errores = errores;
		this.fecha = fecha;
		this.mensaje = "Error en la petición";
		this.codigo = HttpStatus.BAD_REQUEST.value();
		this.error=true;
	}
	

}
