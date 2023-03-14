package com.imss.sivimss.catvelatorios.model.request;

import java.util.Date;

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
	private String nomVelatorio;
	private String nomRespoSanitario;
	private Integer cveAsignacion;
	private String desCalle;
	private Integer numExterior;
	private Integer idCodigoPostal;
	private String numTelefono;
	private Integer indEstatus;
	private Integer idUsuarioAlta;
	private Date fecAlta;
	private Date fecActualizacion;
	private Date fecBaja;
	private String idUsuarioModifica;
	private Integer idUsuarioBaja;
	private Integer idDelegacion;
	

}