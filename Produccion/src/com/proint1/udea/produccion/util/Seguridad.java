package com.proint1.udea.produccion.util;

import java.util.Iterator;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import com.proint1.udea.administracion.entidades.seguridad.Rol;
import com.proint1.udea.administracion.entidades.seguridad.Usuario;
import com.proint1.udea.administracion.entidades.seguridad.UsuarioRol;

public class Seguridad {
	public static boolean existeSesion() {
		if (Seguridad.getSession() != null) {
			return true;
		} else {
			return false;
		}
	}

	public static void cargarPagina(Component window) {
		System.out.println("llego aqui");
		if (!Seguridad.existeSesion()) {
			System.out.println("no hay session");
			window.detach();
			Executions.getCurrent().getSession().invalidate();
			Executions.sendRedirect("login.zul");
		}
		System.out.println("encontro");
	}

	public static boolean accesoAdministrador() {
		boolean acceso = false;
		Usuario u = Seguridad.usuarioActual_obj();
		if (u == null){
			return acceso;
		}else{
		Set roles = u.getTbAdmUsuarioxrols();
		for (Iterator iterator = roles.iterator(); iterator.hasNext();) {
			UsuarioRol urol = (UsuarioRol) iterator.next();
			Rol r = urol.getTbAdmRol();
			if ("Administrador".equals(r.getVrRolNombre())) {
				acceso = true;
				break;
			}
		}
		return acceso;
		}
	}

	public static void cerrarSesion() {
		System.out.println("vino a cerra");
		Executions.getCurrent().getSession().invalidate();
		Executions.sendRedirect("login.zul");
	}

	public static String usuarioActual() {
		Session sesion = Seguridad.getSession();
		if (sesion != null) {
			Usuario usuarioLogueado = (Usuario) sesion
					.getAttribute("userLogin");
			return usuarioLogueado.getVrUsuLogin();
		} else {
			return null;
		}
	}

	public static Usuario usuarioActual_obj() {
		Session sesion = Seguridad.getSession();
		if (sesion != null) {
			Usuario usuarioLogueado = (Usuario) sesion
					.getAttribute("userLogin");
			return usuarioLogueado;
		} else {
			return null;
		}
	}

	private static Session getSession() {

		try {
			if (Executions.getCurrent().getSession().getAttribute("userLogin") instanceof Usuario) {
				Usuario usuarioLogueado = (Usuario) Executions.getCurrent()
						.getSession().getAttribute("userLogin");
				if (!usuarioLogueado.getVrUsuLogin().equals("")) {
					return Executions.getCurrent().getSession();
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}
}
