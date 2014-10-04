package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccionesxcampo;
import com.proint1.udea.produccion.util.ProduccionDAOException;
	
public interface TipoProduccionDAO {
	/**
	 * Obtiene la lista de TiposProduccion
	 * @return lista de Tipos Produccion
	 */
	public List<TbPrdTipoproduccion> listar()throws ProduccionDAOException;

	/**
	 * Insertar un tipoProduccion
	 * @param tipoProduccion
	 */
	public void insertar(TbPrdTipoproduccion tipoProduccion)throws ProduccionDAOException;

	/**
	 * Eliminar un tipoProduccion
	 * @param tipoProduccion
	 */
	public void eliminar(TbPrdTipoproduccion tipoProduccion)throws ProduccionDAOException;
	
	/**
	 * Editar un tipoProduccion
	 * @param tipoProduccion
	 */
	public void editar(TbPrdTipoproduccion tipoProduccion)throws ProduccionDAOException;
	
	/**
	 * Obtener un tipoProduccion con su id como parametro
	 * @param Long id identificador del tipoProduccion.
	 * @return TbPrdTipoproduccion
	 */
	public TbPrdTipoproduccion obtener(long id)throws ProduccionDAOException;
	
	public List<TbPrdTipoproduccionesxcampo> obtenerCamposXTipo(
			long idTipoProduccion)  throws ProduccionDAOException;
	
	
}