package es.uniapi.tests.bussines.dao;

import java.util.Date;

import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.dao.neo4j.entities.ProjectNeo4j;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;

public class TestProject {

	public static void main(String[] args) {
		
		String[] vector={"100","100","100","100"};
		Project project=new Project(new Date(), "triangulo_de_gauss",ProjectType.PYTHON,"Programa de fundamentos de información, realizado en python. Que computa el triangulo de gauss", 
				"http://estoEsUnaPrueba.org/triangulo_de_gauss","jose@jose.es","default"
				,new Date(),"main.py","solucion.txt",vector, "{'filA': 'X','colA': 'X','filB': 'Y','colB': 'Y'}"
				, "{'matriz': [[0,0,0],[0,0,0],[0,0,0]]}");
		ProjectNeo4j dao=new ProjectNeo4j();
		
		try {
			//dao.create(project);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			Project aux=dao.findByHashCode(project.hash());
			//System.out.println(aux.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			Project[] aux=dao.findByName(project.getName());
			System.out.println(aux[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			Project[] aux=dao.findByType(project.getType());
			System.out.println(aux[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		try {
			Modules.getProjectModule().createProject(project);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
