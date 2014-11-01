package com.proint1.udea.produccion.ngc.impl;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.dao.GrupoInvestigacionDAO;
import com.proint1.udea.produccion.dto.MiembrosGrupoInvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.ngc.GrupoInvestigacionService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class GrupoInvestigacionServiceImpl implements GrupoInvestigacionService {
	
	private static final Logger logger = Logger.getLogger(GrupoInvestigacionServiceImpl.class);
	
	GrupoInvestigacionDAO grupoInvestigacionDAO;
	
	/**Implementacipon para entregar la lista de todos los Grupos de investigacion existentes en la base de datos
	 * @return Lista de grupos de investigacion
	 * @throws ProduccionBLException, si ocurre problema obteniendo la lista de dispositivos
	 */	
	@Override
	public List<TbPrdGrupoinvestigacion> listar() throws ProduccionBLException {
		logger.info("-- Iniciando consulta de grupos de investigacion");
		try {
			return  grupoInvestigacionDAO.listar();
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException("No se pudo obtener la lista de grupos de investigacion. " + e.getMessage());
		}
	}
	
	/**
	 * Implementacion para entregar una lista de Grupos de Investigacion filtrados por su nombre
	 * @param nombre del grupo de investigacion que desea ser buscado
	 * @return Lista de Grupos de Investigacion
	 * @throws ProduccionBLException, si ocurre problema obteniendo la lista de Grupos de Investigacion
	 */
	@Override
	public List<TbPrdGrupoinvestigacion> obtenerGrupos(String nombre) throws ProduccionBLException {
		List<TbPrdGrupoinvestigacion> grupos;
		try {
			grupos = grupoInvestigacionDAO.obtenerGrupos(nombre);
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException("No se pudo obtener la lista de grupos de investigacion por criterio de nombre");
		}
		
		return grupos;
	}

	/**
	 * Implementacion para actualizar los miembros pertenecientes a un grupo de investigacion
	 * @param idGrupo identificacion del grupo que se desea modificar
	 * @param nuevosMiembros lista de los nuevos miembros
	 * @throws ProduccionBLException en caso de suceder errores en la actualizacion con los datos recibidos
	 * @throws ProduccionDAOException 
	 */
	@Override
	public void actualizarMiembros(long idGrupo,List<MiembrosGrupoInvestigacion> nuevosMiembros)	throws ProduccionBLException, ProduccionDAOException {
		//Validacion de los datos
		if ("".equals(idGrupo)) {
			throw new ProduccionBLException("La identificacion del grupo no puede ser nula, ni vacía");
		}
		if (nuevosMiembros == null || nuevosMiembros.size()<=0) {
			throw new ProduccionBLException("No existe lista de los nuevos miembros");
		}
		
		//Elimina todos los integrantes del grupo 
		grupoInvestigacionDAO.eliminarIntegrantesGrupo(idGrupo);
		//Genera los registros de los nuevos integrantes
		grupoInvestigacionDAO.asignarNuevosIntegantes(idGrupo,nuevosMiembros);
	}
	
	@Override
	public boolean insertarGrupoInvestigacion(TbAdmPersona auxiliar,TbAdmPersona director,
			String vrNombre, String vrAbreviatura, Date dtFechacreacion,
			char blEstado, String vrAdtusuario, Date dtAdtfecha)  {
	
			//TODO: Organizar validaciones del servicio 

			try {
				TbPrdGrupoinvestigacion nuevoGrupo = new TbPrdGrupoinvestigacion(auxiliar, director, vrNombre,vrAbreviatura,dtFechacreacion, blEstado, vrAdtusuario, dtAdtfecha);
				grupoInvestigacionDAO.insertarGrupoInvestigacion(nuevoGrupo);
				
				return true;
			} catch (ProduccionDAOException e) {
				return false;
			}
		}

	@Override
	public boolean actualizarGrupoInvestigacion(TbAdmPersona auxiliar,TbAdmPersona director,
			String vrNombre, String vrAbreviatura, Date dtFechacreacion,
			char blEstado, String vrAdtusuario, Date dtAdtfecha)  {
	
			//TODO: Organizar validaciones del servicio 

			try {
				TbPrdGrupoinvestigacion nuevoGrupo = new TbPrdGrupoinvestigacion(auxiliar, director, vrNombre, vrAbreviatura,dtFechacreacion, blEstado, vrAdtusuario, dtAdtfecha);
				grupoInvestigacionDAO.insertarGrupoInvestigacion(nuevoGrupo);
				
				return true;
			} catch (ProduccionDAOException e) {
				return false;
			}
		}
	
	@Override
	public void eliminarGrupoInvesticacion(long nbIdn, TbAdmPersona persona, String vrNombre, String vrAbreviatura, Date dtFechacreacion,
			char blEstado, String vrAdtusuario, Date dtAdtfecha) throws ProduccionBLException, ProduccionDAOException {
		
				
			ProduccionBLException blEx = null;
			
			if ("".equals(nbIdn)) {
				blEx = new ProduccionBLException("La identificación del grupo no puede ser nula ni vacía");
				logger.warn(blEx.getMessage());
				throw blEx;
			}
			
			TbPrdGrupoinvestigacion grupoinvestigacion = new TbPrdGrupoinvestigacion();
			grupoinvestigacion.setNbIdn(nbIdn);
			
			grupoInvestigacionDAO.eliminarGrupoInvestigacion(grupoinvestigacion);
			}

	


	public GrupoInvestigacionDAO getGrupoInvestigacionDAO() {
		return grupoInvestigacionDAO;
	}


	public void setGrupoInvestigacionDAO(GrupoInvestigacionDAO grupoInvestigacionDAO) {
		this.grupoInvestigacionDAO = grupoInvestigacionDAO;
	}
}
	