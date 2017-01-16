package es.uniapi_backend.modules.business.servicegestion.gestorsworkers;


import es.uniapi_backend.modules.business.exception.GestorServiceException;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import es.uniapi_backend.modules.business.servicegestion.GestorWork;
import es.uniapi_backend.modules.business.servicegestion.gestorsworkers.tools.Bingo;
import es.uniapi_backend.modules.business.servicegestion.gestorsworkers.tools.ComandasOfServices;
import es.uniapi_backend.modules.business.servicegestion.gestorsworkers.tools.Dobby;

public class GestorWorkerMap implements GestorWork{
 
	private Dobby theDobby;
	private Bingo theBingo; 
	private ComandasOfServices servicesOrderByUsers;
	
	@Override
	public UsingOne createService(UserLogin user, Proyect proyect) throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void DestroyService(ProgrammingService service) throws GestorServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String treatmentForNewService(String userPath, ProgrammingService service) throws GestorServiceException {
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
	public ProgrammingService[] destroyFileExecutionHierarchy(boolean destroyALL) throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProgrammingService[] getAllActiveService() throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProgrammingService[] getUserActiveService(long userID) throws GestorServiceException {
		// TODO Auto-generated method stub
		return null;
	}


}
