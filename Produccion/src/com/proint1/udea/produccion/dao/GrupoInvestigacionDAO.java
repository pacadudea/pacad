package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.produccion.dto.MiembrosGrupoInvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdMiembrosxgrupo;
import com.proint1.udea.produccion.util.ProduccionDAOException;

/**
 * Interfaz que define los metodos que va a proveer el DAO de Grupos de Investigacion
 * @author Edison Castro
 *
 */

public interface GrupoInvestigacionDAO {

	/**
	 * Entrega la lista de todos los grupos de investigacion existentes en la base de datos
	 * @return Lista de grupos de investigacion
	 * @throws ProduccionDAOException, si ocurre problema obteniendo la lista de grupos
	 */
	public List<TbPrdGrupoinvestigacion> listar() throws ProduccionDAOException;
	
	/**
	 * Entrega una lista de grupos de investigacion dependiendo de un nombre 
	 * @param nombre de los grupos de los cuales se espera obtener la lista
	 * @return Lista de grupos de investigacion
	 * @throws ProduccionDAOException, si ocurre problema obteniendo la lista de dispositivos
	 */
	public List<TbPrdGrupoinvestigacion> obtenerGrupos(String nombre) throws ProduccionDAOException;

	/**
	 * Guarda un registro de grupo de investigacion en la base de datos
	 * @param grupo Instancia del TbPrdGrupoinvestigacion a guardar
	 * @throws ProduccionDAOException,  si ocurre un problema en la operación DAO de guardado
	 */
	public void insertarGrupoInvestigacion(TbPrdGrupoinvestigacion grupo)  throws ProduccionDAOException;
	
	/**
	 * Elimina un registro de grupo de investigación de la base de datos, solo se puede eliminar si no tiene producciones
	 * @param grupo Instancia del Grupo a eliminar
	 * @throws ProduccionDAOException si ocurre un problema en la operación DAO de eliminar
	 */
	public void eliminarGrupoInvestigacion(TbPrdGrupoinvestigacion grupo)  throws ProduccionDAOException;
	
	/**
	 * Actualiza un registro de Grupo de investigacion en la base de datos
	 * @param grupo Instancia del dispositivo a actualizar
	 * @throws ProduccionDAOException si ocurre un problema en la operación DAO de actualizado
	 */
	public void editarGrupoInvestigacion(TbPrdGrupoinvestigacion grupo)  throws ProduccionDAOException;

	/**
	 * Elimina todos los integrantes de un grupo de investigacion
	 * @param grupo Instancia del Grupo a eliminar
	 * @throws ProduccionDAOException si ocurre un problema en la operación DAO de eliminar
	 */
	public void eliminarIntegrantesGrupo(long grupo)  throws ProduccionDAOException;
	
	/**
	 * Asigna nuevos integrantes al grupo de investigaciòn
	 * @param nuevosIntegrantes
	 * @throws ProduccionDAOException
	 */
	public void asignarNuevosIntegantes(long idGrupo, List<MiembrosGrupoInvestigacion> nuevosIntegrantes)  throws ProduccionDAOException;
}
