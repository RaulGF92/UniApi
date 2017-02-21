package es.uniapi.modules.business.dao.neo4j.relationship;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.intf.relations.KnowsDAO;
import es.uniapi.modules.business.dao.neo4j.relationship.model.Knows;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Group.GroupType;
import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.UserLogin;

import java.util.ArrayList;
import java.util.Date;
import org.joda.time.DateTime;

import static org.neo4j.driver.v1.Values.parameters;

public class KnowsDAOImpl implements KnowsDAO {

	Driver driver;
	public KnowsDAOImpl(){
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	
	@Override
	public void create(UserLogin user, Group group) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="MATCH (u:UserLogin),(g:Group) WHERE u.user={email} "
				+ "AND g.hashcode={hashcode2} "
				+ "CREATE (u)-[k:KNOWS {dateFrom:{date}}]->(g)";
		
		StatementResult result=session.run(statement,parameters(
				"email",user.getUser(),
				"hashcode2",group.hash(),
				"date",new Date().getTime()));
		session.close();
	}

	@Override
	public Group[] getAllGroupsKnowsByUser(UserLogin user) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Group> response=new ArrayList<Group>();
		Group aux=null;
		Session session=driver.session();
		String statement="MATCH (u:UserLogin {user:{email}}), "
				+ "h=(u)-[k:KNOWS]->(g:Group) RETURN "
				+ "g.name AS name,"
				+ "g.creationDate AS creationDate,"
				+ "g.type AS type,"
				+ "g.hashcode AS hashcode,"
				+ "g.sharingGroupPermissions AS sharingGroupPermissions,"
				+ "g.projectPropertiesPermissions AS projectPropertiesPermissions,"
				+ "g.memberGestionPermissions AS memberGestionPermissions,"
				+ "g.groupCreationPermissions AS groupCreationPermissions,"
				+ "g.description AS description";
		
		StatementResult result=session.run(statement,parameters(
				"email",user.getUser()
				));
		
		while(result.hasNext()){
			Record record=result.next();
			aux=new Group(record.get("name").asString(),
					new DateTime(record.get("creationDate").asLong()).toDate(),
					GroupType.valueOf(record.get("type").asString()),
					record.get("sharingGroupPermissions").asList().toArray(new String[record.get("sharingGroupPermissions").asList().size()]),
					record.get("projectPropertiesPermissions").asList().toArray(new String[record.get("projectPropertiesPermissions").asList().size()]),
					record.get("memberGestionPermissions").asList().toArray(new String[record.get("memberGestionPermissions").asList().size()]),
					record.get("groupCreationPermissions").asList().toArray(new String[record.get("groupCreationPermissions").asList().size()]),
					record.get("description").asString());
			response.add(aux);
		}
		
		session.close();
		return response.toArray(new Group[response.size()]);
	}

	@Override
	public Group[] getAllTypeGroupsKnowsByUser(UserLogin user, GroupType type) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Group> response=new ArrayList<Group>();
		Group aux=null;
		Session session=driver.session();
		String statement="MATCH (u:UserLogin {user:{email}}), "
				+ "h=(u)-[k:KNOWS]->(g:Group {type:{type}}) RETURN "
				+ "g.name AS name,"
				+ "g.creationDate AS creationDate,"
				+ "g.type AS type,"
				+ "g.hashcode AS hashcode,"
				+ "g.sharingGroupPermissions AS sharingGroupPermissions,"
				+ "g.projectPropertiesPermissions AS projectPropertiesPermissions,"
				+ "g.memberGestionPermissions AS memberGestionPermissions,"
				+ "g.groupCreationPermissions AS groupCreationPermissions,"
				+ "g.description AS description";
		
		StatementResult result=session.run(statement,parameters(
				"email",user.getUser(),
				"type",type.toString()
				));
		
		while(result.hasNext()){
			Record record=result.next();
			aux=new Group(record.get("name").asString(),
					new DateTime(record.get("creationDate").asLong()).toDate(),
					GroupType.valueOf(record.get("type").asString()),
					record.get("sharingGroupPermissions").asList().toArray(new String[record.get("sharingGroupPermissions").asList().size()]),
					record.get("projectPropertiesPermissions").asList().toArray(new String[record.get("projectPropertiesPermissions").asList().size()]),
					record.get("memberGestionPermissions").asList().toArray(new String[record.get("memberGestionPermissions").asList().size()]),
					record.get("groupCreationPermissions").asList().toArray(new String[record.get("groupCreationPermissions").asList().size()]),
					record.get("description").asString());
			response.add(aux);
		}
		
		session.close();
		return response.toArray(new Group[response.size()]);
	}

	@Override
	public Knows getInfoGroupKnowByUser(UserLogin user, Group group) throws Exception {
		// TODO Auto-generated method stub
		Knows response=null;
		Session session=driver.session();
		
		String statement="MATCH (u:UserLogin {user:{hashcode1}}), "
				+ "h=(u)-[k:KNOWS]->(g:Group {hashcode:{hashcode2}}) RETURN "
				+ "u.hashcode AS userLoginHash ,"
				+ "k.dateFrom as dateFrom,"
				+ "g.user AS groupHash";
		StatementResult result=session.run(statement,parameters(
				"hashcode1",user.getUser(),
				"hashcode2",group.hash()
				));
		while(result.hasNext()){
			Record record=result.next();
			response=new Knows(record.get("userLoginHash").asString(), 
					record.get("groupHash").asString(), 
					new DateTime(record.get("dateFrom").asLong()).toDate());
			
		}
		
		session.close();
		return response;
	}

	@Override
	public UserLogin[] getAllUserKnowsGroup(Group group) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<UserLogin> response=new ArrayList<UserLogin>();
		UserLogin aux=null;
		Session session=driver.session();
		String statement="MATCH (a:UserLogin), "
				+ "h=(a)-[k:KNOWS]->(g:Group {hashcode:{hash}}) RETURN "
				+ "a.user AS user,"
				+ "a.pass AS pass,"
				+ "a.creationTime AS creationTime,"
				+ "a.rol AS rol";
		StatementResult result=session.run(statement,parameters(
				"hash",group.hash()
				));
		
		while(result.hasNext()){
			Record record = result.next();
			aux=new UserLogin(record.get("user").asString(),
					record.get("pass").asString(), 
					new DateTime(record.get("creationTime").asLong()).toDate(),
					record.get("rol").asString());
			
			response.add(aux);
		}
		
		session.close();
		return response.toArray(new UserLogin[response.size()]);
	}

	@Override
	public void deleteUserKnowsGroup(UserLogin user, Group group) throws Exception {
		// TODO Auto-generated method stub
		Session session=driver.session();
		String statement="MATCH(u:UserLogin {user:{hashcode1}}) "
				+ ",o=(u)-[k:KNOWS]->(g:Group {hashcode:{hashcode2}}) "
				+ "DELETE k";
		StatementResult result=session.run(statement,parameters(
				"hashcode1",user.getUser(),
				"hashcode2",group.hash()));
		session.close();
	}

}
