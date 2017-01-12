package es.uniapi.modules.execution_enviroment.service.especial.Impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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
import es.uniapi.modules.execution_enviroment.service.especial.EspecialService;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.Proyect;

public class ServiceGit extends EspecialService implements es.uniapi.modules.execution_enviroment.service.especial.Intf.ServiceGit {

	Proyect proyectGit;
	
	public void inicializateService(Proyect proyect) throws ServiceException {
		// TODO Auto-generated method stub
		this.proyectGit=proyect;
		if(!existProyect());
			//throw new ServiceException();
	}

	public boolean existProyect() throws ServiceException{
		// TODO Auto-generated method stub
		File directory=new File(super.getAbsoluteProyectPath(proyectGit));
		return directory.exists();
	}

	public void loadProject() throws ServiceException{
		// TODO Auto-generated method stub
		try {
			GitControl git=new GitControl(super.getAbsoluteProyectPath(proyectGit),proyectGit.getGitRepositoryURL(),proyectGit.getEmail(),proyectGit.getPassword());
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
		proyectGit.setModifyDate(new DateTime().toDate());
		
	}

	public void newProyect() {
		// TODO Auto-generated method stub
		try {
			//GitControl.init(proyectGit.getOriginPath());
			GitControl git=new GitControl(super.getAbsoluteProyectPath(proyectGit),proyectGit.getGitRepositoryURL(),proyectGit.getEmail(),proyectGit.getPassword());
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
		
		proyectGit.setModifyDate(new DateTime().toDate());
	}

	@Override
	@Deprecated
	public String getAbsoluteProyectPath() {
		// TODO Auto-generated method stub
		return null;
	}
}
