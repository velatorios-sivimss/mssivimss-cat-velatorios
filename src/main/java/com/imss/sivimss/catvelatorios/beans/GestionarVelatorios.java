package com.imss.sivimss.catvelatorios.beans;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;


import com.imss.sivimss.catvelatorios.model.request.BuscarVelatoriosRequest;
import com.imss.sivimss.catvelatorios.model.request.VelatoriosRequest;
import com.imss.sivimss.catvelatorios.util.AppConstantes;
import com.imss.sivimss.catvelatorios.util.DatosRequest;
import com.imss.sivimss.catvelatorios.util.QueryHelper;

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
	public DatosRequest catalogoVelatorio(DatosRequest request) {
		String query =" SELECT E.ID_VELATORIO AS ID, E.NOM_VELATORIO AS NOMBRE,"
				+ " E.NOM_RESPO_SANITARIO AS RESPONSABLE, E.CVE_ASIGNACION AS ASIGNACION, E.DES_CALLE AS CALLE, "
				+ " E.NUM_EXT AS NOEXTERIOR, E.ID_CODIGO_POSTAL AS IDCP, E.NUM_TELEFONO AS TELEFONO, E.IND_ESTATUS AS ESTATUS,"
				+ " E.DES_COLONIA AS OTRA_COLONIA, D.DES_DELEGACION AS DELEGACION,"
				+ " (SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=1 AND ID_VELATORIO=E.ID_VELATORIO) AS SALA_EMBALSAMAMIENTO,"
				+ " (SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=0 AND ID_VELATORIO=E.ID_VELATORIO) AS SALA_CREMACION,"
				+ " (SELECT COUNT(*) FROM SVC_CAPILLA WHERE ID_VELATORIO=E.ID_VELATORIO) AS CAPILLAS,"
		     	+ " (SELECT DES_MNPIO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS MUNICIPIO, "
				+ " (SELECT DES_ESTADO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS ESTADO,"
				+ " (SELECT DES_COLONIA FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS COLONIA,"
				+ " (SELECT CVE_CODIGO_POSTAL FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS CP,"
				+ " (SELECT CONCAT (NOM_USUARIO,' ', NOM_APELLIDO_PATERNO ,' ', NOM_APELLIDO_MATERNO)" 
				+ "FROM  SVT_USUARIOS WHERE ID_VELATORIO=E.ID_VELATORIO) AS ADMINISTRADOR"
				+ " FROM SVC_VELATORIO E JOIN SVC_DELEGACION D ";
		request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));

		return request;
	}

		//Agregar velatorio
	public DatosRequest insertar() {
		DatosRequest request= new DatosRequest();
		Map<String, Object> parametro = new HashMap<>();
		final QueryHelper q = new QueryHelper("INSERT INTO SVC_VELATORIO");
		q.agregarParametroValues("NOM_VELATORIO", "'" + this.nomVelatorio + "'");
		q.agregarParametroValues("NOM_RESPO_SANITARIO", "'" + this.nomRespoSanitario + "'");
		q.agregarParametroValues("CVE_ASIGNACION", "'" + this.getCveAsignacion() + "'");
		q.agregarParametroValues("DES_CALLE", "'" + this.desCalle + "'");
		q.agregarParametroValues("NUM_EXT", "'"+ this.numExterior + "'");
		q.agregarParametroValues("ID_CODIGO_POSTAL", "'" + this.idCodigoPostal +"'");
		q.agregarParametroValues("NUM_TELEFONO", "'" + this.numTelefono +"'");
		q.agregarParametroValues("" +AppConstantes.INDESTATUS + "", "1");
		q.agregarParametroValues("ID_USUARIO_ALTA", "" + this.idUsuarioAlta + "");
		q.agregarParametroValues("FEC_ALTA", ""+AppConstantes.NOW+"");
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
		q.agregarParametroValues("CVE_ASIGNACION", "'" + this.getCveAsignacion() + "'");
		q.agregarParametroValues("DES_CALLE", "'" + this.desCalle + "'");
		q.agregarParametroValues("NUM_EXT", "'"+ this.numExterior + "'");
		q.agregarParametroValues("ID_CODIGO_POSTAL", "'" + this.idCodigoPostal +"'");
		q.agregarParametroValues("NUM_TELEFONO", "'" + this.numTelefono +"'");
		q.agregarParametroValues("" +AppConstantes.INDESTATUS + "", "" + this.indEstatus +"");
		q.agregarParametroValues("ID_USUARIO_MODIFICA", "'" + this.idUsuarioModifica + "'");
		q.agregarParametroValues("FEC_ACTUALIZACION", ""+AppConstantes.NOW+"");
		q.agregarParametroValues("ID_DELEGACION", "'" + this.idDelegacion +"'");
		if(this.indEstatus==0) {
			q.agregarParametroValues("FEC_BAJA", ""+AppConstantes.NOW+"");
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
		String palabra = request.getDatos().get("palabra").toString();
		String query = "SELECT E.ID_VELATORIO AS ID, E.NOM_VELATORIO AS NOMBRE,"
				+ "E.NOM_RESPO_SANITARIO AS RESPONSABLE, E.CVE_ASIGNACION AS ASIGNACION, E.DES_CALLE AS CALLE, "
				+ "E.NUM_EXT AS NOEXTERIOR, E.ID_CODIGO_POSTAL AS IDCP, E.NUM_TELEFONO AS TELEFONO, E.IND_ESTATUS AS ESTATUS,"
				+ "E.DES_COLONIA AS OTRA_COLONIA, D.DES_DELEGACION AS DELEGACION,"
				+ "(SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=1 AND ID_VELATORIO=E.ID_VELATORIO) AS SALA_EMBALSAMAMIENTO,"
				+ "(SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=0 AND ID_VELATORIO=E.ID_VELATORIO) AS SALA_CREMACION,"
				+ "(SELECT COUNT(*) FROM SVC_CAPILLA WHERE ID_VELATORIO=E.ID_VELATORIO) AS CAPILLAS,"
		     	+ "(SELECT DES_MNPIO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS MUNICIPIO, "
				+ "(SELECT DES_ESTADO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS ESTADO,"
				+ "(SELECT DES_COLONIA FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS COLONIA,"
				+ "(SELECT CVE_CODIGO_POSTAL FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS CP,"
				+ "(SELECT CONCAT (NOM_USUARIO,' ', NOM_APELLIDO_PATERNO ,' ', NOM_APELLIDO_MATERNO)" 
				+ " FROM  SVT_USUARIOS WHERE ID_VELATORIO=E.ID_VELATORIO) AS ADMINISTRADOR"
				+ " FROM SVC_VELATORIO E JOIN SVC_DELEGACION D ON E.ID_DELEGACION = D.ID_DELEGACION"
				+ " WHERE NOM_VELATORIO LIKE '%" + palabra + "%'  OR DES_DELEGACION LIKE '%" + palabra + "%'";
		request.getDatos().remove("palabra");
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
			q.agregarParametroValues("FEC_BAJA", ""+AppConstantes.NOW+"");
			q.agregarParametroValues("ID_USUARIO_BAJA", "'" + this.idUsuarioBaja + "'");
		} 
		q.addWhere("ID_VELATORIO = " + this.idVelatorio);
			String query = q.obtenerQueryActualizar();
		    String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
			parametro.put(AppConstantes.QUERY, encoded);
			request.setDatos(parametro);
			return request;	
	}
		
	//Buscar por delegacion, velatorio especifico o doble filtrado
		public DatosRequest velatorioPorDelegacion(DatosRequest request, BuscarVelatoriosRequest buscar) {
			String query="";
			if(buscar.getVelatorio()==null || buscar.getDelegacion()==null) {
				
				query = "SELECT E.ID_VELATORIO AS ID, E.NOM_VELATORIO AS NOMBRE,"
						+ "E.NOM_RESPO_SANITARIO AS RESPONSABLE, E.CVE_ASIGNACION AS ASIGNACION, E.DES_CALLE AS CALLE, "
						+ "E.NUM_EXT AS NOEXTERIOR, E.ID_CODIGO_POSTAL AS IDCP, E.NUM_TELEFONO AS TELEFONO, E.IND_ESTATUS AS ESTATUS,"
						+ "E.DES_COLONIA AS OTRA_COLONIA, D.DES_DELEGACION AS DELEGACION,"
						+ "(SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=1 AND ID_VELATORIO=E.ID_VELATORIO) AS SALA_EMBALSAMAMIENTO,"
						+ "(SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=0 AND ID_VELATORIO=E.ID_VELATORIO) AS SALA_CREMACION,"
						+ "(SELECT COUNT(*) FROM SVC_CAPILLA WHERE ID_VELATORIO=E.ID_VELATORIO) AS CAPILLAS,"
				     	+ "(SELECT DES_MNPIO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS MUNICIPIO, "
						+ "(SELECT DES_ESTADO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS ESTADO,"
						+ "(SELECT DES_COLONIA FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS COLONIA,"
						+ "(SELECT CVE_CODIGO_POSTAL FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS CP,"
						+ "(SELECT CONCAT (NOM_USUARIO,' ', NOM_APELLIDO_PATERNO ,' ', NOM_APELLIDO_MATERNO)" 
						+ " FROM  SVT_USUARIOS WHERE ID_VELATORIO=E.ID_VELATORIO) AS ADMINISTRADOR"
						+ " FROM SVC_VELATORIO E JOIN SVC_DELEGACION D ON E.ID_DELEGACION = D.ID_DELEGACION "
						+ " WHERE NOM_VELATORIO LIKE '%" + buscar.getVelatorio() +"%'  OR DES_DELEGACION LIKE '%" + buscar.getDelegacion() +  "%'";
					request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes()));
					return request;
			}else {
				query = "SELECT E.ID_VELATORIO AS ID, E.NOM_VELATORIO AS NOMBRE, "
						+ "E.NOM_RESPO_SANITARIO AS RESPONSABLE, E.CVE_ASIGNACION AS ASIGNACION, E.DES_CALLE AS CALLE,"
						+ "E.NUM_EXT AS NOEXTERIOR, E.ID_CODIGO_POSTAL AS IDCP, E.NUM_TELEFONO AS TELEFONO, E.IND_ESTATUS AS ESTATUS, "
						+ "E.DES_COLONIA AS OTRA_COLONIA, D.DES_DELEGACION AS DELEGACION, "
						+ "(SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=1 AND ID_VELATORIO=E.ID_VELATORIO) AS SALA_EMBALSAMAMIENTO, "
						+ "(SELECT COUNT(*) FROM svc_sala WHERE IND_TIPO_SALA=0 AND ID_VELATORIO=E.ID_VELATORIO) AS SALA_CREMACION, "
						+ "(SELECT COUNT(*) FROM SVC_CAPILLA WHERE ID_VELATORIO=E.ID_VELATORIO) AS CAPILLAS, "
				     	+ "(SELECT DES_MNPIO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS MUNICIPIO,"
						+ "(SELECT DES_ESTADO FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS ESTADO, "
						+ "(SELECT DES_COLONIA FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS COLONIA, "
						+ "(SELECT CVE_CODIGO_POSTAL FROM SVC_CP WHERE ID_CODIGO_POSTAL=E.ID_CODIGO_POSTAL) AS CP, "
						+ "(SELECT CONCAT (NOM_USUARIO,' ', NOM_APELLIDO_PATERNO ,' ', NOM_APELLIDO_MATERNO) " 
						+ "FROM  SVT_USUARIOS WHERE ID_VELATORIO=E.ID_VELATORIO) AS ADMINISTRADOR "
						+ "FROM SVC_VELATORIO E JOIN SVC_DELEGACION D ON E.ID_DELEGACION = D.ID_DELEGACION "
						+ "WHERE NOM_VELATORIO LIKE '%" + buscar.getVelatorio() + "%' AND DES_DELEGACION LIKE '%" + buscar.getDelegacion() + "%'";
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

		}
