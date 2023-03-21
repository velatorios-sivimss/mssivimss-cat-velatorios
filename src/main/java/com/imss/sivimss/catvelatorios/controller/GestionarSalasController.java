package com.imss.sivimss.catvelatorios.controller;

import com.imss.sivimss.catvelatorios.service.GestionarSalasServices;
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
public class GestionarSalasController {

    @Autowired
    GestionarSalasServices gestion;

    @PostMapping("salas/agregar")
    public Response<?> agregarSala(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return gestion.agregarSalas(request, authentication);
    }

    @PostMapping("salas/modificar")
    public Response<?> actualizarSala(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return gestion.modificarSalas(request, authentication);
    }
//
//    @PostMapping("articulos/cambiar-estatus")
//    public Response<?> cambiarEstatusArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
//        return gestion.modificarEstatus(request, authentication);
//    }
//
//    @PostMapping("articulos/buscar")
//    public Response<?> buscarArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
//        return gestion.buscarArticulos(request, authentication);
//    }
}
