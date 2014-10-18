package com.proint1.udea.produccion.ngc;

import java.util.HashMap;
import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
 
public interface ProduccionService {
	public List<TbPrdProduccion> listar();
	
	public void insertar(String titulo, List<TbPrdAutor> autores, String palabrasClave, long tipoProduccion, HashMap<Long, String> informacion);
	
	public TbPrdProduccion obtener(long id);
}