package com.proint1.udea.produccion.ngc;

import java.util.Date;
import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
 
/**
 * Interfaz que define los metodos que va a proveer el Servicio de Autores 
 * @author Edison Castro
 *
 */
public interface AutorService {
	
	/**
	 * Listar los autores de la base de datos
	 * @return
	 * @throws ProduccionBLException
	 */
	public List<TbPrdAutor> listar() throws ProduccionBLException;
	
	/**
	 * Insertar un autor en la base de datos
	 * @param tipoIdentificacionIdn
	 * @param id
	 * @param nombres
	 * @param apellidos
	 * @param direccion
	 * @param telefono
	 * @param email
	 * @param sexo
	 * @param fechaNacimiento
	 * @param nacionalidad
	 * @throws ProduccionBLException
	 */
	public void insertar(long tipoIdentificacionIdn, String id, String nombres, String apellidos, String direccion, String telefono, String email, String sexo, Date fechaNacimiento,
			long nacionalidad)throws ProduccionBLException;
	
	/**
	 * Obtener un autor de la base de datos 
	 * @param id
	 * @return
	 * @throws ProduccionBLException
	 */
	public TbPrdAutor obtenerAutor(long id)throws ProduccionBLException;
}
