package es.uniapi.modules.execution_enviroment.service.programming.Impl;

import java.io.File;
import java.util.ArrayList;

import org.joda.time.DateTime;

import es.uniapi.modules.execution_enviroment.execution.Execution;
import es.uniapi.modules.execution_enviroment.model.Proyect;
import es.uniapi.modules.execution_enviroment.model.ProyectType;
import es.uniapi.modules.execution_enviroment.model.TicketExecution;
import es.uniapi.modules.execution_enviroment.model.exceptions.ExecutionException;
import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;
import es.uniapi.modules.execution_enviroment.model.infoService.InfoPython;
import es.uniapi.modules.execution_enviroment.model.infoService.InfoService;
import es.uniapi.modules.execution_enviroment.service.ProgrammingService;

public class ServicePython implements es.uniapi.modules.execution_enviroment.service.programming.Intf.ServicePython {
	
	Proyect pythonProyect;
	private File responsePython[]={null,null};
	private DateTime initialServiceTime=null;
	private ExecutionState state=ExecutionState.Stopped;
	private Execution currentExecution;
	
	public void InicializateService(Proyect proyect) throws ServiceException {
		// TODO Auto-generated method stub
		this.pythonProyect=proyect;
		if(!ExistProyect())
			throw new ServiceException();
		
	}

	public boolean ExistProyect() {
		// TODO Auto-generated method stub
		File directory=new File(pythonProyect.getOriginPath());
		return directory.exists();
	}

	public File[] getResponse() throws ServiceException {
		// TODO Auto-generated method stub
		if(responsePython[0]==null || responsePython[1]==null)
			throw new ServiceException();
		return responsePython;
	}

	public long getActivatedTime() {
		// TODO Auto-generated method stub
		if(initialServiceTime==null)
			return 0;
		
		DateTime now=new DateTime();
		
		return now.getMillis()-initialServiceTime.getMillis();
	}

	public ExecutionState getState() {
		// TODO Auto-generated method stub
		if(!this.currentExecution.isAlive())
			this.state=ProgrammingService.ExecutionState.Stopped;
		return state;
	}

	public long executedProyect(String outputPath) throws ServiceException {
		// TODO Auto-generated method stub
		
		initialServiceTime=new DateTime();
		
		TicketExecution ticket=null;
		String mainPath=this.pythonProyect.getOriginPath();
		ArrayList<String> arguments;
		
		for(int i=0;i<this.pythonProyect.getServiceInfo().size();i++){
			InfoService container=this.pythonProyect.getServiceInfo().get(i);
			
			if(container.type==ProyectType.PYTHON){
				InfoPython pythoncontainer=(InfoPython) this.pythonProyect.getServiceInfo().get(i);
				mainPath="python "+mainPath+'\\'+pythoncontainer.main;
				arguments=pythoncontainer.arguments;
				arguments.add(outputPath);
				ticket=new TicketExecution(mainPath, arguments,outputPath+'\\'+pythoncontainer.nameResponse);
			}
		}
		
		if(ticket==null)
			throw new ServiceException();
		
		currentExecution=new Execution(ticket);
		this.state=ExecutionState.Running;
		
		currentExecution.run();
		
		return getActivatedTime();
	}

	public void stopedCurrentExecution() throws ServiceException {
		// TODO Auto-generated method stub
		if(currentExecution!=null){
			try {
				currentExecution.getProcess().destroy();
				state=ExecutionState.Stopped;
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
