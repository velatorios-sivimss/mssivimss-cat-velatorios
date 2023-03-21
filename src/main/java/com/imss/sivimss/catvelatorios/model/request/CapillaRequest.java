package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CapillaRequest {
    private Long capillaId;
    @NotNull
    private String nombre;
    @NotNull
    private Integer capacidad;
    @NotNull
    private Double largo;
    @NotNull
    private Double alto;
    @NotNull
    private Double areaTotal;
    // todo - hay que mapear los datos del velatorio, buscar a la persona que esta haciendo esa parte
    @NotNull
    private Long velatorioId;
    @NotNull
    private Boolean estatus;

}
