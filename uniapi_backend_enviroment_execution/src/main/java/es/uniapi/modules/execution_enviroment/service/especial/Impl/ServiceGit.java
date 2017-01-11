package es.uniapi.modules.execution_enviroment.service.especial.Impl;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.joda.time.DateTime;

import es.uniapi.modules.execution_enviroment.git.GitControl;
import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.Proyect;

public class ServiceGit extends ProgrammingService implements es.uniapi.modules.execution_enviroment.service.especial.Intf.ServiceGit {

	Proyect proyectGit;
	InfoGit chacheInfo=null;
	
	public void inicializateService(Proyect proyect) throws ServiceException {
		// TODO Auto-generated method stub
		this.proyectGit=proyect;
		if(!existProyect());
			//throw new ServiceException();
	}

	public boolean existProyect() throws ServiceException{
		// TODO Auto-generated method stub
		File directory=new File(proyectGit.getOriginPath());
		return directory.exists();
	}

	public void loadProject() throws ServiceException{
		// TODO Auto-generated method stub
		try {
			GitControl git=new GitControl(proyectGit.getOriginPath(),takeInfo().url, takeInfo().user, takeInfo().password);
			git.pullFromRepo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (WrongRepositoryStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DetachedHeadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RefNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoHeadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chacheInfo.modifyDate=new DateTime();
		loadInfo(chacheInfo);
		
	}

	public void newProyect() {
		// TODO Auto-generated method stub
		try {
			//GitControl.init(proyectGit.getOriginPath());
			GitControl git=new GitControl(proyectGit.getOriginPath(),takeInfo().url, takeInfo().user, takeInfo().password);
			git.cloneRepo();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		takeInfo();
		chacheInfo.modifyDate=new DateTime();
		loadInfo(chacheInfo);
	}
	private InfoGit takeInfo(){
		if(chacheInfo!=null)
			return chacheInfo;
		
		InfoGit info=null;
		for(int i=0;i<proyectGit.getServiceInfo().size();i++){
			if(proyectGit.getServiceInfo().get(i).type==ProyectType.GIT){
				info=(InfoGit) proyectGit.getServiceInfo().get(i);
			chacheInfo=info;
			}
		}
		
		return info;
		
	}
	private void loadInfo(InfoGit info){
		for(int i=0;i<proyectGit.getServiceInfo().size();i++){
			if(proyectGit.getServiceInfo().get(i).type==ProyectType.GIT){
				proyectGit.getServiceInfo().add(i,info);
				chacheInfo=info;
				return;
			}
		}
		
	}


}
