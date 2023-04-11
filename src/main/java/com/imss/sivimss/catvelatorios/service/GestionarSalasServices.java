package com.imss.sivimss.catvelatorios.service;

import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.Response;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface GestionarSalasServices {
    Response<?> agregarSalas(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> modificarSalas(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> cambiarEstatus(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarSalas(DatosRequest request, Authentication authentication) throws IOException;
}
