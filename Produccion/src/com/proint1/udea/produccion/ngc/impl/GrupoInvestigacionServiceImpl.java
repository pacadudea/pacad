package com.proint1.udea.produccion.ngc.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.administracion.entidades.terceros.Persona;
import com.proint1.udea.produccion.dao.GrupoInvestigacionDAO;
import com.proint1.udea.produccion.dto.MiembrosGrupoInvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdMiembrosxgrupo;
import com.proint1.udea.produccion.ngc.GrupoInvestigacionService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

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
	public void insertarGrupoInvestigacion(long nbIdn,Persona persona,
			String vrNombre, String vrAbreviatura, Date dtFechacreacion,
			char blEstado, String vrAdtusuario, Date dtAdtfecha) throws ProduccionBLException, ProduccionDAOException {
	
		if(vrNombre == null || "".equals(vrNombre)){
			throw new ProduccionBLException("El campo nombre no puede ser nulo o vacío");
		}
		
		if(vrAbreviatura == null || "".equals(vrAbreviatura)){
			throw new ProduccionBLException("El campo abreviatura no puede ser nulo o vacío");
		}
			
			if(dtFechacreacion == null || "".equals(dtFechacreacion)){
				throw new ProduccionBLException("La fecha de creacion no puede ser nula o vacía");
			}
			TbPrdGrupoinvestigacion grupoInvestigacion = new TbPrdGrupoinvestigacion();
			grupoInvestigacion.setNbIdn(nbIdn);
			grupoInvestigacion.setVrNombre(vrNombre);
			grupoInvestigacion.setVrAbreviatura(vrAbreviatura);
			grupoInvestigacion.setDtFechacreacion(dtFechacreacion);
			grupoInvestigacion.setBlEstado(blEstado);
			grupoInvestigacion.setVrAdtusuario(vrAdtusuario);
			grupoInvestigacion.setDtAdtfecha(dtAdtfecha);

			grupoInvestigacionDAO.insertarGrupoInvestigacion(grupoInvestigacion);
					
		}

	@Override
	public void eliminarGrupoInvesticacion(long nbIdn, Persona persona, String vrNombre, String vrAbreviatura, Date dtFechacreacion,
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

	@Override
	public void editarGrupoInvesticacion(long nbIdn, Persona persona,
			String vrNombre, String vrAbreviatura, Date dtFechacreacion,
			char blEstado, String vrAdtusuario, Date dtAdtfecha) throws ProduccionDAOException {
		
		TbPrdGrupoinvestigacion grupoInvestigacion = new TbPrdGrupoinvestigacion();
		grupoInvestigacion.setNbIdn(nbIdn);
		grupoInvestigacion.setVrNombre(vrNombre);
		grupoInvestigacion.setVrAbreviatura(vrAbreviatura);
		grupoInvestigacion.setDtFechacreacion(dtFechacreacion);
		grupoInvestigacion.setBlEstado(blEstado);
		grupoInvestigacion.setVrAdtusuario(vrAdtusuario);
		grupoInvestigacion.setDtAdtfecha(dtAdtfecha);

		grupoInvestigacionDAO.editarGrupoInvestigacion(grupoInvestigacion);
		
	}


	public GrupoInvestigacionDAO getGrupoInvestigacionDAO() {
		return grupoInvestigacionDAO;
	}


	public void setGrupoInvestigacionDAO(GrupoInvestigacionDAO grupoInvestigacionDAO) {
		this.grupoInvestigacionDAO = grupoInvestigacionDAO;
	}
}
	