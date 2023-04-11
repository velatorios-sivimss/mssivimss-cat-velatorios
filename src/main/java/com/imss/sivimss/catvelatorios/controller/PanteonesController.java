package com.imss.sivimss.catvelatorios.controller;

import com.imss.sivimss.catvelatorios.service.PanteonesService;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class PanteonesController {
    private static Logger log = LogManager.getLogger(PanteonesController.class);

    @Autowired
    private PanteonesService panteonesService;

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("panteones/agregar")
    public Response<?> agregarPanteones(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        log.info("Se agrega un nuevo panteon");
        return panteonesService.agregarPanteon(request, authentication);
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("panteones/modificar")
    public Response<?> actualizarPanteones(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        log.info("Actualizar panteon");
        return panteonesService.modificarPanteon(request, authentication);
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("panteones/cambiar-estatus")
    public Response<?> cambiarEstatusPanteones(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        log.info("Cambiar estatus de un panteon");
        return panteonesService.modificarPanteonEstatus(request, authentication);
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("panteones/buscar")
    public Response<?> buscarPanteones(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        log.info("BUsqueda de panteones");
        return panteonesService.buscarPanteon(request, authentication);
    }
}
