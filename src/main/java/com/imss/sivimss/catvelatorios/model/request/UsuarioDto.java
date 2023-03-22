package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDto {
	private Integer id;
	private Integer rol;
	private String nombre;
	private String correo;
}
