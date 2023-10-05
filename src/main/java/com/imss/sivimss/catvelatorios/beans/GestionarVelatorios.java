package com.imss.sivimss.catvelatorios.beans;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.imss.sivimss.catvelatorios.model.request.BuscarVelatorioRequest;
import com.imss.sivimss.catvelatorios.model.request.VelatoriosRequest;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.QueryHelper;
import com.imss.sivimss.catvelatorios.util.SelectQueryUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class GestionarVelatorios {
	
	private Integer idVelatorio;
	private Integer idDelegacion;
	private Integer idAdmin;
	private String nomVelatorio;
	private String nomRespoSanitario;
	private Integer cveAsignacion;
	private String calle;
	private Integer numExterior;
	private Integer cp;
	private String colonia;
	private String tel;
	private String correo;
	private Boolean estatus;
	private Integer idUsuario;
	
	
	public GestionarVelatorios(VelatoriosRequest velatorioRequest) {
		this.idVelatorio = velatorioRequest.getIdVelatorio();
		this.nomVelatorio = velatorioRequest.getNomVelatorio();
		this.idAdmin = velatorioRequest.getIdAdmin();
		this.nomRespoSanitario = velatorioRequest.getNomRespoSanitario();
		this.cveAsignacion = velatorioRequest.getCveAsignacion();
		this.idDelegacion = velatorioRequest.getIdDelegacion();
		this.calle = velatorioRequest.getCalle();
	    this.numExterior = velatorioRequest.getNumExterior();
		this.cp = velatorioRequest.getCp();
		this.colonia = velatorioRequest.getColonia();
		this.tel = velatorioRequest.getTel();
		this.correo = velatorioRequest.getCorreo();
		this.estatus = velatorioRequest.getEstatus();
	}
	
		//Busqueda general
	public DatosRequest catalogoVelatorio(DatosRequest request, BuscarVelatorioRequest filtros) {
		Map<String, Object> parametros = new HashMap<>();
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("VEL.ID_VELATORIO AS idVelatorio", 
				"VEL.DES_VELATORIO AS Velatorio", 
				"VEL.NOM_RESPO_SANITARIO AS respoSanitario", 
				"VEL.CVE_ASIGNACION AS cveAsignacion", 
				"SD.DES_CALLE AS calle", 
				"SD.NUM_EXTERIOR AS numExterior",
				 "SD.DES_CP AS cp", 
				 "VEL.NUM_TELEFONO AS numTelefono", 
				 "VEL.IND_ACTIVO AS estatus",
				  "SD.DES_COLONIA AS colonia", 
				"VEL.ID_DELEGACION AS idDelegacion", 
				 "DEL.DES_DELEGACION AS delegacion",
				  "CONCAT(USR.NOM_USUARIO, ' ', USR.NOM_APELLIDO_PATERNO, ' ', USR.NOM_APELLIDO_MATERNO) AS admin",
				"(SELECT CP.DES_MNPIO FROM SVC_CP CP WHERE SD.DES_CP = CP.CVE_CODIGO_POSTAL LIMIT 1) AS municipio",
				"(SELECT CP.DES_ESTADO FROM SVC_CP CP WHERE SD.DES_CP = CP.CVE_CODIGO_POSTAL LIMIT 1) AS estado",
			"(SELECT COUNT(*) FROM SVC_SALA SAL WHERE IND_TIPO_SALA=1 AND SAL.ID_VELATORIO=VEL.ID_VELATORIO) AS salasEmbalsamamiento",
			"(SELECT COUNT(*) FROM SVC_SALA SAL WHERE IND_TIPO_SALA=0 AND SAL.ID_VELATORIO=VEL.ID_VELATORIO) AS salasCremacion",
			"(SELECT COUNT(*) FROM SVC_CAPILLA CAP WHERE CAP.ID_VELATORIO=VEL.ID_VELATORIO) AS capillas") 
			.from("SVC_VELATORIO VEL")
		.join("SVC_DELEGACION DEL", "VEL.ID_DELEGACION = DEL.ID_DELEGACION")
		.leftJoin("SVT_USUARIOS USR", "VEL.ID_USUARIO_ADMIN = USR.ID_USUARIO")
		.leftJoin("SVT_DOMICILIO SD", "VEL.ID_DOMICILIO = SD.ID_DOMICILIO");
		if(filtros.getIdDelegacion()!=null) {
			queryUtil.where("VEL.ID_DELEGACION= "+filtros.getIdDelegacion()+"");
		}
		if(filtros.getNomVelatorio()!=null) {
			queryUtil.where("VEL.DES_VELATORIO LIKE '%"+filtros.getNomVelatorio()+"%'");
		}
		queryUtil.orderBy("VEL.ID_VELATORIO ASC");
		String query = obtieneQuery(queryUtil);
		log.info("velatorio "+query);
		String encoded = encodedQuery(query);
	    parametros.put(AppConstantes.QUERY, encoded);
	    parametros.put("pagina",filtros.getPagina());
        parametros.put("tamanio",filtros.getTamanio());
        request.getDatos().remove(AppConstantes.DATOS);
	    request.setDatos(parametros);
		return request;
	}
	
	
	
	//Buscar velatorio detalle
		public DatosRequest verDetalle(DatosRequest request) {
			String palabra = request.getDatos().get(""+AppConstantes.PALABRA+"").toString();
			SelectQueryUtil queryUtil = new SelectQueryUtil();
			queryUtil.select("VEL.ID_VELATORIO AS idVelatorio", 
					"VEL.DES_VELATORIO AS Velatorio", 
					"VEL.NOM_RESPO_SANITARIO AS respoSanitario", 
					"VEL.CVE_ASIGNACION AS cveAsignacion", 
					"SD.DES_CALLE AS calle", 
					"SD.NUM_EXTERIOR AS numExterior",
					 "SD.DES_CP AS cp", 
					 "VEL.NUM_TELEFONO AS numTelefono", 
					 "VEL.IND_ACTIVO AS estatus",
					  "SD.DES_COLONIA AS colonia", 
					"VEL.ID_DELEGACION AS idDelegacion", 
					 "DEL.DES_DELEGACION AS delegacion",
					  "CONCAT(USR.NOM_USUARIO, ' ', USR.NOM_APELLIDO_PATERNO, ' ', USR.NOM_APELLIDO_MATERNO) AS admin",
					"(SELECT CP.DES_MNPIO FROM SVC_CP CP WHERE SD.DES_CP = CP.CVE_CODIGO_POSTAL LIMIT 1) AS municipio",
					"(SELECT CP.DES_ESTADO FROM SVC_CP CP WHERE SD.DES_CP = CP.CVE_CODIGO_POSTAL LIMIT 1) AS estado",
				"(SELECT COUNT(*) FROM SVC_SALA SAL WHERE IND_TIPO_SALA=1 AND SAL.ID_VELATORIO=VEL.ID_VELATORIO) AS salasEmbalsamamiento",
				"(SELECT COUNT(*) FROM SVC_SALA SAL WHERE IND_TIPO_SALA=0 AND SAL.ID_VELATORIO=VEL.ID_VELATORIO) AS salasCremacion",
				"(SELECT COUNT(*) FROM SVC_CAPILLA CAP WHERE CAP.ID_VELATORIO=VEL.ID_VELATORIO) AS capillas")
				.from("SVC_VELATORIO VEL")
			.join("SVC_DELEGACION DEL", "VEL.ID_DELEGACION = DEL.ID_DELEGACION")
			.leftJoin("SVT_USUARIOS USR", "VEL.ID_USUARIO_ADMIN = USR.ID_USUARIO")
			.leftJoin("SVT_DOMICILIO SD", "VEL.ID_DOMICILIO = SD.ID_DOMICILIO");
			queryUtil.where("VEL.ID_VELATORIO= "+palabra);
			String query = obtieneQuery(queryUtil);
			log.info("detalle --> "+query);
			String encoded = encodedQuery(query);
			request.getDatos().remove(""+AppConstantes.PALABRA+"");
			request.getDatos().put(AppConstantes.QUERY, encoded);
			return request;
		}

		
		public DatosRequest insertarDomicilio() {
			DatosRequest request= new DatosRequest();
			Map<String, Object> parametro = new HashMap<>();
			final QueryHelper q = new QueryHelper("INSERT INTO SVT_DOMICILIO");
			q.agregarParametroValues("DES_CALLE", "'" + this.calle + "'");
			q.agregarParametroValues("NUM_EXTERIOR", "'" + this.numExterior + "'");
			q.agregarParametroValues("DES_CP", "" + this.cp + "");
			q.agregarParametroValues("DES_COLONIA", "'" + this.colonia + "'");
			q.agregarParametroValues("ID_USUARIO_ALTA", "" + idUsuario + "");
			q.agregarParametroValues("FEC_ALTA", ""+AppConstantes.CURRENT_TIMESTAMP+"");
			String query = q.obtenerQueryInsertar()+"$$"+insertarVelatorio();
			String encoded = encodedQuery(query);
			 parametro.put("separador","$$");
		        parametro.put("replace","idTabla");
			parametro.put(AppConstantes.QUERY, encoded);
			request.setDatos(parametro);
			return request;
		}
		
		private String insertarVelatorio() {
			final QueryHelper q = new QueryHelper("INSERT INTO SVC_VELATORIO");
			q.agregarParametroValues("DES_VELATORIO", "'" + this.nomVelatorio + "'");
			q.agregarParametroValues("ID_USUARIO_ADMIN", "" + this.idAdmin + "");
			q.agregarParametroValues("NOM_RESPO_SANITARIO", "'" + this.nomRespoSanitario + "'");
			q.agregarParametroValues("ID_DELEGACION", "" + this.idDelegacion + "");
			q.agregarParametroValues("CVE_ASIGNACION", "" + this.cveAsignacion + "");
			q.agregarParametroValues("ID_DOMICILIO", "idTabla");
			q.agregarParametroValues("" +AppConstantes.IND_ACTIVO + "", "1");
			q.agregarParametroValues("ID_USUARIO_ALTA", "" + this.idUsuario + "");
			q.agregarParametroValues("FEC_ALTA", ""+AppConstantes.CURRENT_TIMESTAMP+"");
			return q.obtenerQueryInsertar();
		}

	//Agregar velatorio
	/*public DatosRequest insertar() {
		DatosRequest request= new DatosRequest();
		Map<String, Object> parametro = new HashMap<>();
		final QueryHelper q = new QueryHelper("INSERT INTO SVC_VELATORIO");
		q.agregarParametroValues("NOM_VELATORIO", "'" + this.nomVelatorio + "'");
		q.agregarParametroValues("NOM_RESPO_SANITARIO", "'" + this.nomRespoSanitario + "'");
		q.agregarParametroValues("CVE_ASIGNACION", "'" + this.cveAsignacion + "'");
		q.agregarParametroValues("DES_CALLE", "'" + this.calle + "'");
		q.agregarParametroValues("NUM_EXT", "'"+ this.numExterior + "'");
		q.agregarParametroValues("ID_CODIGO_POSTAL", "'" + this.cp +"'");
		q.agregarParametroValues("NUM_TELEFONO", "'" + this.tel +"'");
		q.agregarParametroValues("" +AppConstantes.IND_ACTIVO + "", "1");
		q.agregarParametroValues("ID_USUARIO_ALTA", "" + this.idUsuario + "");
		q.agregarParametroValues("FEC_ALTA", ""+AppConstantes.CURRENT_TIMESTAMP+"");
		q.agregarParametroValues("ID_DELEGACION", "" + this.idDelegacion + "");
		
		String query = q.obtenerQueryInsertar();
		parametro.put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
		request.setDatos(parametro);

		return request; 
	} */
		//Actualizar Velatorio
	public DatosRequest actualizar() {
		DatosRequest request= new DatosRequest();
		Map<String, Object> parametro = new HashMap<>();
		final QueryHelper q = new QueryHelper("UPDATE SVC_VELATORIO");
		q.agregarParametroValues("NOM_VELATORIO", "'" + this.nomVelatorio + "'");
		q.agregarParametroValues("NOM_RESPO_SANITARIO", "'" + this.nomRespoSanitario + "'");
		q.agregarParametroValues("CVE_ASIGNACION", "'" + this.cveAsignacion + "'");
		q.agregarParametroValues("DES_CALLE", "'" + this.calle + "'");
		q.agregarParametroValues("NUM_EXT", "'"+ this.numExterior + "'");
		q.agregarParametroValues("ID_CODIGO_POSTAL", "'" + this.cp +"'");
		q.agregarParametroValues("NUM_TELEFONO", "'" + this.tel +"'");
		q.agregarParametroValues("" +AppConstantes.IND_ACTIVO + "", "" + this.estatus +"");
		q.agregarParametroValues("ID_USUARIO_MODIFICA", "'" + this.idUsuario + "'");
		q.agregarParametroValues("FEC_ACTUALIZACION", ""+AppConstantes.CURRENT_TIMESTAMP+"");
		q.agregarParametroValues("ID_DELEGACION", "'" + this.idDelegacion +"'");
		if(Boolean.FALSE.equals(this.estatus)) {
			q.agregarParametroValues("FEC_BAJA", ""+AppConstantes.CURRENT_TIMESTAMP+"");
			q.agregarParametroValues("ID_USUARIO_BAJA", "'" + this.idUsuario + "'");
		}
		
		q.addWhere("ID_VELATORIO = " + this.idVelatorio);
		String query = q.obtenerQueryActualizar();
		parametro.put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
		request.setDatos(parametro);
		return request;
	}
		
		//Cambiar estatus
	public DatosRequest cambiarEstatus() {
		DatosRequest request= new DatosRequest();
		Map<String, Object> parametro = new HashMap<>();
		final QueryHelper q = new QueryHelper("UPDATE SVC_VELATORIO");
		q.agregarParametroValues("" +AppConstantes.IND_ACTIVO + "", "" + this.estatus +"");
		if(Boolean.FALSE.equals(this.estatus)) {
			q.agregarParametroValues("FEC_BAJA", ""+AppConstantes.CURRENT_TIMESTAMP+"");
			q.agregarParametroValues("ID_USUARIO_BAJA",  "'" + this.idUsuario + "'");
		} 
		q.addWhere("ID_VELATORIO = " + this.idVelatorio);
			String query = q.obtenerQueryActualizar();
		    String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
			parametro.put(AppConstantes.QUERY, encoded);
			request.setDatos(parametro);
			return request;	
	}
		
	//Buscar por delegacion, velatorio especifico o doble filtrado
		public DatosRequest velatorioPorDelegacion(DatosRequest request, BuscarVelatorioRequest buscar) {
			String query="";	
		
			if(buscar.getNomVelatorio()==null  || buscar.getIdDelegacion()==null ||
					buscar.getNomVelatorio().equals("") || (buscar.getIdDelegacion().equals(""))) {
		
						query =" SELECT E.ID_VELATORIO AS idVelatorio, E.NOM_VELATORIO AS nomVelatorio,"
						+ " E.NOM_RESPO_SANITARIO AS nomRespoSanitario, E.CVE_ASIGNACION AS cveAsignacion, E.DES_CALLE AS desCalle, "
						+ " E.NUM_EXT AS numExterior, E.ID_CODIGO_POSTAL AS idCp, E.NUM_TELEFONO AS numTelefono, E.IND_ESTATUS AS estatus,"
						+ " E.DES_COLONIA AS otraColonia, E.ID_DELEGACION AS idDelegacion, D.DES_DELEGACION AS desDelegacion,"
						+ " (SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=1 AND ID_VELATORIO=E.ID_VELATORIO) AS salasEmbalsamamiento,"
						+ " (SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=0 AND ID_VELATORIO=E.ID_VELATORIO) AS salasCremacion,"
						+ " (SELECT COUNT(*) FROM SVC_CAPILLA WHERE ID_VELATORIO=E.ID_VELATORIO) AS capillas,"
				     	+ " (SELECT DES_MNPIO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS desMunicipio, "
						+ " (SELECT DES_ESTADO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS desEstado,"
						+ " (SELECT DES_COLONIA FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS desColonia,"
						+ " (SELECT CVE_CODIGO_POSTAL FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS cveCp,"
						+ " (SELECT CONCAT (NOM_USUARIO,' ', NOM_APELLIDO_PATERNO ,' ', NOM_APELLIDO_MATERNO)" 
						+ "FROM  SVT_USUARIOS WHERE ID_VELATORIO=E.ID_VELATORIO) AS administrador"
						+ " FROM SVC_VELATORIO E JOIN SVC_DELEGACION D ON E.ID_DELEGACION = D.ID_DELEGACION"
						+ " WHERE NOM_VELATORIO LIKE '%" + buscar.getNomVelatorio()  +"%'  OR DES_DELEGACION LIKE '%" + buscar.getIdDelegacion() +  "%'";
							request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
					return request;
			
			}else {
				query =" SELECT E.ID_VELATORIO AS idVelatorio, E.NOM_VELATORIO AS nomVelatorio,"
						+ " E.NOM_RESPO_SANITARIO AS nomRespoSanitario, E.CVE_ASIGNACION AS cveAsignacion, E.DES_CALLE AS desCalle, "
						+ " E.NUM_EXT AS numExterior, E.ID_CODIGO_POSTAL AS idCp, E.NUM_TELEFONO AS numTelefono, E.IND_ESTATUS AS estatus,"
						+ " E.DES_COLONIA AS otraColonia, E.ID_DELEGACION AS idDelegacion, D.DES_DELEGACION AS desDelegacion,"
						+ " (SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=1 AND ID_VELATORIO=E.ID_VELATORIO) AS salasEmbalsamamiento,"
						+ " (SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=0 AND ID_VELATORIO=E.ID_VELATORIO) AS salasCremacion,"
						+ " (SELECT COUNT(*) FROM SVC_CAPILLA WHERE ID_VELATORIO=E.ID_VELATORIO) AS capillas,"
				     	+ " (SELECT DES_MNPIO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS desMunicipio, "
						+ " (SELECT DES_ESTADO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS desEstado,"
						+ " (SELECT DES_COLONIA FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS desColonia,"
						+ " (SELECT CVE_CODIGO_POSTAL FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS cveCp,"
						+ " (SELECT CONCAT (NOM_USUARIO,' ', NOM_APELLIDO_PATERNO ,' ', NOM_APELLIDO_MATERNO)" 
						+ "FROM  SVT_USUARIOS WHERE ID_VELATORIO=E.ID_VELATORIO) AS administrador"
						+ " FROM SVC_VELATORIO E JOIN SVC_DELEGACION D ON E.ID_DELEGACION = D.ID_DELEGACION "
						+ "WHERE NOM_VELATORIO LIKE '%" + buscar.getNomVelatorio() + "%' AND DES_DELEGACION LIKE '%" + buscar.getIdDelegacion() + "%'";
				log.info(query);
				request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
				return request;			
			}
				
	}
		public DatosRequest buscarRepetido(String nomVelatorio) {
			DatosRequest request= new DatosRequest();
			Map<String, Object> parametro = new HashMap<>();
				String query = "SELECT *  FROM SVC_VELATORIO WHERE DES_VELATORIO=  '"+nomVelatorio +"' ";
				String encoded=encodedQuery(query);
				parametro.put(AppConstantes.QUERY, encoded);
				request.setDatos(parametro);
				return request;
		}

		public DatosRequest validacionActualizar(String nomVelatorio, Integer idVelatorio) {
			DatosRequest request= new DatosRequest();
			Map<String, Object> parametro = new HashMap<>();
				String query = "SELECT *  FROM SVC_VELATORIO WHERE NOM_VELATORIO=  '"+nomVelatorio +"' "
				+ " AND ID_VELATORIO!= "+idVelatorio +"";
				String encoded=DatatypeConverter.printBase64Binary(query.getBytes());
				parametro.put(AppConstantes.QUERY, encoded);
					request.setDatos(parametro);
					return request; 
				
				
		}

		public DatosRequest obtenerCp(DatosRequest request) {
			String palabra = request.getDatos().get("palabra").toString();
			String query = "SELECT ID_CODIGO_POSTAL AS idCodigoPostal, DES_COLONIA AS colonia, "
					+ "DES_ESTADO AS estado, DES_MNPIO AS municipio "
					+ "FROM svc_cp WHERE CVE_CODIGO_POSTAL="+ Integer.parseInt(palabra) + "";
			request.getDatos().remove("palabra");
			request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));

			return request;
		}
		
		private static String encodedQuery(String query) {
	        return DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
	    }
		
		private static String obtieneQuery(SelectQueryUtil queryUtil) {
	        return queryUtil.build();
		}


		}
