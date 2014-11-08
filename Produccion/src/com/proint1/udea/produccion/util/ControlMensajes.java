package com.proint1.udea.produccion.util;

import org.zkoss.zul.Messagebox;

public class ControlMensajes {
	
	public static void mensajeError(String mensaje){
		Messagebox.show(mensaje, "Error", Messagebox.OK, Messagebox.ERROR);
	}
	
	public static  void mensajeWarning(String mensaje){
		Messagebox.show(mensaje, "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
	}

	public static  void mensajeQuestion(String mensaje){
		Messagebox.show(mensaje, "Pregunta?", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
	}

	public  static void mensajeInformation(String mensaje){
		Messagebox.show(mensaje, "Información", Messagebox.OK, Messagebox.INFORMATION);
	}

}
