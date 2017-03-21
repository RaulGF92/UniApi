package es.uniapi.modules.apirest.controller;

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
import es.uniapi.modules.apirest.model.MessageExecution;
import es.uniapi.modules.apirest.model.MessageProject;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.sessiongestion.SessionGestor;
import es.uniapi.modules.sessiongestion.impl.SessionGestorMap;

@RestController
@RequestMapping(value="/execution/*")
public class ExecutionController {

	SessionGestor sessionGestor;
	private final RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	public ExecutionController(RequestMappingHandlerMapping handlerMapping) {
		 this.handlerMapping = handlerMapping;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{groupID}/{projectID}",method=RequestMethod.POST)
	public Message createExecution(@PathVariable String token,
			@PathVariable String groupID,
			@PathVariable String projectID,
			@RequestBody String response){
		
		Group group;
		Project project;
		String module;
		
		UserLogin user=this.checkSession(token);
		if(user==null)
			return new Message(4, token,new String[0]);
		
		JSONObject jsonObject=new JSONObject(response);
		
		try {
			group=Modules
					.getGroupModule()
					.findByHash(groupID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Message(23, token,new String[0]);
		}
		
		try {
			project=Modules
					.getProjectModule()
					.getProject(projectID);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(13, token,new String[0]);
		}
		
		try {
			module=Modules.getExecutionModule().makeExecutionOfProject(group, 
					project, 
					user, 
					jsonObject);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(52, token,new String[0]);
		}
		String[] relatedID={module};
		return new Message(0,token,relatedID);
		
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{executionID}",method=RequestMethod.GET)
	public Message getExecution(@PathVariable String token,
			@PathVariable String executionID){
		String[] relatedID={executionID};
		UserLogin user=this.checkSession(token);
		if(user==null)
			return new Message(4, token,new String[0]);
		
		try {
			Execution execution=Modules.getExecutionModule()
					.getExecution(user, executionID);
			Execution[] executions={execution};
			return new MessageExecution(0,token,relatedID,executions);
			
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(53,token,relatedID);
		}
	
		
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/{executionID}/project",method=RequestMethod.GET)
	public Message getProjectOfExecution(@PathVariable String token,
			@PathVariable String executionID){
		String[] relatedID={executionID};
		UserLogin user=this.checkSession(token);
		if(user==null)
			return new Message(4, token,new String[0]);
		
		try {
			Execution execution=Modules.getExecutionModule()
					.getExecution(user, executionID);
			
			
			Project[] projects={Modules
					.getExecutionModule()
					.getProjectOfExecution(execution)};
			
			
			return new MessageProject(0,token,relatedID,projects);
			
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(53,token,relatedID);
		}
	
		
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/running",method=RequestMethod.GET)
	public Message getExecutionsRunning(@PathVariable String token){
		
		UserLogin user=this.checkSession(token);
		if(user==null)
			return new Message(4, token,new String[0]);
		
		try {
			Execution[] execution=Modules.getExecutionModule()
					.getAllExecutionsRunning();
			String[] relatedID=new String[execution.length];
			for(int i=0;i<execution.length;i++){
				relatedID[i]=execution[i].hash();
				System.out.println(execution[i].hash());
			}
			return new Message(0, token, relatedID);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(54, token,new String[0]);
		}
		
		
		
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/finish",method=RequestMethod.GET)
	public Message getExecutionsFinnish(@PathVariable String token){
		
		UserLogin user=this.checkSession(token);
		if(user==null)
			return new Message(4, token,new String[0]);
		
		try {
			Execution[] execution=Modules.getExecutionModule()
					.getAllExecutionsFinish();
			String[] relatedID=new String[execution.length];
			for(int i=0;i<execution.length;i++){
				relatedID[i]=execution[i].hash();
			}
			return new Message(0, token, relatedID);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(54, token,new String[0]);
		}
		
		
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
