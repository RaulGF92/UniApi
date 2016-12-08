package execution_enviroment.model.infoService;

import org.joda.time.DateTime;

import execution_enviroment.model.ProyectType;

public class InfoGit extends InfoService {

	public DateTime modifyDate=null;
	public String user;
	public String password;
	public String url;
	
	public InfoGit(String user,String password,String ulr){
		super(ProyectType.GIT);
		this.user=user;
		this.password=password;
		this.url=ulr;
	}
	
}
