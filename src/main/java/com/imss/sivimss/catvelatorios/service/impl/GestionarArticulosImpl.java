package com.imss.sivimss.catvelatorios.service.impl;

import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.beans.GestionarArticulos;
import com.imss.sivimss.catvelatorios.exception.BadRequestException;
import com.imss.sivimss.catvelatorios.model.request.AgregarArticuloRequest;
import com.imss.sivimss.catvelatorios.model.request.AgregarMedidasRequest;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.service.GestionarArticulosServices;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.ProviderServiceRestTemplate;
import com.imss.sivimss.catvelatorios.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class GestionarArticulosImpl implements GestionarArticulosServices {
    @Value("${endpoints.dominio-consulta}")
    private String urlDominioConsulta;

    @Autowired
    private ProviderServiceRestTemplate providerRestTemplate;
    Gson json = new Gson();
    GestionarArticulos gestion = new GestionarArticulos();
    AgregarMedidasRequest agm = new AgregarMedidasRequest();

    @Override
    public Response<?> agregarArticulos(DatosRequest request, Authentication authentication) throws IOException {
        AgregarArticuloRequest articulosDto = json.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), AgregarArticuloRequest.class);
        UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
        Response<?> response;
        if (!validarRepetido(articulosDto.getDescripcionArticulo(), authentication)) {
            agm.setAlto(articulosDto.getMedidas().getAlto().toString());
            agm.setAncho(articulosDto.getMedidas().getAncho().toString());
            agm.setLargo(articulosDto.getMedidas().getLargo().toString());
            response = providerRestTemplate.consumirServicio(gestion.insertarArticulo(articulosDto, agm, usuarioDto).getDatos(), urlDominioConsulta + "/generico/crearMultiple", authentication);
        } else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Registro repetido");
        }
        return response;
}

    @Override
    public Response<?> modificarArticulo(DatosRequest request, Authentication authentication) throws IOException {
        AgregarArticuloRequest articulosDto = json.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), AgregarArticuloRequest.class);
        UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
        //if (!validarRepetido(articulosDto.getDescripcionArticulo(), authentication)) {
            Response<?> response = providerRestTemplate.consumirServicio(gestion.actualizarArticulo(articulosDto, usuarioDto).getDatos(), urlDominioConsulta + "/generico/actualizar", authentication);
            if (response.getCodigo() == 200) {
                agm.setIdArticulo(String.valueOf(articulosDto.getIdArticulo()));
                agm.setAlto(articulosDto.getMedidas().getAlto().toString());
                agm.setAncho(articulosDto.getMedidas().getAncho().toString());
                agm.setLargo(articulosDto.getMedidas().getLargo().toString());
                providerRestTemplate.consumirServicio(gestion.actualizarArticuloMedida(agm).getDatos(),
                        urlDominioConsulta + "/generico/actualizar", authentication);
            } else {
                throw new BadRequestException(HttpStatus.BAD_REQUEST, "Error al insertar medidas");
            }
            return response;
        //}
        //throw new BadRequestException(HttpStatus.BAD_REQUEST, "Registro repetido");
    }

    @Override
    public Response<?> modificarEstatus(DatosRequest request, Authentication authentication) throws IOException {
        UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
        agm = json.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), AgregarMedidasRequest.class);
        return providerRestTemplate.consumirServicio(gestion.cambiarEstatus(Integer.parseInt(agm.getIdArticulo()), usuarioDto).getDatos(), urlDominioConsulta + "/generico/actualizar",
                authentication);
    }

    @Override
    public Response<?> buscarArticulos(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(gestion.buscarArticulos(request).getDatos(), urlDominioConsulta + "/generico/paginado",
                authentication);
    }

    @Override
    public Response<?> buscarArticulosGeneral(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(gestion.busquedaGeneral(request).getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
    }

    @Override
    public Response<?> buscarCategorias(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(gestion.buscarCategorias().getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
    }

    @Override
    public Response<?> buscarTipoArticulos(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(gestion.buscarTipoArticulos().getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
    }

    @Override
    public Response<?> buscarTipoMateriales(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(gestion.buscarTipoMateriales().getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
    }

    @Override
    public Response<?> buscarTamanios(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(gestion.buscarTamanios().getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
    }

    @Override
    public Response<?> buscarClasificacionProductos(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(gestion.buscarClasificacionProducto().getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
    }

    @Override
    public Response<?> buscarPartidaPresupuestal(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(gestion.buscarPartidaPresupuestal().getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
    }

    @Override
    public Response<?> buscarCuentaContable(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(gestion.buscarCuentaContable().getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
    }

    @Override
    public Response<?> buscarClaveSAT(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(gestion.buscarClaveSAT().getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
    }

    public Boolean validarRepetido(String desArticulo, Authentication authentication) throws IOException {
        Response<?> response = providerRestTemplate.consumirServicio(gestion.validarRepetido(desArticulo).getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
        Object resultado = response.getDatos();
        if (resultado.toString().equals("[]")) {
            return false;
        }
        return true;
    }
}
