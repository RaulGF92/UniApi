package es.uniapi.modules.business.dao.neo4j.relationship;

import java.util.ArrayList;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.dao.neo4j.entities.PersonNeo4j;
import es.uniapi.modules.business.dao.neo4j.relationship.model.MakeReference;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.config.AppConfiguration;

import static org.neo4j.driver.v1.Values.parameters;

public class MakeReferenceDAOImpl {

	Driver driver;
	
	public MakeReferenceDAOImpl() {
		// TODO Auto-generated constructor stub
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	
	public MakeReference getByUserLogin(UserLogin user){
		
		MakeReference makeReference=null;
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		Session session = driver.session();
		String statement="MATCH (u:UserLogin {user:{email}}),"
				+ "h=(u)-[r:MAKE_REFERENCE]->(p) RETURN u.hashcode AS user,p.hashcode AS person";
		StatementResult result=session.run(statement,parameters(
				"email",user.getUser()));
		
		
		while(result.hasNext()){
			Record record=result.next();
			try {
				makeReference= new MakeReference(
						dao.getUniApiDao().getPersonDAO().findByHashCode(record.get("person").asString()), 
						dao.getUniApiDao().getUserLoginDAO().findByHashCode(record.get("user").asString())
						);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				makeReference=new MakeReference(null,null);
			}
		}
		session.close();
		return makeReference;
	}
	
	public void create(UserLogin user,Person person){
		Session session = driver.session();
		String statement="MATCH (u:UserLogin),(p:Person) WHERE u.user={email} "
				+ "AND p.hashcode={hashcode2} CREATE (u)-[:MAKE_REFERENCE]->(p)";
		
		StatementResult result=session.run(statement,parameters(
				"email",user.getUser(),
				"hashcode2",person.hash()));
		
		session.close();
	}

	public MakeReference[] getAll(){
		ArrayList<MakeReference> makeReferences=new ArrayList<MakeReference>();
		
		MakeReference makeReference=null;
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		Session session = driver.session();
		String statement="MATCH (u:UserLogin),"
				+ "h=(u)-[r:MAKE_REFERENCE]->(p) RETURN u.hashcode AS user,p.hashcode AS person";
		
		StatementResult result=session.run(statement);
		
		while(result.hasNext()){
			Record record=result.next();
			try {
				makeReference= new MakeReference(
						dao.getUniApiDao().getPersonDAO().findByHashCode(record.get("person").asString()), 
						dao.getUniApiDao().getUserLoginDAO().findByHashCode(record.get("user").asString())
						);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				makeReference=new MakeReference(null,null);
			}
			makeReferences.add(makeReference);
		}
		session.close();
		
		return makeReferences.toArray(new MakeReference[makeReferences.size()]);
	}

}
