package es.uniapi.modules.apirest.controller;

import java.util.Date;

import org.hibernate.validator.internal.xml.MethodType;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.MessageWhoAmi;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.sessiongestion.SessionGestor;
import es.uniapi.modules.sessiongestion.impl.SessionGestorMap;

@RestController
@RequestMapping("/identity/*")
public class IdentityController {

	SessionGestor sessionGestor;
	private final RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	public IdentityController(RequestMappingHandlerMapping handlerMapping) {
		 this.handlerMapping = handlerMapping;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="{token}/password/{password}",method=RequestMethod.PATCH)
	public Message changePassword(@PathVariable String token,@PathVariable String password){
		UserLogin user = this.checkSession(token);
		String[] relatedID = {""};
		if (user == null)
			return new Message(4, token, relatedID);
		
		relatedID[0] =user.hash();
		
		try {
			Modules.getIdentityModule().changePassword(user, password);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(4, token, relatedID);
		}
		
		return new Message(0, token, relatedID);
		
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="{token}/bio",method=RequestMethod.GET)
	public Message changeBio(@PathVariable String token){
		UserLogin user  = this.checkSession(token);
		Person personToUpdate;
		String[] relatedID = {""};
		if (user == null)
			return new Message(4, token, relatedID);
		
		relatedID[0] =user.hash();
		personToUpdate=new Person("Name", 
				"subname", 
				new Date(), 
				"country", 
				"province", 
				"birthplace", 
				"biografy", 
				"profileImageUrl", 
				new Date());
		return new MessageWhoAmi(0, token, relatedID, user, personToUpdate);
		
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="{token}/bio",method=RequestMethod.PATCH)
	public Message changeBio(@PathVariable String token,@RequestBody String response){
		UserLogin user = this.checkSession(token);
		Person personToUpdate;
		String[] relatedID = {""};
		if (user == null)
			return new Message(4, token, relatedID);
		
		relatedID[0] =user.hash();
		JSONObject obj=new JSONObject(response);
		JSONObject personObj=obj.getJSONObject("person");
		personToUpdate=new Person(personObj.getString("name"), 
				personObj.getString("subname"),
				new DateTime(Long.parseLong(personObj.getString("birthday"))).toDate(), 
				personObj.getString("country"), 
				personObj.getString("province"), 
				personObj.getString("birthplace"), 
				personObj.getString("biografy"), 
				personObj.getString("profileImageUrl"), 
				new DateTime(Long.parseLong(personObj.getString("dateCreation"))).toDate());
		try {
			Modules.getIdentityModule().changeBio(user, personToUpdate);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
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
