package com.proint1.udea.produccion.ctl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdCampos;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccionesxcampo;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.ProduccionIWException;

public class DetalleTipoProduccionCtl extends GenericForwardComposer  implements ListitemRenderer{
	
	private static final long serialVersionUID = 1L;
	
	private TbPrdTipoproduccion tipoProduccion;
	
	Label lbTipoProduccion;
	Label lbDescripcion;
	Label lbEstado;
	Label lbConteo;
	
	Listbox listaCampos;
	Listbox listaProducciones;
	
	Window detalleTipoProduccion;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		try{
		this.tipoProduccion = (TbPrdTipoproduccion) arg.get("tipoProduccion");
		
		}catch (Exception e){
			ControlMensajes.mensajeError("No se recibieron los datos");
		}
	}
	
	public void onCreate() throws ProduccionDAOException, ProduccionBLException, ProduccionIWException {
		this.cargarDatosBasicos();
		this.cargarCampos();
	}
	
	private void cargarDatosBasicos(){
		this.lbTipoProduccion.setValue(this.tipoProduccion.getVrDescripcion());
		this.lbDescripcion.setValue(this.tipoProduccion.getVrDescripcion());
		this.lbEstado.setValue(this.tipoProduccion.getBlEstado()+ "");
		this.lbConteo.setValue(this.tipoProduccion.getTbPrdProduccions().size()+"");
	}
	
	private void cargarCampos(){
		try {
			List<TbPrdCampos> listCampos = new ArrayList<TbPrdCampos>();
			
			Set <TbPrdTipoproduccionesxcampo>camposxprodu =  this.tipoProduccion.getTbPrdTipoproduccionesxcampos();
			for (TbPrdTipoproduccionesxcampo camposxprod : camposxprodu) {
				TbPrdCampos campo = camposxprod.getTbPrdCampos();
				listCampos.add(campo);
			}
			
			this.listaCampos.setModel(new ListModelList<TbPrdCampos>(listCampos));
			this.listaCampos.setItemRenderer(this);
		} catch (Exception e) {
			ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.noCargaDatos"));
		}
	}
	
	@Override
	public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
		
		//Obtengo el objeto pra cada uno de los rows
		TbPrdCampos campo = (TbPrdCampos)arg1;
		
		//Creo las celdas correspondientes
		Listcell cellCampo = new Listcell();
		cellCampo.setLabel(campo.getVrDescripcion());
		
		Listcell cellTipo = new Listcell();
		cellTipo.setLabel(campo.getVrTipocampo()+"");
		
		Listcell cellTam = new Listcell();
		cellTam.setLabel(campo.getNbTamañocampo()+"");
		
		Listcell cellDecim = new Listcell();
		cellDecim.setLabel(campo.getNbDecimales()+"");
		
		Listcell cellEstado = new Listcell();
		cellEstado.setLabel(campo.getBlEstado()+"");
		
		arg0.appendChild(cellCampo);
		arg0.appendChild(cellTipo);
		arg0.appendChild(cellTam);
		arg0.appendChild(cellDecim);
		arg0.appendChild(cellEstado);		
	}
	
}
