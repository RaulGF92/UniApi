package es.uniapi.modules.execution_enviroment.service.programming.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import es.uniapi.modules.execution_enviroment.execution.Execution;
import es.uniapi.modules.model.Proyect;
import es.uniapi.modules.execution_enviroment.model.TicketExecution;
import es.uniapi.modules.execution_enviroment.model.ExecutionException;
import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;

public class ServicePython extends ProgrammingService
		implements es.uniapi.modules.execution_enviroment.service.programming.Intf.ServicePython {

	public ServicePython(long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	Proyect pythonProyect;
	private File responsePython[] = { null, null };
	private Execution currentExecution;

	public void inicializateService(Proyect proyect) throws ServiceException {
		// TODO Auto-generated method stub
		this.pythonProyect = proyect;
		if (!existProyect())
			throw new ServiceException("El servicio ha fallado por no existir el proyecto "+proyect.getMainName());

	}

	public boolean existProyect() throws ServiceException {
		// TODO Auto-generated method stub
		if (pythonProyect == null)
			throw new ServiceException("El servicio ha fallado por no existir el proyecto ");
		File directory = new File(super.getAbsoluteProyectPath(this.pythonProyect));
		return directory.exists();
	}

	public File[] getResponse() throws ServiceException {
		// TODO Auto-generated method stub
		if (responsePython[0] == null || responsePython[1] == null)
			throw new ServiceException("La respuesta entregada es vacia y contiene null en algunos de sus elementos");
		return responsePython;
	}


	public void executedService(String[] inputs, String outputPath) throws ServiceException {
		// TODO Auto-generated method stub

		TicketExecution ticket = null;
		String mainPath = super.getAbsoluteProyectPath(pythonProyect) + "\\" + pythonProyect.getMainName();
		ArrayList<String> arguments=new ArrayList<String>();

		for (int i = 0; i < inputs.length; i++) {
			arguments.add(inputs[i]);
			arguments.add(outputPath);
		}
		
		ticket = new TicketExecution("python",mainPath, arguments, outputPath + '\\' +pythonProyect.getResponseName());

		if (ticket == null)
			throw new ServiceException("ticket para ejecución nulo");

		currentExecution = new Execution(ticket);
		this.state = ExecutionState.Running;

		currentExecution.run();

	}

	public void stopedCurrentService() throws ServiceException {
		// TODO Auto-generated method stub
		if (currentExecution != null) {
			try {
				currentExecution.getProcess().destroy();
				super.state = ExecutionState.Stopped;
				super.finishServiceDate=new DateTime().toDate();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getAbsoluteProyectPath() {
		// TODO Auto-generated method stub
		if (this.pythonProyect == null)
			return null;
		return super.getAbsoluteProyectPath(pythonProyect);
	}

	@Override
	public boolean isWorking() {
		// TODO Auto-generated method stub
		if(currentExecution.isAlive())
			return true;
		super.state=ExecutionState.Stopped;
		super.finishServiceDate=new DateTime().toDate();
		return false;
	}

	@Override
	public Proyect getProyect() {
		// TODO
		return this.pythonProyect;
	}

}
