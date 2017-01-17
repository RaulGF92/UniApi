package es.uniapi.modules.execution_enviroment.service.programming.Impl;

import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.Proyect;

public class ServiceOctave extends ProgrammingService {

	private Proyect octaveProyect;
	
	public ServiceOctave(long id) {
		// TODO Auto-generated constructor stub
		super(id);
	}

	@Override
	public void inicializateService(Proyect proyect) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existProyect() throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAbsoluteProyectPath() {
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
	public Proyect getProyect() {
		// TODO Auto-generated method stub
		return this.octaveProyect;
	}


}
