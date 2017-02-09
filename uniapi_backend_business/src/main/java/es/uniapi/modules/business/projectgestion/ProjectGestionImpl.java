package es.uniapi.modules.business.projectgestion;

import java.io.File;

import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.business.exception.ProjectGestionException;
import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.especial.Impl.ServiceGit;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.config.Configuration;

public class ProjectGestionImpl implements ProjectGestion {

	private String PROJECT_PATH;
	
	public ProjectGestionImpl() {
		// TODO Auto-generated constructor stub
		this.PROJECT_PATH=AppConfiguration.getConfiguration().getProyectSite();
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
			git.inicializateService(project);
			git.newProyect();
		} catch (ServiceException e) {
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

	@Override
	public Project[] getAllProjects(UserLogin user) throws BussinessException {
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


}
