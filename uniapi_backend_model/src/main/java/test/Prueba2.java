package test;

import java.util.Date;

import org.joda.time.DateTime;

import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;

public class Prueba2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long time=Long.parseLong("1485802725552");
		Person person=new Person("Raúl", 
				"Garcia", new DateTime(time).toDate(), "España", 
				"Asturias", "Candás", "pues Aquí tamos", 
				"http://farm4.static.flickr.com/3628/3430561773_9b6087d1e1.jpg");
		System.out.println(person);
		System.out.println("-------------------------------------");
		System.out.println(person.hashCode());
		
		System.out.flush();
		time=Long.parseLong("1485196179093");
		UserLogin user=new UserLogin("raulgf92@gmail.com", "953ee43b7a144d96aeba60f5a4e4f7998286890f", new DateTime(time).toDate(),"admin");
		time=Long.parseLong("1485196257684");
		UserLogin user2=new UserLogin("ludyff@gmail.com", "06ff8af800fe96721124eb7cf8e7d2218a01d75b", new DateTime(time).toDate(),"admin");
		System.out.println(user);
		System.out.println("-------------------------------------");
		System.out.println(user.hashCode());
		System.out.println("                                      ");
		System.out.println(user2);
		System.out.println("-------------------------------------");
		System.out.println(user2.hashCode());
	}

}
