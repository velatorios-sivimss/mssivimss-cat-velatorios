package com.imss.sivimss.catvelatorios.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imss.sivimss.catvelatorios.constants.PanteonesConstants;
import com.imss.sivimss.catvelatorios.model.request.BuscarPanteonRequest;
import com.imss.sivimss.catvelatorios.model.request.PanteonRequest;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.QueryHelper;
import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Panteones {
    private static final Logger logger = LogManager.getLogger(Panteones.class);

    public DatosRequest insertarPanteon(PanteonRequest request, UsuarioDto user) {
        final QueryHelper q = new QueryHelper("INSERT INTO SVT_PANTEON");
        DatosRequest dr = this.panteonComun(request, q);
        Map<String, Object> parametro = new HashMap<>();

        q.agregarParametroValues("ID_USUARIO_ALTA", String.valueOf(user.getIdUsuario()));
        q.agregarParametroValues("FEC_ALTA", PanteonesConstants.FECHAACTUAL);
        q.agregarParametroValues("FEC_ACTUALIZACION", null);
        q.agregarParametroValues(PanteonesConstants.FECBAJA, null);
        q.agregarParametroValues("ID_USUARIO_MODIFICA", null);
        q.agregarParametroValues(PanteonesConstants.IDUSUARIOBAJA, null);
        q.agregarParametroValues("ID_DELEGACION", String.valueOf(request.getIdDelegacion()));
        String query = q.obtenerQueryInsertar();
        logger.info(query);
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }

    public DatosRequest modificarPanteon(PanteonRequest request, UsuarioDto user) {
        final QueryHelper q = new QueryHelper("UPDATE INTO SVT_PANTEON");
        DatosRequest dr = this.panteonComun(request, q);
        Map<String, Object> parametro = new HashMap<>();

        q.agregarParametroValues("FEC_ACTUALIZACION", PanteonesConstants.FECHAACTUAL);
        q.agregarParametroValues("ID_DELEGACION", String.valueOf(request.getIdDelegacion()));
        q.addWhere("ID_PANTEON =" + request.getIdPanteon());
        String query = q.obtenerQueryActualizar();
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }

    public DatosRequest cambiarEstatus(Integer idPanteon, Integer status,UsuarioDto user) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        final QueryHelper q = new QueryHelper("UPDATE INTO SVT_PANTEON");
        q.agregarParametroValues(PanteonesConstants.INDESTATUS, String.valueOf(status));
        q.agregarParametroValues(PanteonesConstants.FECBAJA, PanteonesConstants.FECHAACTUAL);
        q.addWhere("ID_PANTEON =" + idPanteon);
        String query = q.obtenerQueryActualizar();
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }

    public DatosRequest buscarPanteones(DatosRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getDatos());
        BuscarPanteonRequest buscarRequest = mapper.readValue(jsonResult,BuscarPanteonRequest.class);
        logger.info("pagina: {}", buscarRequest.getPagina());
        logger.info("tamanio: {}", buscarRequest.getTamanio());
        String query = "";
        if (buscarRequest.getNombrePanteon() != null) {
            query = "SELECT * FROM SVT_PANTEON SA WHERE SA.NOM_PANTEON LIKE '%" + buscarRequest.getNombrePanteon() + "%'";
            request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
            request.getDatos().remove("datos");
            return request;
        }
        query = "SELECT * FROM SVT_PANTEON SA WHERE SA.NOM_PANTEON";
        logger.info(query);
        request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
        request.getDatos().remove("datos");
        return request;
    }

    public DatosRequest existePanteon(String nombrePanteon) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        String query = "SELECT * FROM SVT_PANTEON  WHERE NOM_PANTEON = '" + nombrePanteon + "'";
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }

    private DatosRequest panteonComun(PanteonRequest request, final QueryHelper q){
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        q.agregarParametroValues("NOM_PANTEON", "'" + request.getNombrePanteon()  + "'");
        q.agregarParametroValues("DES_CALLE", "'" + request.getDescripcionCalle()  + "'");
        q.agregarParametroValues("NUM_EXT", String.valueOf(request.getNumeroExt()));
        q.agregarParametroValues("NUM_INT", String.valueOf(request.getNumeroInt()));
        q.agregarParametroValues("ID_CODIGO_POSTAL", String.valueOf(request.getIdCodigoPostal()));
        q.agregarParametroValues("DES_COLONIA", "'" + request.getDescripcionColonia() + "'");
        q.agregarParametroValues("NUM_TELEFONO", "'" + request.getNumeroTelefono() + "'");
        q.agregarParametroValues("NOM_CONTACTO", "'" + request.getNombreContacto()  + "'");
        q.agregarParametroValues(PanteonesConstants.INDESTATUS, String.valueOf(1));
        dr.setDatos(parametro);
        return dr;
    }
}
