package com.proint1.udea.produccion.ngc;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.TbAdmTipoIdentificacion;
import com.proint1.udea.produccion.util.ProduccionBLException;

public interface TipoIdentificacionService {
	
	public List<TbAdmTipoIdentificacion> listar()throws ProduccionBLException;
	

}
