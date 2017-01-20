package es.uniapi.modules.execution_enviroment.model;

import java.io.File;
import java.util.ArrayList;

public class TicketExecution {
	
	private String order;
	private String mainPath;
	private ArrayList<String> arguments;
	private String outputPath;

	public TicketExecution(String order,String mainPath,ArrayList<String> arguments,String outputPath){
		this.order=order;
		this.mainPath=mainPath;
		this.arguments=arguments;
		this.outputPath=outputPath;
	}
	


	public String getOrder() {
		return order;
	}



	public String getMainPath() {
		return mainPath;
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