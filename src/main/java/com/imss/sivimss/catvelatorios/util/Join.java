package com.imss.sivimss.catvelatorios.util;

import lombok.Getter;

@Getter
public class Join {
    private final String tipo;
    private final String tabla;
    private final String on;

    public Join(String tipo, String tabla, String on) {
        this.tipo = tipo;
        this.tabla = tabla;
        this.on = on;
    }
}
