package com.imss.sivimss.catvelatorios.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.Response;

public interface UsuarioService {

	Response<?> consultarUsuarios(DatosRequest request, Authentication authentication) throws IOException;

	Response<?> buscarUsuario(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<?> buscarUsuarioFiltros(DatosRequest request, Authentication authentication) throws IOException;

	Response<?> detalleUsuario(DatosRequest request, Authentication authentication) throws IOException;

	Response<?> catalogoUsuario(DatosRequest request, Authentication authentication) throws IOException;

	Response<?> agregarUsuario(DatosRequest request, Authentication authentication) throws IOException;

	Response<?> actualizarUsuario(DatosRequest request, Authentication authentication) throws IOException;

	Response<?> cambiarEstatusUsuario(DatosRequest request, Authentication authentication) throws IOException;

	Response<?> descargarDocumento(DatosRequest request, Authentication authentication) throws IOException;
}
