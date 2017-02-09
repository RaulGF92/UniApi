package es.uniapi.tests.bussines.gestorworker;

import java.util.Date;

import org.joda.time.DateTime;
import org.neo4j.kernel.impl.nioneo.store.PropertyType;

import es.uniapi.modules.business.exception.GestorServiceException;
import es.uniapi.modules.business.servicegestion.GestorWork;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.UserLogin;

public class Caso1 {

	/*
	 * Caso 1:
	 * 
	 * Un usuario se utiliza por primera vez el gestor.
	 * Crea un proyecto vacio y lanza el servicio.
	 * 
	 * 	Usuario:
	 * 		email:raulgf92@gmail.com
	 * 		pass: daigual
	 * 
	 * 	Proyecto:
	 * 		name:proyecto1
	 * 		type:python
	 * 		descripcion: vacia
	 * 		gitRepository: https://github.com/RaulGF92/prueba.git
	 * 		email: ""
	 * 		password:""
	 * 		mainName:main.py
	 * 		responseName:responsePrueba
	 * 		inputDescription:"FilaX ColumnaX FilaY ColumnaY"
	 * 		outputDescription: matrix of order ColumnaX,FilaY
	 *		defaultInputs= 1000 1000 1000 1000
	 *  	
	 */
	

	public Caso1(GestorWork gestor){
		this.gestor=gestor;
	}
	
	GestorWork gestor;
	UserLogin raul=new UserLogin("raulgf92@gmail.com","daigual",new DateTime().toDate(),"admin");
	
	String input[]={"50","50","50","50"};
	String input1[]={"80","80","80","80"};
	String input2[]={"100","100","100","100"};
	String input3[]={"900","900","900","900"};
	
	
	Project proyecto1=new Project( 
			new Date(), "prueba", 
			ProjectType.PYTHON, 
			"vacia", 
			"https://github.com/RaulGF92/prueba.git", 
			"", 
			"", 
			new DateTime().toDate(), 
			"main.py", 
			"responsePrueba.txt", 
			input, 
			"FilaX ColumnaX FilaY ColumnaY", 
			"matrix of order ColumnaX,FilaY");
	
	public void make(){
		
		try {
			System.out.println("Vamos a crear servicios con el proyecto");
			
			System.out.println("Un servicio");
			
			proyecto1.setDefaultInputs(input3);
			gestor.createService(raul, proyecto1);
			
			System.out.println("Un servicio"); 
		
			proyecto1.setDefaultInputs(input2);
			gestor.createService(raul, proyecto1);
			
			System.out.println("Un servicio");
			
			proyecto1.setDefaultInputs(input1);
			gestor.createService(raul, proyecto1);
			
			System.out.println("Un servicio");
			
			proyecto1.setDefaultInputs(input);
			gestor.createService(raul, proyecto1);

			System.out.println("vamos a solicitar todos los servicios del usuario");
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ProgrammingService[] services=gestor.getAllUserServices(raul.getUser());
			for(int i=0;i<services.length;i++){
				System.out.println(""+i+") "+services[i].toString());
			}
			
		} catch (GestorServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
