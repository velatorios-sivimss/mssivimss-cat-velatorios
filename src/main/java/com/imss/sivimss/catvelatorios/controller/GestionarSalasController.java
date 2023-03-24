package com.imss.sivimss.catvelatorios.controller;

import com.imss.sivimss.catvelatorios.service.GestionarSalasServices;
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
public class GestionarSalasController {

    @Autowired
    GestionarSalasServices gestion;
    @Autowired
    private ProviderServiceRestTemplate providerRestTemplate;


    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("salas/agregar")
    public CompletableFuture<?>  agregarSala(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.agregarSalas(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("salas/modificar")
    public CompletableFuture<?> actualizarSala(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response =  gestion.modificarSalas(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("salas/cambiar-estatus")
    public CompletableFuture<?> cambiarEstatusSala(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.cambiarEstatus(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }
    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("salas/buscar")
    public CompletableFuture<?> buscarASala(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarSalas(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
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
