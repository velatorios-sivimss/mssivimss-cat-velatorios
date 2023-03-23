package com.imss.sivimss.catvelatorios.beans;

import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.model.request.CapillaDto;
import com.imss.sivimss.catvelatorios.model.request.FiltrosCapillas;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.QueryHelper;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

public class Capilla {

    private static final String WHERE = "WHERE ";

    /**
     * Inserta una capilla con los campos necesarios para su creacion
     *
     * @return
     */
    public DatosRequest insertar(CapillaDto capillaDto, UsuarioDto usuario) {
        DatosRequest datos = new DatosRequest();
        Map<String, Object> parametrosRequest = new HashMap<>();
        // todo - falta hacer la funcionalidad para que se auto-genere el id del sistema
        QueryHelper queryHelper = new QueryHelper("INSERT INTO SVC_CAPILLA");
        // todo - ver como evitar el uso de las comillas para evitar errores
        queryHelper.agregarParametroValues("NOM_CAPILLA", "'" + capillaDto.getNombre() + "'");
        queryHelper.agregarParametroValues("CAN_CAPACIDAD", String.valueOf(capillaDto.getCapacidad()));
        queryHelper.agregarParametroValues("NUM_LARGO", String.valueOf(capillaDto.getLargo()));
        queryHelper.agregarParametroValues("NUM_ALTO", String.valueOf(capillaDto.getAlto()));
        queryHelper.agregarParametroValues("ID_VELATORIO", String.valueOf(capillaDto.getIdVelatorio()));
        queryHelper.agregarParametroValues("CVE_ESTATUS", String.valueOf(1));
        queryHelper.agregarParametroValues("ID_USUARIO_ALTA", String.valueOf(usuario.getId()));
        queryHelper.agregarParametroValues("FEC_ALTA", "CURRENT_TIMESTAMP");
        queryHelper.agregarParametroValues("ID_USUARIO_MODIFICA", "null");
        queryHelper.agregarParametroValues("FEC_ACTUALIZACION", "null");
        queryHelper.agregarParametroValues("ID_USUARIO_BAJA", "null");
        queryHelper.agregarParametroValues("FEC_BAJA", "null");

        String query = queryHelper.obtenerQueryInsertar();
        System.out.println(query);
        parametrosRequest.put(
                AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes())
        );
        datos.setDatos(parametrosRequest);
        return datos;
    }

    /**
     * @param capillaDto
     * @param usuario
     * @return
     */
    public DatosRequest actualizar(CapillaDto capillaDto, UsuarioDto usuario) {
        DatosRequest datos = new DatosRequest();

        QueryHelper queryHelper = new QueryHelper("UPDATE SVC_CAPILLA");

        queryHelper.agregarParametroValues("NOM_CAPILLA", "'" + capillaDto.getNombre() + "'");
        queryHelper.agregarParametroValues("CAN_CAPACIDAD", String.valueOf(capillaDto.getCapacidad()));
        queryHelper.agregarParametroValues("NUM_LARGO", String.valueOf(capillaDto.getLargo()));
        queryHelper.agregarParametroValues("NUM_ALTO", String.valueOf(capillaDto.getAlto()));
        queryHelper.agregarParametroValues("ID_VELATORIO", String.valueOf(capillaDto.getIdVelatorio()));
        queryHelper.agregarParametroValues("ID_USUARIO_MODIFICA", String.valueOf(usuario.getId()));
        queryHelper.agregarParametroValues("FEC_ACTUALIZACION", "CURRENT_TIMESTAMP");
        // todo - se puede detallar mas el where para que reciba mas parametros que nos diga el tipo de comparacion etc.
        queryHelper.addWhere("ID_CAPILLA = " + capillaDto.getIdCapilla());

        String query = queryHelper.obtenerQueryActualizar();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put(
                AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes())
        );
        datos.setDatos(parametros);
        return datos;
    }

    public DatosRequest cambiarEstatus(Long idCapilla, UsuarioDto usuario) {
        DatosRequest datos = new DatosRequest();
        String query = "UPDATE SVC_CAPILLA SET " +
                "CVE_ESTATUS = !CVE_ESTATUS, " +
                "FEC_BAJA = CURRENT_TIMESTAMP, " +
                "ID_USUARIO_BAJA = " + usuario.getId() +
                " WHERE ID_CAPILLA = " + idCapilla;

        Map<String, Object> parametros = new HashMap<>();

        parametros.put(
                AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes())
        );
        datos.setDatos(parametros);

        return datos;
    }

    /**
     * @param request
     * @return
     */
    public DatosRequest consultarCapillas(DatosRequest request) {
        Gson gson = new Gson();
        FiltrosCapillas filtros = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), FiltrosCapillas.class);

        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        queryBuilder.append("ID_CAPILLA AS capillaId, ");
        queryBuilder.append("NOM_CAPILLA AS nombre, ");
        queryBuilder.append("CAN_CAPACIDAD AS capacidad, ");
        queryBuilder.append("NUM_LARGO AS largo, ");
        queryBuilder.append("NUM_ALTO AS alto, ");
        queryBuilder.append("CVE_ESTATUS AS estatus, ");
        queryBuilder.append("velatorio.ID_VELATORIO AS velatorioId, ");
        queryBuilder.append("velatorio.NOM_VELATORIO AS nombreVelatorio ");
        queryBuilder.append("FROM SVC_CAPILLA capilla ");
        queryBuilder.append("JOIN SVC_VELATORIO velatorio ON capilla.ID_VELATORIO = velatorio.ID_VELATORIO ");

        if (filtros.getIdCapilla() != null) {
            queryBuilder.append(WHERE);
            queryBuilder.append("capilla.id_capilla = ").append(filtros.getIdCapilla());
        }
        if (filtros.getNombreCapilla() != null) {

            if (queryBuilder.toString().contains(WHERE)) {
                queryBuilder.append(" capilla.NOM_CAPILLA = ").append(filtros.getNombreCapilla());
            } else {
                queryBuilder.append(WHERE)
                        .append(" capilla.NOM_CAPILLA = ").append(filtros.getNombreCapilla());
            }

        }
        if (filtros.getIdVelatorio() != null) {
            if (queryBuilder.toString().contains(WHERE)) {
                queryBuilder.append("capilla.ID_VELATORIO = ").append(filtros.getIdVelatorio());
            } else {
                queryBuilder.append(WHERE)
                        .append("capilla.ID_VELATORIO = ").append(filtros.getIdVelatorio());
            }
        }

//        queryHelper.obtenerQueryActualizar()
//        String query = "SELECT " +
//                "ID_CAPILLA AS capillaId, " +
//                "NOM_CAPILLA AS nombre, " +
//                "CAN_CAPACIDAD AS capacidad, " +
//                "NUM_LARGO AS largo, " +
//                "NUM_ALTO AS alto, " +
//                "CVE_ESTATUS AS estatus, " +
//                "velatorio.ID_VELATORIO AS velatorioId, " +
//                "velatorio.NOM_VELATORIO AS nombreVelatorio " +
//                "FROM SVC_CAPILLA capilla " +
//                "JOIN SVC_VELATORIO velatorio ON capilla.ID_VELATORIO = velatorio.ID_VELATORIO " +
//                "WHERE " +
//                "(" + filtros.getIdVelatorio() + " IS NULL OR capilla.ID_VELATORIO = " + filtros.getIdVelatorio() + ") " +
//                "AND (" + filtros.getIdCapilla() + " IS NULL OR capilla.ID_CAPILLA = " + filtros.getIdCapilla() + ") " +
//                "AND (" + filtros.getNombreCapilla() + " IS NULL OR capilla.NOM_CAPILLA = " + filtros.getNombreCapilla() + ") " +
//                "GROUP BY ID_CAPILLA";
        Map<String, Object> parametros = new HashMap<>();
        String query = queryBuilder.toString();
        System.out.println("********************************");
        System.out.println(query);
        parametros.put(
                AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes())
        );
        request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
        request.getDatos().remove("datos");
        return request;
    }

    /**
     * Crea la consulta para recuperar una capilla por su identificador
     *
     * @param idCapilla
     * @return
     */
    public DatosRequest buscarCapilla(Long idCapilla) {
        DatosRequest datos = new DatosRequest();
        String query = "SELECT " +
                "ID_CAPILLA AS idCapilla, " +
                "NOM_CAPILLA AS nombre, " +
                "CAN_CAPACIDAD AS capacidad, " +
                "NUM_LARGO AS largo, " +
                "NUM_ALTO AS alto, " +
                "CVE_ESTATUS AS estatus, " +
                "velatorio.ID_VELATORIO AS idVelatorio, " +
                "velatorio.NOM_VELATORIO AS nombreVelatorio " +
                "FROM SVC_CAPILLA capilla " +
                "JOIN SVC_VELATORIO velatorio ON capilla.ID_VELATORIO = velatorio.ID_VELATORIO " +
                WHERE +
                "ID_CAPILLA = " + idCapilla +
                " GROUP BY ID_CAPILLA";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put(AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes()));
        datos.setDatos(parametros);
        return datos;
    }

    public DatosRequest consultarCatalogoCapillas() {
        DatosRequest datos = new DatosRequest();
        Map<String, Object> parametros = new HashMap<>();
        String query = "SELECT " +
                "ID_CAPILLA AS idCapilla, " +
                "NOM_CAPILLA AS nombre " +
                "FROM SVC_CAPILLA";
        parametros.put(AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes()));
        datos.setDatos(parametros);
        return datos;
    }
}
