package es.uniapi.modules.execution_enviroment.service.file.Impl;

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
import es.uniapi.modules.execution_enviroment.model.Proyect;
import es.uniapi.modules.execution_enviroment.model.ProyectType;
import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;
import es.uniapi.modules.execution_enviroment.model.infoService.InfoGit;

public class ServiceGit implements es.uniapi.modules.execution_enviroment.service.file.Intf.ServiceGit {

	Proyect proyectGit;
	InfoGit chacheInfo=null;
	
	public void InicializateService(Proyect proyect) throws ServiceException {
		// TODO Auto-generated method stub
		this.proyectGit=proyect;
		if(!ExistProyect());
			//throw new ServiceException();
	}

	public boolean ExistProyect() {
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
