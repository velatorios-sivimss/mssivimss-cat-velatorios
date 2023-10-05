package com.imss.sivimss.catvelatorios.service.impl;

import java.io.IOException;
import java.util.logging.Level;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.beans.GestionarVelatorios;
import com.imss.sivimss.catvelatorios.exception.BadRequestException;
import com.imss.sivimss.catvelatorios.model.request.BuscarVelatorioRequest;
import com.imss.sivimss.catvelatorios.model.request.UsuarioDto;
import com.imss.sivimss.catvelatorios.model.request.VelatoriosRequest;
import com.imss.sivimss.catvelatorios.service.GestionarVelatorioService;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.LogUtil;
import com.imss.sivimss.catvelatorios.util.MensajeResponseUtil;
import com.imss.sivimss.catvelatorios.util.ProviderServiceRestTemplate;
import com.imss.sivimss.catvelatorios.util.Response;

import lombok.extern.slf4j.Slf4j;


@Service
public class GestionarVelatorioServiceImpl implements GestionarVelatorioService {
	
	@Value("${endpoints.rutas.dominio-consulta}")
	private String urlConsulta;

	@Value("${endpoints.rutas.dominio-consulta-paginado}")
	private String urlPaginado; 
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Autowired
	private LogUtil logUtil;
	
	private static final String BAJA = "baja";
	private static final String ALTA = "alta";
	private static final String MODIFICACION = "modificacion";
	private static final String CONSULTA = "consulta";
	private static final String INFORMACION_INCOMPLETA = "Informacion incompleta";
	private static final String SIN_INFORMACION = "45";
	private static final String EXITO = "EXITO";
	
	Gson gson = new Gson();
	
	GestionarVelatorios velatorio= new GestionarVelatorios();

	@Override
	public Response<?> consultaGeneral(DatosRequest request, Authentication authentication) throws IOException {
		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
   		BuscarVelatorioRequest filtros = gson.fromJson(datosJson, BuscarVelatorioRequest .class);
   	 Integer pagina = Integer.valueOf(Integer.parseInt(request.getDatos().get("pagina").toString()));
     Integer tamanio = Integer.valueOf(Integer.parseInt(request.getDatos().get("tamanio").toString()));
     filtros.setTamanio(tamanio.toString());
     filtros.setPagina(pagina.toString());
     
    		  Response <?> response = MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicio(velatorio.catalogoVelatorio(request,filtros).getDatos(), urlPaginado,
      				authentication), SIN_INFORMACION);
    		  logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"CONSULTA CATALOGO VELATORIOS OK", CONSULTA);
    		  return response;
	}

	@Override
	public Response<?> buscarVelatorio(DatosRequest request, Authentication authentication) throws IOException {
		return providerRestTemplate.consumirServicio(velatorio.verDetalle(request).getDatos(), urlConsulta,
				authentication);
	}

	@Override
	public Response<?> buscarVelatorioDelegacion(DatosRequest request, Authentication authentication)throws IOException {
		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		BuscarVelatorioRequest buscar = gson.fromJson(datosJson, BuscarVelatorioRequest.class);
		return providerRestTemplate.consumirServicio(velatorio.velatorioPorDelegacion(request, buscar).getDatos(), urlConsulta,
				authentication);
	}

	@Override
	public Response<?> agregarVelatorio(DatosRequest request, Authentication authentication) throws IOException {
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
	
		VelatoriosRequest velatorioRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), VelatoriosRequest.class);
		 
			if(!validarRegistro(velatorioRequest.getNomVelatorio(), authentication)) {
				velatorio= new GestionarVelatorios(velatorioRequest);
				velatorio.setIdUsuarioAlta(usuarioDto.getIdUsuario());
				return providerRestTemplate.consumirServicio(velatorio.insertar().getDatos(), urlConsulta + "/generico/crear",
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
		 return providerRestTemplate.consumirServicio(velatorio.actualizar().getDatos(), urlConsulta + "/generico/actualizar",
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
		return providerRestTemplate.consumirServicio(velatorio.cambiarEstatus().getDatos(), urlConsulta + "/generico/actualizar",
				authentication);
	}
	
	private boolean validarRegistro(String nomVelatorio, Authentication authentication) throws IOException{
			Response<?> response= providerRestTemplate.consumirServicio(velatorio.buscarRepetido(nomVelatorio).getDatos(), urlConsulta + "/generico/consulta",
					authentication);
			if (response.getCodigo()==200){
				Object rst=response.getDatos();
				return !rst.toString().equals("[]");	
				}
			 throw new BadRequestException(HttpStatus.BAD_REQUEST, "ERROR AL REGISTRAR EL VELATORIO ");
			}
		
	private boolean validarRegistroActualizar(String nomVelatorio, Integer idVelatorio, Authentication authentication) throws IOException {
		Response<?> response= providerRestTemplate.consumirServicio(velatorio.validacionActualizar(nomVelatorio, idVelatorio).getDatos(), urlConsulta + "/generico/consulta",
				authentication);
		if (response.getCodigo()==200){
	Object rst=response.getDatos();
	return !rst.toString().equals("[]");
		}
		 throw new BadRequestException(HttpStatus.BAD_REQUEST, "ERROR AL REGISTRAR EL VELATORIO ");
	}

	@Override
	public Response<?> obtenerCp(DatosRequest request, Authentication authentication) throws IOException {
		return providerRestTemplate.consumirServicio(velatorio.obtenerCp(request).getDatos(), urlConsulta + "/generico/consulta",
				authentication);	
	}
	
}
