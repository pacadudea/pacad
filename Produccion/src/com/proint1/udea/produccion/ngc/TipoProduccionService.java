package com.proint1.udea.produccion.ngc;

import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccionesxcampo;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
 

public interface TipoProduccionService {
	/**
	 * Obtener la lista de Tipo Produccion desde la base de datos.
	 * @return lista de TiposProduccion
	 */
	public List<TbPrdTipoproduccion> listar()throws ProduccionBLException;
	
	/**
	 * Insertar un TipoProducion
	 */
	public void insertar()throws ProduccionBLException;
	
	/**
	 * Obtener desde la base de datos un Tipoproduccion
	 * @param long id de tipoProduccion 
	 * @return TipoProduccion
	 */
	public TbPrdTipoproduccion obtener(long id)throws ProduccionBLException;
	
	/**
	 * 
	 * @param idTipoProduccion
	 * @return
	 */
	public List<TbPrdTipoproduccionesxcampo> obtenerCamposXTipo(long idTipoProduccion)  throws ProduccionDAOException ;
	
}