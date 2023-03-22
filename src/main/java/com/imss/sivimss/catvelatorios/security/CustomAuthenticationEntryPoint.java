package com.imss.sivimss.catvelatorios.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.ErrorsMessageResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger log = LogManager.getLogger(CustomAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException)
			throws IOException, ServletException {
		log.info("Valida AuthenticationEntryPoint");
		try {
			String mensaje = this.response(req.getAttribute(AppConstantes.STATUSEXCEPTION).toString());
			String json = new Gson().toJson(new ErrorsMessageResponse().builder().error(true).codigo(403)
					.mensaje(mensaje).datos(req.getRequestURI()).build());
			res.setContentType("application/json;charset=UTF-8");
			res.setStatus(403);
			res.getWriter().write(json);
		} catch (Exception e) {
			String json = new Gson().toJson(new ErrorsMessageResponse().builder().error(true).codigo(403)
					.mensaje(AppConstantes.FORBIDDENEXCEPTION_MENSAJE).datos(req.getRequestURI()).build());
			res.setContentType("application/json;charset=UTF-8");
			res.setStatus(403);
			res.getWriter().write(json);
		}

	}

	private String response(String valor) {
		String message = "";
		switch (valor) {
		case AppConstantes.EXPIREDJWTEXCEPTION:
			message = AppConstantes.EXPIREDJWTEXCEPTION_MENSAJE;
			break;
		case AppConstantes.MALFORMEDJWTEXCEPTION:
			message = AppConstantes.MALFORMEDJWTEXCEPTION_MENSAJE;
			break;
		case AppConstantes.UNSUPPORTEDJWTEXCEPTION:
			message = AppConstantes.UNSUPPORTEDJWTEXCEPTION_MENSAJE;
			break;
		case AppConstantes.ILLEGALARGUMENTEXCEPTION:
			message = AppConstantes.ILLEGALARGUMENTEXCEPTION_MENSAJE;
			break;
		case AppConstantes.SIGNATUREEXCEPTION:
			message = AppConstantes.SIGNATUREEXCEPTION_MENSAJE;
			break;
		default:
			message = AppConstantes.FORBIDDENEXCEPTION_MENSAJE;
			break;
		}
		return message;
	}

}
