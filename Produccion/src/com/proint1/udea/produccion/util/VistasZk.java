package com.proint1.udea.produccion.util;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class VistasZk {

	public static Div obtenerDivCenter(Window window) {
		Div div;
		List<Component> list = window.getParent().getParent().getChildren();
		for (Component child : list) {
			if ("divCenter".equals(child.getId())){
				div = (Div) child;
				return div;
			}
		}
		return null;
	}
	
	public static void limpiarCampoConstraint(Textbox textbox){
		if (textbox.getConstraint() != null){
			Constraint c = textbox.getConstraint();
			textbox.setConstraint((String)null);
			textbox.setText("");
			textbox.setConstraint(c);
		}
	}
	
	public static void limpiarBandConstraint(Bandbox bandbox){
		if (bandbox.getConstraint() != null){
			Constraint c = bandbox.getConstraint();
			bandbox.setConstraint((String)null);
			bandbox.setText("");
			bandbox.setConstraint(c);
		}
	}

}
