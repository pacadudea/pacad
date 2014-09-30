package com.proint1.udea.produccion.ngc;

import java.util.Date;
import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.Persona;
import com.proint1.udea.produccion.dto.MiembrosGrupoInvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
//import com.proint1.udea.produccion.entidades.Persona;
 

public interface GrupoInvestigacionService {
	
	
	/**
	 * Entrega la lista de todos los Grupos de investigacion existentes en la base de datos
	 * @return Lista de grupos de investigacion
	 * @throws ProduccionBLException, si ocurre problema obteniendo la lista de dispositivos
	 */
	public List<TbPrdGrupoinvestigacion> listar() throws ProduccionBLException;
	
	/**
	 * Entrega una lista de Grupos de Investigacion filtrados por su nombre
	 * @param nombre del grupo de investigacion que desea ser buscado
	 * @return Lista de Grupos de Investigacion
	 * @throws ProduccionBLException, si ocurre problema obteniendo la lista de Grupos de Investigacion
	 */
	public List<TbPrdGrupoinvestigacion> obtenerGrupos(String nombre) throws ProduccionBLException;
	
	/**
	 * Actualiza los miembros pertenecientes a un grupo de investigacion
	 * @param idGrupo identificacion del grupo que se desea modificar
	 * @param nuevosMiembros lista de los nuevos miembros
	 * @throws ProduccionBLException en caso de suceder errores en la actualizacion con los datos recibidos
	 * @throws ProduccionDAOException 
	 */
	public void actualizarMiembros(long idGrupo, List<MiembrosGrupoInvestigacion> nuevosMiembros) throws ProduccionBLException, ProduccionDAOException;
	
	
	public void insertarGrupoInvestigacion(long nbIdn, Persona persona, String vrNombre, String vrAbreviatura, Date dtFechacreacion, 
			char blEstado, String vrAdtusuario, Date dtAdtfecha) throws ProduccionBLException, ProduccionDAOException; 
					//throws ProduccionBLException, ProduccionDAOException;
	
	public void eliminarGrupoInvesticacion(long nbIdn, Persona persona, String vrNombre, String vrAbreviatura, Date dtFechacreacion, 
			char blEstado, String vrAdtusuario, Date dtAdtfecha)throws ProduccionBLException, ProduccionDAOException;
	
	public void editarGrupoInvesticacion(long nbIdn, Persona persona, String vrNombre, String vrAbreviatura, Date dtFechacreacion, 
			char blEstado, String vrAdtusuario, Date dtAdtfecha)throws ProduccionBLException, ProduccionDAOException;

	}
