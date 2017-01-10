package es.uniapi.modules.execution_enviroment.service.programming.Intf;

import java.io.File;

import es.uniapi.modules.execution_enviroment.model.Proyect;
import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;
import es.uniapi.modules.execution_enviroment.service.ProgrammingService;

public interface ServicePython extends ProgrammingService {

	public void InicializateService(Proyect proyect) throws ServiceException;
	public boolean ExistProyect();
	
	public File[] getResponse() throws ServiceException;
	
	//----------Time----------------
	public long getActivatedTime();
	
	//processing
	public ExecutionState getState();
	public long executedProyect(String outputPath) throws ServiceException;
	public void stopedCurrentExecution() throws ServiceException;
	
	
	
	
	
}