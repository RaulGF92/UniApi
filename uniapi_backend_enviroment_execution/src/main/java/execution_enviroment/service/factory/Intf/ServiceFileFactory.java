package execution_enviroment.service.factory.Intf;

import execution_enviroment.model.exceptions.ServiceException;
import execution_enviroment.service.ServiceFactory;
import execution_enviroment.service.file.Intf.ServiceGit;

public interface ServiceFileFactory extends ServiceFactory {

	public ServiceGit requestServiceGit() throws ServiceException;
}
