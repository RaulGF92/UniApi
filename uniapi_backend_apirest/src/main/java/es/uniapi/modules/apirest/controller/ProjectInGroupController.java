package es.uniapi.modules.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.sessiongestion.SessionGestor;
import es.uniapi.modules.sessiongestion.impl.SessionGestorMap;

@RestController
@RequestMapping("/group/")
public class ProjectInGroupController {
	
	SessionGestor sessionGestor;
	private final RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	public ProjectInGroupController(RequestMappingHandlerMapping handlerMapping) {
		 this.handlerMapping = handlerMapping;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/{token}/{groupID}/contain/project/{projectID}", method = RequestMethod.POST)
	public Message projectInGroup(@PathVariable String token, @PathVariable String groupID,
			@PathVariable String projectID) {
		Group group = null;
		Project project = null;
		UserLogin user = this.checkSession(token);
		String[] relatedID = { projectID };
		
		if (user == null)
			return new Message(4, token, relatedID);
		// take group
		try {
			group = Modules.getGroupModule().findByHash(groupID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Message(23, token, relatedID);
		}
		// take project
		try {
			project = Modules.getProjectModule().getProject(projectID);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(13, token, relatedID);
		}
		// Make contain
		try {
			Modules.getGroupModule().putProjectIntoGroup(user, group, project);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(42, token, relatedID);
		}

		return new Message(0, token, relatedID);
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}/contain/project/{projectID}",method=RequestMethod.DELETE)
	public Message  deleteProjectInGroup(@PathVariable String token,
			@PathVariable String groupID,
			@PathVariable String projectID){
		Group group=null;
		Project project=null;
		UserLogin user=this.checkSession(token);
		String[] relatedID={projectID};
		if(user == null)
			return new Message(4, token, relatedID);
		//take group
		try {
			group=Modules.getGroupModule().findByHash(groupID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Message(23, token, relatedID);
		}
		//take project
		try {
			project=Modules.getProjectModule().getProject(projectID);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(13, token, relatedID);
		}
		//Make contain
		try {
			Modules.getGroupModule().deleteProjectIntoGroup(user, group, project);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(42, token, relatedID);
		}
		return new Message(0, token, relatedID);
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}/contain/project",method=RequestMethod.GET)
	public Message getAllProjectsInGroup(@PathVariable String token,@PathVariable String groupID){
		Group group=null;
		Project[] projects=null;
		UserLogin user=this.checkSession(token);
		String[] relatedID={};
		if(user == null)
			return new Message(4, token, relatedID);
		//take group
		try {
			group=Modules.getGroupModule().findByHash(groupID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Message(23, token, relatedID);
		}
		
		try {
			projects=Modules.getGroupModule().getAllProjectsIntoGroup(user, group);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(42, token, relatedID);
		}
		
		relatedID=new String[projects.length];
		for(int i=0;i<projects.length;i++){
			relatedID[i]=projects[i].hash();
		}
		
		return new Message(0, token, relatedID);
	}
	public UserLogin checkSession(String token){
		sessionGestor=SessionGestorMap.getSessionGestor();
		UserLogin userLogin;
		try {
			userLogin = sessionGestor.checkSession(token);
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return userLogin;
	}
}
