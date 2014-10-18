package com.proint1.udea.produccion.ngc;

import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdCampo;
import com.proint1.udea.produccion.util.ProduccionBLException;

public interface CampoService {
	
	public List<TbPrdCampo> listar()throws ProduccionBLException;

	public void insertar(String descripcion, boolean estado)throws ProduccionBLException;

	public TbPrdCampo obtener(long id)throws ProduccionBLException;
}
