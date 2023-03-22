package com.imss.sivimss.catvelatorios.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosArchivosRequest {
	private String datos;
	private MultipartFile [] archivos;
}
