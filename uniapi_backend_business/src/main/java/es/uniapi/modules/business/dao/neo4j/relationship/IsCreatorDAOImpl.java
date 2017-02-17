package es.uniapi.modules.business.dao.neo4j.relationship;

import org.joda.time.DateTime;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.neo4j.relationship.model.IsCreator;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.UserLogin;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.Date;

public class IsCreatorDAOImpl implements es.uniapi.modules.business.dao.intf.relations.IsCreatorDAO {

	Driver driver;
	public IsCreatorDAOImpl(){
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	
	@Override
	public void create(UserLogin user, Project project) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="MATCH (u:UserLogin),(p:Project) WHERE u.user={hashcode1} "
				+ "AND p.hashcode={hashcode2} CREATE (u)-[ic:IS_CREATOR {dateFrom:{date}}]->(p)";
		
		StatementResult result=session.run(statement,parameters(
				"hashcode1",user.hash(),
				"hashcode2",project.hash(),
				"date",new Date().getTime()));
		session.close();
	}

	@Override
	public Project[] getAllProjectsCreateByUser(UserLogin user) throws Exception {
		// TODO Auto-generated method stub
		Session session= driver.session();
		ArrayList<Project> projects=new ArrayList<Project>();
		
		String statement="MATCH (u:UserLogin {user:{email}}), "
				+ "h=(u)-[r:IS_CREATOR]->(p:Project) RETURN "
				+ "p.name AS name,"
				+ "p.type AS type,"
				+ "p.description AS description,"
				+ "p.creationTime AS creationTime,"
				+ "p.gitRepositoryURL AS gitRepositoryURL,"
				+ "p.email AS email,"
				+ "p.password AS password,"
				+ "p.modifyDate AS modifyDate,"
				+ "p.mainName AS mainName,"
				+ "p.responseName AS responseName,"
				+ "p.defaultInputs AS defaultInputs,"
				+ "p.inputDescription AS inputDescription,"
				+ "p.outputDescription AS outputDescription";
		
		StatementResult result=session.run(statement,parameters(
				"email",user.getUser()
				));
		
		while(result.hasNext()){
			Record record=result.next();
			Project container = new Project(new DateTime(record.get("creationTime").asLong()).toDate(), record.get("name").asString(), 
					ProjectType.valueOf(record.get("type").asString()), record.get("description").asString(), 
					record.get("gitRepositoryURL").asString(), record.get("email").asString(), 
					record.get("password").asString(), new DateTime(record.get("modifyDate").asLong()).toDate(), 
					record.get("mainName").asString(), record.get("responseName").asString(), 
					record.get("defaultInputs").asList().toArray(new String[record.get("defaultInputs").asList().size()]), record.get("inputDescription").asString(), 
					record.get("outputDescription").asString());
			projects.add(container);
		}
		session.close();
		return projects.toArray(new Project[projects.size()]);
	}

	@Override
	public Project[] getTypeProjectsCreateByUser(UserLogin user, ProjectType type) {
		// TODO Auto-generated method stub
		Session session= driver.session();
		ArrayList<Project> projects=new ArrayList<Project>();
		
		String statement="MATCH (u:UserLogin {user:{email}}), "
				+ "h=(u)-[r:IS_CREATOR]->(p:Project {type:{type}}) RETURN "
				+ "p.name AS name,"
				+ "p.type AS type,"
				+ "p.description AS description,"
				+ "p.creationTime AS creationTime,"
				+ "p.gitRepositoryURL AS gitRepositoryURL,"
				+ "p.email AS email,"
				+ "p.password AS password,"
				+ "p.modifyDate AS modifyDate,"
				+ "p.mainName AS mainName,"
				+ "p.responseName AS responseName,"
				+ "p.defaultInputs AS defaultInputs,"
				+ "p.inputDescription AS inputDescription,"
				+ "p.outputDescription AS outputDescription";
		
		StatementResult result=session.run(statement,parameters(
				"email",user.getUser(),
				"type",type.toString()));
		
		while(result.hasNext()){
			Record record=result.next();
			Project container = new Project(new DateTime(record.get("creationTime").asLong()).toDate(), record.get("name").asString(), 
					ProjectType.valueOf(record.get("type").asString()), record.get("description").asString(), 
					record.get("gitRepositoryURL").asString(), record.get("email").asString(), 
					record.get("password").asString(), new DateTime(record.get("modifyDate").asLong()).toDate(), 
					record.get("mainName").asString(), record.get("responseName").asString(), 
					record.get("defaultInputs").asList().toArray(new String[record.get("defaultInputs").asList().size()]), record.get("inputDescription").asString(), 
					record.get("outputDescription").asString());
			projects.add(container);
		}
		session.close();
		return projects.toArray(new Project[projects.size()]);
	}

	@Override
	public IsCreator getInfo(UserLogin user, Project project) throws Exception {
		// TODO Auto-generated method stub
		
		IsCreator response=null;
		Session session= driver.session();
		
		String statement="MATCH (u:UserLogin {user:{user}}), h=(u)-[r:IS_CREATOR]->(p:Project {hashcode:{hash}}) RETURN "
				+ "p.hashcode AS hashcode ,"
				+ "r.dateFrom as dateFrom,"
				+ "u.user AS user";
		
		StatementResult result=session.run(statement,parameters(
				"email",user.getUser(),
				"hash",project.hash()));
		
		while(result.hasNext()){
			Record record=result.next();
			response=new IsCreator(record.get("user").asString(), 
					record.get("hashcode").asString(), 
					new DateTime(record.get("dateFrom").asLong()).toDate());
		}
		session.close();
		return response;
	}

	@Override
	public void delete(UserLogin user, Project project) throws Exception {
		// TODO Auto-generated method stub
		Session session= driver.session();
		String statement="MATCH(u:UserLogin {hashcode:{hashcode1}}) "
				+ ",o=(u)-[r:IS_CREATOR]->(p:Project {hashcode:{hashcode2}}) "
				+ "DELETE r";
		StatementResult result=session.run(statement,parameters(
				"hashcode1",user.hash(),
				"hashcode2",project.hash()));
		session.close();
	}

}
