package com.imss.sivimss.catvelatorios.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.beans.GestionarVelatorios;
import com.imss.sivimss.catvelatorios.exception.BadRequestException;
import com.imss.sivimss.catvelatorios.model.request.BuscarVelatoriosRequest;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.model.request.VelatoriosRequest;
import com.imss.sivimss.catvelatorios.service.GestionarVelatorioService;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.ProviderServiceRestTemplate;
import com.imss.sivimss.catvelatorios.util.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GestionarVelatorioServiceImpl implements GestionarVelatorioService {
	
	@Value("${endpoints.dominio-consulta}")
	private String urlDominioConsulta;

	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	Gson gson = new Gson();
	
	GestionarVelatorios velatorio= new GestionarVelatorios();

	@Override
	public Response<?> consultaGeneral(DatosRequest request, Authentication authentication) throws IOException {
		return providerRestTemplate.consumirServicio(velatorio.catalogoVelatorio(request).getDatos(), urlDominioConsulta + "/generico/paginado ",
				authentication);
	}

	@Override
	public Response<?> buscarVelatorio(DatosRequest request, Authentication authentication) throws IOException {
		return providerRestTemplate.consumirServicio(velatorio.buscarVelatorio(request).getDatos(), urlDominioConsulta + "/generico/paginado",
				authentication);
	}

	@Override
	public Response<?> buscarVelatorioDelegacion(DatosRequest request, Authentication authentication)throws IOException {
		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		BuscarVelatoriosRequest buscar = gson.fromJson(datosJson, BuscarVelatoriosRequest.class);
		return providerRestTemplate.consumirServicio(velatorio.velatorioPorDelegacion(request, buscar).getDatos(), urlDominioConsulta + "/generico/paginado",
				authentication);
	}

	@Override
	public Response<?> agregarVelatorio(DatosRequest request, Authentication authentication) throws IOException {
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
	
		VelatoriosRequest velatorioRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), VelatoriosRequest.class);
		 
			if(!validarRegistro(velatorioRequest.getNomVelatorio(), authentication)) {
				velatorio= new GestionarVelatorios(velatorioRequest);
				velatorio.setIdUsuarioAlta(usuarioDto.getIdUsuario());
				return providerRestTemplate.consumirServicio(velatorio.insertar().getDatos(), urlDominioConsulta + "/generico/crear",
						authentication);
			}
				throw new BadRequestException(HttpStatus.BAD_REQUEST, "No se puede registar el Velatorio con ese nombre: " +velatorioRequest.getNomVelatorio());
		      
	}

	@Override
	public Response<?> actualizarVelatorio(DatosRequest request, Authentication authentication) throws IOException {
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		VelatoriosRequest velatorioRequest = gson.fromJson( String.valueOf(request.getDatos().get(AppConstantes.DATOS)), VelatoriosRequest.class);
		
		if (velatorioRequest.getIdVelatorio() == null) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Informacion incompleta");
		}
		if(!validarRegistroActualizar(velatorioRequest.getNomVelatorio(), velatorioRequest.getIdVelatorio(), authentication)) {
		velatorio= new GestionarVelatorios(velatorioRequest);
		velatorio.setIdUsuarioModifica(usuarioDto.getIdUsuario().toString());
		velatorio.setIdUsuarioBaja(usuarioDto.getIdUsuario());
		 return providerRestTemplate.consumirServicio(velatorio.actualizar().getDatos(), urlDominioConsulta + "/generico/actualizar",
				authentication);
		}
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "No se puede actualizar el Velatorio con ese nombre: " +velatorioRequest.getNomVelatorio());		
	}

	@Override
	public Response<?> cambiarEstatusVelatorio(DatosRequest request, Authentication authentication) throws IOException {
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		VelatoriosRequest velatorioRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), VelatoriosRequest.class);
		
		if (velatorioRequest.getIdVelatorio()== null || velatorioRequest.getIndEstatus()==null) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Informacion incompleta");
		}
		velatorio= new GestionarVelatorios(velatorioRequest);
		velatorio.setIndEstatus(velatorioRequest.getIndEstatus());
		velatorio.setIdUsuarioBaja(usuarioDto.getIdUsuario());
		return providerRestTemplate.consumirServicio(velatorio.cambiarEstatus().getDatos(), urlDominioConsulta + "/generico/actualizar",
				authentication);
	}
	
	private boolean validarRegistro(String nomVelatorio, Authentication authentication) throws IOException{
			Response<?> response= providerRestTemplate.consumirServicio(velatorio.buscarRepetido(nomVelatorio).getDatos(), urlDominioConsulta + "/generico/consulta",
					authentication);
			if (response.getCodigo()==200){
				Object rst=response.getDatos();
				return !rst.toString().equals("[]");	
				}
			 throw new BadRequestException(HttpStatus.BAD_REQUEST, "ERROR AL REGISTRAR EL VELATORIO ");
			}
		
	private boolean validarRegistroActualizar(String nomVelatorio, Integer idVelatorio, Authentication authentication) throws IOException {
		Response<?> response= providerRestTemplate.consumirServicio(velatorio.validacionActualizar(nomVelatorio, idVelatorio).getDatos(), urlDominioConsulta + "/generico/consulta",
				authentication);
		if (response.getCodigo()==200){
	Object rst=response.getDatos();
	return !rst.toString().equals("[]");
		}
		 throw new BadRequestException(HttpStatus.BAD_REQUEST, "ERROR AL REGISTRAR EL VELATORIO ");
	}

	@Override
	public Response<?> obtenerCp(DatosRequest request, Authentication authentication) throws IOException {
		return providerRestTemplate.consumirServicio(velatorio.obtenerCp(request).getDatos(), urlDominioConsulta + "/generico/consulta",
				authentication);	
	}
	
}
