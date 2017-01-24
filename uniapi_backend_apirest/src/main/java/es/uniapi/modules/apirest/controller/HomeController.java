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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.servicegestion.gestorsworkers.GestorWorkerMap;
import es.uniapi.modules.model.Proyect;
import es.uniapi.modules.model.Proyect.ProyectType;
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
	public ArrayList<String[]> test(HttpServletResponse response) throws IOException{
		ArrayList<String[]> patterns=new ArrayList<String[]>();
		Iterator<RequestMappingInfo> it=this.handlerMapping.getHandlerMethods().keySet().iterator();
		while(it.hasNext()){
			PatternsRequestCondition pc=it.next().getPatternsCondition();
			patterns.add(pc.getPatterns().toArray(new String[pc.getPatterns().size()]));
		}
		return patterns;
	}
	
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
		
		return responseCorrect;
		
	}
}
