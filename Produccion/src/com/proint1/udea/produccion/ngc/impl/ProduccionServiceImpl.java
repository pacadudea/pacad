package com.proint1.udea.produccion.ngc.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.proint1.udea.produccion.dao.ProduccionDAO;
import com.proint1.udea.produccion.entidades.ReporteProduccion;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdDetalleproducciones;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.ngc.ProduccionService;

public class ProduccionServiceImpl implements ProduccionService {

	ProduccionDAO produccionDAO;

	private static Logger logger = Logger
			.getLogger(ProduccionServiceImpl.class);

	@Override
	public List<TbPrdProduccion> listar() {
		logger.info("Iniciando consulta de Producciones");
		return produccionDAO.listar();
	}

	@Override
	public TbPrdProduccion obtener(long id) {
		return produccionDAO.obtener(id);
	}

	@Override
	public void insertar(String titulo, List<TbPrdAutor> autores,
			String palabrasClave, long tipoProduccion,
			HashMap<Long, String> informacion) {
		// TODO Auto-generated method stub
	}

	public List<ReporteProduccion> obtenerValoresReporte(String fechaInicial,
			String fechaFinal, TbPrdGrupoinvestigacion grupo) {
		List<ReporteProduccion> repProducciones = new ArrayList<ReporteProduccion>();
		List<TbPrdProduccion> producciones = produccionDAO.listar();
		Date fechaIni = new Date(fechaInicial);
		Date fechaFin = new Date(fechaFinal);
		for (Iterator iterator = producciones.iterator(); iterator.hasNext();) {
			ReporteProduccion reporteProd = new ReporteProduccion();
			TbPrdProduccion tbPrdProduccion = (TbPrdProduccion) iterator.next();
			String tipoProduccion = tbPrdProduccion.getTbPrdTipoproduccion().getVrDescripcion();
			reporteProd.setTipoProduccion(tipoProduccion);
			Set tbAutXProd = new HashSet();
			Set tbDetalleProducciones = new HashSet();
			tbDetalleProducciones = tbPrdProduccion.getTbPrdDetalleproduccioneses();
			tbAutXProd = tbPrdProduccion.getTbPrdAutoresxproduccions();
			Iterator it = tbAutXProd.iterator();
			TbPrdAutoresxproduccion autXProd = (TbPrdAutoresxproduccion) it.next();
			reporteProd.setGrupoInvestigacion(autXProd.getTbPrdGrupoinvestigacion().getVrNombre());
			long idGrupo = autXProd.getTbPrdGrupoinvestigacion().getNbIdn();
			reporteProd.setAutores(obtenerAutoresXProd(tbAutXProd));
			reporteProd.setDetalles(obtenerDetalleProducciones(tbDetalleProducciones));
			reporteProd.setNombreProduccion(tbPrdProduccion.getVrNombreproduccion());
			reporteProd.setUrlProduccion(tbPrdProduccion.getVrUrl());
			reporteProd.setDoiProduccion(tbPrdProduccion.getVrDoi());
			Date fechaPublicacion = tbPrdProduccion.getDtFechapublicacion();
			reporteProd.setFechaPublicacion(fechaPublicacion.toString());
			
			if(fechaPublicacion.getTime()>= fechaIni.getTime() && fechaPublicacion.getTime()<= fechaFin.getTime()){
				if(grupo == null){
					repProducciones.add(reporteProd);
				}else if(grupo.getNbIdn()== idGrupo){
					repProducciones.add(reporteProd);
				}
				
			}
		}
		return repProducciones;
	}
	
	public String obtenerDetalleProducciones(Set tbDetalleProds){
		String detalle = "";
		for( Iterator it = tbDetalleProds.iterator(); it.hasNext();) { 
			TbPrdDetalleproducciones detalleProd = (TbPrdDetalleproducciones) it.next();
			detalle = detalle + detalleProd.getTbPrdTipoproduccionesxcampo().getTbPrdCampos().getVrTipocampo() + ";";
			detalle = detalle + detalleProd.getTbPrdTipoproduccionesxcampo().getTbPrdCampos().getVrDescripcion() + "-";
		}
		System.out.println("Detalle --->" + detalle);
		return detalle;
	}
	
	public String obtenerAutoresXProd(Set tbAutXProd){
		String autores = "";
		for( Iterator it = tbAutXProd.iterator(); it.hasNext();) { 
			TbPrdAutoresxproduccion autXProd = (TbPrdAutoresxproduccion) it.next();
			autores = autores + autXProd.getTbPrdAutor().getPersona().getVrNombres() + " " + autXProd.getTbPrdAutor().getPersona().getVrApellidos()+";"; 
		}
		return autores;
	}

	
	public ProduccionDAO getProduccionDAO() {
		return produccionDAO;
	}

	public void setProduccionDAO(ProduccionDAO produccionDAO) {
		this.produccionDAO = produccionDAO;
	}

}