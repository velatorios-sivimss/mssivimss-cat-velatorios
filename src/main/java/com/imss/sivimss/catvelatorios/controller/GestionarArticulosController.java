package com.imss.sivimss.catvelatorios.controller;

import com.imss.sivimss.catvelatorios.service.GestionarArticulosServices;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.ProviderServiceRestTemplate;
import com.imss.sivimss.catvelatorios.util.Response;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/")
public class GestionarArticulosController {
    @Autowired
    GestionarArticulosServices gestion;

    @Autowired
    private ProviderServiceRestTemplate providerRestTemplate;

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/agregar")
    public Response<?> agregarArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return gestion.agregarArticulos(request, authentication);
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/modificar")
    public Response<?> actualizarArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return gestion.modificarArticulo(request, authentication);
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/cambiar-estatus")
    public Response<?> cambiarEstatusArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return gestion.modificarEstatus(request, authentication);
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/buscar")
    public Response<?> buscarArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        return gestion.buscarArticulos(request, authentication);
    }

    /**
     * fallbacks generico
     *
     * @return respuestas
     */
    private CompletableFuture<?> fallbackGenerico(@RequestBody DatosRequest request, Authentication authentication,
                                                  CallNotPermittedException e) {
        Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
        return CompletableFuture
                .supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
    }

    private CompletableFuture<?> fallbackGenerico(@RequestBody DatosRequest request, Authentication authentication,
                                                  RuntimeException e) {
        Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
        return CompletableFuture
                .supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
    }

    private CompletableFuture<?> fallbackGenerico(@RequestBody DatosRequest request, Authentication authentication,
                                                  NumberFormatException e) {
        Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
        return CompletableFuture
                .supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
    }

}
