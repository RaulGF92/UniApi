package execution_enviroment.service.factory.Impl;

import execution_enviroment.model.exceptions.ServiceException;
import execution_enviroment.service.programming.Intf.ServiceOctave;
import execution_enviroment.service.programming.Intf.ServicePython;
import execution_enviroment.service.programming.Intf.ServiceR;

public class ServiceProgrammingFactory implements execution_enviroment.service.factory.Intf.ServiceProgrammingFactory {

	public ServicePython requestServicePython() throws ServiceException {
		// TODO Auto-generated method stub
		return new execution_enviroment.service.programming.Impl.ServicePython();
	}

	public ServiceOctave requestServiceOctave() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceR requestServiceR() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
