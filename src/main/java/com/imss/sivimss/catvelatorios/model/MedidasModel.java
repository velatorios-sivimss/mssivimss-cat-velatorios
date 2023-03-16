package com.imss.sivimss.catvelatorios.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MedidasModel {
    @JsonProperty
    private Double largo;
    @JsonProperty
    private Double ancho;
    @JsonProperty
    private Double alto;
}
