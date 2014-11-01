package com.proint1.udea.produccion.ngc;

import java.util.HashMap;
import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
 
public interface ProduccionService {
	
	public List<TbPrdProduccion> listar()throws ProduccionBLException;
	
	public void insertar(String titulo, List<TbPrdAutor> autores, String palabrasClave, 
			long tipoProduccion, HashMap<Long, String> informacion)throws ProduccionBLException;
	
	public TbPrdProduccion obtener(long id)throws ProduccionBLException;
}