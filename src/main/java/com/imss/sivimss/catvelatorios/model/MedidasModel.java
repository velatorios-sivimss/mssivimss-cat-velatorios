package com.imss.sivimss.catvelatorios.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MedidasModel {
    @JsonProperty
    private Integer largo;
    @JsonProperty
    private Integer ancho;
    @JsonProperty
    private Integer alto;
}
