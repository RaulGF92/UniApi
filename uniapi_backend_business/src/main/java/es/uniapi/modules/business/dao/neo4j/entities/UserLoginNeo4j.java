package es.uniapi.modules.business.dao.neo4j.entities;

import es.uniapi.modules.business.dao.intf.UserLoginDAO;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.config.AppConfiguration;

import org.joda.time.DateTime;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.Date;


public class UserLoginNeo4j implements UserLoginDAO {

	Driver driver;
	
	public UserLoginNeo4j() {
		// TODO Auto-generated constructor stub
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	
	@Override
	public void create(UserLogin userLogin) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="CREATE (a:UserLogin {user:{user},pass:{pass},creationTime:{creationTime}})";
		
		StatementResult result=session.run(statement
				,parameters("user",userLogin.getUser()
						,"pass",userLogin.getPass()
						,"creationTime",new Date().getTime()));
		session.close();
	}

	@Override
	public void delete(UserLogin userLogin) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		
		String statement="MATCH (a:UserLogin) WHERE a.user = {user} AND a.pass = {pass} AND a.creationTime = {creationTime} DELETE a";
		StatementResult result=session.run(statement,parameters("user",userLogin.getUser(),"pass",userLogin.getPass(),"creationTime",userLogin.getCreationDate().getTime()));
	}

	@Override
	public UserLogin findByEmail(String Email) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		UserLogin userLogin=null;
		
		String statement="MATCH (a:UserLogin) WHERE a.user = {user} RETURN a.id AS id, a.user AS user, a.pass AS pass,a.creationTime AS creationTime";
		StatementResult result=session.run(statement,parameters("user",Email));
		
		while(result.hasNext()){
			Record record = result.next();
			userLogin=new UserLogin(record.get("user").asString(),
					record.get("pass").asString(), 
					new DateTime(record.get("creationTime").asLong()).toDate(), 
					0);
		}
		
		return userLogin;
	}

	@Deprecated
	@Override
	public UserLogin findByID(long id) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();

		UserLogin userLogin=null;
		
		String statement="MATCH (a:UserLogin) WHERE a.id = {id} RETURN a.id AS id, a.user AS user, a.pass AS pass,a.creationTime AS creationTime";
		StatementResult result=session.run(statement,parameters("id",id));
		
		while(result.hasNext()){
			Record record = result.next();
			userLogin=new UserLogin(record.get("user").asString(),
					record.get("pass").asString(), 
					new DateTime(record.get("creationTime").asLong()).toDate(), 
					record.get("id").asLong());
		}
		
		return userLogin;
	}

	@Override
	public UserLogin[] findAll() throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		ArrayList<UserLogin> userLogins=new ArrayList<UserLogin>();
		UserLogin userLogin=null;
		
		String statement="MATCH (a:UserLogin) RETURN a.id AS id, a.user AS user, a.pass AS pass,a.creationTime AS creationTime";
		StatementResult result=session.run(statement);
		
		while(result.hasNext()){
			Record record = result.next();
			userLogin=new UserLogin(record.get("user").asString(),
					record.get("pass").asString(), 
					new DateTime(record.get("creationTime").asLong()).toDate(), 
					0);
			userLogins.add(userLogin);
		}
		
		return userLogins.toArray(new UserLogin[userLogins.size()]);
	}

}
