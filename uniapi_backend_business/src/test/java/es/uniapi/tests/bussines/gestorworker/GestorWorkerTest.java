package es.uniapi.tests.bussines.gestorworker;

import java.util.Scanner;

import es.uniapi.modules.business.servicegestion.GestorWork;
import es.uniapi.modules.business.servicegestion.gestorsworkers.GestorWorkerMap;

public class GestorWorkerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		case1();
		
	}
	
	public static void configuracionCase(){
		ComunciaciónConfiguración conf=new ComunciaciónConfiguración();
		conf.make();
	}
	public static void case1(){
		GestorWork gestor=GestorWorkerMap.getGestorWorkerMap();
		Caso1 caso1=new Caso1(gestor);
		caso1.make();
		
	}

}
