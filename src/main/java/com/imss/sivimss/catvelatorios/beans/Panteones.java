package com.imss.sivimss.catvelatorios.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imss.sivimss.catvelatorios.model.request.BuscarPanteonRequest;
import com.imss.sivimss.catvelatorios.model.request.PanteonRequest;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.QueryHelper;
import com.imss.sivimss.catvelatorios.util.SelectQueryUtil;
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

    private static final String INDESTATUS="IND_ESTATUS";
    private static final String FECBAJA="FEC_BAJA";
    private static final String IDUSUARIOBAJA="ID_USUARIO_BAJA";
    private static final String FECHAACTUAL="CURRENT_TIMESTAMP()";
    
    public DatosRequest insertarPanteon(PanteonRequest request, UsuarioDto user) {
        final QueryHelper q = new QueryHelper("INSERT INTO SVT_PANTEON");
        DatosRequest dr = this.panteonComun(request, q);
        Map<String, Object> parametro = new HashMap<>();

        q.agregarParametroValues("ID_USUARIO_ALTA", String.valueOf(user.getIdUsuario()));
        q.agregarParametroValues("FEC_ALTA", FECHAACTUAL);
        q.agregarParametroValues("FEC_ACTUALIZACION", null);
        q.agregarParametroValues(FECBAJA, null);
        q.agregarParametroValues("ID_USUARIO_MODIFICA", null);
        q.agregarParametroValues(IDUSUARIOBAJA, null);
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

        q.agregarParametroValues("FEC_ACTUALIZACION", FECHAACTUAL);
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
        q.agregarParametroValues(INDESTATUS, String.valueOf(status));
        q.agregarParametroValues(FECBAJA, FECHAACTUAL);
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
        SelectQueryUtil queryUtil = new SelectQueryUtil();
        queryUtil.select("ID_PANTEON",
                        "NOM_PANTEON",
                        "DES_CALLE",
                        "NUM_INT",
                        "ID_CODIGO_POSTAL",
                        "DES_COLONIA",
                        "NUM_TELEFONO",
                        "NOM_CONTACTO",
                        "IND_ESTATUS",
                        "ID_USUARIO_ALTA",
                        "FEC_ALTA",
                        "FEC_ACTUALIZACION",
                        "FEC_BAJA",
                        "ID_USUARIO_MODIFICA",
                        "ID_USUARIO_BAJA",
                        "ID_DELEGACION")
                .from("SVT_PANTEON SA");
        if (buscarRequest.getNombrePanteon() != null) {
            queryUtil.where("SA.NOM_PANTEON = :nomPanteon")
                    .setParameter("nomPanteon", buscarRequest.getNombrePanteon());
        }
        query = queryUtil.build();
        logger.info(query);
        request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
        request.getDatos().remove("datos");
        return request;
    }

    public DatosRequest existePanteon(String nombrePanteon) {
        SelectQueryUtil queryUtil = new SelectQueryUtil();
        queryUtil.select("ID_PANTEON",
                        "NOM_PANTEON",
                        "DES_CALLE",
                        "NUM_INT",
                        "ID_CODIGO_POSTAL",
                        "DES_COLONIA",
                        "NUM_TELEFONO",
                        "NOM_CONTACTO",
                        "IND_ESTATUS",
                        "ID_USUARIO_ALTA",
                        "FEC_ALTA",
                        "FEC_ACTUALIZACION",
                        "FEC_BAJA",
                        "ID_USUARIO_MODIFICA",
                        "ID_USUARIO_BAJA",
                        "ID_DELEGACION")
                .from("SVT_PANTEON SA")
                .where("SA.NOM_PANTEON = :nomPanteon")
                .setParameter("nomPanteon", nombrePanteon);
        String query = queryUtil.build();
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
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
        q.agregarParametroValues(INDESTATUS, String.valueOf(1));
        dr.setDatos(parametro);
        return dr;
    }
}
