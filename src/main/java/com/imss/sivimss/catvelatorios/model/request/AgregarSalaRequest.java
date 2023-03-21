package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AgregarSalaRequest {
    @JsonProperty
    private int idSala;
    @JsonProperty
    private String nombreSala;
    @JsonProperty
    private String tipoSala;
    @JsonProperty
    private int capacidadSala;
    @JsonProperty
    private int idVelatorio;
}
