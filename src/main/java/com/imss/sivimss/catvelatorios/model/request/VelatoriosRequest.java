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
public class VelatoriosRequest {
	
	private Integer idVelatorio;
	private Integer idDelegacion;
	private Integer idDomicilio;
	private Integer idAdmin;
	private String nomVelatorio;
	private String nomRespoSanitario;
	private Integer cveAsignacion;
	private String calle;
	private String colonia;
	private Integer numExterior;
	private Integer cp;
	private String tel;
	private String correo;
	private Boolean estatus;
	
}