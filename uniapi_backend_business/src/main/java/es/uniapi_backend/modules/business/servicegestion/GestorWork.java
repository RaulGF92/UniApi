package es.uniapi_backend.modules.business.servicegestion;

import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.*;
import es.uniapi_backend.modules.business.exception.GestorServiceException;

public interface GestorWork {

	/**
	 * Función que crea un servicio y lo inicializa, 
	 * lo enlaza con el sistema, relacionando las ejecuciones con el usuario 
	 * y lo pone en ejecución 
	 * 
	 * @param user Usuario que ejecuta el acceso
	 * @param proyect Proyecto sobre el que realiza la acción
	 * @return Una entidad Utiliza_Un con la información detallada de la acción
	 * @throws GestorServiceException
	 */
	public UsingOne createService(UserLogin user,Proyect proyect) throws GestorServiceException;
	
	/**
	 * Dado un servicio indicado, este sera buscado por los conjuntos de servicios. Posteriormente sera detenido y eliminado
	 * 
	 * @param service Servicio que se quiere destruir
	 * @throws GestorServiceException
	 */
	public void DestroyService(ProgrammingService service) throws GestorServiceException;
	
	/**
	 * Para cada servicio que sea previamente creado y posteriormmente lanzado. Sera necesario preparar una jerarquia para que disponga de espacio.
	 * En caso de que la ejecución lo requiera.
	 * 
	 * @param userPath jerarquia del usuario donde se necesita su preparación
	 * @param service Servicio que necesita preparar su jerarquia de salidas
	 * @return ServicePath Devuelve la dirección absoluta donde sera almacenado los ficheros necesarios para la ejecucion, asi como las respestas.
	 * @throws GestorServiceException
	 */
	public String treatmentForNewService(String userPath,ProgrammingService service) throws GestorServiceException; //ServicePath
	/**
	 * Para cada usuario que quiera lanzar un servicio se debera reservar un espacio especial, donde se almacenara las ejecuciones.
	 * 
	 * @param userID
	 * @return UserPath Devuelve la jerarquia donde se almacenara los programas realizados por el usuario
	 * @throws GestorServiceException
	 */
	public String createNewUserExecutionHierarchy(long userID) throws GestorServiceException; //UserPath
	
	/**
	 * Para desarrollar la ejecución de los servicios, sera necesario el crear una jerarquia dentro del sistema de ficheros del 
	 * sistema operativo donde se ejecute la aplicación. En ello seran almacenados todos los servicios, que sean ejecutados por los usuarios. 
	 * 
	 * @return PATH de la jerarquia de la aplicación
	 * @throws GestorServiceException
	 */
	public String createFileExecutionHierarchy() throws GestorServiceException;
	/**
	 * Orden de la aplicación para limpiar la jerarquia de directorios de la aplicación, si existe alguna aplicación en ejecucion.
	 * Esta sera devuelta para información del programa y la orden no sera realizada.En caso de un parametro positivo en la entrada.
	 * Este destruira toda la jerarquia, pararando los servicios sin importar su ejecución.
	 * 
	 * @param destroyALL  Indica si no se quiere prestar atención a los servicios en ejecución
	 * @return ServiceProgramming[] Devuelve todas las aplicaciones que estan en ejecucion
	 * @throws GestorServiceException
	 */
	public ProgrammingService[] destroyFileExecutionHierarchy(boolean destroyALL) throws GestorServiceException;
	/**
	 * Retorna todas los servicios que estan activos, indistintamente de los usuarios que sean
	 * 
	 * @return ServiceProgramming[] Devuelve todas las aplicaciones que estan en ejecucion
	 * @throws GestorServiceException
	 */
	public ProgrammingService[] getAllActiveService() throws GestorServiceException;
	/**
	 * Retorna todos los servicios que estan activos por parte de un usuario
	 * @param userID ID del usuario que se quiere conocer los servicios
	 * @return
	 * @throws GestorServiceException
	 */
	public ProgrammingService[] getUserActiveService(long userID) throws GestorServiceException;
	
	
}
