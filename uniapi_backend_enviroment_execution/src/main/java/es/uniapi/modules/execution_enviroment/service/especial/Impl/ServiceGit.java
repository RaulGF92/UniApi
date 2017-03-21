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
import es.uniapi.modules.model.Project;

public class ServiceGit extends EspecialService implements es.uniapi.modules.execution_enviroment.service.especial.Intf.ServiceGit {

	Project projectGit;
	GitOption option;
	public enum GitOption{
		LOAD,NEW
	}
	public void inicializateService(Project project,GitOption option) throws ServiceException {
		// TODO Auto-generated method stub
		this.projectGit=project;
		this.option=option;
		//if(!existProject());
			//throw new ServiceException();
	}

	public boolean existProject() throws ServiceException{
		// TODO Auto-generated method stub
		File directory=new File(super.getAbsoluteProjectPath(projectGit));
		return directory.exists();
	}
	public void run(){
		switch(this.option){
		case LOAD:
			this.loadProject();
		break;
		case NEW:
			this.newProyect();
		break;
		}
	}
	public void loadProject(){
		// TODO Auto-generated method stub
		try {
			System.out.println(super.getAbsoluteProjectPath(projectGit));
			GitControl git=new GitControl(
					super.getAbsoluteProjectPath(projectGit),
					projectGit.getGitRepositoryURL(),
					projectGit.getEmail(),
					projectGit.getPassword());
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
		projectGit.setModifyDate(new DateTime().toDate());
		
	}

	public void newProyect() {
		// TODO Auto-generated method stub
		try {
			//GitControl.init(proyectGit.getOriginPath());
			GitControl git=new GitControl(super.getAbsoluteProjectPath(projectGit),projectGit.getGitRepositoryURL(),projectGit.getEmail(),projectGit.getPassword());
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
		
		projectGit.setModifyDate(new DateTime().toDate());
	}

	@Override
	@Deprecated
	public String getAbsoluteProjectPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return projectGit;
	}

	@Override
	public void inicializateService(Project project) throws ServiceException {
		// TODO Auto-generated method stub
		
	}


}
