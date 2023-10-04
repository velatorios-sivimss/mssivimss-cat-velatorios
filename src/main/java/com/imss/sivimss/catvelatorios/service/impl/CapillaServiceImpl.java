package com.imss.sivimss.catvelatorios.service.impl;

import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.beans.Capilla;
import com.imss.sivimss.catvelatorios.exception.BadRequestException;
import com.imss.sivimss.catvelatorios.model.request.CapillaDto;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.service.CapillaService;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.ProviderServiceRestTemplate;
import com.imss.sivimss.catvelatorios.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class CapillaServiceImpl implements CapillaService {
    @Value("${endpoints.mod-catalogos}")
    private String urlDominioConsulta;

    @Value("${endpoints.ms-reportes}")
    private String urlReportes;

    private final ProviderServiceRestTemplate restTemplate;

    private final ModelMapper modelMapper;

    private final Gson gson;
    private final Capilla capilla;

    public CapillaServiceImpl(ProviderServiceRestTemplate restTemplate,
                              ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.gson = new Gson();
        this.capilla = new Capilla();
    }

    @Override
    public Response<?> consultarCapillas(DatosRequest request, Authentication authentication) throws IOException {

        final Map<String, Object> datos = capilla.consultarCapillas(request).getDatos();
        final Response<?> response = restTemplate.consumirServicio(
                datos,
                urlDominioConsulta + "/generico/paginado",
                authentication
        );
        if (response.getCodigo() != 200) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Error al consultar las capillas");
        }
        return response;
    }

    @Override
    public Response<?> buscarCapilla(DatosRequest request, Authentication authentication) throws IOException {
        Long idCapilla = gson.fromJson(
                String.valueOf(request.getDatos().get("palabra")),
                Long.class);
        final Response<?> response = restTemplate.consumirServicio(
                capilla.buscarCapilla(idCapilla).getDatos(),
                urlDominioConsulta + "/generico/consulta",
                authentication
        );
        if (response.getCodigo() != 200) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Error al consultar la capilla");
        }
        return response;
    }

    private Long getIdCapilla(Map<String, Object> request) {
        return gson.fromJson(
                String.valueOf(request.get(AppConstantes.DATOS)),
                CapillaDto.class).getIdCapilla();
    }

    @Override
    public Response<?> consultarCatalogoCapillas(DatosRequest request, Authentication authentication) throws IOException {

        return restTemplate.consumirServicio(
                capilla.consultarCatalogoCapillas().getDatos(),
                urlDominioConsulta + "/generico/consulta",
                authentication
        );
    }

    @Override
    public Response<?> crearCapilla(DatosRequest request, Authentication authentication) throws IOException {

        final CapillaDto capillaDto = getCapillaDto(request);
        final UsuarioDto usuarioDto = getUsuarioDto(authentication);

        return restTemplate.consumirServicio(
                capilla.insertar(capillaDto, usuarioDto).getDatos(),
                urlDominioConsulta + "/generico/crear",
                authentication
        );
    }

    @Override
    public Response<?> actualizarCapilla(DatosRequest request, Authentication authentication) throws IOException {

        final CapillaDto capillaDto = getCapillaDto(request);
        final UsuarioDto usuarioDto = getUsuarioDto(authentication);

        return restTemplate.consumirServicio(
                capilla.actualizar(capillaDto, usuarioDto).getDatos(),
                urlDominioConsulta + "/generico/actualizar",
                authentication
        );
    }

    @Override
    public Response<?> cambiarEstatusCapilla(DatosRequest request, Authentication authentication) throws IOException {
        Long idCapilla = getIdCapilla(request.getDatos());
        return restTemplate.consumirServicio(
                capilla.cambiarEstatus(idCapilla, getUsuarioDto(authentication)).getDatos(),
                urlDominioConsulta + "/generico/actualizar",
                authentication
        );
    }

    private CapillaDto getCapillaDto(DatosRequest request) {
        return gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), CapillaDto.class);
    }

    private UsuarioDto getUsuarioDto(Authentication authentication) {
        return gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
    }
}
