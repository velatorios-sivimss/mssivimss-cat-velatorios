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
    public CompletableFuture<?> agregarArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.agregarArticulos(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/modificar")
    public CompletableFuture<?> actualizarArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.modificarArticulo(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/cambiar-estatus")
    public CompletableFuture<?> cambiarEstatusArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.modificarEstatus(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/buscar")
    public CompletableFuture<?> buscarArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarArticulos(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/buscarGeneral")
    public CompletableFuture<?> buscarArticuloSinPag(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarArticulosGeneral(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/categorias")
    public CompletableFuture<?> buscarCategorias(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarCategorias(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/tipo-articulo")
    public CompletableFuture<?> buscarTipoArticulo(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarTipoArticulos(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/tipo-materiales")
    public CompletableFuture<?> buscarTipoMateriales(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarTipoMateriales(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/tamanios")
    public CompletableFuture<?> buscarTamanios(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarTamanios(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/clasificacion-producto")
    public CompletableFuture<?> buscarClasificacionProducto(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarClasificacionProductos(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/partida-presupuestal")
    public CompletableFuture<?> buscarPP(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarPartidaPresupuestal(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/cuenta-contable")
    public CompletableFuture<?> buscarCC(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarCuentaContable(request, authentication);
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response,HttpStatus.valueOf(response.getCodigo())));
    }

    @CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
    @TimeLimiter(name = "msflujo")
    @PostMapping("articulos/clave-sat")
    public CompletableFuture<?> buscarClaveSAT(@RequestBody DatosRequest request, Authentication authentication) throws IOException {
        Response<?> response = gestion.buscarClaveSAT(request, authentication);
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
