package com.imss.sivimss.catvelatorios.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@JsonIgnoreType(value = true)
public class VelatorioResponse {
	
	@JsonProperty(value = "idVelatorio")
	private Integer ID_VELATORIO;
	
	@JsonProperty(value = "nomVelatorio")
	private String NOM_VELATORIO;
	
	@JsonProperty(value = "nomRespoSanitario")
	private String NOM_RESPO_SANITARIO;
	
	@JsonProperty(value = "cveAsignacion")
	private Integer CVE_ASIGNACION;
	
	@JsonProperty(value = "desCalle")
	private String DES_CALLE;
	
	@JsonProperty(value = "numExterior")
	private Integer NUM_EXTERIOR;
	
	@JsonProperty(value = "idCodigoPostal")
	private Integer ID_CODIGO_POSTAL;
	
	@JsonProperty(value = "desColonia")
	private Integer DES_COLONIA;
	
	@JsonProperty(value = "numTelefono")
	private String NUM_TELEFONO;
	
	@JsonProperty(value = "indEstatus")
	private Integer IND_ESTATUS;

}

