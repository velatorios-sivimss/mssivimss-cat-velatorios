package com.imss.sivimss.catvelatorios.controller;

import com.imss.sivimss.catvelatorios.service.CapillaService;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


// todo - si se van a hacer reportes como se van a manejar?
@RestController
@RequestMapping("/capillas")
@AllArgsConstructor
public class CapillaController {

    private final CapillaService capillaService;

    @PostMapping("/consultar")
    public Response<?> consultarCapilla(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return capillaService.buscarCapilla(request, authentication);
    }

    @PostMapping
    public Response<?> consultarCapillaPorFiltros(@RequestBody  DatosRequest request, Authentication authentication) throws IOException {
        return capillaService.consultarCapillas(request, authentication);
    }

    @PostMapping("/consultar-catalogo")
    public Response<?> consultarCatalogoCapillas(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return capillaService.consultarCatalogoCapillas(request, authentication);
    }

    @PostMapping("/crear")
    public Response<?> crearCapilla(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return capillaService.crearCapilla(request, authentication);
    }

    @PostMapping("/actualizar")
    public Response<?> actualizarCapilla(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return capillaService.actualizarCapilla(request, authentication);
    }

    @PostMapping("cambiar-estatus")
    public Response<?> cambiarEstatusCapilla(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return capillaService.cambiarEstatusCapilla(request, authentication);
    }

}
