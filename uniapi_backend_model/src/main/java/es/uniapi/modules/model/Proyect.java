package es.uniapi.modules.model;

import java.util.Arrays;
import java.util.Date;

public class Proyect {



	public Proyect(long id, String name, ProyectType type, String description, String gitRepositoryURL, String email,
			String password, Date modifyDate, String mainName, String responseName, String[] defaultInputs,
			String inputDescription, String outputDescription) {
		super();
		this.id = id;
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
	}

	public Proyect(String name, ProyectType type, String description, String gitRepositoryURL, String email,
			String password, Date modifyDate, String mainName, String responseName, String[] defaultInputs,
			String inputDescription, String outputDescription) {
		super();
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
	}

	private long id;
	private String name;
	private ProyectType type;
	private String description;
	
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
	
	public enum ProyectType {
		JAVA,R,OCTAVE,GIT,PYTHON
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

	public ProyectType getType() {
		return type;
	}

	public void setType(ProyectType type) {
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
	public String toString() {
		return "Proyect [id=" + id + ", name=" + name + ", type=" + type + ", description=" + description
				+ ", gitRepositoryURL=" + gitRepositoryURL + ", email=" + email + ", password=" + password
				+ ", modifyDate=" + modifyDate + ", mainName=" + mainName + ", responseName=" + responseName
				+ ", defaultInputs=" + Arrays.toString(defaultInputs) + ", inputDescription=" + inputDescription
				+ ", outputDescription=" + outputDescription + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
