package es.uniapi.modules.execution_enviroment.model;

import java.io.File;
import java.util.ArrayList;

public class TicketExecution {
	
	private String orderPath;
	private ArrayList<String> arguments;
	private String outputPath;

	public TicketExecution(String orderPath,ArrayList<String> arguments,String outputPath){
		this.orderPath=orderPath;
		this.arguments=arguments;
		this.outputPath=outputPath;
	}

	public String getOrderPath() {
		return orderPath;
	}

	public ArrayList<String> getArguments() {
		return arguments;
	}

	public String getOutputPath() {
		return outputPath;
	}
	
	public File getResponse(){
		
		File response=new File(outputPath);
		
		if(!response.exists())
			response=null;
		
		return response;
	}
}