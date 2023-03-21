package com.imss.sivimss.catvelatorios.service.impl;

import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.beans.GestionarSalas;
import com.imss.sivimss.catvelatorios.model.request.AgregarSalaRequest;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.service.GestionarSalasServices;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.ProviderServiceRestTemplate;
import com.imss.sivimss.catvelatorios.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class GestionarSalasImpl implements GestionarSalasServices {
    @Value("${endpoints.dominio-consulta}")
    private String urlDominioConsulta;
    @Autowired
    private ProviderServiceRestTemplate providerRestTemplate;
    Gson json = new Gson();
    GestionarSalas gs = new GestionarSalas();
    @Override
    public Response<?> agregarSalas(DatosRequest request, Authentication authentication) throws IOException {
        AgregarSalaRequest  salasInfo = json.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)),AgregarSalaRequest.class);
        UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
        Response<?> respuesta = providerRestTemplate.consumirServicio(gs.insertarSala(salasInfo,usuarioDto).getDatos(),urlDominioConsulta
        + "/generico/crear", authentication );
        return respuesta;
    }

    @Override
    public Response<?> modificarSalas(DatosRequest request, Authentication authentication) throws IOException {
        AgregarSalaRequest  salasInfo = json.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)),AgregarSalaRequest.class);
        UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
        return providerRestTemplate.consumirServicio(gs.modificarSala(salasInfo,usuarioDto).getDatos(),
                urlDominioConsulta + "/generico/actualizar", authentication);
    }

    @Override
    public Response<?> cambiarEstatus(DatosRequest request, Authentication authentication) throws IOException {
        UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
        AgregarSalaRequest  salasInfo = json.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)),AgregarSalaRequest.class);
        return providerRestTemplate.consumirServicio(gs.cambiarEstatus(salasInfo.getIdSala(), usuarioDto).getDatos(),
                urlDominioConsulta + "/generico/actualizar",
                authentication);
    }

    @Override
    public Response<?> buscarSalas(DatosRequest request, Authentication authentication) throws IOException {
        return null;
    }
}
