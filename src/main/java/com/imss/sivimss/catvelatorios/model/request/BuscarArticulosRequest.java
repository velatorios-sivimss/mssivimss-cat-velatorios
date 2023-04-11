package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuscarArticulosRequest {
    @JsonProperty
    private String nivel;
    @JsonProperty
    private String nombreArticulo;
    @JsonProperty
    private String pagina;
    @JsonProperty
    private String tamanio;
}
