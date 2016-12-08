package execution_enviroment.service.programming.Intf;

import java.io.File;

import execution_enviroment.model.Proyect;
import execution_enviroment.model.exceptions.ServiceException;
import execution_enviroment.service.ProgrammingService;
import execution_enviroment.service.ProgrammingService.ExecutionState;

public interface ServiceOctave extends ProgrammingService {

	public void InicializateService(Proyect proyect) throws ServiceException;
	public boolean ExistProyect();
	
	public File[] getResponse() throws ServiceException;
	
	//----------Time----------------
	public long getActivatedTime();
	
	//processing
	public ExecutionState getState();
	public long executedProyect() throws ServiceException;
	public void stopedCurrentExecution() throws ServiceException;
}
