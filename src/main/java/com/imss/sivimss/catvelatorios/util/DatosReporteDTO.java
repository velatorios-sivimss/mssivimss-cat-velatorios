package com.imss.sivimss.catvelatorios.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosReporteDTO {
	private Map<String, Object> datos;
	private String nombreReporte;
	private String tipoReporte;

	public DatosReporteDTO(Map<String, Object> datos, String nombreReporte, String tipoReporte) {
		this.datos = datos;
		this.nombreReporte = nombreReporte;
		this.tipoReporte = tipoReporte;
	}
}