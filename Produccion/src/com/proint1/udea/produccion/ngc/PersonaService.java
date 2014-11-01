package com.proint1.udea.produccion.ngc;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface PersonaService {

	public List<TbAdmPersona> listar()throws ProduccionBLException;
	
	/**
	 * Busca una persona por el numero de su identificacion
	 * @param identificacion: Identificacion a buscar
	 * @return TbAdmPersona: Instancia de la persona encontrada o null
	 * @throws ProduccionBLException
	 */
	public TbAdmPersona buscarPersona(String identificacion)throws ProduccionBLException;
	
}
