package com.imss.sivimss.catvelatorios.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.Response;

public interface GestionarVelatorioService {

	public Response<?> consultaGeneral(DatosRequest request, Authentication authentication) throws IOException;
	

	public Response<?> buscarVelatorio(DatosRequest request, Authentication authentication) throws IOException;
	

	public Response<?> buscarVelatorioDelegacion(DatosRequest request, Authentication authentication)throws IOException;
	

	public Response<?> agregarVelatorio(DatosRequest request, Authentication authentication)throws IOException;
	

	public Response<?> actualizarVelatorio(DatosRequest request, Authentication authentication) throws IOException;
	

	public Response<?> cambiarEstatusVelatorio(DatosRequest request, Authentication authentication) throws IOException;
	

}
