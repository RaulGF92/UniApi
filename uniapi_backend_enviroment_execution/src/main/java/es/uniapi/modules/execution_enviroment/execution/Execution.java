package es.uniapi.modules.execution_enviroment.execution;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import es.uniapi.modules.execution_enviroment.model.*;
import es.uniapi.modules.execution_enviroment.model.ExecutionException;
import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.config.AppConfiguration.OS;

public class Execution extends Thread {

	private TicketExecution ticket;
	private OS OS;
	public Process processExecution;

	public Execution(TicketExecution ticket) {
		super();
		this.ticket = ticket;
		//this.OS = AppConfiguration.getConfiguration().getOs();
		String operativeSystem = System.getProperty("os.name");
		if (operativeSystem.contains("Windows")) {
			this.OS = OS.WINDOWS;
		} else {
			this.OS = OS.LINUX;
		}
	}

	public void run() {
		if (OS == OS.WINDOWS) {
			processExecution = executionOnWindows();

		}

		if (OS == OS.LINUX) {
			processExecution = executionOnLinux();
		}

	}

	@SuppressWarnings("unused")
	private Process executionOnLinux() {
		// TODO Auto-generated method stub
		//Nota los programas de ejecución rapida, no es posible captar el out
		
		try {
			String[] command=makeLinuxCommand();		
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			FileWriter file = new FileWriter(this.ticket.getOutputPath());
			
			// Se lee la primera linea
			String aux = br.readLine();
			// Mientras se haya leido alguna linea
			while (aux != null) {
				file.write(aux);
				// y se lee la siguiente.
				aux = br.readLine();
			}
			return process;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("unused")
	private Process executionOnWindows() {
		// TODO Auto-generated method stub

		try {

			String command = "Powershell /C ";
			command = command + makeWinCommand();
			Process process = Runtime.getRuntime().exec(command);
			return process;

		} catch (Exception e) {

		}
		return null;
	}

	public String makeWinCommand() throws ExecutionException {

		String command = "";
		
		command = ticket.getOrder()+" "+ticket.getMainPath();
		command = command + " ";
		for (int i = 0; i < ticket.getArguments().size(); i++) {
			command = command + ticket.getArguments().get(i) + " ";
		}
		command = command + ">>" + ticket.getOutputPath();

		if (command == "")
			// throw exception
			throw new ExecutionException("El comando tratado a ejecutar esta vacio");

		return command;
	}
	public String[] makeLinuxCommand()throws ExecutionException{
		
		int commandSize=this.ticket.getArguments().size()+2;
		String[] command=new String[commandSize];
		command[0]=this.ticket.getOrder();
		command[1]=this.ticket.getMainPath();
		for(int i=2;i<commandSize;i++){
			command[i]=ticket.getArguments().get(i-2);
		}

		if (command.length<0)
			// throw exception
			throw new ExecutionException("El comando tratado a ejecutar esta vacio");
		
		String commando="";
		for(int i=0;i<command.length;i++){
			commando=commando+" "+command[i];
		}
		return command;
		
	}

	public Process getProcess() throws ExecutionException {
		if (processExecution == null)
			throw new ExecutionException("El proceso devuelto es de tipo null");
		return processExecution;

	}

	public TicketExecution getTicket() {
		return ticket;
	}

	public void setTicket(TicketExecution ticket) {
		this.ticket = ticket;
	}
}
