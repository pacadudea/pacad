package com.proint1.udea.produccion.util;

import org.apache.log4j.Logger;

public class ProduccionDAOException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger(ProduccionDAOException.class);
	
	public ProduccionDAOException() {
		// TODO Auto-generated constructor stub
	}

	public ProduccionDAOException(String arg0) {
		super(arg0);
		logger.error("** - ERROR DAO :: " + arg0);
	}

	public ProduccionDAOException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ProduccionDAOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ProduccionDAOException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
	
}
