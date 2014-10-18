package com.proint1.udea.produccion.ctl;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.ngc.AutorService;
import com.proint1.udea.produccion.util.ProduccionBLException;

public class PruebaCtl extends GenericForwardComposer{
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger=Logger.getLogger(PruebaCtl.class);
	
	/**
	 * beans del negocio usados
	 */
	AutorService autorService;
	
	public List<TbPrdAutor>  listarDatos() throws ProduccionBLException{
		System.err.println("llego aqui");
		List<TbPrdAutor> autores = autorService.listar();
		System.err.println("paso aqui");
		return autores;
	}
	

	public AutorService getAutorService() {
		return autorService;
	}
	public void setAutorService(AutorService autorService) {
		this.autorService = autorService;
	}
	
	
	
}
