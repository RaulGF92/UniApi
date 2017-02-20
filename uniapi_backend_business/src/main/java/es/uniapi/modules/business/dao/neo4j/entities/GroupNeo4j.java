package es.uniapi.modules.business.dao.neo4j.entities;

import org.joda.time.DateTime;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.intf.entities.GroupDAO;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Group.GroupType;
import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.Project;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.Date;

public class GroupNeo4j implements GroupDAO {

	Driver driver;
	
	public GroupNeo4j() {
		// TODO Auto-generated constructor stub
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	
	@Override
	public void create(Group group) throws Exception {
		// TODO Auto-generated method stub
		Session session=driver.session();
		String statement="CREATE(g:Group{"
				+ "name:{name},"
				+ "creationDate:{creationDate},"
				+ "type:{type},"
				+ "hashcode:{hash},"
				+ "sharingGroupPermissions:{sharingGroupPermissions},"
				+ "projectPropertiesPermissions:{projectPropertiesPermissions},"
				+ "memberGestionPermissions:{memberGestionPermissions},"
				+ "groupCreationPermissions:{groupCreationPermissions},"
				+ "description:{description}})";
		
		StatementResult result=session.run(statement,parameters(
				"name",group.getName(),
				"creationDate",group.getCreationDate().getTime(),
				"type",group.getType().toString(),
				"hash",group.hash(),
				"sharingGroupPermissions",group.getSharingGroup(),
				"projectPropertiesPermissions",group.getProjectProperties(),
				"memberGestionPermissions",group.getMemberGestion(),
				"groupCreationPermissions",group.getGroupCreation(),
				"description",group.getDescription()
				));
		
		session.close();
	}

	@Override
	public void delete(Group group) throws Exception {
		// TODO Auto-generated method stub
		Session session=driver.session();
		String statement="MATCH(g:Group {"
				+ "hashcode:{hash}"
				+ "}) DELETE g";
		StatementResult result=session.run(statement,parameters(
				"hash",group.hash()
				));		
		session.close();		
	}

	@Override
	public Group findByHashCode(String hash) throws Exception {
		// TODO Auto-generated method stub
		Session session=driver.session();
		Group response=null;
		String statement="MATCH(g:Group {hashcode:{hash}}) RETURN "
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
				"hash",hash
				));
		while(result.hasNext()){
			Record record=result.next();
			response=new Group(record.get("name").asString(),
					new DateTime(record.get("creationDate").asLong()).toDate(),
					GroupType.valueOf(record.get("type").asString()),
					record.get("sharingGroupPermissions").asList().toArray(new String[record.get("sharingGroupPermissions").asList().size()]),
					record.get("projectPropertiesPermissions").asList().toArray(new String[record.get("projectPropertiesPermissions").asList().size()]),
					record.get("memberGestionPermissions").asList().toArray(new String[record.get("memberGestionPermissions").asList().size()]),
					record.get("groupCreationPermissions").asList().toArray(new String[record.get("groupCreationPermissions").asList().size()]),
					record.get("description").asString());
		}
		session.close();
		return response;
	}

	@Override
	public Group[] findAll() throws Exception {
		// TODO Auto-generated method stub
		Session session=driver.session();
		ArrayList<Group> response=new ArrayList<Group>();
		Group aux=null;
		String statement="MATCH(g:Group) RETURN "
				+ "g.name AS name,"
				+ "g.creationDate AS creationDate,"
				+ "g.type AS type,"
				+ "g.hashcode AS hashcode,"
				+ "g.sharingGroupPermissions AS sharingGroupPermissions,"
				+ "g.projectPropertiesPermissions AS projectPropertiesPermissions,"
				+ "g.memberGestionPermissions AS memberGestionPermissions,"
				+ "g.groupCreationPermissions AS groupCreationPermissions,"
				+ "g.description AS description";
		
		StatementResult result=session.run(statement);
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
	public Group[] findByType(GroupType type) throws Exception {
		// TODO Auto-generated method stub
		Session session=driver.session();
		ArrayList<Group> response=new ArrayList<Group>();
		Group aux=null;
		String statement="MATCH(g:Group {type:{type}}) RETURN "
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
	public void update(String hash, Group group) {
		// TODO Auto-generated method stub
		Session session=driver.session();
		String statement="MATCH(g:Group) WHERE g.hashcode={hash} SET "
				+ "g.type={type},"
				+ "g.sharingGroupPermissions={sharingGroupPermissions},"
				+ "g.projectPropertiesPermissions={projectPropertiesPermissions},"
				+ "g.memberGestionPermissions={memberGestionPermissions},"
				+ "g.groupCreationPermissions={groupCreationPermissions},"
				+ "g.description={description}";
		
		StatementResult result=session.run(statement,parameters(
				"hash",hash,
				"type",group.getType().toString(),
				"sharingGroupPermissions",group.getSharingGroup(),
				"projectPropertiesPermissions",group.getProjectProperties(),
				"memberGestionPermissions",group.getMemberGestion(),
				"groupCreationPermissions",group.getGroupCreation(),
				"description",group.getDescription()));	
		
		session.close();
	}

}
