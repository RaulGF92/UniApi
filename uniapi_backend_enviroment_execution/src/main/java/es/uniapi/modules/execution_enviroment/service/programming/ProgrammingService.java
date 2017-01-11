package es.uniapi.modules.execution_enviroment.service.programming;

import java.util.Date;

import org.joda.time.DateTime;

import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.GeneralService;
import es.uniapi.modules.model.Proyect;

public abstract class ProgrammingService implements GeneralService {
	
	public ProgrammingService(){
		this.createServiceDate=new DateTime().toDate();
		this.state=state.Running;
	}
	
	public String getAbsoluteProyectPath(Proyect proyect){
		
		//Cojer del archivo de propiedades el path de los proyectos
		String instalationProyectPath="C:\\UniApi\\proyects";
		
		String proyectsPath=proyect.getName()+"id["+proyect.getid+"]";
		String response=instalationProyectPath+"\\"+proyectsPath;
		
		return response;
	}
	
	public long milisServiceWorking(){
		
		DateTime now= new DateTime();
		DateTime past=new DateTime(createServiceDate);
		
		if(isWorking() && finishServiceDate!=null){
			now=new DateTime(finishServiceDate);
		}
		
		return now.getMillis()-past.getMillis(); 
	}
	
	public boolean isWorking(){
		if(ExecutionState.Running==state)
			return true;
		return false;
	}
	
	public abstract void stopedCurrentService() throws ServiceException;
	public abstract void executedService(String[] inputs,String outputPath) throws ServiceException;
	
	protected ExecutionState state;
	private Date createServiceDate;
	protected Date finishServiceDate;
	
	
	
	public enum ExecutionState{
		Running,
		Stopped
	};
}
