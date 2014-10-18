package com.proint1.udea.produccion.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase que sirve para administrar el archivo propiedades para la internacionalizacion
 * @author Edison
 *
 */
public class ArchivoInternacionalizacion {

		 
		 public Properties getProperties() 
		  {
			 
			 File miDir = new File (".");
		     try {
		       System.out.println ("Directorio actual: " + miDir.getCanonicalPath());
		       }
		     catch(Exception e) {
		       e.printStackTrace();
		       }
		    
		     System.out.println("OTRA RUTA --  " + System.getProperty("user.dir")); 
		 
			 	
		        try
		        {
		            //se crea una instancia a la clase Properties
		            Properties propiedades = new Properties();
		            //se leen el archivo .properties
		            System.out.println("mira ... " + getClass().getName());
		            System.out.println("BUSCANDO");
		            
		            //properties.load (this.getClass().getClassLoader().getResourceAsStream("es/indra/aodb/informes/web/action/informes/reportProperties/Report.properties"));
		            
		            propiedades.load(this.getClass().getClassLoader().getResourceAsStream("PI2Web/WebContent/WEB-INF/zk-label_es.properties"));
		            //si el archivo de propiedades NO esta vacio retornan las propiedes leidas
		            if (!propiedades.isEmpty()) 
		            {                
		                return propiedades;
		            } else {//sino  retornara NULL
		                return null;
		            }
		        } catch (IOException ex) {
		            return null;
		        }
		   }
}
