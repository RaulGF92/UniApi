package es.uniapi.modules.apirest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.RepaintManager;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.MessageWhoAmi;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.dao.intf.UniapiDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.business.identitygestion.IdentityGestion;
import es.uniapi.modules.business.servicegestion.gestorsworkers.GestorWorkerMap;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.config.SHA1;
import es.uniapi.modules.sessiongestion.SessionGestor;
import es.uniapi.modules.sessiongestion.impl.SessionGestorMap;


@RestController
public class HomeController {

	private final AtomicLong counter = new AtomicLong();
	SessionGestor sessionGestor;
	
	 private final RequestMappingHandlerMapping handlerMapping;

	 @Autowired
	 public HomeController(RequestMappingHandlerMapping handlerMapping) {
		 this.handlerMapping = handlerMapping;
	 }
	 
	@RequestMapping(value="/")
	public ArrayList<String[]> test(HttpServletResponse response) throws Exception{
		ArrayList<String[]> patterns=new ArrayList<String[]>();
		Iterator<RequestMappingInfo> it=this.handlerMapping.getHandlerMethods().keySet().iterator();
		while(it.hasNext()){
			PatternsRequestCondition pc=it.next().getPatternsCondition();
			patterns.add(pc.getPatterns().toArray(new String[pc.getPatterns().size()]));
		}
			
		return patterns;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{user}/{pass}")
	public Message setLogin(HttpServletRequest request,HttpServletResponse response,
			@PathVariable String user,
			@PathVariable String pass){
		
		Message responseCorrect=null;
		sessionGestor=SessionGestorMap.getSessionGestor();
		
		try {
			responseCorrect=sessionGestor.getSession(user, pass,request.getRemoteAddr());
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(responseCorrect.toString());
		return responseCorrect;
		
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/{token}/whoami", method = RequestMethod.GET)
	public Message getWhoAmi(@PathVariable String token){
		
		Message responseResponse=null;
		sessionGestor=SessionGestorMap.getSessionGestor();
		
		try {
			
			UserLogin userLogin=sessionGestor.checkSession(token);
			System.out.println("[WhoAmi] token:"+token);
			if(userLogin==null){
				throw new SessionGestionException("No se ha encontrado el tokken");
			}
			System.out.println("[whoami] "+userLogin.toString());
			
			Person person=Modules.getIdentityModule()
					.getPerson(userLogin);
			String[] relatedIDs={userLogin.hash(),person.hash()};
			responseResponse=new MessageWhoAmi(0, token, relatedIDs,userLogin, person);
		} catch (SessionGestionException e) {
			// TODO Auto-generated catch block
			return new MessageWhoAmi(4,token,new String[0],null,null);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new MessageWhoAmi(3,token,new String[0],null,null);
		}
		//System.out.println(responseResponse.toString());
		return responseResponse;
		
		//Person person=new Person("Raúl","Garcia",new Date(),"España","Asturias","Candás","pues Aquí tamos","http://farm4.static.flickr.com/3628/3430561773_9b6087d1e1.jpg");
		
	}
	
	//Funcion especia para crear cuentas desde la aplicación web 
	@RequestMapping(value="/createAccount", method = RequestMethod.GET)
	@ResponseBody
	public Message especialCreateAccount(@RequestParam(value="email", required=true) String email,
										@RequestParam(value="password",required=true) String password,
										@RequestParam(value="name",required=true) String name,
										@RequestParam(value="subname",required=true) String subname,
										@RequestParam(value="birthday",required=true) Date birthday,
										@RequestParam(value="birthplace",required=true) String birthplace,
										@RequestParam(value="province",required=true) String province,
										@RequestParam(value="country",required=true) String country,
										@RequestParam(value="profileImageUrl",required=true) String profileImageUrl,
										@RequestParam(value="biografy",required=true) String biografy){
		
		UserLogin userLogin=new UserLogin(email, password, new Date(), "user");
		Person person=new Person(name, subname, birthday, country, province, birthplace, biografy, profileImageUrl,new Date());
		System.out.println(userLogin.toString());
		System.out.println(person.toString());
		try {
			Modules.getIdentityModule().createAccount(userLogin, person);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			return new Message(3,SHA1.encryptPassword("EspecialToken"),new String[0]);
		}
		
		String[] relatedIDs={userLogin.hash(),person.hash()};
		return new Message(0,SHA1.encryptPassword("EspecialToken"),relatedIDs);
	}
	
	
}
