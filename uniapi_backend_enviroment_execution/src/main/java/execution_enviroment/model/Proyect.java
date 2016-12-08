package execution_enviroment.model;

import java.util.ArrayList;

import execution_enviroment.model.infoService.InfoPython;
import execution_enviroment.model.infoService.InfoService;

public class Proyect {

	private String name;
	private String originPath;
	private ProyectType type;
	ArrayList<InfoService> ServiceInfo;
	
	public Proyect(String name,String originPath,ProyectType type){
		this.name=name;
		this.originPath=originPath;
		this.type=type;
		ServiceInfo=new ArrayList<InfoService>();
	}
	
	public void addInfoService(InfoService info){
		ServiceInfo.add(info);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginPath() {
		return originPath;
	}

	public void setOriginPath(String originPath) {
		this.originPath = originPath;
	}

	public ProyectType getType() {
		return type;
	}

	public void setType(ProyectType type) {
		this.type = type;
	}

	public ArrayList<InfoService> getServiceInfo() {
		return ServiceInfo;
	}

	public void setServiceInfo(ArrayList<InfoService> serviceInfo) {
		ServiceInfo = serviceInfo;
	}
	
	
}
