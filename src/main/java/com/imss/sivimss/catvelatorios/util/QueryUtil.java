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
    private static final String JOIN = "JOIN";
    private static final String ON = "ON";
    private static final String ASTERISKS = "*";
    private static final String COLON = ":";

    // campos
    private final List<String> tablas = new ArrayList<>();
    private List<String> columnas = new ArrayList<>();
    private List<String> condiciones = new ArrayList<>();
    private Map<String, Object> parametros = new HashMap<>();
    private List<Join> joins = new ArrayList<>();
    private List<String> orderBy = new ArrayList<>();

    /**
     * La funci&oacute;n <b>{@code select()}</b>, se tiene que invocar 2 veces, la primera es para crear una instancia de
     * <b>QueryUtil</b> y se llama sin pasar ning&uacute;n argumento, la segunda invocaci&oacute;n, en caso de ser
     * necesario, se pasan la lista de columnas que se van a usar para la consulta.
     * <p>
     * Los valores que recibe la funci&oacute;n puede ir de 1 a N, son valores separados por comas y en caso de
     * que se requieran todos los campos, la funci&oacute;n puede ir vac&iacte;a. Por ejemplo:
     * <p>
     * - <b>{@code select("columna_1 as id")}</b>
     * <p>
     * - <b>{@code select("columna_1 as id", "columna_2 as nombre", ...)}</b>
     * <p>
     * - <b>{@code select()}</b>
     *
     * @param columnas Lista de columnas, dichas columnas representan los valores que se van a recuperar
     *                 de la consulta.
     * @return Regresa la misma instancia para que se le puedan agregar m&aacute;s funciones.
     */
    public QueryUtil select(String... columnas) {
        this.columnas = Arrays.asList(columnas);
        return this;
    }

    /**
     * Agrega la tabla o tablas necesarias para la consulta que se est&eacute; armando.
     * <p>
     * Se puede agregar, por ejemplo:
     * <p>
     * - Para solo una tabla: <b>{@code from("USUARIO as usuario")}</b>
     * <p>
     * - Para varias tablas: <b>{@code from("USUARIO as usuario", "ROL as rol")}</b>
     *
     * @param tabla Es una cadena que representa la o las tablas a las que va a realizar la consulta
     * @return Regresa la misma instancia para que se puedan anidar las otras funciones
     */
    public QueryUtil from(String... tabla) {
        this.tablas.addAll(Arrays.asList(tabla));
        return this;
    }

    /**
     * La funci&oacute;n `where` se usa para agregar condiciones, estas pueden ser agregadas separadas por comas.
     * La sentencia where de sql se agrega hasta que se manda llamar la funci&oacute;n build
     *
     * @param condiciones Lista de condiciones que se van a evaluar en el query
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
     * Agrega par&aacute;metros para que se pueda hacer la sustituci&oacute;n de dicho elemento para armar el query
     * con los valores que se agreguen en el mapa de par&aacute;metros.
     *
     * @param nombre
     * @param valor
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    public QueryUtil setParameter(String nombre, Object valor) {
        if (this.parametros == null) {
            this.parametros = new HashMap<>();
        }

        this.parametros.put(nombre, valor);
        return this;
    }

    /**
     * Agrega la sentencia SQL para ordenar la consulta.
     *
     * @param columna
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    public QueryUtil orderBy(String columna) {
        this.orderBy.add(columna);
        return this;
    }

    /**
     * Agrega la sentencia de LEFT JOIN para hacer consultas con otras tablas.
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
     * Agrega la sentencia JOIN para hacer consultas usando otras tablas.
     *
     * @param tabla
     * @param on
     * @return
     */
    public QueryUtil join(String tabla, String on) {
        joins.add(new Join(JOIN, tabla, on));
        return this;
    }

    /**
     * Regresa el query que se construy&oacute;.
     * <p>
     * todo - agregar validaciones de las partes que deben de estar obligatoriamente
     *
     * @return
     */
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(SELECT).append(SPACE);

        agregarColumnas(stringBuilder);

        stringBuilder.append(FROM).append(SPACE);
        stringBuilder.append(String.join(", ", tablas)).append(SPACE);

        agregarJoins(stringBuilder);

        agregarCondiciones(stringBuilder);

        addOrderBy(stringBuilder);

        return stringBuilder.toString();
    }

    private void addOrderBy(StringBuilder stringBuilder) {
        if (!orderBy.isEmpty()) {
            stringBuilder.append(" order by ").append(String.join(", ", orderBy)).append(" ");
        }
    }

    private void agregarCondiciones(StringBuilder stringBuilder) {
        if (!condiciones.isEmpty()) {
            stringBuilder.append(SPACE).append(WHERE).append(SPACE);
            for (int index = 0; index < condiciones.size(); index++) {
                String condicion = condiciones.get(index);
                if (condicion.contains(COLON)) {
                    String nombreParametro = condicion.substring(condicion.indexOf(COLON) + 1);
                    Object value = parametros.get(nombreParametro);
                    condicion = condicion.replace(COLON + nombreParametro, value.toString());
                } else {
                    condicion = condicion.trim();
                }
                stringBuilder.append(condicion).append(SPACE);

                if (index != (condiciones.size() - 1)) {
                    stringBuilder.append(SPACE).append("AND").append(SPACE);
                }
            }
        }
    }

    private void agregarJoins(StringBuilder stringBuilder) {
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
    }

    private void agregarColumnas(StringBuilder stringBuilder) {
        if (columnas.isEmpty()) {
            stringBuilder.append(ASTERISKS).append(SPACE);
        } else {
            stringBuilder.append(String.join(", ", columnas)).append(SPACE);
        }
    }
}
