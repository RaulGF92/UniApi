package es.uniapi.modules.apirest.controller;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.RepaintManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(value="/")
	public String test(HttpServletResponse response) throws IOException{
		return "{id:"+counter.incrementAndGet()+",message:'¿Hola que tal? parece que esto funciona! : )'}";
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
