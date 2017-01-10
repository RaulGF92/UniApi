package es.uniapi.modules.execution_enviroment.service.factory.Intf;

import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;
import es.uniapi.modules.execution_enviroment.service.ServiceFactory;
import es.uniapi.modules.execution_enviroment.service.programming.Intf.ServiceOctave;
import es.uniapi.modules.execution_enviroment.service.programming.Intf.ServicePython;
import es.uniapi.modules.execution_enviroment.service.programming.Intf.ServiceR;

public interface ServiceProgrammingFactory extends ServiceFactory {

	public ServicePython requestServicePython() throws ServiceException;
	public ServiceOctave requestServiceOctave() throws ServiceException;
	public ServiceR requestServiceR() throws ServiceException;
}
