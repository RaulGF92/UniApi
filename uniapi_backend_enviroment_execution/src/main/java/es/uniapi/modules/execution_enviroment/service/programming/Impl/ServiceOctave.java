package es.uniapi.modules.execution_enviroment.service.programming.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;

import es.uniapi.modules.execution_enviroment.execution.Execution;
import es.uniapi.modules.execution_enviroment.model.ExecutionException;
import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.model.TicketExecution;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService.ExecutionState;
import es.uniapi.modules.model.Project;

public class ServiceOctave extends ProgrammingService
implements es.uniapi.modules.execution_enviroment.service.programming.Intf.ServiceOctave {

public ServiceOctave(long id) {
super(id);
// TODO Auto-generated constructor stub
}

Project OctaveProyect;
private File responseOctave[] = { null, null };
private Execution currentExecution;

public void inicializateService(Project proyect) throws ServiceException {
// TODO Auto-generated method stub
this.OctaveProyect = proyect;
if (!existProject())
	throw new ServiceException("El servicio ha fallado por no existir el proyecto "+proyect.getMainName());

}

@Override
public boolean isWorking() {
try {
	if (this.currentExecution == null || !this.currentExecution.isAlive()){
		super.state=ExecutionState.Stopped;
		super.finishServiceDate=new DateTime().toDate();
		return false;
	}
} catch (Exception e) {
	// TODO: handle exception
	return false;
}
return true;

}

public boolean existProject() throws ServiceException {
// TODO Auto-generated method stub
if (OctaveProyect == null)
	throw new ServiceException("El servicio ha fallado por no existir el proyecto ");
File directory = new File(super.getAbsoluteProjectPath(this.OctaveProyect));
return directory.exists();
}

@Override
public File[] getResponse() throws ServiceException {
// TODO Auto-generated method stub
responseOctave[0]=new File(this.currentExecution.getTicket().getArguments().get(this.currentExecution.getTicket().getArguments().size()-1)
		+"/"+this.getProject().getResponseName());
responseOctave[1]=new File(this.currentExecution.getTicket().getOutputPath());
if (responseOctave[0] == null || responseOctave[1] == null)
	throw new ServiceException("La respuesta entregada es vacia y contiene null en algunos de sus elementos");
return responseOctave;
}


public void executedService(String[] inputs, String outputPath) throws ServiceException {
// TODO Auto-generated method stub

TicketExecution ticket = null;
String mainPath = super.getAbsoluteProjectPath(OctaveProyect).concat("/");
mainPath=mainPath.concat(OctaveProyect.getMainName());
ArrayList<String> arguments=new ArrayList<String>();

for (int i = 0; i < inputs.length; i++) {
	arguments.add(inputs[i]);
}
arguments.add(outputPath+"/");

ticket = new TicketExecution("octave",mainPath, arguments, outputPath + "/" +OctaveProyect.getName()+"_UniApi_Output");

if (ticket == null)
	throw new ServiceException("ticket para ejecución nulo");

currentExecution = new Execution(ticket);
this.state = ExecutionState.Running;

currentExecution.start();

return;

}

@Override
public String toString() {
return "ServiceOctave [OctaveProyect=" + OctaveProyect + ", responseOctave=" + Arrays.toString(responseOctave)
		+ ", currentExecution=" + currentExecution + ", toString()=" + super.toString() + "]";
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
public String getAbsoluteProjectPath() {
// TODO Auto-generated method stub
if (this.OctaveProyect == null)
	return null;
return super.getAbsoluteProjectPath(OctaveProyect);
}


@Override
public Project getProject() {
// TODO
return this.OctaveProyect;
}

}
