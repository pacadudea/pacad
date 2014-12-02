package com.proint1.udea.produccion.ctl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.ProduccionIWException;
import com.proint1.udea.produccion.util.VistasZk;

public class DetalleGrupoXTipoProdCtl extends GenericForwardComposer  implements ListitemRenderer{
	
	private static final long serialVersionUID = 1L;
	
	private TbPrdTipoproduccion tipoProduccion;
	private TbPrdGrupoinvestigacion grupoInvestigacion;
	private List<TbPrdProduccion>producciones= new ArrayList<TbPrdProduccion>();
		
	
	Label lbGrupo;
	Listbox listboxTiposProd;
	Listbox listboxProducciones;
	
	Window detalleGrupoPorTipos;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		try{
			this.tipoProduccion = (TbPrdTipoproduccion) arg.get("tipo");
			this.grupoInvestigacion = (TbPrdGrupoinvestigacion) arg.get("grupo");
			
		}catch (Exception e){
			ControlMensajes.mensajeError("No se recibieron los datos");
		}
	}
	
	
	
	public void onCreate() throws ProduccionDAOException, ProduccionBLException, ProduccionIWException {
		this.cargarCampos();
		this.cargarDatos(tipoProduccion, grupoInvestigacion);
	}
	
	public void cargarDatos(TbPrdTipoproduccion tp, TbPrdGrupoinvestigacion g){
		List<TbPrdProduccion>datos = new ArrayList<TbPrdProduccion>();
		
		Set lista = g.getTbPrdAutoresxproduccions();
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			TbPrdAutoresxproduccion ap = (TbPrdAutoresxproduccion) iterator.next();
			
			TbPrdProduccion p = ap.getTbPrdProduccion();
			if (p.getTbPrdTipoproduccion().getVrDescripcion().equals(tp.getVrDescripcion())){
				datos.add(p);
			}
		}
		this.listboxProducciones.setModel(new ListModelList<TbPrdProduccion>(datos));
		this.listboxProducciones.setItemRenderer(this);
		
	}
	
	
	
	/**
	 * 
	 */
	private void cargarCampos(){
		//Titulo grupo seleccionado
		this.lbGrupo.setValue(this.grupoInvestigacion.getVrNombre());
		//Tipos de las producciones del Grupo
		
		List<TbPrdTipoproduccion>tipos = new ArrayList<TbPrdTipoproduccion>();
		
		try{	
		
		Set autoresXprod = this.grupoInvestigacion.getTbPrdAutoresxproduccions();
		for(Iterator iterator = autoresXprod.iterator(); iterator.hasNext();){
			TbPrdAutoresxproduccion autorxPr =(TbPrdAutoresxproduccion) iterator.next();
			//Busca los tipos de Produccion del Grupo
			if(!tipos.contains(autorxPr.getTbPrdProduccion().getTbPrdTipoproduccion())){
				tipos.add(autorxPr.getTbPrdProduccion().getTbPrdTipoproduccion());
			}	
		}
		//Llenar el listboxTiposProd
		this.listboxTiposProd.setModel(new ListModelList<TbPrdTipoproduccion>(tipos));
		//this.listboxTiposProd.setItemRenderer(this);
				
		} catch (Exception e) {
			ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.noCargaDatos"));
		}
	}
	
	/**
	 * Metodo que controla la seleccion de un tipo de produccion en el listbox.
	 * @throws ProduccionIWException
	 */
	public void onSelect$listboxTiposProd() throws ProduccionIWException  {
		Set<TbPrdTipoproduccion> selection = ((Selectable<TbPrdTipoproduccion>) listboxTiposProd
				.getModel()).getSelection();
		TbPrdTipoproduccion t = selection.iterator().next();
		
		this.cargarDatos(t, this.grupoInvestigacion);
		
		
		/*Set<TbPrdTipoproduccion> tipoSeleccion =((Selectable<TbPrdTipoproduccion>)listboxTiposProd.getModel()).getSelection();
		Set autoresXprod = this.grupoInvestigacion.getTbPrdAutoresxproduccions();
		for(Iterator iterator = autoresXprod.iterator(); iterator.hasNext();){
			TbPrdAutoresxproduccion autorxPr =(TbPrdAutoresxproduccion) iterator.next();
			
			if(autorxPr.getTbPrdProduccion().getTbPrdTipoproduccion() == tipoSeleccion){
				//Agrega las producciones del tipo seleccionado creadas del grupo
				if(!producciones.contains(autorxPr.getTbPrdProduccion())){
					producciones.add(autorxPr.getTbPrdProduccion());
				}
			}
		}
		this.listboxProducciones.setModel(new ListModelList<TbPrdProduccion>(producciones));
		//this.listboxProducciones.setItemRenderer(this);*/
	}

	
	@Override
	public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
		String estiloLink= "color:blue; text-decoration:underline;font-weight:bold";
		
		//Obtengo el objeto produccion
		TbPrdProduccion pr = (TbPrdProduccion)arg1;
		
		//Creo las celdas correspondientes
		Listcell cellPrd = new Listcell();
		cellPrd.setLabel(pr.getVrNombreproduccion());
		cellPrd.setStyle(estiloLink);	
		cellPrd.addEventListener(Events.ON_CLICK, new ProduccionSel(pr));		
				
		Listcell cellFecha = new Listcell();
		cellFecha.setLabel(pr.getDtFechapublicacion().toString());
		
		arg0.appendChild(cellPrd);
		arg0.appendChild(cellFecha);
	}
	
	/**
	 * Clase que controla el evento de seleccionar la produccón
	 */
	private class ProduccionSel implements EventListener {
		
		private TbPrdProduccion produccion;
		
		public ProduccionSel(TbPrdProduccion produccion){
			this.produccion = produccion;
		}
		
	    public void onEvent(Event event) {
	    	Map a = new HashMap<>();
			a.put("produccion", produccion);
			
			java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/detalleProduccion.zul") ;
			java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
			
			try {
				Window window;
				Div divCenter = VistasZk.obtenerDivCenter(detalleGrupoPorTipos);
				
				divCenter.getChildren().clear();
				window= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,a) ;	
				window.doEmbedded();
			} catch (IOException e) {
				System.err.println("ERROR ENVIANDO");
				e.printStackTrace();
			}
	    }
	}

}
