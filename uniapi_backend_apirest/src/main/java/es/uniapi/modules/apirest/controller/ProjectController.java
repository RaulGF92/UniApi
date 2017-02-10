package es.uniapi.modules.apirest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Param;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.uniapi.modules.apirest.model.MessageProject;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.sessiongestion.SessionGestor;
import es.uniapi.modules.sessiongestion.impl.SessionGestorMap;


@RestController
@RequestMapping(value="/project/*")
public class ProjectController {

	SessionGestor sessionGestor;
	
	 private final RequestMappingHandlerMapping handlerMapping;


	 @Autowired
	 public ProjectController(RequestMappingHandlerMapping handlerMapping) {
		 this.handlerMapping = handlerMapping;
	 }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{projectId}", method=RequestMethod.GET)
	public MessageProject getProyect(@PathVariable String token,@PathVariable String projectId){
		
		UserLogin user;
		MessageProject messageProject=null;
		Project[] projects=new Project[1];
		Project project;
		sessionGestor=SessionGestorMap.getSessionGestor();
		
		try{
			user=sessionGestor.checkSession(token);
		}catch(SessionGestionException s){
			messageProject=new MessageProject(4, token,new Project[0]);
			return messageProject;
		}
		try {
			project=Modules.getProjectModule().getProject(projectId);
			projects[0]=project;
			messageProject=new MessageProject(0, token, projects);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			messageProject=new MessageProject(13, token,new Project[0]);
		}
		
		
		return messageProject;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/project",method=RequestMethod.GET)
	public MessageProject getProjectExample(@PathVariable String token){
		
		MessageProject messageProject;
		UserLogin user;
		sessionGestor=SessionGestorMap.getSessionGestor();
		try{
			
			user=sessionGestor.checkSession(token);
			if(user==null){
				messageProject=new MessageProject(4, token,new Project[0]);
				return messageProject;
			}
		}catch(SessionGestionException s){
			messageProject=new MessageProject(4, token,new Project[0]);
			return messageProject;
		}
		
		//explain project
		String[] v={"default","inputs","of","the","program"};
		Project project=new Project(new Date(),"name Of Project",ProjectType.TypeOfProject, "description of project", "git Repository URL", "email of repository", "password of repository", new Date(), "Name of the main program", "Name of the response name of the program", v, "Description of the program's inputs", "Description of the program's outputs");
		
		Project[] projects=new Project[1];
		projects[0]=project;
		
		messageProject=new MessageProject(0, token, projects);
		
		return messageProject;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/project",method=RequestMethod.POST,consumes={MediaType.APPLICATION_JSON_VALUE})
	public MessageProject newProject(@PathVariable String token,@RequestBody String project){
		
		System.out.println("[token: "+token+"] New create proyect");
		System.out.println(project);
		UserLogin user=null;
		MessageProject messageProject=null;
		sessionGestor=SessionGestorMap.getSessionGestor();
		try{
			user=sessionGestor.checkSession(token);
			if(user==null){
				messageProject=new MessageProject(4, token,new Project[0]);
				return messageProject;
			}
			
			JSONObject obj = new JSONObject(project);
			
			JSONArray arr=obj.getJSONArray("defaultInputs");
			List<String> list = new ArrayList<String>();
			for(int i = 0; i < arr.length(); i++){
			    list.add(arr.getString(i));
			}
			
			Project projectToCreate=new Project(new Date(), 
					obj.getString("name"),
					ProjectType.valueOf(obj.getString("type")), 
					obj.getString("description"), 
					obj.getString("gitRepositoryURL"), 
					obj.getString("email"), 
					obj.getString("password"), 
					new Date(), 
					obj.getString("mainName"), 
					obj.getString("responseName"), 
					list.toArray(new String[list.size()]), 
					obj.getString("inputDescription"), 
					obj.getString("outputDescription"));
			
			try {
				Modules.getProjectModule().createProject(user, projectToCreate);
			} catch (BussinessException e) {
				// TODO Auto-generated catch block
				messageProject=new MessageProject(11, token,new Project[0]);
				return messageProject;
			}
			messageProject=new MessageProject(0, token,new Project[0]);
			
		}catch(SessionGestionException s){
			messageProject=new MessageProject(4, token,new Project[0]);
			return messageProject;
		}
		
		return messageProject;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/project/all",method=RequestMethod.GET)
	public MessageProject getAllProjects(@PathVariable String token){
		
		UserLogin user;
		MessageProject messageProject=null;
		sessionGestor=SessionGestorMap.getSessionGestor();
		try{
			user=sessionGestor.checkSession(token);
		}catch(SessionGestionException s){
			messageProject=new MessageProject(4, token,new Project[0]);
			return messageProject;
		}
		
		try {
			Project[] projects=Modules.getProjectModule().getAllProjects(user);
			messageProject=new MessageProject(0, token, projects);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			messageProject=new MessageProject(14, token,new Project[0]);
			return messageProject;
		}
		return messageProject;
	}
}
