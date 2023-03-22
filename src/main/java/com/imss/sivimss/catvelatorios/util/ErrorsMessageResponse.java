package com.imss.sivimss.catvelatorios.util;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
/**
 * Clase ErrorsMessageResponse para regresar la respuesta de las execpeciones
 *
 * @author Pablo Nolasco
 * @puesto dev
 * @date 24 nov. 2022
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({ "error", "codigo", "mensaje", "datos" })
public class ErrorsMessageResponse {

	private Date fecha;

	private String mensaje;

	private String datos;

	private long codigo;

	private boolean error;

	public ErrorsMessageResponse(Date timestamp, long codigo, String mensaje, String detalles) {
		super();
		this.fecha = timestamp;
		this.error = true;
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.datos = detalles;

	}

	public long getCodigo() {
		return codigo;
	}

	public boolean isError() {
		return error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getDatos() {
		return datos;
	}
}
