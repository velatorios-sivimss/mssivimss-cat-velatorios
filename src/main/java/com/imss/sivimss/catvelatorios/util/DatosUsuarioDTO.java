package com.imss.sivimss.catvelatorios.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

@Getter
@JsonRootName(value = "DatosUsuario")
public class DatosUsuarioDTO {
	@JsonProperty
	public String rol;

	@JsonProperty
	public String matricula;
	
	@JsonProperty
	public Integer idOoad;
	
	@JsonProperty
	public String cveDepartamento;


}
