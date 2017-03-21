package es.uniapi.modules.business.dao.neo4j.entities;

import org.joda.time.DateTime;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.intf.entities.ExecutionDAO;
import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Execution.ExecutionState;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.config.AppConfiguration;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.Date;

public class ExecutioNeo4j implements ExecutionDAO {

	Driver driver;
	
	public ExecutioNeo4j() {
		// CREATE CONSTRAINT ON (u:userLogin) ASSERT u.email IS UNIQUE
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}

	@Override
	public void create(Execution execution) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="CREATE (e:Execution "
				+ "{nameExecution:{nameExecution},"
				+ "groupOfExecution:{groupOfExecution},"
				+ "stateOfExecution:{stateOfExecution},"
				+ "inputJson:{inputJson},"
				+ "creationDate:{creationDate},"
				+ "finishDate:{finishDate},"
				+ "response:{response},"
				+ "console:{console},"
				+ "hashcode:{hashcode}})";
		
		StatementResult result=session.run(statement
				,parameters("stateOfExecution",execution.getStateOfExecution().toString(),
						"nameExecution",execution.getNameExecution(),
						"groupOfExecution",execution.getGroupOfExecution()
						,"inputJson",execution.getInputJson()
						,"creationDate",new Date().getTime(),
						"finishDate",(long)0,
						"response",execution.getResponse(),
						"console",execution.getConsole(),
						"hashcode",execution.hash()));
		session.close();
		
	}

	@Override
	public void update(String hash, Execution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(hash);
		Session session = driver.session();
		String statement="MATCH (e:Execution {hashcode:{hash}}) "
				+ "SET e.nameExecution={nameExecution},"
				+ "e.groupOfExecution={groupOfExecution},"
				+ "e.stateOfExecution={stateOfExecution},"
				+ "e.inputJson={inputJson},"
				+ "e.creationDate={creationDate},"
				+ "e.finishDate={finishDate},"
				+ "e.response={response},"
				+ "e.console={console},"
				+ "e.hashcode={hash}";
		
		StatementResult result=session.run(statement
				,parameters(
						"hash",hash,
						"stateOfExecution",execution.getStateOfExecution().toString(),
						"nameExecution",execution.getNameExecution(),
						"groupOfExecution",execution.getGroupOfExecution()
						,"inputJson",execution.getInputJson()
						,"creationDate",execution.getCreationDate().getTime(),
						"finishDate",execution.getFinishDate().getTime(),
						"response",execution.getResponse(),
						"console",execution.getConsole(),
						"hashcode",execution.hash()));
		session.close();
		System.out.println("jfofd");
	}

	@Override
	public Execution findByHash(String hash) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		Execution execution=null;
		
		String statement="MATCH (e:Execution {hashcode:{hash}} ) RETURN  "
				+ "e.stateOfExecution AS stateOfExecution,"
				+ "e.nameExecution AS nameExecution,"
				+ "e.groupOfExecution AS groupOfExecution,"
				+ "e.inputJson AS inputJson,"
				+ "e.creationDate AS creationDate,"
				+ "e.finishDate AS finishDate,"
				+ "e.response AS response,"
				+ "e.console AS console";
		StatementResult result=session.run(statement,parameters(
				"hash",hash));
		
		while(result.hasNext()){
			Record record = result.next();
			execution=new Execution(
					record.get("nameExecution").asString(),
					record.get("groupOfExecution").asString(),
					ExecutionState.valueOf(record.get("stateOfExecution").asString()),
					record.get("inputJson").asString(), 
					new DateTime(record.get("creationDate").asLong()).toDate(), 
					new DateTime(record.get("finishDate").asLong()).toDate(), 
					record.get("response").asString(), 
					record.get("console").asString());
		}
		
		session.close();
		return execution;
	}

	@Override
	public Execution[] findAllExecution() throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		ArrayList<Execution> response=new ArrayList<Execution>();
		Execution execution=null;
		
		String statement="MATCH (e:Execution) RETURN  "
				+ "e.stateOfExecution AS stateOfExecution,"
				+ "e.nameExecution AS nameExecution,"
				+ "e.groupOfExecution AS groupOfExecution,"
				+ "e.inputJson AS inputJson,"
				+ "e.creationDate AS creationDate,"
				+ "e.finishDate AS finishDate,"
				+ "e.response AS response,"
				+ "e.console AS console";
		StatementResult result=session.run(statement);
		
		while(result.hasNext()){
			Record record = result.next();
			execution=new Execution(
					record.get("nameExecution").asString(),
					record.get("groupOfExecution").asString(),
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

	@Override
	public Execution[] findAllExecutionByState(ExecutionState state) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		ArrayList<Execution> response=new ArrayList<Execution>();
		Execution execution=null;
		
		String statement="MATCH (e:Execution) "
				+ "WHERE e.stateOfExecution={stateOfExecution1} RETURN  "
				+ "e.stateOfExecution AS stateOfExecution,"
				+ "e.nameExecution AS nameExecution,"
				+ "e.groupOfExecution AS groupOfExecution,"
				+ "e.inputJson AS inputJson,"
				+ "e.creationDate AS creationDate,"
				+ "e.finishDate AS finishDate,"
				+ "e.response AS response,"
				+ "e.console AS console";
		StatementResult result=session.run(statement,parameters(
				"stateOfExecution1",state.toString()));
		
		while(result.hasNext()){
			Record record = result.next();
			execution=new Execution(
					record.get("nameExecution").asString(),
					record.get("groupOfExecution").asString(),
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
	
	
	