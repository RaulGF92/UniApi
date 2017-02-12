package es.uniapi.modules.apirest.model;

import java.util.Arrays;

import es.uniapi.modules.model.Project;

public class MessageProject extends Message {

	private Project[] projects;
	
	public MessageProject(int state, String tokenSession,String[] relatedIDs,Project[] projects) {
		super(state, tokenSession,relatedIDs);
		// TODO Auto-generated constructor stub
		this.projects=projects;
		
		if(this.projects == null)
			return;
		
		for(int i=0;i<projects.length;i++){
			String ocult="";
			for(int j=0;j<this.projects[i].getPassword().length();j++){
				ocult+="*";
			}
			this.projects[i].setPassword(ocult);
		}
		
		
	}

	public Project[] getProjects() {
		return projects;
	}

	public void setProject(Project[] projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "MessageProject [projects=" + Arrays.toString(projects) + ", toString()=" + super.toString() + "]";
	}

	

}
