package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CapillaRequest {
    private Long capillaId;
    private String nombre;
    private Integer capacidad;
    private Double largo;
    private Double alto;
    private Double areaTotal;
    // todo - hay que mapear los datos del velatorio, buscar a la persona que esta haciendo esa parte
    private Long velatorioId;
    private Boolean estatus;

}
