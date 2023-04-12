package com.imss.sivimss.catvelatorios.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnviarDatosArchivosRequest {
	private String datos;
	private MultipartFile [] archivos;
}
