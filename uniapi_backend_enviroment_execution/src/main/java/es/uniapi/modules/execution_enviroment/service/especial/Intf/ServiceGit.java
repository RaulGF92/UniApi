package es.uniapi.modules.execution_enviroment.service.especial.Intf;


import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.GeneralService;

public interface ServiceGit extends GeneralService {

	public void loadProject() throws ServiceException;
	public void newProyect();
	
}
