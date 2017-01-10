package es.uniapi_backend.modules.apirest.controller;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class HomeController {

	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value="/")
	public String test(HttpServletResponse response) throws IOException{
		return "{id:"+counter.incrementAndGet()+",message:'¿Hola que tal? parece que esto funciona! : )'}";
	}
}
