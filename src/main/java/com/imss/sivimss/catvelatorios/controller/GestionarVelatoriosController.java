package com.imss.sivimss.catvelatorios.controller;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.catvelatorios.service.GestionarVelatorioService;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/velatorio")
public class GestionarVelatoriosController {
	
	private GestionarVelatorioService velatorioService;
	
	@PostMapping("/catalogo")
	public Response<?> catalogo(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		return velatorioService.consultaGeneral(request,authentication);
      
	}
	
	@PostMapping("/buscar")
	public Response<?> buscar(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		return velatorioService.buscarVelatorio(request,authentication);
      
	}
	
	@PostMapping("/delega")
	public Response<?> buscarDelegacion(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		return velatorioService.buscarVelatorioDelegacion(request,authentication);
      
	}
	
	@PostMapping("/detalle")
	public Response<?> detalle(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		return velatorioService.detalleVelatorio(request,authentication);
      
	}
	
	@PostMapping("/agregar")
	public Response<?> agregar(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		return velatorioService.agregarVelatorio(request,authentication);
      
	}
	
	@PostMapping("/actualiza")
	public Response<?> actualizar(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		return velatorioService.actualizarVelatorio(request,authentication);
      
	}
	
	@PostMapping("/estatus")
	public Response<?> cambiarEstatus(@RequestBody DatosRequest request,Authentication authentication) throws IOException {
	
		return velatorioService.cambiarEstatusVelatorio(request,authentication);
      
	}

}
