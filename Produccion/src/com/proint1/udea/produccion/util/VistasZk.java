package com.proint1.udea.produccion.util;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
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

}
