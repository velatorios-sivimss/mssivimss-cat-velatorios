package com.imss.sivimss.catvelatorios.model.response;

import com.imss.sivimss.catvelatorios.model.MedidasModel;
import lombok.Data;

@Data
public class ArticulosResponse {
    private int idArticulo;
    private int idCategoria;
    private int idTipoArticulo;
    private int idTipoMaterial;
    private int idTamanio;
    private int idClasificacion;
    private String descripcionModelo;
    private MedidasModel medidas;
    private boolean estatus;
}
