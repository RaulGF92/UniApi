package es.uniapi.modules.apirest.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.MessageSubgroup;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.apirest.model.Subgroup;
import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.sessiongestion.SessionGestor;
import es.uniapi.modules.sessiongestion.impl.SessionGestorMap;

@RestController
@RequestMapping(value="/group/")
public class SubgroupController {

	SessionGestor sessionGestor;
	private final RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	public SubgroupController(RequestMappingHandlerMapping handlerMapping) {
		 this.handlerMapping = handlerMapping;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}/subgroups",method=RequestMethod.GET)
	public Message getSubgroup(@PathVariable String token,@PathVariable String groupID){
		UserLogin user=checkSession(token);
		String[] relatedID={groupID};
		Group[] groups;
		Subgroup[] subgroups;
		Group group;
		if(user == null)
			return new Message(4, token, relatedID);
		
		try {
			group=Modules.getGroupModule().findByHash(groupID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Message(23, token, relatedID);
		}
		
		try{
			groups=Modules.getGroupModule().getSubgroupsOfGroup(group);
			subgroups=this.parseSubgroup(group, groups);
		}catch(BussinessException e){
			return new Message(32, token, relatedID);
		}
		
		return new MessageSubgroup(0,token,relatedID,subgroups);
		
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/subgroups/top",method=RequestMethod.GET)
	public Message getSubgroupTop(@PathVariable String token){
		UserLogin user=checkSession(token);
		String[] relatedID={};
		Group[] groups;
		Subgroup[] subgroups;
		Group group;
		if(user == null)
			return new Message(4, token, relatedID);
		
		
		return new Message(0,token,relatedID);
		
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}/subgroups/{subgroupID}",method=RequestMethod.POST)
	public Message makeSubgroup(@PathVariable String token,@PathVariable String groupID,
			@PathVariable String subgroupID){
		UserLogin user=checkSession(token);
		String[] relatedID={groupID,subgroupID};
		Group group;
		Group subgroup;
		if(user == null)
			return new Message(4, token, relatedID);
		
		try {
			group=Modules.getGroupModule().findByHash(groupID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Message(23, token, relatedID);
		}
		
		try {
			subgroup=Modules.getGroupModule().findByHash(subgroupID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Message(23, token, relatedID);
		}
		
		try{
			Modules.getGroupModule().groupIsSubgroupOfGroup(group,subgroup);
		}catch(BussinessException e){
			return new Message(31, token, relatedID);
		}
		
		return new Message(0,token,relatedID);
		
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}/subgroups/{subgroupID}",method=RequestMethod.DELETE)
	public Message deleteSubgroup(@PathVariable String token,@PathVariable String groupID,@PathVariable String subgroupID){
		UserLogin user=checkSession(token);
		String[] relatedID={groupID,subgroupID};
		Group group;
		Group subgroup;
		
		if(user == null)
			return new Message(4, token, relatedID);
		
		try {
			group=Modules.getGroupModule().findByHash(groupID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Message(23, token, relatedID);
		}
		
		try {
			subgroup=Modules.getGroupModule().findByHash(subgroupID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Message(23, token, relatedID);
		}
		
		try{
			Modules.getGroupModule().deleteGroupIsSubgroupOfGroup(user,group,subgroup,null);
		}catch(BussinessException e){
			return new Message(31, token, relatedID);
		}
		
		return new Message(0,token,relatedID);
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
	
	public Subgroup[] parseSubgroup(Group father,Group[] groups){
		ArrayList<Subgroup> response=new ArrayList<Subgroup>();
		Subgroup container;
		Group group;
		for(int i=0;i<groups.length;i++){
			group=groups[i];
			try{
			container=new Subgroup(group.getName(), 
					group.getHashcode(), 
					group.getType().toString(), 
					Modules.getGroupModule().getInfoSubgroup(father,group).getSince());
			response.add(container);
			}catch(BussinessException e){
				
			}
		}
		
		return response.toArray(new Subgroup[response.size()]);
	}
}
