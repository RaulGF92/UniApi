package es.uniapi.modules.business.projectgestion;

import java.util.HashMap;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.especial.Impl.ServiceGit;
import es.uniapi.modules.model.Project;

public class TheUpdater extends Thread {
	private int count=0;
	private long timeToSleep=1000*60*1;
	private Project[] projects;
	private HashMap<String,ServiceGit> services=new HashMap<String,ServiceGit>();
	private UniApiFactoryDAO dao=new UniApiFactoryDAO();
	
	public void run(){
		sleepTime(timeToSleep);
		print("Me he despertado");
		while(true){
			try {
				projects=dao.getUniApiDao().getProjectDAO().findAll();
				for(int i=0;i<projects.length;i++){
					makeUpdate(projects[i]);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sleepTime(timeToSleep);
			checkBuffer();
		}
	}
	private void checkBuffer() {
		// TODO Auto-generated method stub
		count++;
		if(count>3){
			this.services=new HashMap<String,ServiceGit>();
		}
	}
	private void makeUpdate(Project project) throws ServiceException {
		// TODO Auto-generated method stub
		ServiceGit container=new ServiceGit();
		container.inicializateService(project,ServiceGit.GitOption.LOAD);
		services.put(project.hash(),container);
		services.get(project.hash()).start();
	}
	private void sleepTime(long timeToSleep2) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(timeToSleep2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			print("Fallo en dormir");
		}
	}
	private void print(String string) {
		// TODO Auto-generated method stub
		String first="{[TheUpdater]";
		String end="}";
		System.out.println(first.concat(string).concat(end));
	}
	
}
