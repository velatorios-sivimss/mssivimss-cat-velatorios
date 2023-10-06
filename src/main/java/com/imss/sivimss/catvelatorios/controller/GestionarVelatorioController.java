package com.imss.sivimss.catvelatorios.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.catvelatorios.service.GestionarVelatorioService;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.ProviderServiceRestTemplate;
import com.imss.sivimss.catvelatorios.util.Response;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/velatorio")
public class GestionarVelatorioController {
	
	@Autowired
	private GestionarVelatorioService velatorioService;
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;

	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	@PostMapping("/catalogo")
	public CompletableFuture<?> catalogo(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		Response<?> response = velatorioService.consultaGeneral(request,authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

      
	}
	
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	@PostMapping("/detalle")
	public CompletableFuture<?> buscar(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		Response<?> response = velatorioService.buscarVelatorio(request,authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

      
	}
	
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	@PostMapping("/agregar")
	public CompletableFuture<?> agregar(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		Response<?> response = velatorioService.agregarVelatorio(request,authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));      
	}
	
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	@PostMapping("/actualizar")
	public CompletableFuture<?> actualizar(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		Response<?> response = velatorioService.actualizarVelatorio(request,authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));      
	}
	
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	@PostMapping("/filtros")
	public CompletableFuture<?> buscarDelegacion(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		Response<?> response = velatorioService.buscarVelatorioDelegacion(request,authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

      
	}
	
	
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	@PostMapping("/estatus")
	public CompletableFuture<?> cambiarEstatus(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		Response<?> response = velatorioService.cambiarEstatusVelatorio(request,authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}
	
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	@PostMapping("/buscar/cp")
	public  CompletableFuture<?> buscarCp(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		Response<?> response = velatorioService.obtenerCp(request,authentication);		
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

      
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
