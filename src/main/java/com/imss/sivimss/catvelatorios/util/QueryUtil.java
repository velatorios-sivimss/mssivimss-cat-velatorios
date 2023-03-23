package com.imss.sivimss.catvelatorios.util;

import java.util.*;

// todo - se pretende renombrar la clase para que lleve una semantica, algo como un factory o algo asi
public class QueryUtil {
    // constantes
    private static final String SELECT = "SELECT";
    private static final String SPACE = " ";
    private static final String FROM = "FROM";
    private static final String WHERE = "WHERE";
    private static final String LEFT_JOIN = "LEFT JOIN";
    private static final String ON = "ON";
    private static final String ASTERISKS = "*";

    // campos
    private final List<String> from = new ArrayList<>();
    private List<String> columnas = new ArrayList<>();
    private List<String> condiciones = new ArrayList<>();
    private Map<String, Object> parametros = new HashMap<>();
    private List<Join> joins = new ArrayList<>();
    private List<String> orderBy = new ArrayList<>();

    /**
     * El m&eacute;todo select, se tiene que invocar 2 veces, la primera es para crear una instancia de QueryUtil
     * y se llama sin pasar ning&uacute;n argumento, la segunda invocaci&oacute;n, en caso de ser necesario, se pasan
     * la lista de columnas que se van a usar para la consulta.
     *
     * @param columnas
     * @return
     */
    public QueryUtil select(String... columnas) {
        this.columnas = Arrays.asList(columnas);
        return this;
    }

//    public QueryUtil from(String tabla) {
//        this.tabla = tabla;
//        return this;
//    }

    /**
     * todo - Falta implementar, ese es de ejercicio
     *
     * @param tabla
     * @return
     */
    public QueryUtil from(String tabla) {
        // todo - agregar las tablas para la consulta
        this.from.add(tabla);
        return this;
    }

    /**
     * todo - agregar documentacion
     * todo - hacer pruebas
     * @param condiciones
     * @return
     */
    public QueryUtil where(String... condiciones) {
        if (this.condiciones == null) {
            this.condiciones = new ArrayList<>();
        }

        this.condiciones.addAll(Arrays.asList(condiciones));
        return this;
    }

    /**
     * todo - agregar documentacion
     *
     * @param nombre
     * @param valor
     * @return
     */
    public QueryUtil setParameter(String nombre, Object valor) {
        if (this.parametros == null) {
            this.parametros = new HashMap<>();
        }

        this.parametros.put(nombre, valor);
        return this;
    }

    /**
     * todo - agregar documentacion
     *
     * @param columna
     * @return
     */
    public QueryUtil orderBy(String columna) {
        this.orderBy.add(columna);
        return this;
    }

    /**
     * todo - falta agregar validaciones a las condiciones
     *
     * @param tabla
     * @param on
     * @return
     */
    public QueryUtil leftJoin(String tabla, String on) {
        joins.add(new Join(LEFT_JOIN, tabla, on));
        return this;
    }

    /**
     * Regrea el query que se construy&oacute;.
     *
     * @return
     */
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(SELECT).append(SPACE);

        if (columnas.isEmpty()) {
            stringBuilder.append(ASTERISKS).append(SPACE);
        } else {
            stringBuilder.append(String.join(", ", columnas)).append(SPACE);
        }

        stringBuilder.append("FROM").append(SPACE);
        stringBuilder.append(from.get(0)).append(SPACE);

        if (!joins.isEmpty()) {
            for (Join join : joins) {
                stringBuilder.append(SPACE);
                stringBuilder.append(join.getTipo());
                stringBuilder.append(SPACE);
                stringBuilder.append(join.getTabla());
                stringBuilder.append(SPACE).append(ON).append(SPACE);
                stringBuilder.append(join.getOn());
                stringBuilder.append(SPACE);
            }
        }

        if (!condiciones.isEmpty()) {
            stringBuilder.append(" WHERE ");
            for (int index = 0; index < condiciones.size(); index++) {
                String condicion = condiciones.get(index);
                if (condicion.contains(":")) {
                    String nombreParametro = condicion.substring(condicion.indexOf(":") + 1);
                    Object value = parametros.get(nombreParametro);
                    condicion = condicion.replace(":" + nombreParametro, value.toString());
                } else {
                    condicion = condicion.trim();
                }
                stringBuilder.append(condicion);

                if (index != (condiciones.size() - 1)) {
                    stringBuilder.append(SPACE).append("AND").append(SPACE);
                }
            }
            stringBuilder.append(String.join(" AND ", condiciones)).append(" ");
        }

        if (!orderBy.isEmpty()) {
            stringBuilder.append(" order by ").append(String.join(", ", orderBy)).append(" ");
        }

        return stringBuilder.toString();
    }
}
