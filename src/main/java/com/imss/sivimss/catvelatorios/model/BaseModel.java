package com.imss.sivimss.catvelatorios.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseModel {
    private Date fechaAlta;
    private Date fechaActualizacion;
    private Date fechaBaja;
    private String claveUsuario;

}
