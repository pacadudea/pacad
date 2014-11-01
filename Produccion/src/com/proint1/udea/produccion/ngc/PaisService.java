package com.proint1.udea.produccion.ngc;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPaises;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface PaisService {

	public List<TbAdmPaises> listar()throws ProduccionBLException;
}
