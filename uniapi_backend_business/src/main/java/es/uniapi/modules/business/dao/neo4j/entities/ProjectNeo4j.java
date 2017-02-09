package es.uniapi.modules.business.dao.neo4j.entities;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.intf.entities.ProjectDAO;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.Project.ProjectType;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.neo4j.driver.v1.AuthTokens;

public class ProjectNeo4j implements ProjectDAO{

	Driver driver;
	
	public ProjectNeo4j() {
		// CREATE CONSTRAINT ON (p:Project) ASSERT p.hashcode IS UNIQUE
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	@Override
	public void create(Project project) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="CREATE (a:Project {"
				+ "name:{name},"
				+ "type:{type},"
				+ "creationTime:{creationTime}"
				+ ",description:{description},"
				+ "gitRepositoryURL:{gitRepositoryURL},"
				+ "email:{email},"
				+ "password:{password},"
				+ "modifyDate:{modifyDate},"
				+ "mainName:{mainName},"
				+ "responseName:{responseName},"
				+ "defaultInputs:{defaultInputs},"
				+ "inputDescription:{inputDescription},"
				+ "outputDescription:{outputDescription},"
				+ "hashcode:{hashcode}})";
		
		
		StatementResult result=session.run(statement,parameters(
				"name",project.getName(),
				"type",project.getType().toString(),
				"creationTime",project.getCreationDate().getTime(),
				"description",project.getDescription(),
				"gitRepositoryURL",project.getGitRepositoryURL(),
				"email",project.getEmail(),
				"password",project.getPassword(),
				"modifyDate",project.getModifyDate().getTime(),
				"mainName",project.getMainName(),
				"responseName",project.getResponseName(),
				"defaultInputs",project.getDefaultInputs(),
				"inputDescription",project.getInputDescription(),
				"outputDescription",project.getOutputDescription(),
				"hashcode",project.hash()
				));
		
		session.close();
	}

	@Override
	public void delete(Project project) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		
		String statement="MATCH (p:Project) WHERE p.hashcode={hashcode} DELETE p";
		StatementResult result=session.run(statement,parameters(
				"hashcode",project.hash()));
	}

	@Override
	public Project findByHashCode(String hash) throws Exception {
		// TODO Auto-generated method stub
	
		Project response=null;
		Session session = driver.session();
		
		String statement="MATCH (p:Project) WHERE p.hashcode={hashcode} RETURN "
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
				"hashcode",hash));
		
		while(result.hasNext()){
			Record record=result.next();
			response=new Project(new DateTime(record.get("creationTime").asLong()).toDate(), record.get("name").asString(), 
					ProjectType.valueOf(record.get("type").asString()), record.get("description").asString(), 
					record.get("gitRepositoryURL").asString(), record.get("email").asString(), 
					record.get("password").asString(), new DateTime(record.get("modifyDate").asLong()).toDate(), 
					record.get("mainName").asString(), record.get("responseName").asString(), 
					record.get("defaultInputs").asList().toArray(new String[record.get("defaultInputs").asList().size()]), record.get("inputDescription").asString(), 
					record.get("outputDescription").asString());
		}
		
		session.close();
		return response;
	}

	@Override
	public Project[] findAll() throws Exception {
		// TODO Auto-generated method stub

		ArrayList<Project> projects=new ArrayList<Project>();
		Project container=null;
		
		Session session = driver.session();
		
		String statement="MATCH (p:Project) RETURN "
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
		StatementResult result=session.run(statement);
		
		while(result.hasNext()){
			Record record=result.next();
			container=new Project(new DateTime(record.get("creationTime").asLong()).toDate(), record.get("name").asString(), 
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
	public Project[] findByName(String name) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Project> projects=new ArrayList<Project>();
		Project container=null;
		
		Session session = driver.session();
		
		String statement="MATCH (p:Project) WHERE p.name={name} RETURN "
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
				"name",name));
		
		while(result.hasNext()){
			Record record=result.next();
			container=new Project(new DateTime(record.get("creationTime").asLong()).toDate(), record.get("name").asString(), 
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
	public Project[] findByType(ProjectType type) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Project> projects=new ArrayList<Project>();
		Project container=null;
		
		Session session = driver.session();
		
		String statement="MATCH (p:Project) WHERE p.type={type} RETURN "
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
				"type",type.toString()));
		
		while(result.hasNext()){
			Record record=result.next();
			container=new Project(new DateTime(record.get("creationTime").asLong()).toDate(), record.get("name").asString(), 
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

}
