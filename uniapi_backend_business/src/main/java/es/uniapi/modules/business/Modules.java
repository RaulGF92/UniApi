package es.uniapi.modules.business;

import es.uniapi.modules.business.groupgestion.GroupGestion;
import es.uniapi.modules.business.groupgestion.GroupGestionImpl;
import es.uniapi.modules.business.identitygestion.IdentityGestion;
import es.uniapi.modules.business.identitygestion.IdentityGestionImpl;
import es.uniapi.modules.business.pathgestion.PathGestion;
import es.uniapi.modules.business.pathgestion.PathGestionImpl;
import es.uniapi.modules.business.projectgestion.ProjectGestion;
import es.uniapi.modules.business.projectgestion.ProjectGestionImpl;
import es.uniapi.modules.business.servicegestion.GestorWork;
import es.uniapi.modules.business.servicegestion.gestorsworkers.GestorWorkerMap;

public class Modules {

	
	public static IdentityGestion getIdentityModule(){
		return new IdentityGestionImpl();
	}
	public static GroupGestion getGroupModule(){
		return new GroupGestionImpl();
	}
	public static GestorWork getGestorServiceModule(){
		return GestorWorkerMap.getGestorWorkerMap();
	}
	public static ProjectGestion getProjectModule(){
		return new ProjectGestionImpl();
	}
	public static PathGestion getPathModule(){
		return new PathGestionImpl();
	}
	

}
