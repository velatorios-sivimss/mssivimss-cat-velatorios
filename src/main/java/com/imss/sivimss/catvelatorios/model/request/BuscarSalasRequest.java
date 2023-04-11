package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BuscarSalasRequest {
    @JsonProperty
    private Integer idVelatorio;
    @JsonProperty
    private String nombreSala;
    @JsonProperty
    private Integer idTipoSala;
}
