package execution_enviroment.service.factory.Intf;

import execution_enviroment.model.exceptions.ServiceException;
import execution_enviroment.service.ServiceFactory;
import execution_enviroment.service.programming.Intf.ServiceOctave;
import execution_enviroment.service.programming.Intf.ServicePython;
import execution_enviroment.service.programming.Intf.ServiceR;

public interface ServiceProgrammingFactory extends ServiceFactory {

	public ServicePython requestServicePython() throws ServiceException;
	public ServiceOctave requestServiceOctave() throws ServiceException;
	public ServiceR requestServiceR() throws ServiceException;
}
