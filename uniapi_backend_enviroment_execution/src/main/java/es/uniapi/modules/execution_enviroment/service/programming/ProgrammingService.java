package es.uniapi.modules.execution_enviroment.service.programming;

import java.util.Date;

import org.joda.time.DateTime;

import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.GeneralService;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.config.AppConfiguration;

public abstract class ProgrammingService implements GeneralService {
	
	@Override
	public String toString() {
		return "ProgrammingService [INSTALATION_PROJECT_PATH=" + INSTALATION_PROJECT_PATH + ", id=" + id + ", state="
				+ state + ", createServiceDate=" + createServiceDate + ", finishServiceDate=" + finishServiceDate + "]";
	}

	//private final String INSTALATION_PROJECT_PATH="C:\\UniApi\\data\\Proyects";
	private final String INSTALATION_PROJECT_PATH;
	private long id;
	
	public ProgrammingService(long id){
		this.createServiceDate=new DateTime().toDate();
		this.state=state.Running;
		this.INSTALATION_PROJECT_PATH=AppConfiguration.getConfiguration().getProyectSite();
		this.id=id;
	}
	
	public String getAbsoluteProjectPath(Project proyect){
		
		//Cojer del archivo de propiedades el path de los proyectos
		
		String proyectsPath=proyect.getName()+"id["+proyect.hash()+"]";
		String response=INSTALATION_PROJECT_PATH+"/"+proyectsPath;
		
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
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ExecutionState getState() {
		return state;
	}

	public void setState(ExecutionState state) {
		this.state = state;
	}

	public Date getCreateServiceDate() {
		return createServiceDate;
	}

	public void setCreateServiceDate(Date createServiceDate) {
		this.createServiceDate = createServiceDate;
	}

	public Date getFinishServiceDate() {
		return finishServiceDate;
	}

	public void setFinishServiceDate(Date finishServiceDate) {
		this.finishServiceDate = finishServiceDate;
	}

	protected ExecutionState state;
	private Date createServiceDate;
	protected Date finishServiceDate;
	
	
	
	public enum ExecutionState{
		Running,
		Stopped
	};
}
