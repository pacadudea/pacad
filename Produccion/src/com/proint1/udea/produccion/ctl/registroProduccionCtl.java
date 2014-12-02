package com.proint1.udea.produccion.ctl;

import java.util.ArrayList;
import java.util.List;

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

import com.proint1.udea.administracion.entidades.terceros.TbAdmTipoIdentificacion;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccionesxcampo;
import com.proint1.udea.produccion.ngc.ProduccionService;
import com.proint1.udea.produccion.ngc.TipoProduccionService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionIWException;

/**
 * Gestion de Producciones
 * @author Juan Fernandonando
 *
 */
public class registroProduccionCtl extends GenericForwardComposer {
	
	//Servicios de la logica de Negocio
	ProduccionService produccionService;
	TipoProduccionService tipoProduccionService;

	//Listas con informacion de la base de datos
	private List<TbPrdProduccion>listaProducciones;
	private List<TbPrdTipoproduccion>tipos = new ArrayList<TbPrdTipoproduccion>();
	
	//Componentes del formulario
	Window winProducciones;
	Textbox txtFiltrarProduccion;
	Listbox lbtProducciones;
	Listbox ltbTipoProduccion;
 	Button btnGuardar;
 	Button btnActualizar;
 	Button btnEliminar;
	
	
	private static Logger logger = Logger.getLogger(registroProduccionCtl.class);
		
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		logger.info("Cargando interfaz Registro de Producción");
	}
	
	/**
	 * Carga inicial de la informacion.
	 * Para los listbox producciones y tiposProduccion
	 * @throws ProduccionIWException
	 */
	public void onCreate() throws ProduccionIWException {
		this.cargarProducciones();
		this.cargarTiposProduccion();
		
	}

	private void cargarProducciones() throws ProduccionIWException {
		try {
			this.listaProducciones = produccionService.listar();
			lbtProducciones.setModel(new ListModelList<TbPrdProduccion>(listaProducciones));
		} catch (Exception e) {
			throw new ProduccionIWException("No se pudo cargar la lista de producciones");
		}
	}

	private void cargarTiposProduccion()  throws ProduccionIWException {
		try{
			this.tipos = tipoProduccionService.listar();
			for (TbPrdTipoproduccion tipo : tipos) {
				ltbTipoProduccion.appendChild(new Listitem(tipo.getVrDescripcion(),tipo.getNbIdn()));
			}
		}catch (Exception e){
			throw new ProduccionIWException("NO pude cargar las lista en el listbox TiposProd.");
		}
	
	}
	
	public void onChange$txtFiltrarProduccion() {
		List<TbPrdProduccion> listaFiltrada = new ArrayList<TbPrdProduccion>();
		String filtro = this.txtFiltrarProduccion.getValue().toLowerCase();
		for (TbPrdProduccion prod : this.listaProducciones) {
			if(prod.getVrNombreproduccion().toLowerCase().contains(filtro)){
				listaFiltrada.add(prod);
			}
		}
		this.lbtProducciones.setModel(new ListModelList<TbPrdProduccion>(listaFiltrada));
	}
	
	public void onSelect$ltbTipoProduccion() {
		//Messagebox.show("Tipo elegido: " + ltbTipoProduccion.getSelectedItem().getValue().toString());
		List<TbPrdTipoproduccionesxcampo> camposXTipo = null;
		try {
			//camposXTipo = tipoProduccionService.obtenerCamposXTipo(Long.parseLong(ltbTipoProduccion.getSelectedItem().getValue().toString()));
			//Messagebox.show("Campos: " + camposXTipo.size());
			Rows rows = new Rows();
			Row row = new Row();
			
			row.appendChild(new Label("ISBN"));
			row.appendChild(new Textbox());
			rows.appendChild(row);
			
			
			//gridCampos.appendChild(rows);
		} catch (NumberFormatException e) {
			Messagebox.show("Error: " + e.getMessage());
		} //catch (ProduccionDAOException e) {
//			Messagebox.show("Error: " + e.getMessage());
//		}
		
	}

		

	public void onClick$btnNuevo() {
		//produccionService.insertar();
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

	public void setTipoProduccionService(TipoProduccionService tipoProduccionService) {
		this.tipoProduccionService = tipoProduccionService;
	}
	
	
}