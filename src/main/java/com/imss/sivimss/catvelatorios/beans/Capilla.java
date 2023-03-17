package com.imss.sivimss.catvelatorios.beans;

import com.imss.sivimss.catvelatorios.model.request.CapillaDto;
import com.imss.sivimss.catvelatorios.model.request.FiltrosCapillas;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.QueryHelper;

import javax.xml.bind.DatatypeConverter;

public class Capilla {
    private CapillaDto capilla;

    /**
     * Inserta una capilla con los campos necesarios para su creacion
     *
     * @return
     */
    public DatosRequest insertar(CapillaDto capillaDto, UsuarioDto usuario) {
        DatosRequest datos = new DatosRequest();
//        Map<String, Object> parametrosRequest = new HashMap<>();
        // todo - falta hacer la funcionalidad para que se auto-genere el id del sistema
        QueryHelper queryHelper = new QueryHelper("INSERT INTO SVC_CAPILLAS");
        queryHelper.agregarParametroValues("NOM_CAPILLA", this.capilla.getNombre());
        queryHelper.agregarParametroValues("CAN_CAPILLA", String.valueOf(capillaDto.getCapacidad()));
        queryHelper.agregarParametroValues("NUM_LARGO", String.valueOf(capillaDto.getLargo()));
        queryHelper.agregarParametroValues("NUM_ALTO", String.valueOf(capillaDto.getAlto()));
        queryHelper.agregarParametroValues("ID_VELATORIO", String.valueOf(capillaDto.getVelatorioId()));
        queryHelper.agregarParametroValues("CVE_ESTAUS", String.valueOf(1));
        queryHelper.agregarParametroValues("FEC_ALTA", "NOW()");
        queryHelper.agregarParametroValues("ID_USUARIO_REGISTRA", String.valueOf(usuario.getId()));

        String query = queryHelper.obtenerQueryInsertar();
//        parametrosRequest
        datos.getDatos().put(
                AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes())
        );
        return datos;
    }

    /**
     * @param capillaDto
     * @param usuario
     * @return
     */
    public DatosRequest actualizar(CapillaDto capillaDto, UsuarioDto usuario) {
        DatosRequest datos = new DatosRequest();
        QueryHelper queryHelper = new QueryHelper("update svc_capillas");

        queryHelper.agregarParametroValues("NOM_CAPILLA", this.capilla.getNombre());
        queryHelper.agregarParametroValues("CAN_CAPILLA", String.valueOf(capillaDto.getCapacidad()));
        queryHelper.agregarParametroValues("NUM_LARGO", String.valueOf(capillaDto.getLargo()));
        queryHelper.agregarParametroValues("NUM_ALTO", String.valueOf(capillaDto.getAlto()));
        queryHelper.agregarParametroValues("ID_VELATORIO", String.valueOf(capillaDto.getVelatorioId()));
        queryHelper.agregarParametroValues("FEC_ACTUALIZACION", "NOW()");
        queryHelper.agregarParametroValues("ID_USUARIO_MODIFICA", String.valueOf(usuario.getId()));
        // todo - se puede detallar mas el where para que reciba mas parametros que nos diga el tipo de comparacion etc.
        queryHelper.addWhere("ID_CAPILLA = " + capillaDto.getCapillaId());

        String query = queryHelper.obtenerQueryActualizar();
        datos.getDatos().put(
                AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes())
        );
        return datos;
    }

    public DatosRequest cambiarEestatus(CapillaDto capillaDto, UsuarioDto usuario) {
        DatosRequest datos = new DatosRequest();
//        QueryHelper queryHelper = new QueryHelper("update svc_capillas");
        String query = "UPDATE SVC_CAPILLAS SET " +
                "CVE_ESTATUS = !CVE_ESTATUS, " +
                "FEC_BAJA = now(), " +
                "ID_USUARIO_BAJA = " + usuario.getId() +
                "WHERE ID_CAPILLA = " + capillaDto.getCapillaId();

        datos.getDatos().put(
                AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes())
        );
        return datos;
    }

    public DatosRequest consultarCapillas(FiltrosCapillas filtros) {
        DatosRequest datos = new DatosRequest();
        String query = "SELECT " +
                "ID_CAPILLA AS capillaId, " +
                "NOM_CAPILLA AS nombre, " +
                "CAN_CAPACIDAD AS capacidad, " +
                "NUM_LARGO AS largo, " +
                "NUM_ALTO AS alto, " +
                "CVE_ESTATUS AS estatus, " +
                "velatorio.ID_VELATORIO AS velatorioId, " +
                "velatorio.NOM_VELATORIO AS nombreVelatorio " +
                "FROM SVC_CAPILLAS AS capilla" +
                "INNER JOIN SVC_VELATORIOS AS velatorio " +
                "WHERE " +
                "(" + filtros.getIdVelatorio() + " IS NULL OR velatorio.ID_VELATORIO = " + filtros.getIdVelatorio() + ")" +
                "AND (" + filtros.getIdCapilla() + " IS NULL OR capilla.ID_CAPILLA = " + filtros.getIdCapilla() + ")" +
                "AND (" + filtros.getNombreCapilla() + " IS NULL OR capilla.NOM_CAPILLA = " + filtros.getNombreCapilla() + ")";
        System.out.println("*********** query ************");
        System.out.println(query);

        datos.getDatos().put(
                AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes())
        );
        return datos;
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
                "NUM_LARGO AS largo " +
                "NUM_ALTO AS alto, " +
                "CVE_ESTATUS AS estatus, " +
                "velatorio.ID_VELATORIO AS idVelatorio, " +
                "velatorio.NOM_VELATORIO AS nombreVelatorio, " +
                "FROM SVC_CAPILLAS AS capilla " +
                "INNER JOIN SVC_VELATORIOS AS velatorio " +
                "WHERE " +
                "ID_CAPILLA = " + idCapilla;
        datos.getDatos().put(
                AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes())
        );
        System.out.println("***********************************");
        System.out.println(query);
        return datos;
    }

    public DatosRequest consultarCatalogoCapillas() {
        DatosRequest datos = new DatosRequest();
        String query = "SELECT " +
                "ID_CAPILLA AS idCapilla, " +
                "NOM_CAPILLA AS nombre " +
                "FROM SVC_CAPILLAS";
        datos.getDatos().put(
                AppConstantes.QUERY,
                DatatypeConverter.printBase64Binary(query.getBytes())
        );
        return datos;
    }
}
