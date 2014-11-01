package com.proint1.udea.produccion.ngc;

import java.util.Date;
import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdCampos;
import com.proint1.udea.produccion.util.ProduccionBLException;

public interface CampoService {
	
	public List<TbPrdCampos> listar()throws ProduccionBLException;

	public TbPrdCampos obtener(long id)throws ProduccionBLException;
	
	public void insertar(String vrDescripcion,long vrTipocampo,String nbTamañocampo,
			Boolean nbDecimales,String vrAdtusuario)throws ProduccionBLException;

	 
}
