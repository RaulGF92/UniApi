package es.uniapi_backend.modules.business.servicegestion.gestorsworkers;

import es.uniapi.modules.execution_enviroment.service.programming.Impl.ServiceProgramming;
import es.uniapi_backend.modules.business.exception.GestorServiceException;
import es.uniapi_backend.modules.model.*;
import es.uniapi_backend.modules.business.servicegestion.GestorWork;

public class GestorWorkerMap implements GestorWork{

	@Override
	public UsingOne createService(UserLogin user, Proyect proyect) throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void DestroyService(ServiceProgramming service) throws GestorServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String treatmentForNewService(String userPath, ServiceProgramming service) throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewUserExecutionHierarchy(long userID) throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createFileExecutionHierarchy() throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProgramming[] destroyFileExecutionHierarchy(boolean destroyALL) throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProgramming[] getAllActiveService() throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProgramming[] getUserActiveService(long userID) throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
