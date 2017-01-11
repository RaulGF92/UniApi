package es.uniapi_backend.modules.model;

public class Proyect {

	private String name;
	private ProyectType type;
	private String description;
	
	//git part
	private String gitRepositoryURL;
	private String email;
	private String password;
	
	//main
	private String mainName;
	
	//input-Output
	private String[] defaultInputs;
	private String inputDescription;
	private String outputDescription;
	
	public enum ProyectType {
		JAVA,R,OCTAVE,GIT,PYTHON
	}

}
