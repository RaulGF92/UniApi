package es.uniapi.modules.apirest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import es.uniapi.modules.apirest.model.MessageGroup;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Group.GroupType;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.sessiongestion.SessionGestor;
import es.uniapi.modules.sessiongestion.impl.SessionGestorMap;

@RestController
@RequestMapping(value="/group/*")
public class GroupController {
	
	SessionGestor sessionGestor;
	private final RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	public GroupController(RequestMappingHandlerMapping handlerMapping) {
		 this.handlerMapping = handlerMapping;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}",method=RequestMethod.PATCH)
	public Message updateGroup(@PathVariable String token,@PathVariable String groupID,String response){
		Message msg=null;
		UserLogin userLogin;
		String[] relatedID={groupID};
		Group group;
		sessionGestor=SessionGestorMap.getSessionGestor();
		
		try {
			userLogin=sessionGestor.checkSession(token);
			if(userLogin==null){
				msg=new Message(4, token,relatedID);
				return msg;
			}
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			msg=new Message(4, token, relatedID);
			return msg;
		}
		try {
			JSONObject obj=new JSONObject(response);
			
			JSONArray sharingGroupPermissionsJsonArray=obj.getJSONArray("sharingGroupPermissions");
			List<String> sharingGroupPermissions = new ArrayList<String>();
			for(int i = 0; i < sharingGroupPermissionsJsonArray.length(); i++){
				sharingGroupPermissions.add(sharingGroupPermissionsJsonArray.getString(i));
			}
			
			JSONArray projectPropertiesPermissionsJsonArray=obj.getJSONArray("projectPropertiesPermissions");
			List<String> projectPropertiesPermissions = new ArrayList<String>();
			for(int i = 0; i < projectPropertiesPermissionsJsonArray.length(); i++){
				projectPropertiesPermissions.add(projectPropertiesPermissionsJsonArray.getString(i));
			}
			
			JSONArray memberGestionPermissionsJsonArray=obj.getJSONArray("memberGestionPermissions");
			List<String> memberGestionPermissions = new ArrayList<String>();
			for(int i = 0; i < memberGestionPermissionsJsonArray.length(); i++){
				memberGestionPermissions.add(memberGestionPermissionsJsonArray.getString(i));
			}
			
			JSONArray groupCreationPermissionsJsonArray=obj.getJSONArray("groupCreationPermissions");
			List<String> groupCreationPermissions = new ArrayList<String>();
			for(int i = 0; i < groupCreationPermissionsJsonArray.length(); i++){
				groupCreationPermissions.add(groupCreationPermissionsJsonArray.getString(i));
			}
			
			Group groupToUpdate=new Group(obj.getString("name")
					,new Date(), 
					GroupType.valueOf(obj.getString("type")), 
					sharingGroupPermissions.toArray(new String[sharingGroupPermissions.size()]), 
					projectPropertiesPermissions.toArray(new String[projectPropertiesPermissions.size()]), 
					memberGestionPermissions.toArray(new String[memberGestionPermissions.size()]), 
					groupCreationPermissions.toArray(new String[groupCreationPermissions.size()]),
					obj.getString("description"));
			
			if(groupToUpdate!=null)
				Modules.getGroupModule().deleteGroup(userLogin, groupToUpdate);
			msg=new Message(0, token, relatedID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg=new Message(25, token, relatedID);
		}
		
		return msg;
		
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}",method=RequestMethod.DELETE)
	public Message deleteGroup(@PathVariable String token,@PathVariable String groupID){
		Message msg=null;
		UserLogin userLogin;
		String[] relatedID={groupID};
		Group group;
		sessionGestor=SessionGestorMap.getSessionGestor();
		try {
			userLogin=sessionGestor.checkSession(token);
			if(userLogin==null){
				msg=new Message(4, token,relatedID);
				return msg;
			}
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			msg=new Message(4, token, relatedID);
			return msg;
		}
		try {
			group=Modules.getGroupModule().findByHash(groupID);
			if(group!=null)
				Modules.getGroupModule().deleteGroup(userLogin, group);
			msg=new Message(0, token, relatedID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg=new Message(24, token, relatedID);
		}
		
		return msg;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}",method=RequestMethod.GET)
	public MessageGroup getGroup(@PathVariable String token,@PathVariable String groupID){
		MessageGroup msg=null;
		UserLogin userLogin;
		sessionGestor=SessionGestorMap.getSessionGestor();
		
		try {
			userLogin=sessionGestor.checkSession(token);
			if(userLogin==null){
				msg=new MessageGroup(4, token, new String[0],new Group[0]);
				return msg;
			}
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			msg=new MessageGroup(4, token, new String[0],new Group[0]);
			return msg;
		}
		
		try {
			
			Group[] groups={
					Modules.getGroupModule().findByHash(groupID)
			};
			String[] relatedID={
					groups[0].hash()
			};
			msg=new MessageGroup(0, token, relatedID, groups);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg=new MessageGroup(23, token, new String[0],new Group[0]);
			return msg;
		}
		
		return msg;
		
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/all")
	public Message getAllUserGroups(@PathVariable String token){
		Message msg=null;
		UserLogin userLogin;
		sessionGestor=SessionGestorMap.getSessionGestor();
		
		try {
			userLogin=sessionGestor.checkSession(token);
			if(userLogin==null){
				msg=new Message(4, token, new String[0]);
				return msg;
			}
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			msg=new Message(4, token, new String[0]);
			return msg;
		}
		
		try {
			Group[] groups=Modules.getGroupModule().findAllUserGroup(userLogin);
			String[] relatedID=new String[groups.length];
			for(int i=0;i<groups.length;i++){
				relatedID[i]=groups[i].hash();
			}
			msg=new Message(0, token, relatedID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg=new Message(21, token, new String[0]);
			return msg;
		}
		return msg;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/create",method=RequestMethod.GET)
	public MessageGroup getExampleGroup(@PathVariable String token){
		
		MessageGroup msg=null;
		
		sessionGestor=SessionGestorMap.getSessionGestor();
		try {
			sessionGestor.checkSession(token);
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			msg=new MessageGroup(4, token, new String[0], new Group[0]);
			return msg;
		}
		
		String[] sharingGroup={"YES","YES","YES","YES"};
		String[] projectProperties={"YES","YES","YES"};
		String[] memberGestion={"YES","YES","YES","YES"};
		String[] groupCreation={"YES","YES","YES","YES"};
		Group GroupExample=new Group("NombreOfProject", 
				new Date(), 
				GroupType.PUBLIC_GROUP, 
				sharingGroup, 
				projectProperties, 
				memberGestion, 
				groupCreation, 
				"Description of the project. You can put anything you like.");
		Group[] groups={GroupExample};
		msg=new MessageGroup(0, token, new String[0], groups);
		
		return msg; 
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/create",method=RequestMethod.POST,consumes={MediaType.APPLICATION_JSON_VALUE})
	public Message createGroup(@PathVariable String token,@RequestBody String response){
		
		System.out.println("[token: "+token+"] New group proyect");
		System.out.println(response);
		sessionGestor=SessionGestorMap.getSessionGestor();
		UserLogin userLogin = null;
		Message msg = null;
		try {
			userLogin = sessionGestor.checkSession(token);
			if(userLogin==null){
				msg=new Message(4, token,new String[0]);
				return msg;
			}
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			msg=new Message(4, token,new String[0]);
			return msg;
		}
		
		JSONObject obj=new JSONObject(response);
		
		JSONArray sharingGroupPermissionsJsonArray=obj.getJSONArray("sharingGroupPermissions");
		List<String> sharingGroupPermissions = new ArrayList<String>();
		for(int i = 0; i < sharingGroupPermissionsJsonArray.length(); i++){
			sharingGroupPermissions.add(sharingGroupPermissionsJsonArray.getString(i));
		}
		
		JSONArray projectPropertiesPermissionsJsonArray=obj.getJSONArray("projectPropertiesPermissions");
		List<String> projectPropertiesPermissions = new ArrayList<String>();
		for(int i = 0; i < projectPropertiesPermissionsJsonArray.length(); i++){
			projectPropertiesPermissions.add(projectPropertiesPermissionsJsonArray.getString(i));
		}
		
		JSONArray memberGestionPermissionsJsonArray=obj.getJSONArray("memberGestionPermissions");
		List<String> memberGestionPermissions = new ArrayList<String>();
		for(int i = 0; i < memberGestionPermissionsJsonArray.length(); i++){
			memberGestionPermissions.add(memberGestionPermissionsJsonArray.getString(i));
		}
		
		JSONArray groupCreationPermissionsJsonArray=obj.getJSONArray("groupCreationPermissions");
		List<String> groupCreationPermissions = new ArrayList<String>();
		for(int i = 0; i < groupCreationPermissionsJsonArray.length(); i++){
			groupCreationPermissions.add(groupCreationPermissionsJsonArray.getString(i));
		}
		
		Group groupToCreate=new Group(obj.getString("name")
				,new Date(), 
				GroupType.valueOf(obj.getString("type")), 
				sharingGroupPermissions.toArray(new String[sharingGroupPermissions.size()]), 
				projectPropertiesPermissions.toArray(new String[projectPropertiesPermissions.size()]), 
				memberGestionPermissions.toArray(new String[memberGestionPermissions.size()]), 
				groupCreationPermissions.toArray(new String[groupCreationPermissions.size()]),
				obj.getString("description"));
		
		try {
			Modules.getGroupModule().createGroup(userLogin,groupToCreate);
			String[] id={groupToCreate.hash()};
			msg=new Message(0, token,id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg=new Message(4, token,new String[0]);
			return msg;
		}
		
		return msg;
	}
}
