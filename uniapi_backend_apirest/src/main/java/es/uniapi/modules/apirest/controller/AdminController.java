package es.uniapi.modules.apirest.controller;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.MessageAdminUsers;
import es.uniapi.modules.apirest.model.MessageGroup;
import es.uniapi.modules.apirest.model.MessageProject;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.sessiongestion.SessionGestor;
import es.uniapi.modules.sessiongestion.impl.SessionGestorMap;

@RestController
@RequestMapping(value="/admin/*")
public class AdminController {

	SessionGestor sessionGestor;
	private final RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	public AdminController(RequestMappingHandlerMapping handlerMapping) {
		 this.handlerMapping = handlerMapping;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/createAccount",method=RequestMethod.POST)
	public Message createAccount(@PathVariable String token,@RequestBody String response){
		UserLogin user=this.checkSession(token);
		if(user==null && user.getRol()!="admin")
			return new Message(4, token,new String[0]);
		JSONObject obj=new JSONObject(response);
		UserLogin userToCreate=new UserLogin(obj.getString("user"),
				obj.getString("pass"), new Date(), obj.getString("rol"));
		Person personEmpty=new Person("", "", new Date(), "", "", "", "", "http://www.clipartbest.com/cliparts/ecM/EE6/ecMEE6rMi.png", new Date());
		String[] relatedID={user.hash()};
		try {
			Modules.getIdentityModule().createAccount(userToCreate, personEmpty);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(4, token,new String[0]);
		}
		return new Message(0, token, relatedID);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="{token}/allUsers")
	public Message getAllUsers(@PathVariable String token){
		UserLogin[] users;
		UserLogin user=this.checkSession(token);
		if(user==null && user.getRol()!="admin")
			return new Message(4, token,new String[0]);
		try{
			users=Modules.getIdentityModule().getAllUsers();
		}catch(Exception e){
			return new Message(4, token, new String[0]);
		}
		String[] relatedIDs=new String[users.length];
		for(int i=0;i< relatedIDs.length;i++){
			relatedIDs[i]=users[i].hash();
		}
		return new MessageAdminUsers(0, token, relatedIDs, users);
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="{token}/allGroups")
	public Message getAllGroups(@PathVariable String token){
		Group[] groups;
		UserLogin user=this.checkSession(token);
		if(user==null && user.getRol()!="admin")
			return new Message(4, token,new String[0]);
		try{
			groups=Modules.getGroupModule().findAllGroups();
		}catch(Exception e){
			return new Message(4, token, new String[0]);
		}
		String[] relatedIDs=new String[groups.length];
		for(int i=0;i< relatedIDs.length;i++){
			relatedIDs[i]=groups[i].hash();
		}
		return new MessageGroup(0, token, relatedIDs, groups);
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="{token}/allProjects")
	public Message getAllProjects(@PathVariable String token){
		Project[] projects;
		UserLogin user=this.checkSession(token);
		if(user==null && user.getRol()!="admin")
			return new Message(4, token,new String[0]);
		try{
			projects=Modules.getProjectModule().getAllProjects();
		}catch(Exception e){
			return new Message(4, token, new String[0]);
		}
		String[] relatedIDs=new String[projects.length];
		for(int i=0;i< relatedIDs.length;i++){
			relatedIDs[i]=projects[i].hash();
		}
		return new MessageProject(0, token, relatedIDs, projects);
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
