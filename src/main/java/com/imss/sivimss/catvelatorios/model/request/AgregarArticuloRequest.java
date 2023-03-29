package com.imss.sivimss.catvelatorios.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imss.sivimss.catvelatorios.model.MedidasModel;
import lombok.Data;

@Data
public class AgregarArticuloRequest {
    @JsonProperty
    private Integer idArticulo;
    @JsonProperty
    private Integer  idCategoria;
    @JsonProperty
    private Integer  idTipoArticulo;
    @JsonProperty
    private Integer  idTipoMaterial;
    @JsonProperty
    private Integer  idTamanio;
    @JsonProperty
    private Integer  idClasificacionProducto;
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
