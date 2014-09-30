package com.proint1.udea.produccion.ctl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.proint1.udea.produccion.dto.MiembrosGrupoInvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionIWException;
 
public class DualListBoxGrupo extends Div implements IdSpace {
    private static final long serialVersionUID = 5183321186606483396L;
     
    @Wire
    private Listbox listaMiembros;
    @Wire
    private Listbox listaSeleccionados;
 
    private ListModelList<MiembrosGrupoInvestigacion> listaFinalMiembros;
    private ListModelList<MiembrosGrupoInvestigacion> listaFinalSeleccionados;
 
    public DualListBoxGrupo() {
        Executions.createComponents("/v_dualListbox.zul", this, null);
        Selectors.wireComponents(this, this, false);
        Selectors.wireEventListeners(this, this);
    }
 
    @Listen("onClick = #chooseBtn")
    public void chooseItem() {
    	try {
    		Events.postEvent(new ChooseEvent(this, unchooseOne()));
		} catch (Exception e) {
			ControlMensajes.mensajeInformation("No existen miembros para intercambiar");
		}
    }
 
    @Listen("onClick = #removeBtn")
    public void unchooseItem() throws ProduccionIWException {
    	try {
    		Events.postEvent(new ChooseEvent(this, chooseOne()));
		} catch (Exception e) {
			ControlMensajes.mensajeInformation("No existen miembros para intercambiar");
		}
    }
 
    @Listen("onClick = #chooseAllBtn")
    public void chooseAllItem() {
    	try {
    		for (int i = 0, j = listaFinalSeleccionados.getSize(); i < j; i++) {
            	listaFinalMiembros.add(listaFinalSeleccionados.getElementAt(i));
            }
            listaFinalSeleccionados.clear();
		} catch (Exception e) {
			ControlMensajes.mensajeInformation("No existen miembros para intercambiar");
		}
    }
 
    @Listen("onClick = #removeAllBtn")
    public void unchooseAll() {
    	try {
    		for (int i = 0, j = listaFinalMiembros.getSize(); i < j; i++) {
            	listaFinalSeleccionados.add(listaFinalMiembros.getElementAt(i));
            }
            listaFinalMiembros.clear();
		} catch (Exception e) {
			ControlMensajes.mensajeInformation("No existen miembros para intercambiar");
		}
    }
 
    /**
     * Set new candidate ListModelList.
     * 
     * @param candidate
     *            is the data of candidate list model
     */
    public void setModel(List<MiembrosGrupoInvestigacion> candidate,List<MiembrosGrupoInvestigacion> posiblesMiembros) {
    	listaMiembros.setModel(this.listaFinalMiembros = new ListModelList<MiembrosGrupoInvestigacion>(candidate));
    	listaSeleccionados.setModel(listaFinalSeleccionados = new ListModelList<MiembrosGrupoInvestigacion>(posiblesMiembros));
    }
 
    /**
     * @return current chosen data list
     */
    public List<MiembrosGrupoInvestigacion> getChosenDataList() {
        return new ArrayList<MiembrosGrupoInvestigacion>(listaFinalSeleccionados);
    }
 
    private Set<MiembrosGrupoInvestigacion> chooseOne() {
        Set<MiembrosGrupoInvestigacion> set = listaFinalMiembros.getSelection();
        for (MiembrosGrupoInvestigacion selectedItem : set) {
        	listaFinalSeleccionados.add(selectedItem);
        	listaFinalMiembros.remove(selectedItem);
        }
        return set;
    }
 
    private Set<MiembrosGrupoInvestigacion> unchooseOne() {
        Set<MiembrosGrupoInvestigacion> set = listaFinalSeleccionados.getSelection();
        for (MiembrosGrupoInvestigacion selectedItem : set) {
        	listaFinalMiembros.add(selectedItem);
        	listaFinalSeleccionados.remove(selectedItem);
        }
        return set;
    }
 
    // Customized Event
    public class ChooseEvent extends Event {
        private static final long serialVersionUID = -7334906383953342976L;
 
        public ChooseEvent(Component target, Set<MiembrosGrupoInvestigacion> data) {
            super("onChoose", target, data);
        }
    }

	public ListModelList<MiembrosGrupoInvestigacion> getListaFinalMiembros() {
		return listaFinalMiembros;
	}

	public ListModelList<MiembrosGrupoInvestigacion> getListaFinalSeleccionados() {
		return listaFinalSeleccionados;
	}

}