package es.uniapi.modules.execution_enviroment.service.factory;

import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.execution_enviroment.service.especial.EspecialService;
import es.uniapi.modules.model.Project;


public interface ServiceFactory {

	public ProgrammingService getServiceForProject(Project proyect) throws ServiceException;
	public EspecialService getEspecialService() throws ServiceException;
	
}
