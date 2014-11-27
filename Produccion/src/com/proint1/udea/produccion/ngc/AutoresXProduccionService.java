package com.proint1.udea.produccion.ngc;

import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.util.ProduccionBLException;


public interface AutoresXProduccionService {

	public List<TbPrdAutoresxproduccion> listar(TbPrdGrupoinvestigacion grupo)throws ProduccionBLException;
	
	
}
