package com.proint1.udea.produccion.ngc;

import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.util.ProduccionBLException;
 
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
	 * Inserta un autor en la base de datos
	 * @param nombre
	 * @param apellidos
	 * @param tipoIdentificacionIdn
	 * @param id
	 * @param direccion
	 * @param telefono
	 * @param email
	 * @param nacionalidad
	 * @param foto
	 * @return valor booleano que indica si la transaccion fue exitosa o no
	 * @throws ProduccionBLException
	 */
	
	public boolean insertar(long tipoIdentificacionIdn, String id,String apellidos, String nombre, String direccion,String email,String telefono, long nacionalidad, String foto)throws ProduccionBLException;
	
	/**
	 * Obtener un autor de la base de datos 
	 * @param id
	 * @return
	 * @throws ProduccionBLException
	 */
	public TbPrdAutor obtenerAutor(long id)throws ProduccionBLException;
	
	public TbPrdAutor bucarPersona(long id)throws ProduccionBLException;
}
