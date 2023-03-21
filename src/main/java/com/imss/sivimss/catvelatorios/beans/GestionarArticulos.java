package com.imss.sivimss.catvelatorios.beans;

import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.model.request.AgregarArticuloRequest;
import com.imss.sivimss.catvelatorios.model.request.AgregarMedidasRequest;
import com.imss.sivimss.catvelatorios.model.request.BuscarArticulosRequest;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.QueryHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@Slf4j
public class GestionarArticulos {


    public DatosRequest insertarArticulo(AgregarArticuloRequest request, AgregarMedidasRequest agm, UsuarioDto user) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        final QueryHelper q = new QueryHelper("INSERT INTO SVT_ARTICULO");
        q.agregarParametroValues("ID_CATEGORIA_ARTICULO", String.valueOf(request.getIdCategoria()));
        q.agregarParametroValues("ID_TIPO_ARTICULO", String.valueOf(request.getIdTipoArticulo()));
        q.agregarParametroValues("ID_TIPO_MATERIAL", String.valueOf(request.getIdTipoMaterial()));
        q.agregarParametroValues("ID_TAMANIO", String.valueOf(request.getIdTamanio()));
        q.agregarParametroValues("ID_CLASIFICACION_PRODUCTO", String.valueOf(request.getIdClasificacionProducto()));
        q.agregarParametroValues("DES_MODELO_ARTICULO", "'" + request.getModeloArticulo() + "'");
        q.agregarParametroValues("DES_ARTICULO", "'" + request.getDescripcionArticulo() + "'");
        q.agregarParametroValues("ID_PART_PRESUPUESTAL", String.valueOf(request.getIdPartidaPresupuestal()));
        q.agregarParametroValues("ID_CUENTA_PART_PRESU", String.valueOf(request.getIdCuentaContable()));
        q.agregarParametroValues("ID_PRODUCTOS_SERVICIOS", String.valueOf(request.getIdClaveSAT()));
        q.agregarParametroValues("CVE_ESTATUS", String.valueOf(1));
        q.agregarParametroValues("ID_USUARIO_ALTA", String.valueOf(user.getId()));
        q.agregarParametroValues("FEC_ALTA", "NOW()");
        String query = q.obtenerQueryInsertar() + " $$ " + insertArticuloMedida(agm);
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        parametro.put("separador","$$");
        parametro.put("replace","idTabla");
        dr.setDatos(parametro);
        return dr;
    }

    public String insertArticuloMedida(AgregarMedidasRequest agm) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        final QueryHelper q = new QueryHelper("INSERT INTO SVT_ARTICULO_MEDIDA");
        q.agregarParametroValues("ID_ARTICULO", "idTabla");
        q.agregarParametroValues("NUM_LARGO", agm.getLargo());
        q.agregarParametroValues("NUM_ANCHO", agm.getAncho());
        q.agregarParametroValues("NUM_ALTO", agm.getAlto());
        String query = q.obtenerQueryInsertar();
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return query;
    }

    public DatosRequest actualizarArticulo(AgregarArticuloRequest request, UsuarioDto user) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        final QueryHelper q = new QueryHelper("UPDATE SVT_ARTICULO");
        q.agregarParametroValues("ID_CATEGORIA_ARTICULO", String.valueOf(request.getIdCategoria()));
        q.agregarParametroValues("ID_TIPO_ARTICULO", String.valueOf(request.getIdTipoArticulo()));
        q.agregarParametroValues("ID_TIPO_MATERIAL", String.valueOf(request.getIdTipoMaterial()));
        q.agregarParametroValues("ID_TAMANIO", String.valueOf(request.getIdTamanio()));
        q.agregarParametroValues("ID_CLASIFICACION_PRODUCTO", String.valueOf(request.getIdClasificacionProducto()));
        q.agregarParametroValues("DES_MODELO_ARTICULO", "'" + request.getModeloArticulo() + "'");
        q.agregarParametroValues("DES_ARTICULO", "'" + request.getDescripcionArticulo() + "'");
        q.agregarParametroValues("ID_PART_PRESUPUESTAL", String.valueOf(request.getIdPartidaPresupuestal()));
        q.agregarParametroValues("ID_CUENTA_PART_PRESU", String.valueOf(request.getIdCuentaContable()));
        q.agregarParametroValues("ID_PRODUCTOS_SERVICIOS", String.valueOf(request.getIdClaveSAT()));
        q.agregarParametroValues("CVE_ESTATUS", String.valueOf(1));
        q.agregarParametroValues("ID_USUARIO_MODIFICA", String.valueOf(user.getId()));
        q.agregarParametroValues("FEC_ACTUALIZACION", "NOW()");
        q.addWhere("ID_ARTICULO =" + request.getIdArticulo());
        String query = q.obtenerQueryActualizar();
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }

    public DatosRequest actualizarArticuloMedida(AgregarMedidasRequest agm) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        final QueryHelper q = new QueryHelper("UPDATE SVT_ARTICULO_MEDIDA");
        q.agregarParametroValues("NUM_LARGO", agm.getLargo());
        q.agregarParametroValues("NUM_ANCHO", agm.getAncho());
        q.agregarParametroValues("NUM_ALTO", agm.getAlto());
        q.addWhere("ID_ARTICULO =" + agm.getIdArticulo());
        String query = q.obtenerQueryActualizar();
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }

    public DatosRequest cambiarEstatus(int idArticulo, UsuarioDto user) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        String query = "UPDATE SVT_ARTICULO SET CVE_ESTATUS=!CVE_ESTATUS, FEC_ACTUALIZACION=NOW() , FEC_BAJA=NOW(), ID_USUARIO_BAJA = '" + user.getId() + "'  WHERE ID_ARTICULO = " + idArticulo;
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }

    public DatosRequest buscarArticulos(DatosRequest request) {
        Gson json = new Gson();
        BuscarArticulosRequest br = json.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), BuscarArticulosRequest.class);
        String query = "";
        if (br.getNombreArticulo() != null) {
            query = "SELECT " +
                    "SA.ID_ARTICULO AS idArticulo, " +
                    "SA.ID_CATEGORIA_ARTICULO AS idCategoriaArticulo, " +
                    "CA.DES_CATEGORIA_ARTICULO AS categoriaArticulo, " +
                    "SA.ID_TIPO_ARTICULO AS idTipoArticulo, " +
                    "TA.DES_TIPO_ARTICULO AS tipoArticulo, " +
                    "SA.ID_TIPO_MATERIAL AS idTipoMaterial, " +
                    "TM.DES_TIPO_MATERIAL AS tipoMaterial, " +
                    "SA.ID_TAMANIO AS idTamanio, " +
                    "T.DES_TAMANIO AS tamanio, " +
                    "SA.ID_CLASIFICACION_PRODUCTO AS idClasificacionProducto, " +
                    "CP.DES_CLASIFICACION_PRODUCTO AS clasificacionProducto, " +
                    "SA.CVE_ESTATUS AS estatus, " +
                    "SA.DES_MODELO_ARTICULO AS modeloArticulo, " +
                    "SA.DES_ARTICULO AS desArticulo, " +
                    "SAM.NUM_LARGO AS largo, " +
                    "SAM.NUM_ANCHO AS ancho, " +
                    "SAM.NUM_ALTO AS alto, " +
                    "SA.ID_PART_PRESUPUESTAL AS idPartPresupuestal, " +
                    "PP.DES_PART_PRESUPUESTAL AS partPresupuestal, " +
                    "SA.ID_CUENTA_PART_PRESU AS idCuentaPartPresupuestal, " +
                    "CC.NUM_CUENTA_PART_PRESU AS numCuentaPartPresupuestal, " +
                    "SA.ID_PRODUCTOS_SERVICIOS AS idProductosServicios, " +
                    "CPS.DES_PRODUCTOS_SERVICIOS AS productoServicios " +
                    "FROM SVT_ARTICULO SA  " +
                    "INNER JOIN SVC_CATEGORIA_ARTICULO CA ON SA.ID_CATEGORIA_ARTICULO = CA.ID_CATEGORIA_ARTICULO  " +
                    "INNER JOIN SVC_TIPO_ARTICULO TA ON SA.ID_TIPO_ARTICULO = TA.ID_TIPO_ARTICULO " +
                    "INNER JOIN SVC_TIPO_MATERIAL TM ON SA.ID_TIPO_MATERIAL = TM.ID_TIPO_MATERIAL " +
                    "INNER JOIN SVC_TAMANIO T ON SA.ID_TAMANIO = T.ID_TAMANIO  " +
                    "INNER JOIN SVC_CLASIFICACION_PRODUCTO CP ON SA.ID_CLASIFICACION_PRODUCTO = CP.ID_CLASIFICACION_PRODUCTO " +
                    "INNER JOIN SVT_ARTICULO_MEDIDA SAM ON SA.ID_ARTICULO = SAM.ID_ARTICULO " +
                    "INNER JOIN SVC_PARTIDA_PRESUPUESTAL PP ON SA.ID_PART_PRESUPUESTAL = PP.ID_PART_PRESUPUESTAL  " +
                    "INNER JOIN SVC_CUENTA_PART_PRESU CC ON SA.ID_CUENTA_PART_PRESU = CC.ID_CUENTA_PART_PRESU " +
                    "INNER JOIN SVC_CLAVES_PRODUCTOS_SERVICIOS CPS ON SA.ID_PRODUCTOS_SERVICIOS = CPS.ID_PRODUCTOS_SERVICIOS  " +
                    "WHERE SA.DES_ARTICULO LIKE '%" + br.getNombreArticulo() + "%'";
            request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
            request.getDatos().remove("datos");
            return request;
        }
        query = "SELECT  " +
                "SA.ID_ARTICULO AS idArticulo, " +
                "SA.ID_CATEGORIA_ARTICULO AS idCategoriaArticulo, " +
                "CA.DES_CATEGORIA_ARTICULO AS categoriaArticulo, " +
                "SA.ID_TIPO_ARTICULO AS idTipoArticulo, " +
                "TA.DES_TIPO_ARTICULO AS tipoArticulo, " +
                "SA.ID_TIPO_MATERIAL AS idTipoMaterial, " +
                "TM.DES_TIPO_MATERIAL AS tipoMaterial, " +
                "SA.ID_TAMANIO AS idTamanio, " +
                "T.DES_TAMANIO AS tamanio, " +
                "SA.ID_CLASIFICACION_PRODUCTO AS idClasificacionProducto, " +
                "CP.DES_CLASIFICACION_PRODUCTO AS clasificacionProducto, " +
                "SA.CVE_ESTATUS AS estatus, " +
                "SA.DES_MODELO_ARTICULO AS modeloArticulo, " +
                "SA.DES_ARTICULO AS desArticulo, " +
                "SAM.NUM_LARGO AS largo, " +
                "SAM.NUM_ANCHO AS ancho, " +
                "SAM.NUM_ALTO AS alto, " +
                "SA.ID_PART_PRESUPUESTAL AS idPartPresupuestal, " +
                "PP.DES_PART_PRESUPUESTAL AS partPresupuestal, " +
                "SA.ID_CUENTA_PART_PRESU AS idCuentaPartPresupuestal, " +
                "CC.NUM_CUENTA_PART_PRESU AS numCuentaPartPresupuestal, " +
                "SA.ID_PRODUCTOS_SERVICIOS AS idProductosServicios, " +
                "CPS.DES_PRODUCTOS_SERVICIOS AS productoServicios " +
                "FROM SVT_ARTICULO SA  " +
                "INNER JOIN SVC_CATEGORIA_ARTICULO CA ON SA.ID_CATEGORIA_ARTICULO = CA.ID_CATEGORIA_ARTICULO  " +
                "INNER JOIN SVC_TIPO_ARTICULO TA ON SA.ID_TIPO_ARTICULO = TA.ID_TIPO_ARTICULO " +
                "INNER JOIN SVC_TIPO_MATERIAL TM ON SA.ID_TIPO_MATERIAL = TM.ID_TIPO_MATERIAL " +
                "INNER JOIN SVC_TAMANIO T ON SA.ID_TAMANIO = T.ID_TAMANIO  " +
                "INNER JOIN SVC_CLASIFICACION_PRODUCTO CP ON SA.ID_CLASIFICACION_PRODUCTO = CP.ID_CLASIFICACION_PRODUCTO " +
                "INNER JOIN SVT_ARTICULO_MEDIDA SAM ON SA.ID_ARTICULO = SAM.ID_ARTICULO " +
                "INNER JOIN SVC_PARTIDA_PRESUPUESTAL PP ON SA.ID_PART_PRESUPUESTAL = PP.ID_PART_PRESUPUESTAL  " +
                "INNER JOIN SVC_CUENTA_PART_PRESU CC ON SA.ID_CUENTA_PART_PRESU = CC.ID_CUENTA_PART_PRESU " +
                "INNER JOIN SVC_CLAVES_PRODUCTOS_SERVICIOS CPS ON SA.ID_PRODUCTOS_SERVICIOS = CPS.ID_PRODUCTOS_SERVICIOS ";
        log.info(query);
        request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
        request.getDatos().remove("datos");
        return request;
    }

    public DatosRequest validarRepetido(String desArticulo) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        String query = "SELECT * FROM SVT_ARTICULO  WHERE DES_ARTICULO = '" + desArticulo + "'";
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }
}