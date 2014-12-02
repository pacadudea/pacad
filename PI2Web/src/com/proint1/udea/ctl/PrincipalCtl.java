package com.proint1.udea.ctl;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;

import com.proint1.udea.produccion.util.Seguridad;

public class PrincipalCtl extends GenericForwardComposer{
	
	Div divCenter;
	
	private static Logger logger=Logger.getLogger(PrincipalCtl.class);

	public void onCreate(){
	}
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        
        
        Sessions.getCurrent().setAttribute("divPrincipalCtl", divCenter);
   }
	
}