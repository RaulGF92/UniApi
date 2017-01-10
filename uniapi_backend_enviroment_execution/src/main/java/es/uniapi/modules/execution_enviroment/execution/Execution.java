package es.uniapi.modules.execution_enviroment.execution;

import es.uniapi.modules.execution_enviroment.model.*;
import es.uniapi.modules.execution_enviroment.model.exceptions.ExecutionException;

public class Execution extends Thread {

	private TicketExecution ticket;
	private String OS;
	public Process processExecution;
	
	public Execution(TicketExecution ticket){
		super();
		this.ticket=ticket;
		this.OS=System.getProperty("os.name");
	}
	
	public void run(){
		if(OS.contains("Windows")){
			processExecution=executionOnWindows();
			
		}
		
		if(OS.contains("linux")){
			processExecution=executionOnLinux();
		}
		
	}
	
	@SuppressWarnings("unused")
	private Process executionOnLinux() {
		// TODO Auto-generated method stub
		try{
			Process process = Runtime.getRuntime().exec(makeCommand());
			return process;
		}catch(Exception e){
			
		}
		return null;
		
	}
	@SuppressWarnings("unused")
	private Process executionOnWindows() {
		// TODO Auto-generated method stub
		
		try{
			
			String command="Powershell /C ";
			command=command+makeCommand();
			Process process = Runtime.getRuntime().exec(command);
			return process;
			
		}catch(Exception e){
			
		}
		return null;
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
	public Process getProcess()throws ExecutionException{
		if(processExecution==null)
			throw new ExecutionException();
		return processExecution;
		
	}
}
