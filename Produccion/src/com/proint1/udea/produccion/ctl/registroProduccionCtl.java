package com.proint1.udea.produccion.ctl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdCampos;
import com.proint1.udea.produccion.entidades.TbPrdDetalleproducciones;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccionesxcampo;
import com.proint1.udea.produccion.ngc.ProduccionService;
import com.proint1.udea.produccion.ngc.TipoProduccionService;
import com.proint1.udea.produccion.util.ProduccionIWException;

/**
 * Gestion de Producciones
 * 
 * @author Juan Fernandonando
 *
 *         long nacionalidad = Long.parseLong(ltbNacionalidad.getSelectedItem()
 *         .getValue().toString());
 */
public class registroProduccionCtl extends GenericForwardComposer {

	// Servicios de la logica de Negocio
	ProduccionService produccionService;
	TipoProduccionService tipoProduccionService;

	// Listas con informacion de la base de datos
	private List<TbPrdProduccion> listaProducciones;
	private List<TbPrdTipoproduccion> tipos = new ArrayList<TbPrdTipoproduccion>();
	private TbPrdTipoproduccion tipoProduccionSel;
	private TbPrdProduccion produccionSel;

	// Componentes del formulario
	Window winProducciones;
	Textbox txtFiltrarProduccion;
	Listbox lbtProducciones;
	Listbox ltbTipoProduccion;
	Button btnGuardar;
	Button btnActualizar;
	Button btnEliminar;
	Grid gridCampos;

	private static Logger logger = Logger
			.getLogger(registroProduccionCtl.class);

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		logger.info("Cargando interfaz Registro de Producción");
	}

	/**
	 * Carga inicial de la informacion. Para los listbox producciones y
	 * tiposProduccion
	 * 
	 * @throws ProduccionIWException
	 */
	public void onCreate() throws ProduccionIWException {
		this.cargarProducciones();
		this.cargarTiposProduccion();

	}

	private void cargarProducciones() throws ProduccionIWException {
		try {
			this.listaProducciones = produccionService.listar();
			lbtProducciones.setModel(new ListModelList<TbPrdProduccion>(
					listaProducciones));
		} catch (Exception e) {
			throw new ProduccionIWException(
					"No se pudo cargar la lista de producciones");
		}
	}

	private void cargarTiposProduccion() throws ProduccionIWException {
		try {
			this.tipos = tipoProduccionService.listar();
			ltbTipoProduccion.setModel(new ListModelList<TbPrdTipoproduccion>(
					tipos));
		} catch (Exception e) {
			throw new ProduccionIWException(
					"No se pudo cargar la lista de producciones");
		}

		/*
		 * try{ this.tipos = tipoProduccionService.listar(); for
		 * (TbPrdTipoproduccion tipo : tipos) {
		 * ltbTipoProduccion.appendChild(new
		 * Listitem(tipo.getVrDescripcion(),tipo.getNbIdn())); } }catch
		 * (Exception e){ throw new
		 * ProduccionIWException("NO pude cargar las lista en el listbox TiposProd."
		 * ); }
		 */

	}

	public void onChange$txtFiltrarProduccion() {
		List<TbPrdProduccion> listaFiltrada = new ArrayList<TbPrdProduccion>();
		String filtro = this.txtFiltrarProduccion.getValue().toLowerCase();
		for (TbPrdProduccion prod : this.listaProducciones) {
			if (prod.getVrNombreproduccion().toLowerCase().contains(filtro)) {
				listaFiltrada.add(prod);
			}
		}
		this.lbtProducciones.setModel(new ListModelList<TbPrdProduccion>(
				listaFiltrada));
	}

	private void cargarCampos() {
		System.out.println("OBTENIENDO LISTA");
		Set lCampos = this.tipoProduccionSel.getTbPrdTipoproduccionesxcampos();
		if (lCampos == null) {
			System.out.println("lista nula");
		} else {
			gridCampos.removeChild(gridCampos.getRows());

			/*
			 * Rows rows = gridCampos.getRows(); if (rows == null){
			 * System.out.println("NO HAY ROWS"); rows = new Rows(); }else{
			 * System.out.println("SI HAY ROWS"); }
			 */
			// gridCampos.removeChild(rows);
			Rows rows = new Rows();

			for (Iterator iterator = lCampos.iterator(); iterator.hasNext();) {
				TbPrdTipoproduccionesxcampo tipoxCampo = (TbPrdTipoproduccionesxcampo) iterator
						.next();
				TbPrdCampos campo = tipoxCampo.getTbPrdCampos();
				System.out.println("CAMPO -- " + campo.getVrDescripcion());

				Row row = new Row();

				row.appendChild(new Label(campo.getVrDescripcion()));
				row.appendChild(new Textbox());

				rows.appendChild(row);

			}
			gridCampos.appendChild(rows);
		}
	}

	private void cargarDetalle() {
		System.out.println("OBTENIENDO LISTA");
		Set ldetalles = this.produccionSel.getTbPrdDetalleproduccioneses();
		if (ldetalles == null) {
			System.out.println("lista ldetalles nula");
		} else {
			gridCampos.removeChild(gridCampos.getRows());
			Rows rows = new Rows();

			for (Iterator iterator = ldetalles.iterator(); iterator.hasNext();) {
				TbPrdDetalleproducciones detallesxCampo = (TbPrdDetalleproducciones) iterator
						.next();
				TbPrdCampos campo = detallesxCampo.getTbPrdTipoproduccionesxcampo().getTbPrdCampos();
				String valor = detallesxCampo.getVrValor();
				System.out.println("CAMPO -- " + campo.getVrDescripcion());

				Row row = new Row();

				row.appendChild(new Label(campo.getVrDescripcion()));
				Textbox text = new  Textbox();
				text.setValue(valor);
				row.appendChild(text);

				rows.appendChild(row);

			}
			gridCampos.appendChild(rows);
		}
		
		
	}
	
	public void onSelect$lbtProducciones() {

		Set<TbPrdProduccion> selection = ((Selectable<TbPrdProduccion>) lbtProducciones
				.getModel()).getSelection();
		
		this.produccionSel = selection.iterator().next();
		System.out.println("SELECIONO PROD - "
				+ this.produccionSel.getVrNombreproduccion());
		this.cargarDetalle();

	}

	public void onSelect$ltbTipoProduccion() {

		Set<TbPrdTipoproduccion> selection = ((Selectable<TbPrdTipoproduccion>) ltbTipoProduccion
				.getModel()).getSelection();
		System.out.println("paso set");
		this.tipoProduccionSel = selection.iterator().next();
		System.out.println("SELECIONO CAMPO - "
				+ this.tipoProduccionSel.getVrDescripcion());
		this.cargarCampos();

	}

	public void onClick$btnNuevo() {
		// produccionService.insertar();
	}

	public void onClick$btnGuardar() {

	}

	public void onClick$btnEliminar() {

	}

	public void onClick$btnNuevoCampo() {
	}

	public ProduccionService getProduccionService() {
		return produccionService;
	}

	public void setProduccionService(ProduccionService produccionService) {
		this.produccionService = produccionService;
	}

	public TipoProduccionService getTipoProduccionService() {
		return tipoProduccionService;
	}

	public void setTipoProduccionService(
			TipoProduccionService tipoProduccionService) {
		this.tipoProduccionService = tipoProduccionService;
	}

}