package es.uniapi.modules.business.dao.neo4j.relationship;

import org.joda.time.DateTime;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.intf.relations.GenerateDAO;
import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.Execution.ExecutionState;
import es.uniapi.modules.model.config.AppConfiguration;
import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.Date;

public class GenerateDAOImpl implements GenerateDAO{

	Driver driver;
	public GenerateDAOImpl(){
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	@Override
	public void create(UserLogin userLogin, Execution execution) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="MATCH (u:UserLogin),(p:Execution) WHERE u.user={hashcode1} "
				+ "AND p.hashcode={hashcode2} CREATE (u)-[g:GENERATE {dateFrom:{date}}]->(p)";
		
		StatementResult result=session.run(statement,parameters(
				"hashcode1",userLogin.getUser(),
				"hashcode2",execution.hash(),
				"date",new Date().getTime()));
		session.close();		
	}
	@Override
	public Execution[] getUserExecution(UserLogin userLogin) throws Exception {
		// TODO Auto-generated method stub
		Session session= driver.session();
		ArrayList<Execution> response=new ArrayList<Execution>();
		Execution execution=null;
		
		String statement="MATCH (u:UserLogin {user:{email}}), "
				+ "h=(u)-[r:GENERATE]->(e:Execution) RETURN "
				+ "e.stateOfExecution AS stateOfExecution,"
				+ "e.nameExecution AS nameExecution,"
				+ "e.groupOfExecution AS groupOfExecution,"
				+ "e.inputJson AS inputJson,"
				+ "e.creationDate AS creationDate,"
				+ "e.finishDate AS finishDate,"
				+ "e.response AS response,"
				+ "e.console AS console";
		
		StatementResult result=session.run(statement,parameters(
				"email",userLogin.getUser()));
		
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
	public UserLogin getGeneratorOfExecution(Execution execution) throws Exception {
		// TODO Auto-generated method stub
		Session session= driver.session();
		UserLogin userLogin=null;
		
		String statement="MATCH (u:UserLogin), "
				+ "h=(u)-[r:GENERATE]->(e:Execution {hashcode:{hashcode}}) RETURN "
				+ "u.user AS user,"
				+ "u.pass AS pass,"
				+ "u.creationTime AS creationTime,"
				+ "u.rol AS rol";
		
		StatementResult result=session.run(statement,parameters(
				"hashcode",execution.hash()));
		
		while(result.hasNext()){
			Record record = result.next();
			userLogin=new UserLogin(record.get("user").asString(),
					record.get("pass").asString(), 
					new DateTime(record.get("creationTime").asLong()).toDate(),
					record.get("rol").asString());
		}
		
		session.close();
		return userLogin;
	}
}
