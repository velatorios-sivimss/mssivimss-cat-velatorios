package com.imss.sivimss.catvelatorios.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnviarDatosRequest {
	private Map<String, Object> datos;
}
