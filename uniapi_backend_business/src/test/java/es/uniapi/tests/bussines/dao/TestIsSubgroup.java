package es.uniapi.tests.bussines.dao;

import es.uniapi.modules.business.dao.intf.entities.GroupDAO;
import es.uniapi.modules.business.dao.neo4j.entities.GroupNeo4j;
import es.uniapi.modules.business.dao.neo4j.relationship.IsSubgroupDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.model.IsSubGroup;
import es.uniapi.modules.model.Group;

public class TestIsSubgroup {

	public static void main(String[] args){
		
		String publicHash="9a6e2acfcbc1a5464754cdf847c4122ed9f65153";
		String fisicaHash="16acd9ff2d2f35b977ceb5641afc131d88ba5c1f";
		String guayHash="fd115ab7decbb3eb6c999ad40142cc891ba3c2a3";
		
		String groupPrivateHash="125f2d5ba69fceb75331c8de8a3204a52a6d110f";
		String groupPublicHash="ce815234c8be37eb5fc3dc4b055ad4cfb945821d";
		
		Group groupPrivate=null;
		Group groupPublic=null;
		Group publico=null;
		Group fisica=null;
		Group guay=null;
		
		GroupDAO groupdao=new GroupNeo4j();
		IsSubgroupDAOImpl subgroupDAO=new IsSubgroupDAOImpl(); 
		
		try {
			groupPrivate=groupdao.findByHashCode(groupPrivateHash);
			groupPublic=groupdao.findByHashCode(groupPublicHash);
			publico=groupdao.findByHashCode(publicHash);
			fisica=groupdao.findByHashCode(fisicaHash);
			guay=groupdao.findByHashCode(guayHash);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//subgroupDAO.create(publico, fisica);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//subgroupDAO.create(fisica, guay);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//subgroupDAO.create(guay, groupPublic);
			//
			subgroupDAO.create(guay, groupPrivate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Group[] groups=subgroupDAO.getAllSubgroupOfGroup(fisica);
			for(int i=0;i<groups.length;i++){
				System.out.println(groups[i].toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("----------------------------------------");
			Group[] groups=subgroupDAO.getAllSubgroupsOrderN(publico);
			for(int i=0;i<groups.length;i++){
				System.out.println(groups[i].toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			System.out.println("----------------------------------------");
			System.out.println(subgroupDAO.getInfo(guay, groupPublic).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			//subgroupDAO.delete(guay, groupPublic);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
