package com.imss.sivimss.catvelatorios.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.beans.Panteones;
import com.imss.sivimss.catvelatorios.exception.BadRequestException;
import com.imss.sivimss.catvelatorios.model.request.PanteonRequest;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.service.PanteonesService;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.ProviderServiceRestTemplate;
import com.imss.sivimss.catvelatorios.util.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PanteonesServiceImpl implements PanteonesService {
    private static Logger log = LogManager.getLogger(PanteonesServiceImpl.class);

    @Value("${endpoints.dominio-consulta}")
    private String urlDominioConsulta;

    @Autowired
    private ProviderServiceRestTemplate providerRestTemplate;

    private Gson json = new Gson();

    private ObjectMapper mapper = new ObjectMapper();

    private Panteones panteones=new Panteones();

    private Boolean existePanteon(String nombrePanteon, Authentication authentication) throws IOException {
        Response<?> response = providerRestTemplate.consumirServicio(panteones.existePanteon(nombrePanteon).getDatos(), urlDominioConsulta + "/generico/consulta",
                authentication);
        Object resultado = response.getDatos();
        if (resultado.toString().equals("[]")) {
            log.info("No existen registros");
            return false;
        }
        return true;
    }

    @Override
    public Response<?> agregarPanteon(DatosRequest request, Authentication authentication) throws IOException {
        String jsonResult = this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getDatos());
        PanteonRequest panteonDto = this.mapper.readValue(jsonResult,PanteonRequest.class);
        if(Boolean.FALSE.equals(existePanteon(panteonDto.getNombrePanteon(), authentication))){
                UsuarioDto usuarioDto = json.fromJson(authentication.getPrincipal().toString(), UsuarioDto.class);
                Response<?> response = providerRestTemplate.consumirServicio(panteones.insertarPanteon(panteonDto, usuarioDto).getDatos(), urlDominioConsulta + "/generico/crear", authentication);
                if (response.getCodigo() == 200) {
                    log.info("Registro exitoso");
                    return response;
                } else {
                throw new BadRequestException(HttpStatus.valueOf(response.getCodigo()), "Error al insertar medidas");
            }
        } else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "El panteón " + panteonDto.getNombrePanteon() + " ya existe.");
        }
    }

    @Override
    public Response<?> modificarPanteon(DatosRequest request, Authentication authentication) throws IOException {
        String jsonResult = this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getDatos());
        PanteonRequest panteonDto = this.mapper.readValue(jsonResult,PanteonRequest.class);
        UsuarioDto usuarioDto = null;
        Response<?> response = providerRestTemplate.consumirServicio(panteones.modificarPanteon(panteonDto, usuarioDto).getDatos(), urlDominioConsulta + "/generico/actualizar", authentication);
        if (response.getCodigo() == 200) {
            log.info("Modificacion exitoso");
            return response;
        } else {
            throw new BadRequestException(HttpStatus.valueOf(response.getCodigo()), "Error al insertar medidas");
        }
    }


    @Override
    public Response<?> modificarPanteonEstatus(DatosRequest request, Authentication authentication) throws IOException {
        String jsonResult = this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getDatos());
        PanteonRequest panteonDto = this.mapper.readValue(jsonResult,PanteonRequest.class);
        UsuarioDto usuarioDto=null;
        log.info("Nuevo estatus del panteon {}", panteonDto.getIndEstatus());
        return providerRestTemplate.consumirServicio(panteones.cambiarEstatus(panteonDto.getIdPanteon(), panteonDto.getIndEstatus(), usuarioDto).getDatos(), urlDominioConsulta + "/generico/actualizar",
                authentication);
    }

    @Override
    public Response<?> buscarPanteon(DatosRequest request, Authentication authentication) throws IOException {
        return providerRestTemplate.consumirServicio(panteones.buscarPanteones(request).getDatos(), urlDominioConsulta + "/generico/paginado",
                authentication);
    }
}
