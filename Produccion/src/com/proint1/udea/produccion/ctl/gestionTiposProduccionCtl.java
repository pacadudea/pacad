package com.proint1.udea.produccion.ctl;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.proint1.udea.produccion.entidades.TbPrdCampo;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.ngc.CampoService;
import com.proint1.udea.produccion.ngc.TipoProduccionService;
import com.proint1.udea.produccion.util.ProduccionBLException;

public class gestionTiposProduccionCtl extends GenericForwardComposer {
	
	TipoProduccionService tipoProduccionService;
	CampoService campoService;

	Grid gridTipos;
	Textbox txtDescripcion;
	Textbox txtNuevoCampo;
	Checkbox chkCampoActivo;
	Button btnNuevoCampo;
	Checkbox chkActivo;
	Listbox listCamposAsignados;
	Listbox listCampos;

	private static Logger logger = Logger.getLogger(gestionTiposProduccionCtl.class);
	
	public void onCreate() throws ProduccionBLException {
		 Rows rowsTipos = new Rows(); 
		 List<TbPrdTipoproduccion> tipos = tipoProduccionService.listar();
		 for (TbPrdTipoproduccion tipo : tipos) { 
			 RowTipo row = new RowTipo(tipo.getNbIdn());
			 row.appendChild(new org.zkoss.zul.Label(tipo.getVrDescripcion()));
			 rowsTipos.appendChild(row); 
		 } 
		 gridTipos.appendChild(rowsTipos);	
		
		 actualizarCampos();		 
	}
	
	public void actualizarCampos() throws ProduccionBLException {
		for(int i = 0; i < listCampos.getItemCount(); i++) {
			listCampos.removeItemAt(i);
		}
		
		List<TbPrdCampo> campos = campoService.listar();
		for (TbPrdCampo campo : campos) { 
			 Listitem campoItem = new Listitem(); 
			 campoItem.setValue(campo.getNbIdn());
			 campoItem.setLabel(campo.getVrDescripcion());
			 campoItem.setDraggable("true");
			 campoItem.setDroppable("true");
			 listCampos.appendChild(campoItem); 
		}
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		logger.info("Cargando interfaz Tipo de Producción");
	}

	public void onClick$btnNuevo() {
		try {
			tipoProduccionService.insertar();
		} catch (ProduccionBLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onClick$btnGuardar() {
		
	}
	
	public void onClick$btnEliminar() {
		
	}
	
	public void onClick$btnNuevoCampo() throws WrongValueException, ProduccionBLException {
		if (!txtNuevoCampo.getText().equals("")) {
			campoService.insertar(txtNuevoCampo.getText(), chkCampoActivo.isChecked());
		}
		
		txtNuevoCampo.setText("");
		
		actualizarCampos();
	}

	public TipoProduccionService getTipoProduccionService() {
		return tipoProduccionService;
	}

	public void setTipoProduccionService(TipoProduccionService tipoProduccionService) {
		this.tipoProduccionService = tipoProduccionService;
	}

	public CampoService getCampoService() {
		return campoService;
	}

	public void setCampoService(CampoService campoService) {
		this.campoService = campoService;
	}


	public class RowTipo extends Row {
		private long id;
		
		public RowTipo(long id) {
			super();
			this.id = id;
		}

		public void onClick() throws ProduccionBLException {
	    	TbPrdTipoproduccion tipo = tipoProduccionService.obtener(id);
	    	txtDescripcion.setValue(tipo.getVrDescripcion());
	    	
	    	if (tipo.getBlEstado() =='1') {
	    		chkActivo.setChecked(true);
	    	} else {
	    		chkActivo.setChecked(false);
	    	}
	    }
	}
}
