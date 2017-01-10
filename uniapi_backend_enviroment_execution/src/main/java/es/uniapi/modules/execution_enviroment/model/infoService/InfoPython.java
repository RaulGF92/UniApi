package es.uniapi.modules.execution_enviroment.model.infoService;

import java.util.ArrayList;

import es.uniapi.modules.execution_enviroment.model.ProyectType;

public class InfoPython extends InfoService {

	public String main;
	public String nameOutPut;
	public String nameResponse;
	public ArrayList<String> arguments;
	
	public InfoPython(String main,String nameOutPut,String nameResponse,ArrayList<String> arguments){
		super(ProyectType.PYTHON);
		this.main=main;
		this.nameOutPut=nameOutPut;
		this.nameResponse=nameResponse;
		this.arguments=arguments;
	}
	
	
}
