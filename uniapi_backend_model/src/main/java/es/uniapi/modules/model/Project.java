package es.uniapi.modules.model;

import java.util.Arrays;
import java.util.Date;

import es.uniapi.modules.model.config.SHA1;

public class Project {


	public Project(Date creationDate, String name, ProjectType type, String description, String gitRepositoryURL,
			String email, String password, Date modifyDate, String mainName, String responseName,
			String[] defaultInputs, String inputDescription, String outputDescription) {
		super();
		this.creationDate = creationDate;
		this.name = name;
		this.type = type;
		this.description = description;
		this.gitRepositoryURL = gitRepositoryURL;
		this.email = email;
		this.password = password;
		this.modifyDate = modifyDate;
		this.mainName = mainName;
		this.responseName = responseName;
		this.defaultInputs = defaultInputs;
		this.inputDescription = inputDescription;
		this.outputDescription = outputDescription;
		this.hashcode=hash();
	}
	
	private String name;
	private ProjectType type;
	private Date creationDate;
	private String description;
	private String hashcode;
	
	//git part
	private String gitRepositoryURL;
	private String email;
	private String password;
	private Date modifyDate;
	
	
	//main
	private String mainName;
	private String responseName;
	

	//input-Output
	private String[] defaultInputs;
			
	private String inputDescription;
	private String outputDescription;
	
	
	
	public enum ProjectType {
		JAVA,R,OCTAVE,GIT,PYTHON,TypeOfProject
	}
	
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProjectType getType() {
		return type;
	}

	public void setType(ProjectType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGitRepositoryURL() {
		return gitRepositoryURL;
	}

	public void setGitRepositoryURL(String gitRepositoryURL) {
		this.gitRepositoryURL = gitRepositoryURL;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMainName() {
		return mainName;
	}

	public void setMainName(String mainName) {
		this.mainName = mainName;
	}

	public String[] getDefaultInputs() {
		return defaultInputs;
	}

	public void setDefaultInputs(String[] defaultInputs) {
		this.defaultInputs = defaultInputs;
	}

	public String getInputDescription() {
		return inputDescription;
	}

	public void setInputDescription(String inputDescription) {
		this.inputDescription = inputDescription;
	}

	public String getOutputDescription() {
		return outputDescription;
	}

	public void setOutputDescription(String outputDescription) {
		this.outputDescription = outputDescription;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (!Arrays.equals(defaultInputs, other.defaultInputs))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gitRepositoryURL == null) {
			if (other.gitRepositoryURL != null)
				return false;
		} else if (!gitRepositoryURL.equals(other.gitRepositoryURL))
			return false;
		if (inputDescription == null) {
			if (other.inputDescription != null)
				return false;
		} else if (!inputDescription.equals(other.inputDescription))
			return false;
		if (mainName == null) {
			if (other.mainName != null)
				return false;
		} else if (!mainName.equals(other.mainName))
			return false;
		if (modifyDate == null) {
			if (other.modifyDate != null)
				return false;
		} else if (!modifyDate.equals(other.modifyDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (outputDescription == null) {
			if (other.outputDescription != null)
				return false;
		} else if (!outputDescription.equals(other.outputDescription))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (responseName == null) {
			if (other.responseName != null)
				return false;
		} else if (!responseName.equals(other.responseName))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [name=" + name + ", type=" + type + ", description="
				+ description + ", gitRepositoryURL=" + gitRepositoryURL + ", email=" + email + ", password=" + password
				+ ", modifyDate=" + modifyDate + ", mainName=" + mainName + ", responseName=" + responseName
				+ ", defaultInputs=" + Arrays.toString(defaultInputs) + ", inputDescription=" + inputDescription
				+ ", outputDescription=" + outputDescription + ", creationDate=" + creationDate + "]";
	}
	
	public String hash(){
		return SHA1.encryptPassword(this.especialToString());
	}
	public String especialToString(){
		return "Project [name=" + name + ", type=" + type +", creationDate=" + creationDate +"]";
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getHashcode() {
		return hash();
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

}
