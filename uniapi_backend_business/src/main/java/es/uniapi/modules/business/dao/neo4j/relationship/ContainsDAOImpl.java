package es.uniapi.modules.business.dao.neo4j.relationship;

import org.joda.time.DateTime;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.intf.relations.ContainsDAO;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.config.AppConfiguration;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.Date;

public class ContainsDAOImpl implements ContainsDAO{

	Driver driver;
	public ContainsDAOImpl(){
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	
	@Override
	public void create(Group group, Project project) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="MATCH (g:Group),(p:Project) WHERE g.hashcode={hashcode1} "
				+ "AND p.hashcode={hashcode2} "
				+ "CREATE (g)-[c:CONTAINS {dateFrom:{date}}]->(p)";
		
		StatementResult result=session.run(statement,parameters(
				"hashcode1",group.hash(),
				"hashcode2",project.hash(),
				"date",new Date().getTime()));
		
		session.close();
	}

	@Override
	public void delete(Group group, Project project) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="MATCH (g:Group {hashcode:{hashcode1}}), "
				+ "h=(g)-[c:CONTAINS]->(p:Project {hashcode:{hashcode2}}) DELETE c";
		StatementResult result=session.run(statement,parameters(
				"hashcode1",group.hash(),
				"hashcode2",project.hash()
				));
		session.close();		
	}

	@Override
	public Project[] findAllProjectsContainsInGroup(Group group) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Project> projects=new ArrayList<Project>();
		Project aux;
		Session session = driver.session();
		
		String statement="MATCH (g:Group {hashcode:{hashcode1}}), "
				+ "h=(g)-[c:CONTAINS]->(p:Project) RETURN "
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
				"hashcode1",group.hash()
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

}
