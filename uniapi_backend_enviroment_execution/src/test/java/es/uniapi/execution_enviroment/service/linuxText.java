package es.uniapi.execution_enviroment.service;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import es.uniapi.modules.execution_enviroment.execution.Execution;
import es.uniapi.modules.execution_enviroment.model.TicketExecution;

public class linuxText {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		caso1();
		//caso2();
	}
	
	public static void caso2() throws IOException{
		String[] command={"python","/home/raulgf92/Escritorio/prueba/main.py","10","10","10","10","/home/raulgf92/Escritorio/prueba/"};
		String oneCommand="python /home/raulgf92/Escritorio/prueba/main.py 10 10 10 10 /home/raulgf92/Escritorio/prueba/";
		Process process = Runtime.getRuntime().exec(command);
		//Process process = Runtime.getRuntime().exec(oneCommand);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		FileWriter file = new FileWriter("/home/raulgf92/Escritorio/prueba/outputPrueba.txt");
		
		// Se lee la primera linea
		String aux = br.readLine();
		// Mientras se haya leido alguna linea
		while (aux != null) {
			file.write(aux);
			// y se lee la siguiente.
			aux = br.readLine();
		}
	}
	public static void caso1(){
		ArrayList<String> arguments=new ArrayList<String>();
		arguments.add("10");
		arguments.add("10");
		arguments.add("10");
		arguments.add("10");
		arguments.add("/home/raulgf92/Escritorio/prueba/");
		
		TicketExecution ticket=new TicketExecution(
				"python","/home/raulgf92/Escritorio/prueba/main.py", 
				arguments, 
				"/home/raulgf92/Escritorio/prueba/outputPrueba.txt");
		Execution execute=new Execution(ticket);
		execute.run();

	}

}
