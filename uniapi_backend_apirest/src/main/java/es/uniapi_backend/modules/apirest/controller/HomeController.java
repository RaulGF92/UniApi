package es.uniapi_backend.modules.apirest.controller;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import es.uniapi.modules.business.servicegestion.gestorsworkers.GestorWorkerMap;
import es.uniapi.modules.model.Proyect;
import es.uniapi.modules.model.Proyect.ProyectType;
import es.uniapi.modules.model.config.AppConfiguration;


@RestController
public class HomeController {

	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value="/")
	public String test(HttpServletResponse response) throws IOException{
		return "{id:"+counter.incrementAndGet()+",message:'¿Hola que tal? parece que esto funciona! : )'}";
	}
	
	@RequestMapping(value="/example")
	public String test2(){
		AppConfiguration conf=new AppConfiguration();
		return conf.getConfigPath();
	}
}
