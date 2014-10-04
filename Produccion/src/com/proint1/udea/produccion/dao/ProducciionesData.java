package com.proint1.udea.produccion.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.model.ProduccionesFilter;
import com.proint1.udea.produccion.ngc.ProduccionService;

public class ProducciionesData {

	ProduccionService produccionService;
	
	private static List<TbPrdProduccion> producciones = new ArrayList<TbPrdProduccion>();
	static {
		producciones.add(new TbPrdProduccion(1, null, "Los gatos", null, 'A', "1", null));
		producciones.add(new TbPrdProduccion(1, null, "La logica", null, 'A', "1", null));
		producciones.add(new TbPrdProduccion(1, null, "Matematicas", null, 'A', "1", null));
	}

	//private static List<TbPrdProduccion> producciones = produccionService.listar();
	
	public static List<TbPrdProduccion> getAllFoods() {
		return new ArrayList<TbPrdProduccion>(producciones);
	}
	public static TbPrdProduccion[] getAllFoodsArray() {
		return producciones.toArray(new TbPrdProduccion[producciones.size()]);
	}

	// This Method only used in "Data Filter" Demo
	public static List<TbPrdProduccion> getFilterFoods(ProduccionesFilter filtro) {
		List<TbPrdProduccion> listaFiltrada = new ArrayList<TbPrdProduccion>();
		String nombre = filtro.getNombre().toLowerCase();
		String fecha = filtro.getFecha().toLowerCase();
		
		for (Iterator<TbPrdProduccion> i = producciones.iterator(); i.hasNext();) {
			TbPrdProduccion tmp = i.next();
			if (tmp.getVrNombreproduccion().toLowerCase().contains(nombre)) {
				listaFiltrada.add(tmp);
			}
		}
		return listaFiltrada;
	}

}
