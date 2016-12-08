package execution_enviroment.service;

import execution_enviroment.model.Proyect;
import execution_enviroment.model.exceptions.ServiceException;

public interface ProgrammingService extends GeneralService {

	public void InicializateService(Proyect proyect) throws ServiceException;
	public boolean ExistProyect();
	
	public enum ExecutionState{
		Running,
		Stopped
	};
}
