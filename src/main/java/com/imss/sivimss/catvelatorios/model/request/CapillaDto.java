package com.imss.sivimss.catvelatorios.model.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CapillaDto {
    private Long idCapilla;
    private String nombre;
    private Integer capacidad;
    private Double largo;
    private Double alto;
    private Double areaTotal;
    private Long idVelatorio;
    private String nombreVelatorio;
    private Boolean estatus;

}
