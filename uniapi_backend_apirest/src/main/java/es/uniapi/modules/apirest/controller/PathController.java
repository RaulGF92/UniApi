package es.uniapi.modules.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.MessagePath;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.sessiongestion.SessionGestor;
import es.uniapi.modules.sessiongestion.impl.SessionGestorMap;

@RestController
@RequestMapping("/path/*")
public class PathController {

	SessionGestor sessionGestor;
	private final RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	public PathController(RequestMappingHandlerMapping handlerMapping) {
		 this.handlerMapping = handlerMapping;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/main")
	public Message getMainPath(@PathVariable String token){
		
		//En función de los permisos y derechos del usuario devuelve los projectos y grupos del usuario
		UserLogin user=checkSession(token);
		String[] relatedIDs={};
		String[] groupIDs={};
		String[] projectIDs={};
		String objetiveGroup="public";
		if(user == null)
			return new Message(4, token, relatedIDs);
		/*
		 * MATCH t=(u:UserLogin {user:"raulgf92@gmail.com"})-[k:KNOWS]-(g:Group) WHERE NOT (g)<-[:IS_SUBGROUP]-(:Group) AND NOT g.name="Public"  return g
		 */
		try{
			Group[] groups=Modules.getPathModule().getMainPath(user);
			if(groups.length == 0) {
				UniApiFactoryDAO dao =new UniApiFactoryDAO();
				groups=dao.getActions().getGroupsKnowsByUser(user);
			}
			groupIDs=new String[groups.length];
			for(int i=0;i<groups.length;i++){
				groupIDs[i]=groups[i].hash();
			}
		}catch(Exception e){
			
		}
		
		projectIDs=this.getProjectsInGroup(user, getPublicGroup());
		Group publicGroup=this.getPublicGroup();
		projectIDs=this.getProjectsInGroup(user, publicGroup);
		
		return new MessagePath(0,token,relatedIDs,objetiveGroup,groupIDs,projectIDs);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/enter/{groupID}")
	public Message getGroupPath(@PathVariable String token,@PathVariable String groupID){
		String[] relatedIDs={};
		String[] groupIDs={};
		String[] projectIDs={};
		String objetiveGroup="";
		Group groupObjetive;
		
		//Devuelve los grupos y proyectos que se encuentran dentro del grupo
		UserLogin user=checkSession(token);
		if(user == null)
			return new Message(4, token, relatedIDs);
		
		
		try {
			groupObjetive = Modules.getGroupModule().findByHash(groupID);
			objetiveGroup=groupObjetive.getName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Message(4,token,relatedIDs);
		}
		
		groupIDs=this.getSubgroupsInGroup(user, groupObjetive);
		projectIDs=this.getProjectsInGroup(user, groupObjetive);
		
		return new MessagePath(0,token,relatedIDs,objetiveGroup,groupIDs,projectIDs);
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/public")
	public Message getGroupPath(@PathVariable String token){
		//Devuelve los grupos y proyectos que se encuentran dentro del grupo
		UserLogin user=checkSession(token);
		if(user == null)
			return new Message(4, token, new String[0]);
		
		Group publicGroup=this.getPublicGroup();
		String[] relatedIDs={publicGroup.hash()};
		return new Message(0, token,relatedIDs);
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
	
	public Group getPublicGroup(){
		String publicHash="f2e07c5daa643d64591febde2184807b45c88512";
		try {
			return Modules.getGroupModule().findByHash(publicHash);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	public String[] getProjectsInGroup(UserLogin user,Group group){
		String[] response;
		try {
			Project[] projects=Modules.getGroupModule().getAllProjectsIntoGroup(user, group);
			response=new String[projects.length];
			for(int i=0;i<projects.length;i++){
				response[i]=projects[i].hash();
			}
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			response=new String[0];
		}
		return response;
	}
	public String[]	getSubgroupsInGroup(UserLogin user,Group group){
		String[] response;
		try {
			Group[] groups=Modules.getPathModule().getSubGroupsOfGroup(user, group);
			response=new String[groups.length];
			for(int i=0;i<groups.length;i++){
				response[i]=groups[i].hash();
			}
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			response=new String[0];
		}
		return response;
	}
	
}
