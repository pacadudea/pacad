package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface AutoresXProduccionDAO {

	public List<TbPrdAutoresxproduccion> listar(TbPrdGrupoinvestigacion grupo) throws ProduccionDAOException, ProduccionBLException;
	
	
}
