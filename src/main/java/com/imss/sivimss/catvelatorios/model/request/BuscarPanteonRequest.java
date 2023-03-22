package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BuscarPanteonRequest {
    @JsonProperty
    private String nombrePanteon;
    @JsonProperty
    private String pagina;
    @JsonProperty
    private String tamanio;
}
