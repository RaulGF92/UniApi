package es.uniapi_backend.modules.business.servicegestion;

import es.uniapi.modules.execution_enviroment.service.programming.Impl.ServiceProgramming;
import es.uniapi_backend.modules.business.exception.GestorServiceException;
import es.uniapi_backend.modules.business.model.Proyect;
import es.uniapi_backend.modules.business.model.UserLogin;
import es.uniapi_backend.modules.business.model.UsingOne;
import es.uniapi_backend.modules.business.servicegestion.gestorsworkers.tools.Dobby;

public interface GestorWork {

	public UsingOne createService(UserLogin user,Proyect proyect) throws GestorServiceException;
	public void DestroyService(ServiceProgramming service) throws GestorServiceException;
	
	public String treatmentForNewService(ServiceProgramming service) throws GestorServiceException; //ServicePath
	public String createNewUserExecutionHierarchy(long userID) throws GestorServiceException; //UserPath
	public void createFileExecutionHierarchy() throws GestorServiceException;
	public ServiceProgramming[] destroyFileExecutionHierarchy(boolean destroyALL) throws GestorServiceException;
	/**
	 * Si exixten servicios en ejecución devuelve los servicios en ejecución
	 */
	public ServiceProgramming[] getAllActiveService() throws GestorServiceException;
	public ServiceProgramming[] getUserActiveService() throws GestorServiceException;
	
	
}
