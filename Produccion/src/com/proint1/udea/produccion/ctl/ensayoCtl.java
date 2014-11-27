package com.proint1.udea.produccion.ctl;

import java.awt.Button;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.ngc.AutorService;
import com.proint1.udea.produccion.ngc.ProduccionService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionIWException;

public class ensayoCtl extends GenericForwardComposer {
	
	private static final long serialVersionUID = 1L;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		
		try{
		String nombre = (String) arg.get("juan");
		
	
		}catch (Exception e){
			System.out.println("ERROR RECIBIENDO MAP");
		}
	}

}
