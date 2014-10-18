package com.proint1.udea.produccion.ctl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.ngc.GrupoInvestigacionService;
import com.proint1.udea.produccion.ngc.TipoProduccionService;
import com.proint1.udea.produccion.ngc.impl.AutorServiceImpl;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class reporteDinamicoProduccionesCtl extends GenericForwardComposer{
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger=Logger.getLogger(reporteDinamicoProduccionesCtl.class);
	
	/**
	 * beans del negocio usados
	 */
	TipoProduccionService tipoProduccionService;
	AutorServiceImpl autorService;
	GrupoInvestigacionService grupoInvestigacionService; 

	/**
	 * Metodos Set y Get para los beans.
	 *
	 */
	public AutorServiceImpl getAutorService() {
		return autorService;
	}
	public void setAutorService(AutorServiceImpl autorService) {
		this.autorService = autorService;
	}

	public TipoProduccionService getTipoProduccionService() {
		return tipoProduccionService;
	}
	public void setTipoProduccionService(TipoProduccionService tipoProduccionService) {
		this.tipoProduccionService = tipoProduccionService;
	}
	
	public GrupoInvestigacionService getGrupoInvestigacionService() {
		return grupoInvestigacionService;
	}
	public void setGrupoInvestigacionService(GrupoInvestigacionService grupoInvestigacionService) {
		this.grupoInvestigacionService = grupoInvestigacionService;
	}

	/**
	 * Elementos de la Vista
	 */
	Listbox ltbTipoProduccion;
	Listbox ltbAutores;
	Listbox ltbGrupo;
	
	Datebox tbiFechaInicio;
	Datebox tbiFechaFin;
	
	Button btnNuevoReporte;
	Button btnGenerarReporte;
	
	Textbox result;
	

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		llenarListboxAutores();
		llenarListboxTiposProd();
		llenarListboxGruposInvestigacion();
		logger.info("cargando ventana de reporte dinamico");
	}
	
	
	public void onClick$btnGenerarReporte() throws ProduccionDAOException, ProduccionBLException {
		logger.info("Obtener las opciones seleccionadas en los Listbox");
		
		//Autor
		TbPrdAutor autorBd = null;
		autorBd = autorService.obtenerAutor(Long.parseLong(((Listcell)ltbAutores.getSelectedItem().getFirstChild()).getValue().toString()));
		logger.info(":::Id Autor: "+autorBd.getId());
		
		//TipoProduccion
		TbPrdTipoproduccion tipoBd = null;
		tipoBd = tipoProduccionService.obtener(Long.parseLong(((Listcell)ltbTipoProduccion.getSelectedItem().getFirstChild()).getValue().toString()));
		logger.info("El tipo seleccionado es: " + tipoBd.getVrDescripcion());
		
		//GrupoInvestigacion
		TbPrdGrupoinvestigacion grupoBd = null; 
		//grupoBd = grupoInvestigacionService.obtener(Long.parseLong(((Listcell)ltbGrupo.getSelectedItem().getFirstChild()).getValue().toString()));
		logger.info("El grupoInvestigacion es: "+grupoBd.getVrNombre());
		
		//Fecha Inicial
		String inicial = tbiFechaInicio.getText();
		String finalizar = tbiFechaFin.getText();
		
		logger.info("Las fechas String son: Fecha Inicio: "+inicial+" Fecha Final: "+finalizar);
		result.setValue("Del autor: "+grupoBd.getVrNombre());
	}


	/**
	 * Metodo que limpia los campos del formulario.
	 */
	public void onClick$btnNuevoReporte() {
		tbiFechaFin.setValue(null);
		tbiFechaInicio.setValue(null);
		ltbAutores.clearSelection();
		ltbGrupo.clearSelection();
		ltbTipoProduccion.clearSelection();
	}
	
	/**
	 * Metodo llenarListbox lbtTipoProduccion
	 * trae los datos desde la base de datos y 
	 * genera un elemento cell en el listbox para cada registro encontrado.
	 * Luego los agrega al ListBox.
	 * @throws ProduccionBLException 
	 */
	private void llenarListboxTiposProd() throws ProduccionBLException{
		Listitem items;
		Listcell uncell;
		List<TbPrdTipoproduccion> tiposProd = tipoProduccionService.listar();
		logger.info("se obtuvo la lista de tiposProduccion size: "+tiposProd.size());
		for (TbPrdTipoproduccion tipo : tiposProd) {
			logger.info("Estoy agregando al listbox: "+tipo.getVrDescripcion());
			items = new Listitem();
			uncell = new Listcell(tipo.getVrDescripcion());
			uncell.setParent(items);
			uncell.setValue(tipo.getNbIdn());
			items.setParent(ltbTipoProduccion);
	    }
	}

	/**
	 * Metodo llenarListbox ltbAutores
	 * trae los datos desde la base de datos y 
	 * genera un elemento cell en el listbox para cada registro encontrado.
	 * Luego los agrega al ListBox.
	 * @throws ProduccionBLException 
	 */
	private void llenarListboxAutores() throws ProduccionDAOException, ProduccionBLException{
		Listitem items;
		Listcell uncell;
		List<TbPrdAutor> autores = autorService.listar();
		logger.info("se obtuvo la lista de Autores size: "+autores.size());
		for (TbPrdAutor autor : autores) { 
			logger.info("Estoy agregando al listbox: "+autor.getPersona().getVrNombres()+ "");
			items = new Listitem();
			uncell = new Listcell(autor.getId()+" - "+autor.getPersona().getVrNombres()+ "");
	        uncell.setParent(items);
	        uncell.setValue(autor.getId());
	        items.setParent(ltbAutores);
		}
	}
	
	/**
	 * Metodo llenarListbox ltbGrupo
	 * trae los datos desde la base de datos y 
	 * genera un elemento cell en el listbox para cada registro encontrado.
	 * Luego los agrega al ListBox.
	 * @throws ProduccionBLException 
	 */
	private void llenarListboxGruposInvestigacion() throws ProduccionBLException{
		Listitem items;
		Listcell uncell;
		List<TbPrdGrupoinvestigacion> gruposInv = grupoInvestigacionService.listar();
		logger.info("se obtuvo la lista de Grupos de Investigacion: size= " + gruposInv.size());
		for(TbPrdGrupoinvestigacion grupo : gruposInv){
			logger.info("Estoy agregando al listbox: "+grupo.getVrNombre());
			items = new Listitem();
			uncell = new Listcell(grupo.getVrNombre()+ "");
	        uncell.setParent(items);
	        uncell.setValue(grupo.getNbIdn());
	        items.setParent(ltbGrupo);
	    }
	}

}
