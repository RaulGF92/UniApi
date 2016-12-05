package execution_enviroment.execution;

import execution_enviroment.model.*;
import execution_enviroment.model.exceptions.ExecutionException;

public class Execution extends Thread {

	private TicketExecution ticket;
	private String OS;
	
	public Execution(TicketExecution ticket){
		super();
		this.ticket=ticket;
		this.OS=System.getProperty("os.name");
	}
	
	public void run(){
		if(OS.compareTo("Windows")==0){
			executionOnWindows();
			
		}
		
		if(OS.compareTo("linux")==0){
			executionOnLinux();
		}
		
	}
	
	private void executionOnLinux() {
		// TODO Auto-generated method stub
		try{
			Process process = Runtime.getRuntime().exec(makeCommand());
		}catch(Exception e){
			
		}
		
	}

	private void executionOnWindows() {
		// TODO Auto-generated method stub
		
		try{
			String command="Powershell /C ";
			command=command+makeCommand();
			Process process = Runtime.getRuntime().exec(command);
		}catch(Exception e){
			
		}
		
	}

	public String makeCommand() throws ExecutionException{
		
		String command="";
		
		command=ticket.getOrderPath();
		command=command+" ";
		for(int i=0;i<ticket.getArguments().size();i++){
			command=command+ticket.getArguments().get(i)+" ";
		}
		command=command+">>"+ticket.getOutputPath();
		
		if(command=="")
			//throw exception
			throw new ExecutionException();
		
		return command;
	}
}
