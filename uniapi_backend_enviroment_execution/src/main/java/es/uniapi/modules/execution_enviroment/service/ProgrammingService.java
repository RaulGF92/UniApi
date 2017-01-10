package es.uniapi.modules.execution_enviroment.service;

import es.uniapi.modules.execution_enviroment.model.Proyect;
import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;

public interface ProgrammingService extends GeneralService {

	public void InicializateService(Proyect proyect) throws ServiceException;
	public boolean ExistProyect();
	
	public enum ExecutionState{
		Running,
		Stopped
	};
}
