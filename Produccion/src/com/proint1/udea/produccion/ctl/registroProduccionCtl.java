package com.proint1.udea.produccion.ctl;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccionesxcampo;
import com.proint1.udea.produccion.ngc.ProduccionService;
import com.proint1.udea.produccion.ngc.TipoProduccionService;
import com.proint1.udea.produccion.util.ProduccionBLException;

/**
 * Gestion de Producciones
 * @author 
 *
 */
public class registroProduccionCtl extends GenericForwardComposer {
	
	private static Logger logger = Logger.getLogger(registroProduccionCtl.class);
	
	/**
	 * Servicios a utilizar
	 */
	ProduccionService produccionService;
	TipoProduccionService tipoProduccionService;

	 //Elementos de la vista
	 
	Textbox txtTitulo;
	Listbox ltbTipoProduccion;
	Grid gridCampos;
	
	Button btnNuevo;
	Button btnGuardar;
	
	//Lista de TiposProduccion
	private List<TbPrdTipoproduccion> tipos;
	private TbPrdTipoproduccion tipoSeleccionado;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		logger.info("Cargando interfaz Registro de Producción");
	}
	
	public void onCreate() {
		Rows rows = new Rows();
		
		try {
			tipos = tipoProduccionService.listar();
			for (TbPrdTipoproduccion tipo : tipos) {
				ltbTipoProduccion.appendChild(new Listitem(tipo.getVrDescripcion(), tipo.getNbIdn()));
			}
		} catch (ProduccionBLException e) {
			Messagebox.show("Ocurrió un error: " + e.getMessage());
		}
		
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
			
			
			gridCampos.appendChild(rows);
		} catch (NumberFormatException e) {
			Messagebox.show("Error: " + e.getMessage());
		} //catch (ProduccionDAOException e) {
//			Messagebox.show("Error: " + e.getMessage());
//		}
		
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

