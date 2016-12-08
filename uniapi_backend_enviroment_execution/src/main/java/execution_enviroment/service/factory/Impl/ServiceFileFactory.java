package execution_enviroment.service.factory.Impl;

import execution_enviroment.model.exceptions.ServiceException;
import execution_enviroment.service.file.Intf.ServiceGit;

public class ServiceFileFactory implements execution_enviroment.service.factory.Intf.ServiceFileFactory {

	public ServiceGit requestServiceGit() throws ServiceException {
		// TODO Auto-generated method stub
		return new execution_enviroment.service.file.Impl.ServiceGit();
	}

}
