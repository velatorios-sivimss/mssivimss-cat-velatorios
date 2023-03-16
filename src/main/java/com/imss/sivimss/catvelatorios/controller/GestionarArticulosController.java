package com.imss.sivimss.catvelatorios.controller;

import com.imss.sivimss.catvelatorios.service.GestionarArticulosServices;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/")
public class GestionarArticulosController {
    @Autowired
    GestionarArticulosServices gestion;

    @PostMapping("articulos/agregar")
    public Response<?> agregarArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return gestion.agregarArticulos(request, authentication);
    }

    @PostMapping("articulos/modificar")
    public Response<?> actualizarArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return gestion.modificarArticulo(request, authentication);
    }

    @PostMapping("articulos/cambiar-estatus")
    public Response<?> cambiarEstatusArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return gestion.modificarEstatus(request, authentication);
    }

    @PostMapping("articulos/buscar")
    public Response<?> buscarArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return gestion.buscarArticulos(request, authentication);
    }

}
