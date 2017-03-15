package es.uniapi.modules.business.dao.neo4j.relationship;

import org.joda.time.DateTime;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.intf.relations.UseDAO;
import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Execution.ExecutionState;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.config.AppConfiguration;
import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.Date;

public class UseDAOImpl implements UseDAO {

	Driver driver;
	public UseDAOImpl(){
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	@Override
	public void create(Execution execution, Project project) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="MATCH (e:Execution),(p:Project) WHERE e.hashcode={hashcode1} "
				+ "AND p.hashcode={hashcode2} CREATE (e)-[u:USE {dateFrom:{date}}]->(p)";
		
		StatementResult result=session.run(statement,parameters(
				"hashcode1",execution.hash(),
				"hashcode2",project.hash(),
				"date",new Date().getTime()));
		session.close();		
	}
	@Override
	public Project getProjectUseForExecution(Execution execution) throws Exception {
		// TODO Auto-generated method stub
		Session session= driver.session();
		Project project=null;
		
		String statement="MATCH (e:Execution {hashcode:{hashcode}}), "
				+ "h=(u)-[r:USE]->(p:Project) RETURN "
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
				"hashcode",execution.hash()
				));
		
		while(result.hasNext()){
			Record record=result.next();
			project = new Project(new DateTime(record.get("creationTime").asLong()).toDate(), record.get("name").asString(), 
					ProjectType.valueOf(record.get("type").asString()), record.get("description").asString(), 
					record.get("gitRepositoryURL").asString(), record.get("email").asString(), 
					record.get("password").asString(), new DateTime(record.get("modifyDate").asLong()).toDate(), 
					record.get("mainName").asString(), record.get("responseName").asString(), 
					record.get("defaultInputs").asList().toArray(new String[record.get("defaultInputs").asList().size()]), record.get("inputDescription").asString(), 
					record.get("outputDescription").asString());
		}
		session.close();
		return project;
	}
	@Override
	public Execution[] getExecutionsUsingProject(Project project) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		ArrayList<Execution> response=new ArrayList<Execution>();
		Execution execution=null;
		
		String statement="MATCH (e:Execution), "
				+ "h=(u)-[r:USE]->(p:Project {hashcode:{hashcode}}) RETURN "
				+ "e.stateOfExecution AS stateOfExecution,"
				+ "e.inputJson AS inputJson,"
				+ "e.creationDate AS creationDate,"
				+ "e.finishDate AS finishDate,"
				+ "e.response AS response,"
				+ "e.console AS console";
		
		StatementResult result=session.run(statement,parameters(
				"hashcode",project.hash()));
		
		while(result.hasNext()){
			Record record = result.next();
			execution=new Execution(
					ExecutionState.valueOf(record.get("stateOfExecution").asString()), 
					record.get("inputJson").asString(), 
					new DateTime(record.get("creationDate").asLong()).toDate(), 
					new DateTime(record.get("finishDate").asLong()).toDate(), 
					record.get("response").asString(), 
					record.get("console").asString());
			response.add(execution);
		}
		
		session.close();
		return response.toArray(new Execution[response.size()]);
	}
}