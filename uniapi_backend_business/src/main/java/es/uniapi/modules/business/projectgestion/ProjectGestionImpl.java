package es.uniapi.modules.business.projectgestion;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.business.exception.ProjectGestionException;
import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.especial.Impl.ServiceGit;
import es.uniapi.modules.execution_enviroment.service.especial.Impl.ServiceGit.GitOption;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.config.Configuration;

public class ProjectGestionImpl implements ProjectGestion {

	private String PROJECT_PATH;
	private static boolean created=false;
	private static ProjectGestionImpl singleton;
	private HashMap<String,ServiceGit> services;
	private AtomicInteger id;
	private TheUpdater theUpdater;
	
	public static ProjectGestionImpl getProjectGestionImpl(){
		if(created)
			return singleton;
		singleton=new ProjectGestionImpl();
		created=true;
		return singleton;
	}
	
	private ProjectGestionImpl() {
		// TODO Auto-generated constructor stub
		this.PROJECT_PATH=AppConfiguration.getConfiguration().getProyectSite();
		this.services=new HashMap<String,ServiceGit>();
		this.id=new AtomicInteger(0);
		this.theUpdater=new TheUpdater();
		this.theUpdater.start();
	}
	
	@Override
	public void createProject(UserLogin user,Project project) throws BussinessException {
		// TODO Auto-generated method stub
		
		//creamos la entidad proyecto
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		
		try {
			dao.getUniApiDao().getProjectDAO().create(project);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la introducción del proyecto en la DB.");
		}
		//lo enlazamos con el usuario
		try {
			dao.getActions().userLoginCreateProyect(user, project);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en el enlazado del proyecto en la DB.");
		}
		
		//creamos su jerarquia
		try {
			createProjectHierarchy(project);
		} catch (ProjectGestionException e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la creación de la jerarquia del proyecto");
		}
		
		//lo actualizamos con git
		ServiceGit git=new ServiceGit();
		try {
			git.inicializateService(project,GitOption.NEW);
			
			int i=this.id.getAndIncrement();
			
			this.services.put(""+i,git);
			this.services.get(""+i).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la sincronización con git");
		}
		
		//Ta todo echo prim!!
	}

	

	@Override
	public Project getProject(String hash) throws BussinessException {
		// TODO Auto-generated method stub
		Project response=null;
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
			response=dao.getUniApiDao().getProjectDAO().findByHashCode(hash);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return response;
	}
	
	public void updateProject(String hash,Project project) throws BussinessException{
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try{
			dao.getUniApiDao().getProjectDAO().update(hash,project);
		}catch(Exception e){
			throw new BussinessException("La modificación no ha funcionado");
		}
	}

	@Override
	public Project[] getAllUserProjects(UserLogin user) throws BussinessException {
		// TODO Auto-generated method stub
		Project[] response=new Project[0];
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
			response=dao.getActions().getAllProjectsCreatedByUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response=new Project[0];
		}
		
		return response;
	}

	@Override
	public Project[] getTypeProjects(UserLogin user, ProjectType project) throws BussinessException {
		// TODO Auto-generated method stub
		Project[] response=new Project[0];
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
			response=dao.getActions().getTypeProjectsCreatedByUser(user,project);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response=new Project[0];
		}
		
		return response;
	}
	
	private void createProjectHierarchy(Project project) throws ProjectGestionException {
		// TODO Auto-generated method stub
		
		String hierarchyProject=getHierarchyProject(project);
		System.out.println("Vamos a instalar un nuevo proyecto en: "+hierarchyProject);
		if(ExistHierarchyProject(hierarchyProject))
			throw new ProjectGestionException("Ya existe el projecto");
		File file=new File(hierarchyProject);
		file.mkdirs();
		if(ExistHierarchyProject(hierarchyProject)){
			return;
		}else{
			throw new ProjectGestionException("Ha habido fallo en la creación de la carpeta del projecto");
		}
	}


	private boolean ExistHierarchyProject(String hierarchyProject) {
		// TODO Auto-generated method stub
		File file=new File(hierarchyProject);
		if(file.exists() && file.isDirectory())
			return true;
		return false;
	}


	private String getHierarchyProject(Project project) {
		// TODO Auto-generated method stub
		return PROJECT_PATH+"/"+project.getName()+"_id["+project.hash()+"]";
	}


	@Override
	public void deleteProject(UserLogin user,String hash) throws BussinessException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
		
			Project project=dao.getUniApiDao().getProjectDAO().findByHashCode(hash);
			if(project!=null)
				dao.getActions().deleteUserProjectProperty(user,project);
				dao.getUniApiDao().getProjectDAO().delete(project);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new BussinessException("Fallo en la eliminación de proyectos"); 
			}
	}



	@Override
	public Project[] getAllProjects() throws BussinessException {
		// TODO Auto-generated method stub
		Project[] response=new Project[0];
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
			response=dao.getUniApiDao().getProjectDAO().findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response=new Project[0];
		}
		
		return response;
	}


}
