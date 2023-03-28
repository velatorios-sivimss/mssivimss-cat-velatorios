package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imss.sivimss.catvelatorios.model.MedidasModel;
import lombok.Data;

@Data
public class AgregarArticuloRequest {
    @JsonProperty
    private int idArticulo;
    @JsonProperty
    private int  idCategoria;
    @JsonProperty
    private int  idTipoArticulo;
    @JsonProperty
    private int  idTipoMaterial;
    @JsonProperty
    private int  idTamanio;
    @JsonProperty
    private int  idClasificacionProducto;
    @JsonProperty
    private String modeloArticulo;
    @JsonProperty
    private String descripcionArticulo;
    @JsonProperty
    private MedidasModel medidas;
    @JsonProperty
    private Integer  idPartidaPresupuestal;
    @JsonProperty
    private Integer  idCuentaContable;
    @JsonProperty
    private Integer  idClaveSAT;

}
