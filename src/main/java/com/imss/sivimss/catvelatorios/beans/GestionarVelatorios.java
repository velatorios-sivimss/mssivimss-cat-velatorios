package com.imss.sivimss.catvelatorios.beans;


import java.nio.charset.StandardCharsets;
import java.util.Date;
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
	private String nomVelatorio;
	private String nomRespoSanitario;
	private Integer cveAsignacion;
	private String desCalle;
	private Integer numExterior;
	private Integer idCodigoPostal;
	private String desColonia;
	private String numTelefono;
	private Integer indEstatus;
	private Integer idUsuarioAlta;
	private Date fecAlta;
	private Date fecActualizacion;
	private Date fecBaja;
	private String idUsuarioModifica;
	private Integer idUsuarioBaja;
	private Integer idDelegacion;
	
	public GestionarVelatorios(VelatoriosRequest velatoriosRequest) {
		this.idVelatorio = velatoriosRequest.getIdVelatorio();
		this.nomVelatorio = velatoriosRequest.getNomVelatorio();
		this.nomRespoSanitario = velatoriosRequest.getNomRespoSanitario();
		this.cveAsignacion = velatoriosRequest.getCveAsignacion();
		this.desCalle = velatoriosRequest.getDesCalle();
		this.numExterior = velatoriosRequest.getNumExterior();
		this.idCodigoPostal = velatoriosRequest.getIdCodigoPostal();
		this.numTelefono = velatoriosRequest.getNumTelefono();
		this.indEstatus = velatoriosRequest.getIndEstatus();
		this.idUsuarioAlta = velatoriosRequest.getIdUsuarioAlta();
		this.fecAlta = velatoriosRequest.getFecAlta();
		this.fecActualizacion = velatoriosRequest.getFecActualizacion();
		this.fecBaja = velatoriosRequest.getFecBaja();
		this.idUsuarioBaja = velatoriosRequest.getIdUsuarioBaja();
		this.idDelegacion = velatoriosRequest.getIdDelegacion();
	  
		
	}
	
		//Busqueda general
	public DatosRequest catalogoVelatorio(DatosRequest request, BuscarVelatorioRequest filtros) {
		Map<String, Object> parametros = new HashMap<>();
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("VEL.ID_VELATORIO AS idVelatorio", 
				"VEL.DES_VELATORIO AS Velatorio", 
				"VEL.NOM_RESPO_SANITARIO AS respoSanitario", 
				"VEL.CVE_ASIGNACION AS cveAsignacion", 
				"SD.REF_CALLE AS calle", 
				"SD.NUM_EXTERIOR AS numExterior",
				 "SD.REF_CP AS cp", 
				 "VEL.NUM_TELEFONO AS numTelefono", 
				 "VEL.IND_ACTIVO AS estatus",
				  "SD.REF_COLONIA AS colonia", 
				"VEL.ID_DELEGACION AS idDelegacion", 
				 "DEL.DES_DELEGACION AS delegacion",
				  "USR.NOM_USUARIO AS admin",
				"(SELECT CP.DES_MNPIO FROM SVC_CP CP WHERE SD.REF_CP = CP.CVE_CODIGO_POSTAL LIMIT 1) AS municipio, "
				+"(SELECT CP.DES_ESTADO FROM SVC_CP CP WHERE SD.REF_CP = CP.CVE_CODIGO_POSTAL LIMIT 1) AS estado, "
			+"(SELECT COUNT(*) FROM SVC_SALA SAL WHERE IND_TIPO_SALA=1 AND SAL.ID_VELATORIO=VEL.ID_VELATORIO) AS salasEmbalsamamiento, "
			+"(SELECT COUNT(*) FROM SVC_SALA SAL WHERE IND_TIPO_SALA=0 AND SAL.ID_VELATORIO=VEL.ID_VELATORIO) AS salasCremacion, "
			+"(SELECT COUNT(*) FROM SVC_CAPILLA CAP WHERE CAP.ID_VELATORIO=VEL.ID_VELATORIO) AS capillas")
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
      //  request.getDatos().remove(AppConstantes.DATOS);
	    request.setDatos(parametros);
		return request;
	}

		//Agregar velatorio
	public DatosRequest insertar() {
		DatosRequest request= new DatosRequest();
		Map<String, Object> parametro = new HashMap<>();
		final QueryHelper q = new QueryHelper("INSERT INTO SVC_VELATORIO");
		q.agregarParametroValues("NOM_VELATORIO", "'" + this.nomVelatorio + "'");
		q.agregarParametroValues("NOM_RESPO_SANITARIO", "'" + this.nomRespoSanitario + "'");
		q.agregarParametroValues("CVE_ASIGNACION", "'" + this.cveAsignacion + "'");
		q.agregarParametroValues("DES_CALLE", "'" + this.desCalle + "'");
		q.agregarParametroValues("NUM_EXT", "'"+ this.numExterior + "'");
		q.agregarParametroValues("ID_CODIGO_POSTAL", "'" + this.idCodigoPostal +"'");
		q.agregarParametroValues("NUM_TELEFONO", "'" + this.numTelefono +"'");
		q.agregarParametroValues("" +AppConstantes.INDESTATUS + "", "1");
		q.agregarParametroValues("ID_USUARIO_ALTA", "" + this.idUsuarioAlta + "");
		q.agregarParametroValues("FEC_ALTA", ""+AppConstantes.CURRENT_TIMESTAMP+"");
		q.agregarParametroValues("ID_DELEGACION", "" + this.idDelegacion + "");
		
		String query = q.obtenerQueryInsertar();
		parametro.put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
		request.setDatos(parametro);

		return request;
	}
		//Actualizar Velatorio
	public DatosRequest actualizar() {
		DatosRequest request= new DatosRequest();
		Map<String, Object> parametro = new HashMap<>();
		final QueryHelper q = new QueryHelper("UPDATE SVC_VELATORIO");
		q.agregarParametroValues("NOM_VELATORIO", "'" + this.nomVelatorio + "'");
		q.agregarParametroValues("NOM_RESPO_SANITARIO", "'" + this.nomRespoSanitario + "'");
		q.agregarParametroValues("CVE_ASIGNACION", "'" + this.cveAsignacion + "'");
		q.agregarParametroValues("DES_CALLE", "'" + this.desCalle + "'");
		q.agregarParametroValues("NUM_EXT", "'"+ this.numExterior + "'");
		q.agregarParametroValues("ID_CODIGO_POSTAL", "'" + this.idCodigoPostal +"'");
		q.agregarParametroValues("NUM_TELEFONO", "'" + this.numTelefono +"'");
		q.agregarParametroValues("" +AppConstantes.INDESTATUS + "", "" + this.indEstatus +"");
		q.agregarParametroValues("ID_USUARIO_MODIFICA", "'" + this.idUsuarioModifica + "'");
		q.agregarParametroValues("FEC_ACTUALIZACION", ""+AppConstantes.CURRENT_TIMESTAMP+"");
		q.agregarParametroValues("ID_DELEGACION", "'" + this.idDelegacion +"'");
		if(this.indEstatus==0) {
			q.agregarParametroValues("FEC_BAJA", ""+AppConstantes.CURRENT_TIMESTAMP+"");
			q.agregarParametroValues("ID_USUARIO_BAJA", "'" + this.idUsuarioBaja + "'");
		}
		
		q.addWhere("ID_VELATORIO = " + this.idVelatorio);
		String query = q.obtenerQueryActualizar();
		parametro.put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
		request.setDatos(parametro);
		return request;
	}
		//Buscar velatorio por delegacion o velatorio especifico
	public DatosRequest buscarVelatorio(DatosRequest request) {
		String palabra = request.getDatos().get(""+AppConstantes.PALABRA+"").toString();
		String query ="SELECT E.ID_VELATORIO AS idVelatorio, E.NOM_VELATORIO AS nomVelatorio, "
				+ "E.NOM_RESPO_SANITARIO AS nomRespoSanitario, E.CVE_ASIGNACION AS cveAsignacion, E.DES_CALLE AS desCalle, "
				+ "E.NUM_EXT AS numExterior, E.ID_CODIGO_POSTAL AS idCp, E.NUM_TELEFONO AS numTelefono, E.IND_ESTATUS AS estatus,"
				+ "E.DES_COLONIA AS otraColonia, E.ID_DELEGACION AS idDelegacion, D.DES_DELEGACION AS desDelegacion, "
				+ "(SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=1 AND ID_VELATORIO=E.ID_VELATORIO) AS salasEmbalsamamiento, "
				+ "(SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=0 AND ID_VELATORIO=E.ID_VELATORIO) AS salasCremacion, "
				+ "(SELECT COUNT(*) FROM SVC_CAPILLA WHERE ID_VELATORIO=E.ID_VELATORIO) AS capillas, "
		     	+ "(SELECT DES_MNPIO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS desMunicipio, "
				+ "(SELECT DES_ESTADO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS desEstado, "
				+ "(SELECT DES_COLONIA FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS desColonia, "
				+ "(SELECT CVE_CODIGO_POSTAL FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS cveCp, "
				+ "(SELECT CONCAT (NOM_USUARIO,' ', NOM_APELLIDO_PATERNO ,' ', NOM_APELLIDO_MATERNO) " 
				+ " FROM  SVT_USUARIOS WHERE ID_VELATORIO=E.ID_VELATORIO) AS administrador"
				+ " FROM SVC_VELATORIO E JOIN SVC_DELEGACION D ON E.ID_DELEGACION = D.ID_DELEGACION"
				+ " WHERE NOM_VELATORIO LIKE '%" + palabra + "%'  OR DES_DELEGACION LIKE '%" + palabra + "%'";
		log.info(query);
		request.getDatos().remove(""+AppConstantes.PALABRA+"");
		request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
		return request;
	}
		//Cambiar estatus
	public DatosRequest cambiarEstatus() {
		DatosRequest request= new DatosRequest();
		Map<String, Object> parametro = new HashMap<>();
		final QueryHelper q = new QueryHelper("UPDATE SVC_VELATORIO");
		q.agregarParametroValues("" +AppConstantes.INDESTATUS + "", "" + this.indEstatus +"");
		if(this.indEstatus==0) {
			q.agregarParametroValues("FEC_BAJA", ""+AppConstantes.CURRENT_TIMESTAMP+"");
			q.agregarParametroValues("ID_USUARIO_BAJA",  "'" + this.idUsuarioBaja + "'");
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
				String query = "SELECT *  FROM SVC_VELATORIO WHERE NOM_VELATORIO=  '"+nomVelatorio +"' ";
				String encoded=DatatypeConverter.printBase64Binary(query.getBytes());
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
