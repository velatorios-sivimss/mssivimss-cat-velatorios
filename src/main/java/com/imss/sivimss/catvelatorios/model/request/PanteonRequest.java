package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class PanteonRequest {
    @JsonProperty
    private Integer idPanteon;
    @JsonProperty
    private String  nombrePanteon;
    @JsonProperty
    private String  descripcionCalle;
    @JsonProperty
    private Integer  numeroExt;
    @JsonProperty
    private Integer  numeroInt;
    @JsonProperty
    private Integer  idCodigoPostal;
    @JsonProperty
    private String descripcionColonia;
    @JsonProperty
    private String numeroTelefono;
    @JsonProperty
    private String nombreContacto;
    @JsonProperty
    private Integer indEstatus;
    @JsonProperty
    private Integer  idDelegacion;
}
