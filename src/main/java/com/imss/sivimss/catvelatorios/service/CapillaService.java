package com.imss.sivimss.catvelatorios.service;

import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.Response;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface CapillaService {
    /**
     * Consulta de capillas por filtros, recupera la lista de Capillas, ordenadas y con paginaci&ooacute;n
     *
     * @param request
     * @param authentication
     * @return
     */
    Response<?> consultarCapillas(DatosRequest request, Authentication authentication) throws IOException;

    /**
     * Consulta una Capilla por id, para ver el detalle de la Capilla que se consulta.
     *
     * @param request
     * @param authentication
     * @return
     */
    Response<?> buscarCapilla(DatosRequest request, Authentication authentication) throws IOException;

    /**
     * Se consultan las capillas, para poder mostrar la lista de Capillas como cat&aacute;logo.
     *
     * @param request
     * @param authentication
     * @return
     */
    Response<?> consultarCatalogoCapillas(DatosRequest request, Authentication authentication) throws IOException;

    /**
     * Crea una Capilla con los datos necesarios.
     *
     * @param request
     * @param authentication
     * @return
     */
    Response<?> crearCapilla(DatosRequest request, Authentication authentication) throws IOException;

    /**
     * Actualiza una Capilla por Id.
     *
     * @param request
     * @param authentication
     * @return
     */
    Response<?> actualizarCapilla(DatosRequest request, Authentication authentication) throws IOException;

    /**
     * Cambia el estatus de una Capilla.
     *
     * @param request
     * @param authentication
     * @return
     */
    Response<?> cambiarEstatusCapilla(DatosRequest request, Authentication authentication) throws IOException;
}
