package execution_enviroment.service.file.Intf;

import execution_enviroment.model.Proyect;
import execution_enviroment.model.exceptions.ServiceException;
import execution_enviroment.service.GeneralService;

public interface ServiceGit extends GeneralService {

	public void InicializateService(Proyect proyect) throws ServiceException;
	public boolean ExistProyect();
	public void loadProject() throws ServiceException;
	public void newProyect();
	
}
