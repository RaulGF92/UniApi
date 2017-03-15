package es.uniapi.tests.bussiness.serviceGestion;

import org.json.JSONArray;
import org.json.JSONObject;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.business.servicegestion.ServiceGestion;
import es.uniapi.modules.business.servicegestion.ServiceGestionImpl;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;

public class TestServiceGestion {

	public static void main(String[] args){
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		UserLogin raul=null;
		Project matrices=null;
		Group informatica=null;
		ServiceGestion service=new ServiceGestionImpl();
		
		try{
			raul=dao.getUniApiDao().getUserLoginDAO()
					.findByEmail("raulgf92@gmail.com");
			matrices=dao.getUniApiDao().getProjectDAO()
					.findByHashCode("7dab52161cc549d94c3d63537ee9c51ab9ef61ef");
			informatica=dao.getUniApiDao().getGroupDAO()
					.findByHashCode("89b507dd5ddeb237c4f3b6da055eb2845db2dbd6");
		}catch(Exception e){
			System.err.println("Error en la obtencion de clases auxiliares");
			
		}
		
		JSONObject input1=new JSONObject();
		String[] array1={"1000","1","1000"};
		JSONArray arr1=new JSONArray(array1);
		input1.put("inputs", arr1);
		
		try {
			service.makeExecutionOfProject(informatica, matrices, raul, input1);
			System.out.println("Se he ejecutado uno");
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject input2=new JSONObject();
		String[] array2={"2000","1","2000"};
		JSONArray arr2=new JSONArray(array2);
		input2.put("inputs", arr2);
		
		try {
			service.makeExecutionOfProject(informatica, matrices, raul, input2);
			System.out.println("Se he ejecutado uno");
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject input3=new JSONObject();
		String[] array3={"30","1","30"};
		JSONArray arr3=new JSONArray(array3);
		input3.put("inputs", arr3);
		
		try {
			service.makeExecutionOfProject(informatica, matrices, raul, input3);
			System.out.println("Se he ejecutado uno");
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject input4=new JSONObject();
		String[] array4={"40","1","40"};
		JSONArray arr4=new JSONArray(array4);
		input4.put("inputs", arr4);
		
		try {
			service.makeExecutionOfProject(informatica, matrices, raul, input4);
			System.out.println("Se he ejecutado uno");
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		JSONObject input5=new JSONObject();
		String[] array5={"500","1","500"};
		JSONArray arr5=new JSONArray(array5);
		input5.put("inputs", arr5);
		
		try {
			service.makeExecutionOfProject(informatica, matrices, raul, input5);
			System.out.println("Se he ejecutado uno");
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject input6=new JSONObject();
		String[] array6={"5000","1","5000"};
		JSONArray arr6=new JSONArray(array6);
		input6.put("inputs", arr6);
		
		try {
			service.makeExecutionOfProject(informatica, matrices, raul, input6);
			System.out.println("Se he ejecutado uno");
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		int count=1;
		while(true){
			try {
				System.out.println(service.getAllExecutionsRunning().length);
			} catch (BussinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(1000*60*count);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
		}
	}
}
