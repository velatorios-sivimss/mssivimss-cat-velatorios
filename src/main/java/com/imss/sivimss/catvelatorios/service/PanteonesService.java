package com.imss.sivimss.catvelatorios.service;

import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.Response;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface PanteonesService {
    Response<?> agregarPanteon(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> modificarPanteon(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> modificarPanteonEstatus(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarPanteon(DatosRequest request, Authentication authentication) throws IOException;
}
