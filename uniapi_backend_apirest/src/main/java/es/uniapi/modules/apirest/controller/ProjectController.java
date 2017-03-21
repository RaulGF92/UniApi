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

import es.uniapi.modules.apirest.model.Message;
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
	public Message getProyect(@PathVariable String token,@PathVariable String projectId){
		
		UserLogin user;
		MessageProject messageProject=null;
		Project[] projects=new Project[1];
		String[]  relatedIDs=new String[1];
		Project project;
		sessionGestor=SessionGestorMap.getSessionGestor();
		
		try{
			user=sessionGestor.checkSession(token);
		}catch(SessionGestionException s){
			messageProject=new MessageProject(4, token,new String[0],new Project[0]);
			return messageProject;
		}
		try {
			project=Modules.getProjectModule().getProject(projectId);
			projects[0]=project;
			relatedIDs[0]=projectId;
			messageProject=new MessageProject(0, token,relatedIDs, projects);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			messageProject=new MessageProject(13, token,new String[0],new Project[0]);
		}
		
		
		return messageProject;
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{projectId}", method=RequestMethod.PATCH)
	public Message patchProyect(@PathVariable String token,@PathVariable String projectId,@RequestBody String response){

		UserLogin user = null;
		sessionGestor=SessionGestorMap.getSessionGestor();
		Message msg;
		
		try {
			user=sessionGestor.checkSession(token);
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			msg=new Message(4, token,new String[0]);
		}
		
		JSONObject obj=new JSONObject(response);
		JSONArray arr=obj.getJSONArray("defaultInputs");
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < arr.length(); i++){
		    list.add(arr.getString(i));
		}
		
		Project projectToUpdate=new Project(new Date(), 
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
		
		Project[] projects;
		try {
			projects = Modules.getProjectModule().getAllUserProjects(user);
			for(int i=0;i<projects.length;i++){
				if(projects[i].hash().compareTo(projectId)==0){
					Modules.getProjectModule().updateProject(projectId,projectToUpdate);
					String[] projectID={projectId};
					return new Message(0,token,projectID);
				}
			}
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			msg=new Message(11, token,new String[0]);
		}
		
		
		return null;
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{projectId}", method=RequestMethod.DELETE)
	public Message deleteProyect(@PathVariable String token,@PathVariable String projectId){
		UserLogin user = null;
		sessionGestor=SessionGestorMap.getSessionGestor();
		Message msg=null;
		
		try {
			user=sessionGestor.checkSession(token);
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			msg=new Message(4, token,new String[0]);
		}
		
		Project[] projects;
		try {
			projects = Modules.getProjectModule().getAllUserProjects(user);
			for(int i=0;i<projects.length;i++){
				if(projects[i].hash().compareTo(projectId)==0){
					Modules.getProjectModule().deleteProject(user,projectId);
					String[] projectID={projectId};
					return new Message(0,token,projectID);
				}
			}
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			msg=new Message(11, token,new String[0]);
		}
		
		return msg;
	}
	//--------------------Create zone------------------------------
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/create",method=RequestMethod.GET)
	public Message getProjectExample(@PathVariable String token){
		
		MessageProject messageProject;
		UserLogin user;
		sessionGestor=SessionGestorMap.getSessionGestor();
		try{
			
			user=sessionGestor.checkSession(token);
			if(user==null){
				messageProject=new MessageProject(4, token,new String[0],new Project[0]);
				return messageProject;
			}
		}catch(SessionGestionException s){
			messageProject=new MessageProject(4, token,new String[0],new Project[0]);
			return messageProject;
		}
		
		//explain project
		String[] v={"default","inputs","of","the","program"};
		Project project=new Project(new Date(),"name Of Project",ProjectType.TypeOfProject, "description of project", "git Repository URL", "email of repository", "password of repository", new Date(), "Name of the main program", "Name of the response name of the program", v, "Description of the program's inputs", "Description of the program's outputs");
		
		Project[] projects=new Project[1];
		projects[0]=project;
		
		messageProject=new MessageProject(0, token,new String[0], projects);
		
		return messageProject;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/create",method=RequestMethod.POST,consumes={MediaType.APPLICATION_JSON_VALUE})
	public MessageProject newProject(@PathVariable String token,@RequestBody String project){
		
		System.out.println("[token: "+token+"] New create proyect");
		System.out.println(project);
		UserLogin user=null;
		MessageProject messageProject=null;
		sessionGestor=SessionGestorMap.getSessionGestor();
		try{
			user=sessionGestor.checkSession(token);
			if(user==null){
				messageProject=new MessageProject(4, token,new String[0],new Project[0]);
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
				messageProject=new MessageProject(11, token,new String[0],new Project[0]);
				return messageProject;
			}
			String[] relatedIDs={projectToCreate.hash()};
			messageProject=new MessageProject(0, token,relatedIDs,new Project[0]);
			
		}catch(SessionGestionException s){
			messageProject=new MessageProject(4, token,new String[0],new Project[0]);
			return messageProject;
		}
		
		return messageProject;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/all",method=RequestMethod.GET)
	public Message getAllProjects(@PathVariable String token){
		
		UserLogin user;
		Message messageInfoProject=null;
		sessionGestor=SessionGestorMap.getSessionGestor();
		try{
			user=sessionGestor.checkSession(token);
		}catch(SessionGestionException s){
			messageInfoProject=new Message(4, token,new String[0]);
			return messageInfoProject;
		}
		
		try {
			Project[] projects=Modules.getProjectModule().getAllUserProjects(user);
			String[] projectsID=new String[projects.length];
			for(int i=0;i<projects.length;i++){
				projectsID[i]=projects[i].hash();
			}
			messageInfoProject=new Message(0, token, projectsID);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			messageInfoProject=new Message(4, token,new String[0]);
			return messageInfoProject;
		}
		return messageInfoProject;
	}
}
