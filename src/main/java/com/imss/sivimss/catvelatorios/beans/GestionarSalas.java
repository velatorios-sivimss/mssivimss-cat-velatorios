package com.imss.sivimss.catvelatorios.beans;

import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.model.request.AgregarSalaRequest;
import com.imss.sivimss.catvelatorios.model.request.BuscarSalasRequest;
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
public class GestionarSalas {
    public DatosRequest insertarSala(AgregarSalaRequest request, UsuarioDto user) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        final QueryHelper q = new QueryHelper("INSERT INTO SVC_SALA");
        q.agregarParametroValues("NOM_SALA", "'" + request.getNombreSala() + "'");
        q.agregarParametroValues("IND_TIPO_SALA", request.getTipoSala());
        q.agregarParametroValues("CAN_CAPACIDAD", String.valueOf(request.getCapacidadSala()));
        q.agregarParametroValues("IND_DISPONIBILIDAD", "1");
        q.agregarParametroValues("ID_VELATORIO", String.valueOf(request.getIdVelatorio()));
        q.agregarParametroValues("ID_USUARIO_ALTA", String.valueOf(user.getIdUsuario()));
        q.agregarParametroValues("IND_ESTATUS", "1");
        String query = q.obtenerQueryInsertar();
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }

    public DatosRequest modificarSala(AgregarSalaRequest request, UsuarioDto user) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        final QueryHelper q = new QueryHelper("UPDATE SVC_SALA");
        q.agregarParametroValues("NOM_SALA", "'" + request.getNombreSala() + "'");
        q.agregarParametroValues("CAN_CAPACIDAD", String.valueOf(request.getCapacidadSala()));
        q.agregarParametroValues("FEC_ACTUALIZACION", "NOW()");
        q.agregarParametroValues("ID_USUARIO_MODIFICA", String.valueOf(user.getIdUsuario()));
        q.addWhere("ID_SALA = " + request.getIdSala());
        String query = q.obtenerQueryActualizar();
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }

    public DatosRequest cambiarEstatus(int idSala, UsuarioDto user) {
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        String query = "UPDATE SVC_SALA SET IND_ESTATUS=!IND_ESTATUS, FEC_ACTUALIZACION=NOW() , FEC_BAJA=NOW(), ID_USUARIO_BAJA = '" + user.getIdUsuario() + "'  WHERE ID_SALA = " + idSala;
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }

    public DatosRequest buscarSala(DatosRequest request) {
        Gson json = new Gson();
        BuscarSalasRequest br = json.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), BuscarSalasRequest.class);
        StringBuilder constr = new StringBuilder();
        String query = "select " +
                "s.ID_SALA as idSala, " +
                "s.NOM_SALA as nombreSala, " +
                "s.IND_TIPO_SALA as indTipoSala, " +
                "if(s.IND_TIPO_SALA = 0, 'Cremación', 'Embalsamamiento') as desTipoSala, " +
                "s.CAN_CAPACIDAD as capacidad , " +
                "IND_DISPONIBILIDAD as disponibilidad, " +
                "s.ID_VELATORIO as idVelatorio, " +
                "sv.NOM_VELATORIO as nombreVelatorio, " +
                "s.IND_ESTATUS as estatus " +
                "from " +
                "SVC_SALA s " +
                "inner join SVC_VELATORIO sv on " +
                "s.ID_VELATORIO = sv.ID_VELATORIO";
        constr.append(query);
        if (br.getIdTipoSala() != null && br.getNombreSala() == null && br.getIdVelatorio() == null) {
           constr.append(" WHERE s.IND_TIPO_SALA = " + br.getIdTipoSala());
        } else if (br.getIdTipoSala() != null && br.getNombreSala() != null && br.getIdVelatorio() == null) {
            constr.append(" WHERE s.IND_TIPO_SALA = " + br.getIdTipoSala());
           constr.append(" AND s.NOM_SALA LIKE '%" + br.getNombreSala() + "%'");
        }else if (br.getIdTipoSala() != null && br.getNombreSala() != null && br.getIdVelatorio() != null) {
            constr.append(" WHERE s.IND_TIPO_SALA = " + br.getIdTipoSala());
            constr.append(" AND s.NOM_SALA LIKE '%" + br.getNombreSala() + "%'");
            constr.append(" AND s.ID_VELATORIO = " + br.getIdVelatorio());
        }else if (br.getIdTipoSala() == null && br.getNombreSala() != null && br.getIdVelatorio() == null) {
            constr.append(" WHERE s.NOM_SALA LIKE '%" + br.getNombreSala() + "%'");
        }else if (br.getIdTipoSala() == null && br.getNombreSala() != null && br.getIdVelatorio() != null) {
            constr.append(" WHERE s.ID_VELATORIO = " + br.getIdVelatorio());
            constr.append(" AND s.NOM_SALA LIKE '%" + br.getNombreSala() + "%'");
        }else if (br.getIdTipoSala() != null && br.getNombreSala() == null && br.getIdVelatorio() != null) {
            constr.append(" WHERE s.ID_VELATORIO = " + br.getIdVelatorio());
            constr.append(" AND s.IND_TIPO_SALA = " + br.getIdTipoSala());
        }
        log.info(constr.toString());
        request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(constr.toString().getBytes()));
        request.getDatos().remove("datos");
        return request;
    }
}
