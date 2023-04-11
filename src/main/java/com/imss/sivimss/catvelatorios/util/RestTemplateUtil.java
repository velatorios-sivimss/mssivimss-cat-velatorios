package com.imss.sivimss.catvelatorios.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class RestTemplateUtil {
	private static Logger log = LogManager.getLogger(RestTemplateUtil.class);
	private static final String ERROR="Ha ocurrido un error al enviar";
	private static final String FALLO="Fallo al consumir el servicio, {}";

	private final RestTemplate restTemplate;


	public RestTemplateUtil(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Env&iacute;a una petici&oacute;n con Body.
	 *
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Response<?> sendPostRequestByteArray(String url, EnviarDatosRequest body, Class<?> clazz)
			throws IOException {
		log.info("Prepara la informacion antes de enviar la pericion");
		return sendPostRequest(url,body, clazz, null);
	}

	/**
	 * Env&iacute;a una petici&oacute;n con Body y token.
	 *
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Response<?> sendPostRequestByteArrayToken(String url, EnviarDatosRequest body, String subject,
													 Class<?> clazz) throws IOException {
		return sendPostRequest(url,body, clazz, subject);
	}

	/**
	 * Crea los headers para la petici&oacute;n falta agregar el tema de seguridad
	 * para las peticiones
	 *
	 * @return
	 */
	private static HttpHeaders createHttpHeaders() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return header;
	}

	/**
	 * Crea los headers para la petici&oacute;n con token todo - falta agregar el
	 * tema de seguridad para las peticiones
	 *
	 * @return
	 */
	private static HttpHeaders createHttpHeadersToken(String subject) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("Authorization", "Bearer " + subject);

		header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return header;
	}

	///////////////////////////////////////////////////// peticion con archivos
	/**
	 * Crea los headers para la petici&oacute;n con token todo - falta agregar el
	 * tema de seguridad para las peticiones
	 *
	 * @return
	 */
	/**
	 * Env&iacute;a una petici&oacute;n con Body, archivos y token.
	 *
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Response<?> sendPostRequestByteArrayArchviosToken(String url, EnviarDatosArchivosRequest body,
															 String subject, Class<?> clazz) throws IOException {
		Response<?> responseBody = new Response<>();
		HttpHeaders headers = RestTemplateUtil.createHttpHeadersArchivosToken(subject);

		ResponseEntity<?> responseEntity = null;
		try {

			LinkedMultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

			for (MultipartFile file : body.getArchivos()) {
				if (!file.isEmpty()) {
					parts.add("files",
							new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
				}
			}

			parts.add("datos", body.getDatos());
			HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(parts, headers);

			responseEntity = restTemplate.postForEntity(url, request, clazz);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
				// noinspection unchecked
				responseBody = (Response<List<String>>) responseEntity.getBody();
			} else {
				throw new IOException("Ha ocurrido un error al enviar");
			}
		} catch (IOException ioException) {
			throw ioException;
		} catch (Exception e) {
			log.error("Fallo al consumir el servicio, {}", e.getMessage());
			responseBody.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseBody.setError(true);
			responseBody.setMensaje(e.getMessage());
		}

		return responseBody;
	}

	private static HttpHeaders createHttpHeadersArchivosToken(String subject) {

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		header.setAccept(Arrays.asList(MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON));
		header.set("Authorization", "Bearer " + subject);
		return header;
	}

//////////////////////////////////////////
	/**
	 * Enviar una peticion con Body para reportes.
	 *
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Response<?> sendPostRequestByteArrayReportesToken(String url, DatosReporteDTO body, String subject,
															 Class<?> clazz) throws IOException {
		Response<?> responseBody = new Response<>();
		HttpHeaders headers = RestTemplateUtil.createHttpHeadersToken(subject);

		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		ResponseEntity<?> responseEntity = null;
		responseEntity = restTemplate.postForEntity(url, request, clazz);
		responseBody = (Response<List<String>>) responseEntity.getBody();

		return responseBody;
	}

	private Response<?> sendPostRequest(String url, EnviarDatosRequest body, Class<?> clazz, String subject) throws IOException{
		HttpHeaders headers = null;
		if(subject!=null && subject.trim().length()>0){
			headers = RestTemplateUtil.createHttpHeadersToken(subject);
		} else {
			headers = RestTemplateUtil.createHttpHeaders();
		}
		return sendPostRequestSecond(url,body,clazz,headers);
	}

	private Response<?> sendPostRequestSecond(String url, EnviarDatosRequest body, Class<?> clazz, HttpHeaders headers) throws IOException{
		Response<Object> responseBody = new Response<>();
		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = restTemplate.postForEntity(url, request, String.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
				JSONObject obj=new JSONObject(responseEntity.getBody());
				responseBody.setCodigo(obj.getInt("codigo"));
				responseBody.setMensaje(obj.getString("mensaje"));
				responseBody.setError(obj.getBoolean("error"));
				if(!obj.isNull("datos")){
					ArrayList<Object> listdata = new ArrayList<>();
					for (int i=0;i<obj.getJSONArray("datos").length();i++){
						listdata.add(obj.getJSONArray("datos").getString(i));
					}
					responseBody.setDatos(listdata);
				}
			} else {
				throw new IOException(ERROR);
			}
		} catch (IOException ioException) {
			throw ioException;
		} catch (Exception e) {
			log.error(FALLO, e.getMessage());
			responseBody.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseBody.setError(true);
			responseBody.setMensaje(e.getMessage());
		}
		return responseBody;
	}
}