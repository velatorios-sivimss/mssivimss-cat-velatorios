package com.imss.sivimss.catvelatorios.service;

import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.Response;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface GestionarArticulosServices {
    Response<?> agregarArticulos(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> modificarArticulo(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> modificarEstatus(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarArticulos(DatosRequest request, Authentication authentication) throws IOException;
}
