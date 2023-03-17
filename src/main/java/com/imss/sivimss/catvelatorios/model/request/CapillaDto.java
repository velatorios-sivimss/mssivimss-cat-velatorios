package com.imss.sivimss.catvelatorios.model.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CapillaDto {
    private Long idCapilla;
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
    private Long idVelatorio;
    private String nombreVelatorio;
    @NotNull
    private Boolean estatus;

}
