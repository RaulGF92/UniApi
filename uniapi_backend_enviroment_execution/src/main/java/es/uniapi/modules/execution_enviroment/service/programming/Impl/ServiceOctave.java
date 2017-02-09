package es.uniapi.modules.execution_enviroment.service.programming.Impl;

import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.Project;

public class ServiceOctave extends ProgrammingService {

	private Project octaveProyect;
	
	public ServiceOctave(long id) {
		// TODO Auto-generated constructor stub
		super(id);
	}

	@Override
	public void inicializateService(Project proyect) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existProject() throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAbsoluteProjectPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stopedCurrentService() throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executedService(String[] inputs, String outputPath) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return this.octaveProyect;
	}


}
