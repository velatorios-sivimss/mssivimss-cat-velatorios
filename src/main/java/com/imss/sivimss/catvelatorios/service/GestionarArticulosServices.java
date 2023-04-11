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
    Response<?> buscarArticulosGeneral(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarCategorias(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarTipoArticulos(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarTipoMateriales(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarTamanios(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarClasificacionProductos(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarPartidaPresupuestal(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarCuentaContable(DatosRequest request, Authentication authentication) throws IOException;
    Response<?> buscarClaveSAT(DatosRequest request, Authentication authentication) throws IOException;
}
