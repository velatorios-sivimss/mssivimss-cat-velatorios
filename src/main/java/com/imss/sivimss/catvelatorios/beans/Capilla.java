package com.imss.sivimss.catvelatorios.beans;

import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.model.request.CapillaDto;
import com.imss.sivimss.catvelatorios.model.request.FiltrosCapillas;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.QueryHelper;
import com.imss.sivimss.catvelatorios.util.SelectQueryUtil;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

public class Capilla {

    /**
     * Inserta una capilla con los campos necesarios para su creacion
     *
     * @return
     */
    public DatosRequest insertar(CapillaDto capillaDto, UsuarioDto usuario) {
        DatosRequest datos = new DatosRequest();
        Map<String, Object> parametrosRequest = new HashMap<>();
        QueryHelper queryHelper = new QueryHelper("INSERT INTO SVC_CAPILLA");

        queryHelper.agregarParametroValues("NOM_CAPILLA", "'" + capillaDto.getNombre() + "'");
        queryHelper.agregarParametroValues("CAN_CAPACIDAD", String.valueOf(capillaDto.getCapacidad()));
        queryHelper.agregarParametroValues("NUM_LARGO", String.valueOf(capillaDto.getLargo()));
        queryHelper.agregarParametroValues("NUM_ALTO", String.valueOf(capillaDto.getAlto()));
        queryHelper.agregarParametroValues("ID_VELATORIO", String.valueOf(capillaDto.getIdVelatorio()));
        queryHelper.agregarParametroValues("CVE_ESTATUS", String.valueOf(1));
        queryHelper.agregarParametroValues("ID_USUARIO_ALTA", String.valueOf(usuario.getIdUsuario()));
        queryHelper.agregarParametroValues("FEC_ALTA", "CURRENT_TIMESTAMP");
        queryHelper.agregarParametroValues("ID_USUARIO_MODIFICA", "null");
        queryHelper.agregarParametroValues("FEC_ACTUALIZACION", "null");
        queryHelper.agregarParametroValues("ID_USUARIO_BAJA", "null");
        queryHelper.agregarParametroValues("FEC_BAJA", "null");

        String query = queryHelper.obtenerQueryInsertar();
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
        queryHelper.agregarParametroValues("ID_USUARIO_MODIFICA", String.valueOf(usuario.getIdUsuario()));
        queryHelper.agregarParametroValues("FEC_ACTUALIZACION", "CURRENT_TIMESTAMP");
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
                "ID_USUARIO_BAJA = " + usuario.getIdUsuario() +
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

        SelectQueryUtil selectQueryUtil = new SelectQueryUtil();

        selectQueryUtil
                .select("ID_CAPILLA AS idCapilla",
                        "NOM_CAPILLA AS nombre",
                        "CAN_CAPACIDAD AS capacidad",
                        "NUM_LARGO AS largo",
                        "NUM_ALTO AS alto",
                        "CVE_ESTATUS AS estatus",
                        "velatorio.ID_VELATORIO AS idVelatorio",
                        "velatorio.NOM_VELATORIO AS nombreVelatorio")
                .from("SVC_CAPILLA capilla")
                .join("SVC_VELATORIO velatorio", "capilla.ID_VELATORIO = velatorio.ID_VELATORIO");

        if (filtros.getIdCapilla() != null) {
            selectQueryUtil.where("capilla.ID_CAPILLA = :idCapilla")
                    .setParameter("idCapilla", filtros.getIdCapilla());
        }
        if (filtros.getNombreCapilla() != null) {
            selectQueryUtil.where("capilla.NOM_CAPILLA = :nombreCapilla")
                    .setParameter("nombreCapilla", filtros.getNombreCapilla());
        }
        if (filtros.getIdVelatorio() != null) {
            selectQueryUtil.where("capilla.ID_VELATORIO = :idVelatorio")
                    .setParameter("idVelatorio", filtros.getIdVelatorio());
        }

        selectQueryUtil.orderBy("ID_CAPILLA");

        Map<String, Object> parametros = new HashMap<>();
        String query = selectQueryUtil.build();
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
        SelectQueryUtil selectQueryUtil = new SelectQueryUtil();
        selectQueryUtil.select("ID_CAPILLA AS idCapilla",
                        "NOM_CAPILLA AS nombre",
                        "CAN_CAPACIDAD AS capacidad",
                        "NUM_LARGO AS largo",
                        "NUM_ALTO AS alto",
                        "CVE_ESTATUS AS estatus",
                        "velatorio.ID_VELATORIO AS idVelatorio",
                        "velatorio.NOM_VELATORIO AS nombreVelatorio ")
                .from("SVC_CAPILLA capilla")
                .join("SVC_VELATORIO velatorio", "capilla.ID_VELATORIO = velatorio.ID_VELATORIO")
                .where("ID_CAPILLA = :idCapilla")
                .setParameter("idCapilla", idCapilla)
                .orderBy("ID_CAPILLA");

        Map<String, Object> parametros = new HashMap<>();
        String query = selectQueryUtil.build();

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
