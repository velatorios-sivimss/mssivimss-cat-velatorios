package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@JsonIgnoreType(value = true)
public class BuscarVelatorioRequest {
	
	private String nomVelatorio;
	private String idDelegacion;
	private String idVelatorio;
	private Boolean estatus;
	private Integer idCatalogo;
	private Integer cp;
	private String tamanio;
	private String pagina;
	
}