package com.imss.sivimss.catvelatorios.beans;

import com.imss.sivimss.catvelatorios.model.request.AgregarSalaRequest;
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
    public DatosRequest insertarSala(AgregarSalaRequest request, UsuarioDto user){
        DatosRequest dr = new DatosRequest();
        Map<String, Object> parametro = new HashMap<>();
        final QueryHelper q = new QueryHelper("INSERT INTO SVC_SALA");
        q.agregarParametroValues("NOM_SALA", "'" + request.getNombreSala() + "'");
        q.agregarParametroValues("IND_TIPO_SALA", request.getTipoSala());
        q.agregarParametroValues("CAN_CAPACIDAD",String.valueOf(request.getCapacidadSala()));
        q.agregarParametroValues("IND_DISPONIBILIDAD","1");
        q.agregarParametroValues("ID_VELATORIO",String.valueOf(request.getIdVelatorio()));
        q.agregarParametroValues("ID_USUARIO_ALTA",String.valueOf(user.getId()));
        q.agregarParametroValues("IND_ESTATUS","1");
        String query = q.obtenerQueryInsertar();
        log.info(query);
        String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
        parametro.put(AppConstantes.QUERY, encoded);
        dr.setDatos(parametro);
        return dr;
    }
}
