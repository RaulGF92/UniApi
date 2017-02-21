package es.uniapi.modules.apirest.controller;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.uniapi.modules.apirest.model.MemberGroup;
import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.MessageMemberGroup;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.business.groupgestion.GroupGestion.GroupRole;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.sessiongestion.SessionGestor;
import es.uniapi.modules.sessiongestion.impl.SessionGestorMap;


@RestController
@RequestMapping(value="/group/")
public class GroupMemberController {
	
	SessionGestor sessionGestor;
	private final RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	public GroupMemberController(RequestMappingHandlerMapping handlerMapping) {
		 this.handlerMapping = handlerMapping;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}/member",method=RequestMethod.GET)
	public Message getAllMembers(@PathVariable String token,@PathVariable String groupID){
		Message msg=null;
		UserLogin userLogin;
		String[] relatedID={groupID};
		MemberGroup[] users;
		Group group=null;
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
		
		try{
			
			group=Modules.getGroupModule().findByHash(groupID);
		}catch (Exception e) {
			// TODO: handle exception
			msg=new MessageMemberGroup(23, token, relatedID, new MemberGroup[0]);
		}
		
		try{
			
			UserLogin[] response=Modules.getGroupModule().findAllMemberOfGroup(group);
			users=new MemberGroup[response.length];
			for(int i=0;i<response.length;i++){
				users[i]=new MemberGroup(response[i].getUser(),Modules.getGroupModule().getUserRoleOnGruop(response[i],group).toString(), Modules.getGroupModule().getInfoMember(response[i],group));
			}
			
			msg=new MessageMemberGroup(0, token, relatedID, users);
		}catch (Exception e) {
			// TODO: handle exception
			msg=new MessageMemberGroup(26, token, relatedID, new MemberGroup[0]);
		}
		return msg;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}/member/{user}/delete",method=RequestMethod.DELETE)
	public Message deleteMember(@PathVariable String token,@PathVariable String groupID,@PathVariable String user){
		Message msg=null;
		UserLogin userLogin;
		String[] relatedID={groupID};
		Group group = null;
		UserLogin userToDelete = null;
		
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
		
		try{
			
			group=Modules.getGroupModule().findByHash(groupID);
		}catch (Exception e) {
			// TODO: handle exception
			msg=new MessageMemberGroup(23, token, relatedID, new MemberGroup[0]);
		}
		
		try {
			userToDelete=Modules.getIdentityModule().findUserLoginByUser(user);
		} catch (BussinessException e1) {
			// TODO Auto-generated catch block
			msg=new MessageMemberGroup(1, token, relatedID, new MemberGroup[0]);
		}
		
		try {
			Modules.getGroupModule().deleteUserMemberOfGroup(userLogin,userToDelete, group);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			msg=new MessageMemberGroup(27, token, relatedID, new MemberGroup[0]);
		}
		msg=new Message(0, token, relatedID);
		return msg;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}/member/create",method=RequestMethod.GET)
	public Message exampleCreateNewMemberToGroup(@PathVariable String token,@PathVariable String groupID){
		Message msg=null;
		UserLogin userLogin;
		String[] relatedID={groupID};
		
		MemberGroup[] users=new MemberGroup[3];
		MemberGroup memberGroup=new MemberGroup("userToAdd1@uniapi.es", GroupRole.ADMIN.toString(), new Date());
		users[0]=memberGroup;
		memberGroup.setUser("userToAdd2@uniapi.es");
		memberGroup.setRol(GroupRole.OWNER.toString());
		users[1]=memberGroup;
		memberGroup.setUser("userToAdd3@uniapi.es");
		memberGroup.setRol(GroupRole.Member.toString());
		users[2]=memberGroup;
		
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
		msg=new MessageMemberGroup(0, token,relatedID,users);
		return msg;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}/member/create",method=RequestMethod.POST)
	public Message createNewMemberToGroup(@PathVariable String token,@PathVariable String groupID,@RequestBody String response){
		Message msg=null;
		JSONObject obj=new JSONObject(response);
		ArrayList<MemberGroup> usersSuccess=new ArrayList<MemberGroup>();
		ArrayList<UserLogin> usersToAdd=new ArrayList<UserLogin>();
		org.json.JSONArray usersArr=obj.getJSONArray("users");
		UserLogin aux=null;
		UserLogin userLogin;
		String[] relatedID={groupID};
		Group group = null;
		
		try {
			
			userLogin=sessionGestor.checkSession(token);
			if(userLogin==null){
				msg=new Message(4,token,relatedID);
				return msg;
			}
			
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			msg=new Message(4, token, relatedID);
			return msg;
		}
		
		try{
			
			group=Modules.getGroupModule().findByHash(groupID);
		}catch (Exception e) {
			// TODO: handle exception
			msg=new MessageMemberGroup(23, token, relatedID, new MemberGroup[0]);
		}

		for(int i=0;i<usersArr.length();i++){
			try {
				aux=Modules.getIdentityModule().findUserLoginByUser(usersArr.getString(i));
			} catch (Exception e){
				System.out.println("[createNewMemberToGroup]Fallo en la busqueda");
			}
			if(aux!=null){
				usersToAdd.add(aux);
			}
		}
		
		for(int i=0;i<usersToAdd.size();i++){
			try {
				Modules.getGroupModule().makeUserMemberOfGroup(userLogin,usersToAdd.get(i), group);
				usersSuccess.add(new MemberGroup(usersToAdd.get(i).getUser(),Modules.getGroupModule().getUserRoleOnGruop(usersToAdd.get(i),group).toString(), Modules.getGroupModule().getInfoMember(usersToAdd.get(i),group)));
			} catch (BussinessException e) {
				// TODO Auto-generated catch block
			}
		}
		
		msg=new MessageMemberGroup(0, token, relatedID, usersSuccess.toArray(new MemberGroup[usersSuccess.size()]));
		return msg;
	}
}
