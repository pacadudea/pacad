package com.proint1.udea.produccion.ctl;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Textbox;

import com.proint1.udea.administracion.entidades.seguridad.Usuario;
import com.proint1.udea.produccion.ngc.UsuarioService;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.VistasZk;

/**
 * Controlador para las funcionalidades de la vista Pais
 * @author JuanBar
 *
 */
public class LoginCtl extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginCtl.class);
	
	UsuarioService usuarioService;

	Textbox txtUsuario;
	Textbox txtPass;	
	
	Menuitem menuCerrarSesion;
	Menuitem menuUsuario;
	
	Button btnIngresar;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	public void onCreate() {
		
	}

	public void onClick$btnIngresar() {
		System.out.println("INICIANMDO ÑLPOGIN");
		
		txtUsuario.getText();
		txtPass.getText();
		
		Usuario user = null;
			
		try {
			user = usuarioService.loguearUsuario(txtUsuario.getText(), txtPass.getText());
			
			
			
			if (user == null) {
				ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.falloSesison"));
				VistasZk.limpiarCampoConstraint(txtUsuario);
				VistasZk.limpiarCampoConstraint(txtPass);
				
				txtUsuario.setFocus(true);
			}else{
				System.out.println("se obtuvo el usuario " + user.getVrUsuLogin());
			
				Executions.getCurrent().getSession().setAttribute("userLogin", user);
				Executions.sendRedirect("principal.zul"); 
			}
		} catch (WrongValueException | ProduccionBLException e) {
			ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.falloSesison"));
		}	
	}
	
	public void onClick$menuCerrarSesion() {
		System.out.println("quieres cerrar session");
		Executions.getCurrent().getSession().invalidate();
		Executions.sendRedirect("login.zul"); 
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	
	
}
